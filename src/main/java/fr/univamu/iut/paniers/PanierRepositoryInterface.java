package fr.univamu.iut.paniers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface d'accès aux paniers
 */
public interface PanierRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les paniers
     */
    public void close();

    /**
     * Méthode retournant le panier dont l'id est passé en paramètre
     * @param id_panier identifiant du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    public Panier getPanier( int id_panier );

    /**
     * Méthode retournant la liste des paniers
     * @return une liste d'objets panier
     */
    public ArrayList<Panier> getAllPaniers();

    /**
     * Méthode ajoutant un panier
     * @param prix prix du panier
     * @param qtt_panier_dispo quantité disponible du panier
     * @param id_produits liste des ids des produits contenus dans le panier
     * @return true si l'ajout a été fait, false sinon
     */
    public boolean addPanier( float prix, int qtt_panier_dispo, List<Integer> id_produits );

    /**
     * Méthode supprimant un panier
     * @param id_panier identifiant du panier à supprimer
     * @return true si suppression effectuée
     */
    public boolean deletePanier(int id_panier);

    /**
     * Méthode permettant de mettre à jour un panier enregistré
     * @param id_panier identifiant du panier à mettre à jour
     * @param prix nouveau prix du panier
     * @param qtt_panier_dispo nouvelle quantité du panier disponible
     * @return true si le panier existe et la mise à jour a été faite, false sinon
     */
    public boolean updatePanier( int id_panier, float prix, int qtt_panier_dispo);

    /**
     * À partir d'un id de panier,
     * @param id_panier id du panier auquel ajouter le produit
     * @param id_produit id du produit à ajouter
     * @param quantite quantité de produit
     * @return true si l'ajout s'est bien effectué, false sinon
     */
    public boolean addProduitToPanier(int id_panier, int id_produit, int quantite);

    /**
     * À partir d'un id de panier, renvoie la liste des ids produits qui le compose
     * @param id_panier id du panier
     * @return Liste des ids produits
     */
    public List<Integer> getProduitIdsOfPanier(int id_panier);

    /**
     * Méthode permettant de supprimer un produit d'un panier
     * @param id_panier id du panier
     * @param id_produit id du produit à supprimer
     * @return true si le panier existe et la mise à jour a été faite, false sinon
     */
    public boolean deleteProduitFromPanier( int id_panier, int id_produit );

    /**
     * Méthode permettant d'ajouter un produit à un panier, ou mettre à jour sa quantité
     * @param id_panier id du panier
     * @param id_produit id du produit à ajouter/mettre à jour
     * @param quantite quantité de produit dans le panier
     * @return true si modification effectuée, false sinon
     */
    public boolean updateProduitOfPanier(int id_panier, int id_produit, int quantite);
}
