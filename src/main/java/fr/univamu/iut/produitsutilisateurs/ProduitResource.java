package fr.univamu.iut.produitsutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux produits
 * (point d'accès de l'API REST)
 */
@Path("/produits")
@ApplicationScoped
public class ProduitResource {
    /**
     * Service utilisé pour accéder aux données des produits et récupérer/modifier leurs informations
     */
    private ProduitService service;

    /**
     * Constructeur par défaut
     */
    public ProduitResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param produitRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject ProduitResource(ProduitsUtilisateursRepositoryInterface produitRepo){
        this.service = new ProduitService(produitRepo) ;
    }

    /**
     * Enpoint permettant de publier de tous les produits enregistrés
     * @return la liste des produits (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllProduits() {
        return service.getAllProduitsJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un produit dont la référence est passée paramètre dans le chemin
     * @param id référence du produit recherché
     * @return les informations du produit recherché au format JSON
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getProduit(@PathParam("id") int id) {
        String result = service.getProduitJSON(id);

        // si le produit n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint permettant de mettre à jour un produit
     * @param id l'id du produit
     * @param produit le produit transmis en HTTP au format JSON et convertit en objet Produit
     * @return une réponse "updated" si la mise à jour a été effectuée, une erreur NotFound sinon
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateProduit(@PathParam("id") int id, Produit produit) {
        // si le produit n'a pas été trouvé
        if(!service.updateProduit(id, produit))
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    /**
     * Endpoint permettant de créer un produit
     * @param produit le produit transmis en HTTP au format JSON et converti en objet Produit
     * @return une réponse "created" si le produit a été créé, une erreur NotFound sinon
     */
    @POST
    @Consumes("application/json")
    public Response createProduit(Produit produit) {
        int id = service.createProduit(produit);
        if(id == -1)
            throw new NotFoundException();
        else
            return Response.ok("created").build();
    }

    /**
     * Endpoint permettant de supprimer un produit
     * @param idProduit identifiant du produit à supprimer
     * @return une réponse "deleted" si le produit a été supprimé, une erreur NotFound sinon
     */
    @DELETE
    @Path("{id}")
    public Response deleteProduit(@PathParam("id") int idProduit) {
        if(!service.deleteProduit(idProduit))
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }
}