<?php

namespace controllers;

use models\ModelBaskets;
use views\ViewBaskets;

class ControllerBaskets
{
    public function execute(): void
    {

        // Vérification de la connexion de l'utilisateur
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        $model = new ModelBaskets();
        $baskets = $model->getBaskets(); // Récupération des paniers depuis le modèle

        (new ViewBaskets($baskets))->show();
    }

}