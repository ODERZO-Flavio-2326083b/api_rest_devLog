package fr.univamu.iut.paniers;

import java.util.ArrayList;

/**
 * Interface d'accès aux données des produits
 */
public interface ProduitsUtilisateursRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les produits
     */
    public void close();

    /**
     * Méthode retournant le produit dont la référence est passée en paramètre
     * @param id identifiant du produit recherché
     * @return un objet Produit représentant le produit recherché
     */
    public Produit getProduit(int id);

    /**
     * Méthode retournant la liste des produits
     * @return une liste d'objets produits
     */
    public ArrayList<Produit> getAllProduits() ;

    /**
     * Méthode permettant de mettre à jours un produit enregistré
     * @param id identifiant du produit à mettre à jours
     * @param nom nouveau nom du produit
     * @param quantite nouvelle quantité du produit
     * @param unite nouvelle unité du produit
     * @return true si le produit existe et la mise à jours a été faite, false sinon
     */
    public boolean updateProduit(int id, String nom, int quantite, String unite);

    /**
     * Méthode permettant de créer un produit
     * @param nom nom du produit
     * @param quantite quantité du produit
     * @param unite unité du produit
     * @return l'identifiant du produit créé
     */
    public int createProduit(String nom, int quantite, String unite);

    /**
     * Méthode permettant de supprimer un produit
     * @param idProduit identifiant du produit
     * @return true si le produit a été supprimé, false sinon
     */
    public boolean deleteProduit(int idProduit);}