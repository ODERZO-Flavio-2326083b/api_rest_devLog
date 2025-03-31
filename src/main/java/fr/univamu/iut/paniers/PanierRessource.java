package fr.univamu.iut.paniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

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
    public @Inject PanierRessource(PanierRepositoryInterface panierRepo, ProduitClientInterface produitClient) {
        this.service = new PanierService(panierRepo, produitClient);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux paniers
     */
    public PanierRessource( PanierService service ){
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllPaniers() {
        return service.getAllPaniersJSON();
    }

    @GET
    @Path("coucou")
    @Produces("application/json")
    public String getAllProduits() {
        return service.getAllProduitsJSON();
    }

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


}
