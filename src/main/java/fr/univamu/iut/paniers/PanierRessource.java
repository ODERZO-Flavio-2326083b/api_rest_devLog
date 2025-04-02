package fr.univamu.iut.paniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.lang.annotation.Repeatable;
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

    /**
     * Constructeur permettant d'initialiser le service d'accès aux paniers
     */
    public PanierRessource( PanierService service ){
        this.service = service;
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
    @Path("{reference}")
    @Produces("application/json")
    public String getPanier( @PathParam("reference") int id_panier){

        String result = service.getPanierJSON(id_panier);

        // si le panier n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @PUT
    @Path("{reference}")
    @Consumes("application/json")
    public Response updatePanier(@PathParam("reference") int id_panier, Panier panier ){

        // si le panier n'a pas été trouvé
        if( ! service.updatePanier(id_panier, panier) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }

    @DELETE
    @Path("/{id_panier}/produits/{id_produit}")
    public Response deleteProduitFromPanier(@PathParam("id_panier") int id_panier,
                                            @PathParam("id_produit") int id_produit) {

        if( !service.deleteProduitFromPanier(id_panier, id_produit) )
            throw new NotFoundException();
        else
            return Response.ok("deleted").build();
    }

    @PUT
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

}
