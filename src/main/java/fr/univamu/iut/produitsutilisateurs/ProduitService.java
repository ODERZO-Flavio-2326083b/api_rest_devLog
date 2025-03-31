package fr.univamu.iut.produitsutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;


/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'éccès aux données)
 */
public class ProduitService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les produits
     */
    protected ProduitsUtilisateursRepositoryInterface produitsUtilisateursRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param produitsUtilisateursRepo objet implémentant l'interface d'accès aux données
     */
    public ProduitService(ProduitsUtilisateursRepositoryInterface produitsUtilisateursRepo) {
        this.produitsUtilisateursRepo = produitsUtilisateursRepo;
    }

    /**
     * Méthode retournant les informations sur les produits au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllProduitsJSON(){

        ArrayList<Produit> allProduits = produitsUtilisateursRepo.getAllProduits();

        // création du json et conversion de la liste de produits
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProduits);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un produit recherché
     * @param id la référence du produit recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getProduitJSON(int id){
        String result = null;
        Produit myProduit = produitsUtilisateursRepo.getProduit(id);

        // si le produit a été trouvé
        if( myProduit != null ) {
            // création du json et conversion du produit
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myProduit);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'un produit
     * @param id identifiant du produit à mettre à jours
     * @param produit produit contenant les nouvelles informations
     * @return true si le produit a pu être mis à jours
     */
    public boolean updateProduit(int id, Produit produit) {
        return produitsUtilisateursRepo.updateProduit(id, produit.getNom(), produit.getQuantite(), produit.getUnite());
    }

    /**
     * Méthode permettant de créer un produit
     * @param produit le produit à créer
     * @return l'identifiant du produit créé
     */
    public int createProduit(Produit produit) {
        return produitsUtilisateursRepo.createProduit(produit.getNom(), produit.getQuantite(), produit.getUnite());
    }

    /**
     * Méthode permettant de supprimer un produit
     * @param idProduit identifiant du produit
     * @return true si le produit a été supprimé, false sinon
     */
    public boolean deleteProduit(int idProduit) {
        return produitsUtilisateursRepo.deleteProduit(idProduit);
    }
}