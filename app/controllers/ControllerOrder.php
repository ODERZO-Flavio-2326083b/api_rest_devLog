<?php

namespace controllers;

use models\ModelOrder;
use views\ViewOrder;

class ControllerOrder
{
    public function execute(): void
    {
        /*
        // Vérifier si l'utilisateur est connecté
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        $model = new ModelOrder();
        $commandes = $model->getCommandes(); // Récupération des commandes depuis le modèle
        */

        (new ViewOrder())->show();
    }
}