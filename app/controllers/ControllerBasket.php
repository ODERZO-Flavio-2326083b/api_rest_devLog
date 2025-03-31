<?php

namespace controllers;

class ControllerBasket
{
    public function execute(): void
    {
        /*
        // Vérifier si l'utilisateur est connecté
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }
        */

        // Afficher le panier
        (new \views\ViewBasket())->show();
    }
}