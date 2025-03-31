package fr.univamu.iut.produitsutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier la ressource et le mode d'accès aux données)
 */
public class UtilisateurService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected ProduitsUtilisateursRepositoryInterface repository;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param repository objet implémentant l'interface d'accès aux données
     */
    public UtilisateurService(ProduitsUtilisateursRepositoryInterface repository) {
        this.repository = repository;
    }

    /**
     * Méthode retournant les informations sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllUtilisateursJSON() {
        ArrayList<Utilisateur> allUtilisateurs = repository.getAllUtilisateurs();

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allUtilisateurs);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un utilisateur recherché
     * @param id l'identifiant de l'utilisateur recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getUtilisateurJSON(int id) {
        String result = null;
        Utilisateur utilisateur = repository.getUtilisateur(id);

        if (utilisateur != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(utilisateur);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant de mettre à jour les informations d'un utilisateur.
     * Ici, seule la modification du pseudo et du mot de passe est envisagée.
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param utilisateur utilisateur contenant les nouvelles informations
     * @return true si l'utilisateur a pu être mis à jour
     */
    public boolean updateUtilisateur(int id, Utilisateur utilisateur) {
        return repository.updateUtilisateur(id, utilisateur.getPseudo(), utilisateur.getMdp());
    }

    /**
     * Méthode permettant de créer un utilisateur
     * @param utilisateur l'utilisateur à créer
     * @return l'identifiant de l'utilisateur créé
     */
    public int createUtilisateur(Utilisateur utilisateur) {
        return repository.createUtilisateur(utilisateur.getPseudo(), utilisateur.getMdp());
    }

    /**
     * Méthode permettant de supprimer un utilisateur
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si l'utilisateur a été supprimé, false sinon
     */
    public boolean deleteUtilisateur(int idUtilisateur) {
        return repository.deleteUtilisateur(idUtilisateur);
    }
}
