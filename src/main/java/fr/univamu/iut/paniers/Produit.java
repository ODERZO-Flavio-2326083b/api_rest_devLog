package fr.univamu.iut.paniers;

/**
 * Classe représentant un produit
 */
public class Produit {
    /**
     * id du produit
     */
    protected int id_produit;

    /**
     * nom du produit
     */
    protected String nom_produit;

    /**
     * quantite de produit
     */
    protected int quantite;

    /**
     * Unite du produit
     */
    protected String unite;

    /**
     * Constructeur par défaut
     */
    public Produit(){
    }

    /**
     * Constructeur de produit
     * @param id id du produit
     * @param nom titre du produit
     * @param quantite quantite du produit
     * @param unite unite du produit
     */
    public Produit(int id, String nom, int quantite, String unite){
        this.id_produit = id;
        this.nom_produit = nom;
        this.quantite = quantite;
        this.unite = unite;
    }

    /**
     * Méthode permettant d'accéder à l'id du produit
     * @return un chaîne de caractères avec l'id du produit
     */
    public int getId() {
        return id_produit;
    }

    /**
     * Méthode permettant d'accéder au titre du produit
     * @return un chaîne de caractères avec le titre du produit
     */
    public String getNom() {
        return nom_produit;
    }

    /**
     * Méthode permettant d'accéder à la quantite du produit
     * @return un entier avec la quantite du produit
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Méthode permettant d'accéder à l'unité du produit
     * @return un chaîne de caractères avec l'unité du produit
     */
    public String getUnite() {
        return unite;
    }

    /**
     * Méthode permettant de modifier l'id du produit
     * @param id une chaîne de caractères avec l'id à utiliser
     */
    public void setId(int id) {
        this.id_produit = id;
    }

    /**
     * Méthode permettant de modifier le titre du produit
     * @param title une chaîne de caractères avec le titre à utiliser
     */
    public void setNom(String title) {
        this.nom_produit = title;
    }

    /**
     * Méthode permettant de modifier la quantite du produit
     * @param quantite un entier avec la quantite à utiliser
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Méthode permettant de modifier l'unité du produit
     * @param unite une chaîne de caractères avec l'unité à utiliser
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id_produit='" + id_produit + '\'' +
                ", nom_produit='" + nom_produit + '\'' +
                ", quantite=" + quantite +
                ", unite='" + unite + '\'' +
                '}';
    }
}