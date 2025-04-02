package fr.univamu.iut.paniers;

import java.util.Date;
import java.util.List;

/**
 * Classe représentant un panier
 */
public class Panier {

    /**
     * id du panier
     */
    protected int id_panier;

    /**
     * Prix du panier
     */
    protected float prix;

    /**
     * Quantité de paniers disponibles
     */
    protected int qtt_panier_dispo;

    /**
     * Date de la dernière mise à jour du panier
     */
    protected Date derniere_maj;

    /**
     * Liste des produits composant le panier
     */
    protected List<Produit> produits;

    /**
     * Liste des ids produits composant le panier
     */
    protected List<Integer> id_produits;

    public Panier() {}

    public Panier(int id_panier, float prix, int qtt_panier_dispo, Date derniere_maj) {
        this.id_panier = id_panier;
        this.prix = prix;
        this.qtt_panier_dispo = qtt_panier_dispo;
        this.derniere_maj = derniere_maj;
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQtt_panier_dispo() {
        return qtt_panier_dispo;
    }

    public void setQtt_panier_dispo(int qtt_panier_dispo) {
        this.qtt_panier_dispo = qtt_panier_dispo;
    }

    public Date getDerniere_maj() {
        return derniere_maj;
    }

    public void setDerniere_maj(Date derniere_maj) {
        this.derniere_maj = derniere_maj;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Integer> getId_produits() {
        return id_produits;
    }

    public void setId_produits(List<Integer> id_produits) {
        this.id_produits = id_produits;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id_panier=" + id_panier +
                ", prix=" + prix +
                ", qtt_panier_dispo=" + qtt_panier_dispo +
                ", derniere_maj=" + derniere_maj +
                '}';
    }
}
