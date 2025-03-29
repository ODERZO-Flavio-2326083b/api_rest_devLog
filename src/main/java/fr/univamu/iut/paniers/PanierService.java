package fr.univamu.iut.paniers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class PanierService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les paniers
     */
    protected PanierRepositoryInterface panierRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param panierRepo objet implémentant l'interface d'accès aux données
     */
    public PanierService(PanierRepositoryInterface panierRepo) {
        this.panierRepo = panierRepo;
    }

    /**
     * Méthode retournant les informations sur les paniers au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllPaniersJSON() {
        ArrayList<Panier> allPaniers = panierRepo.getAllPaniers();

        // création du json et conversion de la liste de paniers
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allPaniers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un panier recherché
     * @param id_panier identifiant du panier recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getPanierJSON( int id_panier ){
        String result = null;
        Panier panier = panierRepo.getPanier(id_panier);

        // si le panier a été trouvé
        if( panier != null ) {

            // création du json et conversion du panier
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(panier);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant de mettre à jours les informations d'un panier
     * @param id_panier référence du panier à mettre à jours
     * @param panier les nouvelles infromations a utiliser
     * @return true si le panier a pu être mis à jours
     */
    public boolean updatePanier(int id_panier, Panier panier) {
        return panierRepo.updatePanier(id_panier, panier.prix, panier.qtt_panier_dispo);
    }
}
