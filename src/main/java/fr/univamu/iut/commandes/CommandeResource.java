package fr.univamu.iut.commandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Map;

/**
 * Ressource associée aux commandes
 * (point d'accès de l'API REST)
 */
@Path("/commandes")
@ApplicationScoped
public class CommandeResource {

    /**
     * Service utilisé pour accéder aux données des commandes et récupérer/modifier leurs informations
     */
    private CommandeService service;

    /**
     * Constructeur par défaut
     */
    public CommandeResource()
    {
    }

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param commandeRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject CommandeResource(CommandeRepositoryInterface commandeRepo, PanierRepositoryInterface panierRepo)
    {
        this.service = new CommandeService(commandeRepo, panierRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     * @param service le service d'accès aux livres
     */
    public CommandeResource (CommandeService service)
    {
        this.service = service;
    }

    /**
     * Endpoint permettant de publier toutes les commandes enregistrées
     * @return la liste des commandes (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllCommands() {
        return service.getAllCommandsJSON();
    }

    @POST
    @Consumes("application/json")
    public Response addCommande(Commande commande) {
        if (!service.addCommande(commande))
            throw new NotFoundException();
        else
            return Response.ok("added").build();
    }

    /**
     * Endpoint permettant de publier les informations d'une commande dont l'id est passé en paramètre
     * @param id_commande l'id de la commande recherchée
     * @return les informations de la commande recherchée au format JSON
     */
    @GET
    @Path("{id_commande}")
    @Produces("application/json")
    public String getCommande(@PathParam("id_commande") int id_commande)
    {
        String result = service.getCommandeJSON(id_commande);

        if (result == null)
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour le statu d'un livre uniquement
     * @param id_commande l'id de la commande dont il faut changer le statut
     * @param commande la commande transmie en HTTP au format JSON et convertie en objet Commande
     * @return une réponse "updated" si la mise à jour a bien été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id_commande}")
    @Consumes("application/json")
    public Response updateCommande(@PathParam("id_commande") int id_commande, Commande commande) {
        if (!service.udpateCommande(id_commande, commande))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    @PUT
    @Path("/{id_commande}/paniers/{id_panier}")
    @Consumes("application/json")
    public Response addPanierToCommande(@PathParam("id_commande") int id_commande,
                                        @PathParam("id_panier") int id_panier,
                                        Map<String, Integer> body) {
        int quantite = body.getOrDefault("quantite", 1);
        if (!service.addPanierToCommande(id_commande, id_panier, quantite))
            throw new NotFoundException();
        else
            return Response.ok("added").build();
    }

    @DELETE
    @Path("{id_commande}/paniers/{id_panier}")
    public Response deletePanierFromCommande(@PathParam("id_commande") int id_commande,
                                             @PathParam("id_panier") int id_panier) {
        if (!service.deletePanierFromCommande(id_commande, id_panier))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

    @DELETE
    @Path("{id_commande}")
    public Response deleteCommande(@PathParam("id_commande") int id_commande) {
        if (!service.deleteCommande(id_commande))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }
}
