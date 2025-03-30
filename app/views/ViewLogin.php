<?php

namespace views;

class ViewLogin
{
    public function show(): void
    {
        ob_start();
        $email = htmlspecialchars($_GET['email'] ?? '', ENT_QUOTES, 'UTF-8');
        ?>
        <h1>
            Connectez- vous !
        </h1>
        <form method="POST" action="">
            <label for="email">E-mail</label> <br>
            <input
                type="email"
                id="email"
                name="email"
                value="<?= htmlspecialchars($email, ENT_QUOTES, 'UTF-8') ?>"
                required>
            <br>
            <label for="password">Mot de passe</label> <br>
            <input
                type="password"
                id="password"
                name="password"
                required>
            <br>
            <input type="submit" value="Connexion">
        </form>

        <?php
        (new ViewLayout("Connexion", ob_get_clean()))->show();
    }
}