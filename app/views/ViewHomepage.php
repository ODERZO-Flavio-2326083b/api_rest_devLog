<?php

namespace views;

use views\ViewLayout;

/**
 * Class ViewHomepage
 * @package views
 *
 * Permet d'afficher la page d'accueil de la coopérative agricole.
 */
class ViewHomepage
{
    /**
     * Affiche la page d'accueil.
     *
     * Cette méthode génère le contenu de la page d'accueil et l'affiche à l'aide de la classe ViewLayout.
     *
     * @return void
     */
    public function show(): void
    {
        // Démarre la mise en tampon de sortie
        ob_start();
        ?>
            <h1>Bienvenue sur la plateforme de la coopérative agricole !</h1>
        <section>
            <h2>Découvrez nos paniers de produits frais</h2>
            <p>Chaque semaine, nous vous proposons une sélection de fruits, légumes, œufs, volailles et fromages issus de nos exploitations locales.</p>
            <h3>Un engagement local et durable</h3>
            <p>En achetant nos paniers, vous soutenez les producteurs locaux et favorisez une alimentation saine et responsable.</p>
            <a href="/index.php?action=baskets" class="btn">Explorer nos paniers</a>
        </section>

        <?php

        // Récupère le contenu mis en tampon et l'affiche
        (new ViewLayout("Accueil - Coopérative Agricole", ob_get_clean()))->show();
    }
}