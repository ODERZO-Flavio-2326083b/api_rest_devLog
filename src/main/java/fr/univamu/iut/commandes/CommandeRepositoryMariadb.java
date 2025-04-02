package fr.univamu.iut.commandes;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public int addCommande(int id_utilisateur, Date date_retrait, List<Integer> id_paniers, String relai) {
        String query = "INSERT INTO Commande (id_utilisateur, date_retrait, relai) VALUES (?,?,?)";

        if (id_paniers.isEmpty()) {
            throw new IllegalArgumentException("id_panier vide");
        }

        int rowsAffected;
        int idCommande = 0;
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, id_utilisateur);
            ps.setDate(2, date_retrait);
            ps.setString(3, relai);

            rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next())
                        idCommande = rs.getInt(1);
                }
            }

            // liaison des paniers à la commande
            if (idCommande != 0) {
                for (Integer idPanier : id_paniers) {
                    rowsAffected += addPanierToCommande(idCommande, idPanier, 1) ? 1 : 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.err.println(idCommande);
        return idCommande;
    }

    private boolean addPanierToCommande(int id_commande, int id_panier, int quantite) {
        String query = "INSERT INTO ComposeCommande (id_commande, id_panier, quantite) VALUES (?,?,?)" +
                " ON DUPLICATE KEY UPDATE quantite = ?";

        int rowsAffected = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_commande);
            ps.setInt(2, id_panier);
            ps.setInt(3, quantite);
            ps.setInt(4, quantite);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rowsAffected != 0;
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
                String relai = result.getString("relai");

                selectedCommande = new Commande(id_commande, id_utilisateur, date_retrait, relai);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedCommande;
    }

    public List<Integer> getPanierIdsOfCommandes(int id_commande) {
        List<Integer> panierIds = new ArrayList<>();
        String query = "SELECT id_panier FROM ComposeCommande WHERE id_commande = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)){
            ps.setInt(1, id_commande);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                panierIds.add(rs.getInt("id_panier"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return panierIds;
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
                String relai = result.getString("relai");

                Commande currentCommande = new Commande(id_commande, id_utilisateur, date_retrait, relai);

                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }

    @Override
    public boolean updateCommande(int id_commande, int id_utilisateur, java.sql.Date date_retrait, String relai) {
        String query = "UPDATE Commande SET id_utilisateur=?, date_retrait=?, relai=? WHERE id_commande=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, Integer.toString(id_utilisateur));
            ps.setDate(2, date_retrait);
            ps.setString(3, Integer.toString(id_commande));
            ps.setString(4, relai);

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    public boolean updatePanierOfCommande(int id_commande, int id_panier, int quantite) {
        String checkQuery = "SELECT quantite FROM ComposeCommande WHERE id_commande = ? AND id_panier = ?";
        String updateQuery = "UPDATE ComposeCommande SET quantite = ? WHERE id_commande = ? AND id_panier = ?";
        String insertQuery = "INSERT INTO ComposeCommande (id_commande, id_panier, quantite) VALUES (?, ?, ?)";

        int rowsAffected;
        try (PreparedStatement checkStmt = dbConnection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, id_panier);
            checkStmt.setInt(2, id_commande);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = dbConnection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, quantite);
                    updateStmt.setInt(2, id_commande);
                    updateStmt.setInt(3, id_panier);
                    rowsAffected = updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = dbConnection.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, id_commande);
                    insertStmt.setInt(2, id_panier);
                    insertStmt.setInt(3, quantite);
                    rowsAffected = insertStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0);
    }

    public boolean deletePanierFromCommande(int id_commande, int id_panier) {
        String query = "DELETE FROM ComposeCommande WHERE id_commande = ? AND id_panier = ?";
        int rowsAffected;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id_commande);
            ps.setInt(2, id_panier);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (rowsAffected != 0);
    }

    @Override
    public boolean deleteCommande(int id_commande) {
        String query = "DELETE FROM ComposeCommande WHERE id_commande = ?";

        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(id_commande));

            nbRowModified += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "DELETE FROM Commande WHERE id_commande=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(id_commande));

            nbRowModified += ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }
}
