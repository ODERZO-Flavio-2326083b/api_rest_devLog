package fr.univamu.iut.paniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
@ApplicationScoped
public class PanierService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les paniers
     */
    protected PanierRepositoryInterface panierRepo;

    /**
     * Objet permettant d'accéder aux données sur les produits
     */
    protected ProduitsUtilisateursRepositoryInterface produitRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param panierRepo objet implémentant l'interface d'accès aux données
     */
    public PanierService(PanierRepositoryInterface panierRepo, ProduitsUtilisateursRepositoryInterface produitRepo) {
        this.panierRepo = panierRepo;
        this.produitRepo = produitRepo;
    }

    /**
     * Méthode permettant d'ajouter un panier à la base de données
     * @param panier Objet panier contenant les informations
     * @return true si l'ajout a été effectué, false sinon
     */
    public boolean addPanier(Panier panier) {
        return panierRepo.addPanier(panier.prix, panier.qtt_panier_dispo, panier.id_produits);
    }

    /**
     * Méthode retournant les informations sur les paniers au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllPaniersJSON() {
        ArrayList<Panier> allPaniers = panierRepo.getAllPaniers();

        // création du json et conversion de la liste de paniers
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allPaniers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un panier recherché
     * @param id_panier identifiant du panier recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getPanierJSON( int id_panier ){
        String result = null;
        Panier panier = panierRepo.getPanier(id_panier);

        // si le panier a été trouvé
        if( panier != null ) {

            List<Integer> produitsIds = panierRepo.getProduitIdsOfPanier(id_panier);
            List<Produit> produits = new ArrayList<>();
            for (Integer idProduit : produitsIds) {
                Produit produit = produitRepo.getProduit(idProduit);
                if (produit != null) {
                    produits.add(produit);
                }
            }
            panier.setProduits(produits);

            // création du json et conversion du panier
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(panier);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'un panier
     * @param id_panier id du panier à mettre à jours
     * @param panier les nouvelles infromations a utiliser
     * @return true si le panier a pu être mis à jours
     */
    public boolean updatePanier(int id_panier, Panier panier) {
        return panierRepo.updatePanier(id_panier, panier.prix, panier.qtt_panier_dispo);
    }

    /**
     * Méthode permettant de supprimer un panier du repo
     * @param id_panier id du panier à supprimer
     * @return true si le panier a bien été supprimé
     */
    public boolean deletePanier(int id_panier) {
        return panierRepo.deletePanier(id_panier);
    }

    /**
     * Méthode permettant de retirer un produit à un panier
     * @param id_panier id du panier
     * @param id_produit id du produit à retirer
     * @return true si le produit a été retiré
     */
    public boolean deleteProduitFromPanier(int id_panier, int id_produit) {
        return panierRepo.deleteProduitFromPanier(id_panier, id_produit);
    }

    /**
     * Méthode permettant d'ajouter un produit à un panier
     * @param id_panier id du panier
     * @param id_produit id du produit à ajouter
     * @return true si le produit a été ajouté
     */
    public boolean addProduitToPanier(int id_panier, int id_produit, int quantite) {
        return panierRepo.addProduitToPanier(id_panier, id_produit, quantite);
    }

    /**
     * Méthode permettant de changer la quantité d'un produit dans un panier
     * @param id_panier id du panier concerné
     * @param id_produit id du produit concerné
     * @param quantite nouvelle quantité
     * @return true si le changement a été effectué
     */
    public boolean updateProduitOfPanier(int id_panier, int id_produit, int quantite) {
        return panierRepo.updateProduitOfPanier(id_panier, id_produit, quantite);
    }
}
