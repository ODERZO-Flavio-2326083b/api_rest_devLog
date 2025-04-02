package fr.univamu.iut.commandes;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class CommandeApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter l'API Paniers au moment de la création de la ressource
     * @return une instance de l'API avec l'url à utiliser
     */
    @Produces
    private PanierRepositoryInterface connectPanierApi(){
        return new PanierRepositoryAPI("http://localhost:8080/paniers-1.0-SNAPSHOT/api/");
    }

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création de la
     * ressource
     * @return un objet implémentant l'interface CommandeRepositoryInterface utilisée pour accéder aux données des
     * commandes, voire les modifier
     */
    @Produces
    private CommandeRepositoryInterface openDbConnection() {
        CommandeRepositoryMariadb db = null;

        try {
            db = new CommandeRepositoryMariadb("jdbc:mariadb://mysql-matheobertin.alwaysdata.net/matheobertin_cooperative", "380594", "archilog25");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param commandeRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes CommandeRepositoryInterface commandeRepo) {
        commandeRepo.close();
    }

    /**
     * Méthode permettant de fermer la connexion avec l'API Paniers lorsque l'application est arrêtée
     * @param panierRepo la connexion à l'API instanciée dans la méthode @connectPanierApi
     */
    private void closePanierApi(@Disposes PanierRepositoryInterface panierRepo) {
        panierRepo.close();
    }
}