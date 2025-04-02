package fr.univamu.iut.paniers;

import java.io.Closeable;
import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

/**
 * Implémentation de l'interface de PanierRepository pour MariaDB
 */
public class PanierRepositoryMariadb implements Closeable, PanierRepositoryInterface {

    /**
     * Accès à la base de données
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection chaine de caractères avec les informations de connexion
     * @param user chaine de caractères avec le nom d'utilisateur
     * @param pwd chaine de caractères avec le mot de passe
     */
    public PanierRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public boolean addPanier(float prix, int qtt_panier_dispo, List<Integer> id_produits) {
        String query = "INSERT INTO Panier (prix, qtt_panier_dispo, derniere_maj) VALUES (?, ?, ?)";

        int rowsAffected;
        int idPanier = 0;
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setFloat(1, prix);
            ps.setInt(2, qtt_panier_dispo);
            ps.setDate(3, Date.valueOf(java.time.LocalDate.now()));

            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPanier = rs.getInt(1);
                    }
                }
            }

            // liaison des produits au panier
            if (idPanier != 0) {
                for (Integer idProduit : id_produits) {
                    rowsAffected += addProduitToPanier(idPanier, idProduit, 1) ? 1 : 0;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idPanier != 0 && rowsAffected != 0;
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
        String query = "UPDATE Panier SET prix=?, qtt_panier_dispo=? WHERE id_panier=?";

        int nbRowModified = 0;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        try ( PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(prix));
            ps.setString(2, String.valueOf(qtt_panier_dispo));
            ps.setString(3, String.valueOf(id_panier));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return (nbRowModified != 0 && changeLastUpdate(id_panier)) ;
    }

    @Override
    public boolean deletePanier(int id_panier) {
        String query = "DELETE FROM ComposePanier WHERE id_panier=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(id_panier));

            nbRowModified += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "DELETE FROM Panier WHERE id_panier=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(id_panier));

            nbRowModified += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    @Override
    public boolean deleteProduitFromPanier(int id_panier, int id_produit) {
        String query = "DELETE FROM ComposePanier WHERE id_panier = ? AND id_produit = ?";
        int rowsAffected = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_panier);
            ps.setInt(2, id_produit);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0 && changeLastUpdate(id_panier)) ;
    }

    @Override
    public boolean addProduitToPanier(int id_panier, int id_produit, int quantite) {
        String query = "INSERT INTO ComposePanier (id_panier, id_produit, quantite) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantite = ?";

        int rowsAffected = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_panier);
            ps.setInt(2, id_produit);
            ps.setInt(3, quantite);
            ps.setInt(4, quantite);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0 && changeLastUpdate(id_panier));
    }

    @Override
    public boolean updateProduitOfPanier(int id_panier, int id_produit, int quantite) {
        String query = "UPDATE ComposePanier SET quantite = ? WHERE id_panier = ? AND id_produit = ?";

        int rowsAffected = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, quantite);
            ps.setInt(2, id_panier);
            ps.setInt(3, id_produit);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0 && changeLastUpdate(id_panier));
    }

    public boolean changeLastUpdate(int id_panier) {
        String query = "UPDATE Panier SET derniere_maj=? WHERE id_panier=?";

        int nbRowModified = 0;
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        try ( PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            ps.setString(2, String.valueOf(id_panier));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
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
