package fr.univamu.iut.commandes;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class CommandeService {

    /**
     * Objet permettant d'accéder au dépô où sont stockées les informations sur les commandes
     */
    protected CommandeRepositoryInterface commandRepo;

    protected PanierRepositoryInterface panierRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param commandeRepo objet implémentant l'interface d'accès aux données des commandes
     * @param panierRepo objet implémentant l'interface d'accès aux données des paniers
     */
    public CommandeService(CommandeRepositoryInterface commandeRepo, PanierRepositoryInterface panierRepo) {
        this.commandRepo = commandeRepo;
        this.panierRepo = panierRepo;

        if (commandRepo == null) {
            System.err.println("Erreur : commandRepo est null !");
        }
    }

    public boolean addCommande(Commande commande) {
        return commandRepo.addCommande(commande.id_utilisateur, commande.date_retrait, commande.id_paniers);
    }

    /**
     * Méthode retournant les informations sur les commandes au format JSON
     * @return une chaîne de caractères contenant les informations au format JSON
     */
    public String getAllCommandsJSON() {
        ArrayList<Commande> allCommandes = commandRepo.getAllCommandes();

        String result = null;
        try(Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCommandes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur une commande recherchée
     * @param id_commande l'id de la commande recherchée
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getCommandeJSON(int id_commande) {
        String result = null;

        Commande myCommande = commandRepo.getCommande(id_commande);

        if (myCommande != null) {

            List<Integer> paniersIds = commandRepo.getPanierIdsOfCommandes(id_commande);
            List<Panier> paniers = new ArrayList<>();
            for (Integer idPanier : paniersIds) {
                Panier panier = panierRepo.getPanier(idPanier);
                if (panier != null) {
                    paniers.add(panier);
                }
            }
            myCommande.setPaniers(paniers);

            // création du json et conversion de la commande
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return result;
    }

    /**
     * Méthode permettant de mettre à jour les informations d'une commande
     * @param id_commande l'id de la commande à mettre à jour
     * @param commande les nouvelles informations à utiliser
     * @return true si la commande à pu être mise à jour
     */
    public boolean udpateCommande(int id_commande, Commande commande) {
        return commandRepo.updateCommande(id_commande, commande.id_utilisateur, commande.date_retrait);
    }

    public boolean addPanierToCommande(int id_commande, int id_panier, int quantite) {
        return commandRepo.updatePanierOfCommande(id_commande, id_panier, quantite);
    }

    public boolean deletePanierFromCommande(int id_commande, int id_panier) {
        return commandRepo.deletePanierFromCommande(id_commande, id_panier);
    }

    public boolean deleteCommande(int id_commande) {
        return commandRepo.deleteCommande(id_commande);
    }
}
