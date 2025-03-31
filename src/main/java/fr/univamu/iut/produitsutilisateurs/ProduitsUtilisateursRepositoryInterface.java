package fr.univamu.iut.produitsutilisateurs;

import java.util.*;

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
    public boolean deleteProduit(int idProduit);

    /**
     * Méthode retournant l'utilisateur dont l'identifiant est passé en paramètre
     * @param id identifiant de l'utilisateur recherché
     * @return un objet Utilisateur représentant l'utilisateur recherché
     */
    public Utilisateur getUtilisateur(int id);

    /**
     * Méthode retournant la liste des utilisateurs enregistrés
     * @return une liste d'objets Utilisateur
     */
    public ArrayList<Utilisateur> getAllUtilisateurs();

    /**
     * Méthode permettant de mettre à jour un utilisateur enregistré
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param nouveauPseudo nouveau pseudo de l'utilisateur
     * @param nouveauMdp nouveau mot de passe de l'utilisateur
     * @return true si l'utilisateur existe et que la mise à jour a été effectuée, false sinon
     */
    public boolean updateUtilisateur(int id, String nouveauPseudo, String nouveauMdp);

    /**
     * Méthode permettant de créer un utilisateur
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @return l'identifiant de l'utilisateur créé
     */
    public int createUtilisateur(String pseudo, String mdp);

    /**
     * Méthode permettant de supprimer un utilisateur
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si l'association a été créée, false sinon
     */
    public boolean deleteUtilisateur(int idUtilisateur);
}