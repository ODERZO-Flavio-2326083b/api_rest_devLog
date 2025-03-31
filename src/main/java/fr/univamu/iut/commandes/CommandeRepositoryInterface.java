package fr.univamu.iut.commandes;

import java.util.ArrayList;

/**
 * Interface d'accès aux données des livres
 */
public interface CommandeRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les commandes
     */
    void close();

    /**
     * Méthode retournant la commande dont l'id est passé en paramètre
     * @param id_commande l'id de la commande recherchée
     * @return un objet Commande représentant la commande recherchée
     */
    Commande getCommande(int id_commande);

    /**
     * Méthode retournant une liste de commandes
     * @return une liste d'objets Commande
     */
    ArrayList<Commande> getAllCommandes();

    /**
     * Méthode permettant de mettre à jour une commande enregistrée.
     * @param id_commande le nouvel id de la commande
     * @param id_utilisateur le nouvel id de l'utilisateur
     * @param date_retrait la nouvelle date de retrait de la commande
     * @return true si le livre existe et la mise à jour a été faite, false sinon
     */
    boolean updateCommande(int id_commande, int id_utilisateur, java.sql.Date date_retrait);

}
