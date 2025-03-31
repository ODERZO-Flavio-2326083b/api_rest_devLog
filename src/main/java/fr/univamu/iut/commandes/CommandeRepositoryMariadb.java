package fr.univamu.iut.commandes;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accéder aux livres stockés dans une base de données Mariadb
 */
public class CommandeRepositoryMariadb implements CommandeRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_cooperative
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public CommandeRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException
    {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Commande getCommande(int id_commande) {
        Commande selectedCommande = null;

        String query = "SELECT * FROM Commande WHERE id_commande = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, Integer.toString(id_commande));

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                int id_utilisateur = result.getInt("id_utilisateur");
                Date date_retrait = result.getDate("date_retrait");

                selectedCommande = new Commande(id_commande, id_utilisateur, date_retrait);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedCommande;
    }

    @Override
    public ArrayList<Commande> getAllCommandes() {
        ArrayList<Commande> listCommandes;

        String query = "SELECT * FROM Commande";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listCommandes = new ArrayList<>();

            while (result.next()) {
                int id_commande = result.getInt("id_commande");
                int id_utilisateur = result.getInt("id_utilisateur");
                Date date_retrait = result.getDate("date_retrait");

                Commande currentCommande = new Commande(id_commande, id_utilisateur, date_retrait);

                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }

    @Override
    public boolean updateCommande(int id_commande, int id_utilisateur, java.sql.Date date_retrait) {
        String query = "UPDATE Commande SET id_utilisateur=?, date_retrait=? WHERE id_commande=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, Integer.toString(id_utilisateur));
            ps.setDate(2, date_retrait);
            ps.setString(3, Integer.toString(id_commande));

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }
}
