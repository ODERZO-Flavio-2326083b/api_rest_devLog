package fr.univamu.iut.produitsutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/utilisateurs")
@ApplicationScoped
public class UtilisateurResource {

    /**
     * Service utilisé pour accéder aux données des utilisateurs et récupérer/modifier leurs informations
     */
    private UtilisateurService service;

    /**
     * Constructeur par défaut
     */
    public UtilisateurResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param repository objet implémentant l'interface d'accès aux données
     */
    public @Inject UtilisateurResource(ProduitsUtilisateursRepositoryInterface repository){
        this.service = new UtilisateurService(repository);
    }

    /**
     * Endpoint permettant de publier tous les utilisateurs enregistrés
     * @return la liste des utilisateurs (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllUtilisateurs() {
        return service.getAllUtilisateursJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un utilisateur dont l'identifiant est passé en paramètre dans le chemin
     * @param id identifiant de l'utilisateur recherché
     * @return les informations de l'utilisateur recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUtilisateur(@PathParam("id") int id) {
        String result = service.getUtilisateurJSON(id);

        // si l'utilisateur n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour les informations d'un utilisateur.
     * (La requête PUT doit fournir les nouvelles informations de l'utilisateur, les autres données sont ignorées)
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param utilisateur l'utilisateur transmis au format JSON et converti en objet Utilisateur
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateUtilisateur(@PathParam("id") int id, Utilisateur utilisateur) {
        if(!service.updateUtilisateur(id, utilisateur))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de créer un utilisateur
     * @param utilisateur l'utilisateur transmis au format JSON et converti en objet Utilisateur
     * @return une réponse "created" si l'utilisateur a été créé, une erreur NotFound sinon
     */
    @POST
    @Consumes("application/json")
    public Response createUtilisateur(Utilisateur utilisateur) {
        int id = service.createUtilisateur(utilisateur);
        if (id == -1) {
            throw new NotFoundException();
        } else {
            return Response.status(Response.Status.CREATED).entity("created").build();
        }
    }

    /**
     * Endpoint permettant de supprimer un utilisateur
     * @param id identifiant de l'utilisateur à supprimer
     * @return une réponse "deleted" si l'utilisateur a été supprimé, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    public Response deleteUtilisateur(@PathParam("id") int id) {
        if (!service.deleteUtilisateur(id)) {
            throw new NotFoundException();
        } else {
            return Response.ok("deleted").build();
        }
    }
}
