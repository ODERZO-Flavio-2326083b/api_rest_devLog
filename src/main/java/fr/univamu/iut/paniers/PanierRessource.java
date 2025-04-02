package fr.univamu.iut.paniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Map;

/**
 * Ressource associée aux paniers
 * (point d'accès de l'API REST)
 */
@Path("/paniers")
@ApplicationScoped
public class PanierRessource {

    /**
     * Service utilisé pour accéder aux données des paniers et récupérer/modifier leurs informations
     */
    private PanierService service;

    /**
     * Constructeur par défaut
     */
    public PanierRessource() {}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param panierRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject PanierRessource(PanierRepositoryInterface panierRepo, ProduitsUtilisateursRepositoryInterface produitRepo) {
        this.service = new PanierService(panierRepo, produitRepo);
    }

    /** Endpoint qui permet d'ajouter un panier au repo
     * les informations nécessaires sont : prix: float, qtt_panier_dispo: int et id_produits: int[]
     * @param panier objet panier à ajouter au repo
     * @return "added" si le panier a été ajouté, une erreur sinon
     */
    @POST
    @Consumes("application/json")
    public Response addPanier(Panier panier) {
        if( ! service.addPanier(panier) )
            throw new NotFoundException();
        else
            return Response.ok("added").build();
    }

    /**
     * Endpoint qui récupère tous les paniers disponibles dans la base
     * @return la liste des paniers
     */
    @GET
    @Produces("application/json")
    public String getAllPaniers() {
        return service.getAllPaniersJSON();
    }

    /**
     * Endpoint qui récupère un panier avec son identifiant
     * @param id_panier identifiant du panier
     * @return Le panier avec ses produits
     */
    @GET
    @Path("{id_panier}")
    @Produces("application/json")
    public String getPanier( @PathParam("id_panier") int id_panier){

        String result = service.getPanierJSON(id_panier);

        // si le panier n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour un panier en fonction de son id
     * @param id_panier id du panier concerné
     * @param panier nouvel objet panier qui contient les nouvelles données
     * @return "updated" si le panier a été mis à jour, une erreur sinon
     */
    @PUT
    @Path("{id_panier}")
    @Consumes("application/json")
    public Response updatePanier(@PathParam("id_panier") int id_panier, Panier panier ){

        // si le panier n'a pas été trouvé
        if( ! service.updatePanier(id_panier, panier) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de supprimer un panier en fonction de son id
     * @param id_panier id du panier concerné
     * @return "deleted" si le panier a été supprimé, une erreur sinon
     */
    @DELETE
    @Path("{id_panier}")
    public Response deletePanier(@PathParam("id_panier") int id_panier){

        if (!service.deletePanier(id_panier))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

    /**
     * Endpoint permettant de supprimer un produit dans un panier en fonction de leurs ids
     * @param id_panier id du panier concerné
     * @param id_produit id du produit à supprimer
     * @return "deleted" si le produit a été supprimé, une erreur sinon
     */
    @DELETE
    @Path("/{id_panier}/produits/{id_produit}")
    public Response deleteProduitFromPanier(@PathParam("id_panier") int id_panier,
                                            @PathParam("id_produit") int id_produit) {

        if( !service.deleteProduitFromPanier(id_panier, id_produit) )
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

    /**
     * Endpoint permettant d'ajouter un produit à un panier existant
     * Si le produit existe déjà, la quantité sera simplement modifiée
     * @param id_panier id du panier concerné
     * @param id_produit id du produit à ajouter
     * @param body body contenant la clé "quantité": int
     * @return "added" si le produit a été ajouté, une erreur sinon
     */
    @POST
    @Path("/{id_panier}/produits/{id_produit}")
    @Consumes("application/json")
    public Response addProduitToPanier(@PathParam("id_panier") int id_panier,
                                       @PathParam("id_produit") int id_produit,
                                        Map<String, Integer> body) {
        int quantite = body.getOrDefault("quantite", 1);
        if( !service.addProduitToPanier(id_panier, id_produit, quantite) )
            throw new NotFoundException();
        else
            return Response.ok("added").build();
    }

    /**
     * Endpoint permettant de mettre à jour une quantité de produit sur un panier existant
     * @param id_panier id du panier concerné
     * @param id_produit id du produit à ajouter
     * @param body body contenant la clé "quantité": int
     * @return "updated" si le changement a été effectué, une erreur sinon
     */
    @PUT
    @Path("/{id_panier}/produits/{id_produit}")
    @Consumes("application/json")
    public Response updateProduitOfPanier(@PathParam("id_panier") int id_panier,
                                          @PathParam("id_produit") int id_produit,
                                          Map<String, Integer> body) {
        int quantite = body.get("quantite");
        if(!service.updateProduitOfPanier(id_panier, id_produit, quantite))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

}
