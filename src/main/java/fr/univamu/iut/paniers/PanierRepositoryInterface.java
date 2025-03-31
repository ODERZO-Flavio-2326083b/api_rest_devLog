package fr.univamu.iut.paniers;

import java.util.ArrayList;

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
     * Méthode permettant de mettre à jour un panier enregistré
     * @param id_panier identifiant du panier à mettre à jour
     * @param prix nouveau prix du panier
     * @param qtt_panier_dispo nouvelle quantité du panier disponible
     * @return true si le panier existe et la mise à jour a été faite, false sinon
     */
    public boolean updatePanier( int id_panier, float prix, int qtt_panier_dispo);

}
