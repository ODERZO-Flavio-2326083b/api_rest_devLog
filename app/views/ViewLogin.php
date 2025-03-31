<?php

namespace views;

class ViewLogin
{
    public function show(): void
    {
        ob_start();
        $pseudo = htmlspecialchars($_GET['email'] ?? '', ENT_QUOTES, 'UTF-8');
        ?>

        <!doctype html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport"
                  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>Connexion</title>
        </head>
        <body>
            <h1>
                Connectez- vous !
            </h1>
            <form method="POST" action="">
                <label for="pseudo">E-mail</label> <br>
                <input
                        type="text"
                        id="pseudo"
                        name="pseudo"
                        value="<?= htmlspecialchars($pseudo, ENT_QUOTES, 'UTF-8') ?>"
                        required>
                <br>
                <label for="password">Mot de passe</label> <br>
                <input
                        type="password"
                        id="password"
                        name="password"
                        required>
                <br>
                <input type="submit" value="Se connecter">
            </form>
        </body>
        </html>

        <?php
    }
}