package fr.univamu.iut.commandes;

import java.util.Date;
import java.util.List;

/**
 * Classe représentant une commande
 */
public class Commande {

    /**
     * Id de la commande
     */
    protected int id_commande;

    /**
     * Id de l'utilisateur qui a passé la commande
     */
    protected int id_utilisateur;

    /**
     * Date de retrait de la commande
     */
    protected java.sql.Date date_retrait;

    protected List<Panier> paniers;

    /**
     * Liste des ids des paniers liés à la commande
     */
    protected List<Integer> id_paniers;

    /**
     * Constructeur par défaut
     */
    public Commande()
    {
    }

    /**
     * Constructeur de commande
     * @param id_commande id de la commande
     * @param id_utilisateur id de l'utilisateur qui a passé la commande
     * @param date_retrait date de retrait de la commande
     */
    public Commande(int id_commande, int id_utilisateur, java.sql.Date date_retrait)
    {
        this.id_commande = id_commande;
        this.id_utilisateur = id_utilisateur;
        this.date_retrait = date_retrait;
    }

    /**
     * Méthode permettant d'accéder à l'id de la commande
     * @return un int correspondant à l'id de la commande
     */
    public int getId_commande() {
        return id_commande;
    }

    /**
     * Méthode permettant d'accéder à l'id de l'utilisateur qui a passé la commande
     * @return un int correspondant à l'id de l'utilisateur
     */
    public int getId_utilisateur() {
        return id_utilisateur;
    }

    /**
     * Méthode permettant d'accéder à la date de retrait de la commande
     * @return une Date correspondant à la date de retrait
     */
    public Date getDate_retrait() {
        return date_retrait;
    }



    public List<Panier> getPaniers() {
        return paniers;
    }

    /**
     * Méthode permettant d'accéder à la liste des paniers liés à la commande
     * @return une liste correspondant aux paniers liés à la commande
     */
    public List<Integer> getId_paniers() {
        return id_paniers;
    }

    /**
     * Méthode permettant de modifier l'id de la commande
     * @param id_commande un int correspondant au nouvel id de la commande
     */
    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    /**
     * Méthode permettant de modifier l'id de l'utilisateur qui a passé la commande
     * @param id_utilisateur un int correspondant au nouvel id de l'utilisateur
     */
    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    /**
     * Méthode permettant de modifier a date de retrait de la commande
     * @param date_retrait une Date correspondant à la nouvelle date de retrait la commande
     */
    public void setDate_retrait(java.sql.Date date_retrait) {
        this.date_retrait = date_retrait;
    }

    public void setPaniers(List<Panier> paniers) {
        this.paniers = paniers;
    }

    /**
     * Méthode permettant de modifier la liste des paniers associés à la commande
     * @param id_paniers une liste contenant les nouveaux paniers associés à la commande
     */
    public void setId_paniers(List<Integer> id_paniers) {
        this.id_paniers = id_paniers;
    }

    @Override
    public String toString() {
        return "Commande[" + "id_commande=" + id_commande + ", id_utilisateur=" + id_utilisateur + ", date_retrait=" + date_retrait + ']';
    }
}
