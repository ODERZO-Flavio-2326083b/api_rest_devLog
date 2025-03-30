<?php

namespace views;

class ViewLayout
{

    public function __construct(
        private readonly string $title,
        private readonly string $content
    ) {
    }
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
                            <li><a href="/index.php?ctrl=homepage">Accueil</a></li>
                            <li><a href="/index.php?ctrl=products">Produits</a></li>
                            <li><a href="/index.php?ctrl=login">Connexion</a></li>
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