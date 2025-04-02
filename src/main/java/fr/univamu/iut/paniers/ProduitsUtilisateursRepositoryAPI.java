package fr.univamu.iut.paniers;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Closeable;
import java.util.ArrayList;

public class ProduitsUtilisateursRepositoryAPI implements Closeable, ProduitsUtilisateursRepositoryInterface {

    /**
     * URL de l'API des produits et utilisateurs
     */
    String url;

    public ProduitsUtilisateursRepositoryAPI(String url) {
        this.url = url;
    }

    @Override
    public Produit getProduit(int id) {
        Produit myProduit = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/" + id);
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            myProduit = response.readEntity(Produit.class);
        }

        client.close();
        return myProduit;
    }

    @Override
    public ArrayList<Produit> getAllProduits() {
        ArrayList<Produit> produits = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/");
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            produits = response.readEntity(new GenericType<>() {});
        }

        client.close();
        return produits;
    }

    @Override
    public boolean updateProduit(int id, String nom, int quantite, String unite) {
        boolean result = false;

        Produit updatedProduit = new Produit(id, nom, quantite, unite);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/" + id);
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedProduit, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            result = true;
        }

        client.close();
        return result;
    }

    @Override
    public int createProduit(String nom, int quantite, String unite) {
        int result = 0;

        Produit newProduit = new Produit();
        newProduit.setNom(nom);
        newProduit.setQuantite(quantite);
        newProduit.setUnite(unite);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/");
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(newProduit, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            result = response.readEntity(Integer.class);
        }

        client.close();
        return result;
    }

    @Override
    public boolean deleteProduit(int idProduit) {
        boolean result = false;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget targetEndpoint = target.path("produits/");
        Response response = targetEndpoint.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(idProduit, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            result = true;
        }

        client.close();
        return result;
    }

    @Override
    public void close() {

    }
}
