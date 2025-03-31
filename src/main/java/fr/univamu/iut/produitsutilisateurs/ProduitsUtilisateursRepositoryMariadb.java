package fr.univamu.iut.produitsutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accèder aux produits stockés dans une base de données Mariadb
 */
public class ProduitsUtilisateursRepositoryMariadb implements ProduitsUtilisateursRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection ;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public ProduitsUtilisateursRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
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

    /**
     * Méthode permettant d'ajouter un produit dans la base de données
     * @param id identifiant du produit
     * @return l'identifiant du produit ajouté
     */
    @Override
    public Produit getProduit(int id) {

        Produit selectedProduit = null;

        String query = "SELECT * FROM Produit WHERE id_produit=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du produit est valide)
            if( result.next() )
            {
                String nom = result.getString("nom_produit");
                int quantite = result.getInt("quantite");
                String unite = result.getString("unite");

                // création et initialisation de l'objet Produit
                selectedProduit = new Produit(id, nom, quantite, unite);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedProduit;
    }

    /**
     * Méthode retournant la liste des produits
     * @return une liste d'objets Produit
     */
    @Override
    public ArrayList<Produit> getAllProduits() {
        ArrayList<Produit> listProduits ;

        String query = "SELECT * FROM Produit";

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listProduits = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int id = result.getInt("id_produit");
                String nom = result.getString("nom_produit");
                int quantite = result.getInt("quantite");
                String unite = result.getString("unite");

                // création du produit courant
                Produit currentProduit = new Produit(id, nom, quantite, unite);

                listProduits.add(currentProduit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProduits;
    }

    /**
     * Méthode permettant de mettre à jour un produit enregistré.
     * @param id identifiant du produit à mettre à jour
     * @param title nouveau titre du produit
     * @param quantite nouvelle quantité du produit
     * @param unite nouvelle unité du produit
     * @return true si le produit existe et que la mise à jour a été effectuée, false sinon
     */
    @Override
    public boolean updateProduit(int id, String nom, int quantite, String unite) {
        String query = "UPDATE Produit SET nom_produit=?, quantite=?, unite=? WHERE id_produit=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query)){
            ps.setString(1, nom);
            ps.setInt(2, quantite);
            ps.setString(3, unite);
            ps.setInt(4, id);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    /**
     * Méthode permettant de créer un produit
     * @param nom nom du produit
     * @param quantite quantité du produit
     * @param unite unité du produit
     * @return l'identifiant du produit créé
     */
    @Override
    public int createProduit(String nom, int quantite, String unite) {
        String query = "INSERT INTO Produit (nom_produit, quantite, unite) VALUES (?, ?, ?)";
        int id = -1;

        // construction et exécution d'une requête préparée
        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, nom);
            ps.setInt(2, quantite);
            ps.setString(3, unite);

            // exécution de la requête
            ps.executeUpdate();

            // récupération de l'identifiant du produit créé
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * Méthode permettant de supprimer un produit
     * @param idProduit identifiant du produit
     * @return true si le produit a été supprimé, false sinon
     */
    @Override
    public boolean deleteProduit(int idProduit) {
        String query = "DELETE FROM Produit WHERE id_produit = ?";
        int nbRowModified = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, idProduit);
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }

    /**
     * Méthode retournant l'utilisateur dont l'identifiant est passé en paramètre.
     * @param id identifiant de l'utilisateur recherché
     * @return un objet Utilisateur représentant l'utilisateur recherché, ou null si non trouvé
     */
    @Override
    public Utilisateur getUtilisateur(int id) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM Utilisateur WHERE id_utilisateur = ?";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                String pseudo = result.getString("pseudo");
                String mdp = result.getString("mot_de_passe");
                utilisateur = new Utilisateur(id, pseudo, mdp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilisateur;
    }

    /**
     * Méthode retournant la liste des utilisateurs enregistrés.
     * @return une liste d'objets Utilisateur
     */
    @Override
    public ArrayList<Utilisateur> getAllUtilisateurs() {
        ArrayList<Utilisateur> listUtilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("id_utilisateur");
                String pseudo = result.getString("pseudo");
                String mdp = result.getString("mot_de_passe");
                Utilisateur utilisateur = new Utilisateur(id, pseudo, mdp);
                listUtilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUtilisateurs;
    }

    /**
     * Méthode permettant de mettre à jour un utilisateur enregistré.
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param nouveauPseudo nouveau pseudo de l'utilisateur
     * @param nouveauMdp nouveau mot de passe de l'utilisateur
     * @return true si l'utilisateur existe et que la mise à jour a été effectuée, false sinon
     */
    @Override
    public boolean updateUtilisateur(int id, String nouveauPseudo, String nouveauMdp) {
        String query = "UPDATE Utilisateur SET pseudo = ?, mot_de_passe = ? WHERE id_utilisateur = ?";
        int nbRowModified = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nouveauPseudo);
            ps.setString(2, nouveauMdp);
            ps.setInt(3, id);
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }

    /**
     * Méthode permettant de créer un utilisateur
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @return l'identifiant de l'utilisateur créé
     */
    @Override
    public int createUtilisateur(String pseudo, String mdp) {
        String query = "INSERT INTO Utilisateur (pseudo, mot_de_passe) VALUES (?, ?)";
        int id = -1;
        try (PreparedStatement ps = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pseudo);
            ps.setString(2, mdp);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * Méthode permettant de supprimer un utilisateur
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si l'utilisateur a été supprimé, false sinon
     */
    @Override
    public boolean deleteUtilisateur(int idUtilisateur) {
        String query = "DELETE FROM Utilisateur WHERE id_utilisateur = ?";
        int nbRowModified = 0;
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, idUtilisateur);
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }
}