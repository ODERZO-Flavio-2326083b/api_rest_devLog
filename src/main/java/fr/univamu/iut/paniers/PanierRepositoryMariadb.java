package fr.univamu.iut.paniers;

import java.io.Closeable;
import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class PanierRepositoryMariadb implements Closeable, PanierRepositoryInterface {

    /**
     * Accès à la base de données
     */
    protected Connection dbConnection;

    public PanierRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public Panier getPanier(int id_panier) {
        Panier selectedPanier = null;

        String query = "SELECT * FROM Panier WHERE id_panier = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, Integer.toString(id_panier));

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                float prix = rs.getFloat("prix");
                int qtt_panier_dispo = rs.getInt("qtt_panier_dispo");
                Date derniere_maj = rs.getDate("derniere_maj");

                selectedPanier = new Panier(id_panier, prix, qtt_panier_dispo, derniere_maj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedPanier;
    }

    /**
     * À partir d'un id de panier, renvoie la liste des ids produits qui le compose
     * @param id_panier id du panier
     * @return Liste des ids produits
     */
    public List<Integer> getProduitIdsOfPanier(int id_panier) {
        List<Integer> produitIds = new ArrayList<>();
        String query = "SELECT id_produit FROM ComposePanier WHERE id_panier = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_panier);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                produitIds.add(rs.getInt("id_produit"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return produitIds;
    }

    @Override
    public ArrayList<Panier> getAllPaniers() {
        ArrayList<Panier> paniers;

        String query = "SELECT * FROM Panier";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            paniers = new ArrayList<>();

            while(rs.next()) {
                float prix = rs.getFloat("prix");
                int qtt_panier_dispo = rs.getInt("qtt_panier_dispo");
                Date derniere_maj = rs.getDate("derniere_maj");
                int id_panier = rs.getInt("id_panier");

                paniers.add(new Panier(id_panier, prix, qtt_panier_dispo, derniere_maj));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paniers;
    }

    @Override
    public boolean updatePanier(int id_panier, float prix, int qtt_panier_dispo) {
        String query = "UPDATE Panier SET prix=?, qtt_panier_dispo=?, derniere_maj=? WHERE id_panier=?";

        int nbRowModified = 0;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        try ( PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(prix));
            ps.setString(2, String.valueOf(qtt_panier_dispo));
            ps.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            ps.setString(4, String.valueOf(id_panier));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    @Override
    public boolean deleteProduitFromPanier(int id_panier, int id_produit) {
        String query = "DELETE FROM ComposePanier WHERE id_panier = ? AND id_produit = ?";
        int rowsAffected;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_panier);
            ps.setInt(2, id_produit);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0);
    }

    @Override
    public boolean updateProduitOfPanier(int id_panier, int id_produit, int quantite) {
        String checkQuery = "SELECT quantite FROM ComposePanier WHERE id_panier = ? AND id_produit = ?";
        String updateQuery = "UPDATE ComposePanier SET quantite = quantite + ? WHERE id_panier = ? AND id_produit = ?";
        String insertQuery = "INSERT INTO ComposePanier (id_panier, id_produit, quantite) VALUES (?, ?, ?)";

        int rowsAffected;
        try (PreparedStatement checkStmt = dbConnection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id_panier);
            checkStmt.setInt(2, id_produit);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = dbConnection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, quantite);
                    updateStmt.setInt(2, id_panier);
                    updateStmt.setInt(3, id_produit);
                    rowsAffected = updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = dbConnection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, id_panier);
                    insertStmt.setInt(2, id_produit);
                    insertStmt.setInt(3, quantite);
                    rowsAffected = insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0);
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
