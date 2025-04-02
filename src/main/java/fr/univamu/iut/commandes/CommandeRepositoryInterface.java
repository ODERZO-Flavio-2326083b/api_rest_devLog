package fr.univamu.iut.commandes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface d'accès aux données des livres
 */
public interface CommandeRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les commandes
     */
    void close();

    /**
     * Méthode permettant l'ajout d'une nouvelle commande à la base de données
     * @param id_utilisateur l'id de l'utilisateur ayant passé la commande
     * @param date_retrait la date de retrait de la commande
     * @param id_paniers une liste correspondant aux ids des paniers à contenus dans la commande
     * @return true si l'ajout à été fait, false sinon
     */
    boolean addCommande(int id_utilisateur, Date date_retrait, List<Integer> id_paniers, String relai);

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
     * Méthode retournant la liste des ids des paniers associés à la commande
     * @param id_commande la commande dont on recherche les paniers
     * @return une liste d'Integers correspondant aux ids
     */
    List<Integer> getPanierIdsOfCommandes(int id_commande);

    /**
     * Méthode permettant de mettre à jour une commande enregistrée.
     * @param id_commande le nouvel id de la commande
     * @param id_utilisateur le nouvel id de l'utilisateur
     * @param date_retrait la nouvelle date de retrait de la commande
     * @return true si le livre existe et la mise à jour a été faite, false sinon
     */
    boolean updateCommande(int id_commande, int id_utilisateur, java.sql.Date date_retrait, String relai);

    /**
     * Méthode permettant d'insérer une nouvelle relation entre un panier et une commande, ou de modifier cette relation
     * si elle existe déjà
     * @param id_commande l'id de la commande à associer avec le panier
     * @param id_panier l'id du panier à associer avec la commande
     * @param quantite la nouvelle quantité de paniers associés à la commande
     * @return true si la suppression a bien été effectuée, false sinon
     */
    boolean updatePanierOfCommande(int id_commande, int id_panier, int quantite);

    /**
     * Méthode permettant de supprimer une relation entre un panier et une commande
     * @param id_commande l'id de la commande dont on veut supprimer le panier
     * @param id_panier l'id du panier à supprimer de la commande
     * @return true si la suppression a bien été effectuée, false sinon
     */
    boolean deletePanierFromCommande(int id_commande, int id_panier);

    /**
     * Méthode permettant de supprimer une commande
     * @param id_commande l'id de la commande à supprimer
     * @return true si la suppression a bien été effectuée, false sinon
     */
    boolean deleteCommande(int id_commande);

}
