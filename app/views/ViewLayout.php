<?php

namespace views;

/**
 * Class ViewLayout
 * @package views
 *
 * Permet d'afficher le layout de l'application.
 */
class ViewLayout
{

    /**
     * ViewLayout constructor.
     *
     * @param string $title Titre de la page
     * @param string $content Contenu de la page
     */
    public function __construct(
        private readonly string $title,
        private readonly string $content
    ) {
    }

    /**
     * Affiche le layout de l'application.
     *
     * @return void
     */
    public function show(): void
    {
        ?>

        <!DOCTYPE html>
        <html lang="fr">
            <head>
                <meta charset="utf-8"/>
                <title><?= $this->title; ?></title>
                <meta name="description" content="Application pour une coopérative vendant ses produits en ligne">
            </head>
            <body>
                <header>
                    <nav>
                        <ul>
                            <li><a href="/index.php?action=homepage">Accueil</a></li>
                            <li><a href="/index.php?action=products">Nos produits</a></li>
                            <li><a href="/index.php?action=baskets">Nos paniers</a></li>
                            <li><a href="/index.php?action=orders">Commandes</a></li>
                            <li><a href="/index.php?action=logout">Se déconnecter</a></li>
                        </ul>
                    </nav>
                </header>
                <main>
                    <?= $this->content; ?>
                </main>
                <footer>
                    <p>La coopérative agricole</p>
                </footer>
            </body>

        <?php
    }
}