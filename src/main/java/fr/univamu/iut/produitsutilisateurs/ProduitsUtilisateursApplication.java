package fr.univamu.iut.produitsutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class ProduitsUtilisateursApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface ProduitsUtilisateursRepositoryInterface utilisée
     *          pour accéder aux données des produits, voire les modifier
     */
    @Produces
    private ProduitsUtilisateursRepositoryInterface openDbConnection(){
        ProduitsUtilisateursRepositoryMariadb db = null;

        try{
            db = new ProduitsUtilisateursRepositoryMariadb(
                    "jdbc:mariadb://mysql-matheobertin.alwaysdata.net/matheobertin_cooperative",
                    "380594",
                    "archilog25"
            );
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param produitutilisateurRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes ProduitsUtilisateursRepositoryInterface produitutilisateurRepo ) {
        produitutilisateurRepo.close();
    }
}