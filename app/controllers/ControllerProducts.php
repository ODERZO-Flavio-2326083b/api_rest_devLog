<?php

namespace controllers;

use models\ModelProducts;
use views\ViewProducts;

class ControllerProducts
{
    public function execute(): void
    {
        /*
        // Vérification de la connexion de l'utilisateur
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        $model = new ModelProducts();
        $paniers = $model->getPaniers(); // Récupération des paniers depuis le modèle
        */

        (new ViewProducts())->show();
    }
}
