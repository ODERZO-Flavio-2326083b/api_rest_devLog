package fr.univamu.iut.paniers;

/**
 * Classe représentant un utilisateur
 */
public class Utilisateur {

    /**
     * id du utilisateur
     */
    protected int id_utilisateur;

    /**
     * pseudo du utilisateur
     */
    protected String pseudo_utilisateur;

    /**
     * mot de passe du utilisateur
     */
    protected String mdp_utilisateur;

    /**
     * Constructeur par défaut
     */
    public Utilisateur(){
    }

    /**
     * Constructeur de utilisateur
     * @param id_utilisateur id du utilisateur
     * @param pseudo_utilisateur pseudo du utilisateur
     * @param mdp_utilisateur mot de passe du utilisateur
     */
    public Utilisateur(int id_utilisateur, String pseudo_utilisateur, String mdp_utilisateur){
        this.id_utilisateur = id_utilisateur;
        this.pseudo_utilisateur = pseudo_utilisateur;
        this.mdp_utilisateur = mdp_utilisateur;
    }

    /**
     * Méthode permettant d'accéder à l'id du utilisateur
     * @return un entier avec l'id du utilisateur
     */
    public int getId() {
        return id_utilisateur;
    }

    /**
     * Méthode permettant d'accéder au pseudo du utilisateur
     * @return un chaîne de caractères avec le pseudo du utilisateur
     */
    public String getPseudo() {
        return pseudo_utilisateur;
    }

    /**
     * Méthode permettant d'accéder au mot de passe du utilisateur
     * @return un chaîne de caractères avec le mot de passe du utilisateur
     */
    public String getMdp() {
        return mdp_utilisateur;
    }

    /**
     * Méthode permettant de modifier le pseudo du utilisateur
     * @param pseudo une chaîne de caractères avec le pseudo à utiliser
     */
    public void setPseudo(String pseudo) {
        this.pseudo_utilisateur = pseudo;
    }

    /**
     * Méthode permettant de modifier le mot de passe du utilisateur
     * @param title une chaîne de caractères avec le mot de passe à utiliser
     */
    public void setMdp(String title) {
        this.mdp_utilisateur = title;
    }

    /**
     * Méthode permettant de modifier le pseudo du utilisateur
     * @param authors une chaîne de caractères avec le pseudo à utiliser
     */
    public void setAuthors(String authors) {
        this.pseudo_utilisateur = authors;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id_utilisateur=" + id_utilisateur +
                ", pseudo_utilisateur='" + pseudo_utilisateur + '\'' +
                ", mdp_utilisateur='" + mdp_utilisateur + '\'' +
                '}';
    }
}