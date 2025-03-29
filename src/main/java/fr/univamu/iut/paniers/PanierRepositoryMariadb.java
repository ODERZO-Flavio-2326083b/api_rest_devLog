package fr.univamu.iut.paniers;

import java.io.Closeable;
import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;

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
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
