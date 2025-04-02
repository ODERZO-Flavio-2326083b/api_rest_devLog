package fr.univamu.iut.paniers;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;
import java.io.Closeable;
import java.util.ArrayList;

public class ProduitsUtilisateursRepositoryAPI implements Closeable, ProduitsUtilisateursRepositoryInterface {

    /**
     * URL de l'API des produits et utilisateurs
     */
    String url;

    public ProduitsUtilisateursRepositoryAPI(String url) {
        this.url = url;
    }

    /**
     * Méthode retournant le produit dont la référence est passée en paramètre
     *
     * @param id identifiant du produit recherché
     * @return un objet Produit représentant le produit recherché
     */
    @Override
    public Produit getProduit(int id) {
        Produit myProduit = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/" + id);
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            myProduit = response.readEntity(Produit.class);
        }

        client.close();
        return myProduit;
    }

    /**
     * Méthode retournant la liste des produits
     *
     * @return une liste d'objets produits
     */
    @Override
    public ArrayList<Produit> getAllProduits() {
        return null;
    }

    /**
     * Méthode permettant de mettre à jours un produit enregistré
     *
     * @param id       identifiant du produit à mettre à jours
     * @param nom      nouveau nom du produit
     * @param quantite nouvelle quantité du produit
     * @param unite    nouvelle unité du produit
     * @return true si le produit existe et la mise à jours a été faite, false sinon
     */
    @Override
    public boolean updateProduit(int id, String nom, int quantite, String unite) {
        boolean result = false;

        Produit updatedProduit = new Produit(id, nom, quantite, unite);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/" + id);
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedProduit, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            result = true;
        }

        client.close();
        return result;
    }

    /**
     * Méthode permettant de créer un produit
     *
     * @param nom      nom du produit
     * @param quantite quantité du produit
     * @param unite    unité du produit
     * @return l'identifiant du produit créé
     */
    @Override
    public int createProduit(String nom, int quantite, String unite) {
        return 0;
    }

    /**
     * Méthode permettant de supprimer un produit
     *
     * @param idProduit identifiant du produit
     * @return true si le produit a été supprimé, false sinon
     */
    @Override
    public boolean deleteProduit(int idProduit) {
        return false;
    }

    /**
     * Méthode retournant l'utilisateur dont l'identifiant est passé en paramètre
     *
     * @param id identifiant de l'utilisateur recherché
     * @return un objet Utilisateur représentant l'utilisateur recherché
     */
    @Override
    public Utilisateur getUtilisateur(int id) {
        return null;
    }

    /**
     * Méthode retournant la liste des utilisateurs enregistrés
     *
     * @return une liste d'objets Utilisateur
     */
    @Override
    public ArrayList<Utilisateur> getAllUtilisateurs() {
        return null;
    }

    /**
     * Méthode permettant de mettre à jour un utilisateur enregistré
     *
     * @param id            identifiant de l'utilisateur à mettre à jour
     * @param nouveauPseudo nouveau pseudo de l'utilisateur
     * @param nouveauMdp    nouveau mot de passe de l'utilisateur
     * @return true si l'utilisateur existe et que la mise à jour a été effectuée, false sinon
     */
    @Override
    public boolean updateUtilisateur(int id, String nouveauPseudo, String nouveauMdp) {
        return false;
    }

    /**
     * Méthode permettant de créer un utilisateur
     *
     * @param pseudo pseudo de l'utilisateur
     * @param mdp    mot de passe de l'utilisateur
     * @return l'identifiant de l'utilisateur créé
     */
    @Override
    public int createUtilisateur(String pseudo, String mdp) {
        return 0;
    }

    /**
     * Méthode permettant de supprimer un utilisateur
     *
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si l'association a été créée, false sinon
     */
    @Override
    public boolean deleteUtilisateur(int idUtilisateur) {
        return false;
    }

    /**
     * Closes this stream and releases any system resources associated
     */
    @Override
    public void close() {

    }
}
