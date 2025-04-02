package fr.univamu.iut.commandes;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PanierRepositoryAPI implements PanierRepositoryInterface {

    /**
     * URL de l'API des paniers
     */
    String url;

    /**
     * Constructeur initialisant l'url de l'API
     */
    public PanierRepositoryAPI(String url) {
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public Panier getPanier(int id_panier)
    {
        Panier myPanier = null;

        Client client = ClientBuilder.newClient();

        WebTarget panierResource = client.target(url);

        WebTarget panierEndpoint = panierResource.path("paniers/" + id_panier);

        Response response = panierEndpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {

            String json = response.readEntity(String.class);
            try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
                // désérialisation manuelle en raison d'un problème lors de la création d'un objet date.
                JsonObject jsonObject = jsonReader.readObject();

                myPanier = new Panier();
                myPanier.setId_panier(jsonObject.getInt("id_panier"));

                String dateStr = jsonObject.getString("derniere_maj");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(dateStr);
                myPanier.setDerniere_maj(date);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        client.close();
        return myPanier;
    }

    @Override
    public ArrayList<Panier> getAllPaniers() {
        return null;
    }

    @Override
    public List<Integer> getProduitIdsOfPanier(int id_panier) {
        return List.of();
    }

    @Override
    public boolean updatePanier(int id_panier, float prix, int qtt_panier_dispo) {
        boolean result = false;

        Panier updatedPanier = new Panier(id_panier, prix, qtt_panier_dispo, java.sql.Date.valueOf(java.time.LocalDate.now()));

        Client client = ClientBuilder.newClient();

        WebTarget panierResource = client.target(url);

        WebTarget panierEndpoint = panierResource.path("paniers/" + id_panier);

        Response response = panierEndpoint.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedPanier, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            result = true;
        }

        client.close();

        return result;
    }

    @Override
    public boolean deleteProduitFromPanier(int id_panier, int id_produit) {
        return false;
    }

    @Override
    public boolean updateProduitOfPanier(int id_panier, int id_produit, int quantite) {
        return false;
    }

}
