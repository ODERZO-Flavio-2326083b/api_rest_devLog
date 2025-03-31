package fr.univamu.iut.paniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class PanierApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface PanierRepositoryInterface utilisée
     *          pour accéder aux données des paniers, voire les modifier
     */
    @Produces
    private PanierRepositoryInterface openDbConnection(){
        PanierRepositoryMariadb db = null;

        try{
            db = new PanierRepositoryMariadb("jdbc:mariadb://mysql-matheobertin.alwaysdata.net/matheobertin_cooperative", "380594", "archilog25");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param PanierRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes PanierRepositoryInterface PanierRepo ) {
        PanierRepo.close();
    }

    @Produces
    private ProduitClientInterface openProduitClient() {
        return new ProduitClient();
    }

    /**
     * Méthode pour fermer la connexion à l'API des produits
     * @param ProduitClient la connexion à l'API des produits à fermer
     */
    private void closeProduitClient(@Disposes ProduitClientInterface ProduitClient ) {
        ProduitClient.close();
    }
}