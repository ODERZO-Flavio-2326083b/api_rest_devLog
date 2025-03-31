<?php

namespace views;

use views\ViewLayout;

class ViewHomepage
{
    public function show(): void
    {
        ob_start();
        ?>
            <h1>Bienvenue sur la plateforme de la coopérative agricole !</h1>
        <section>
            <h2>Découvrez nos paniers de produits frais</h2>
            <p>Chaque semaine, nous vous proposons une sélection de fruits, légumes, œufs, volailles et fromages issus de nos exploitations locales.</p>
            <h3>Un engagement local et durable</h3>
            <p>En achetant nos paniers, vous soutenez les producteurs locaux et favorisez une alimentation saine et responsable.</p>
            <a href="/index.php?action=products" class="btn">Explorer nos paniers</a>
        </section>

        <?php
        (new ViewLayout("Accueil - Coopérative Agricole", ob_get_clean()))->show();
    }
}