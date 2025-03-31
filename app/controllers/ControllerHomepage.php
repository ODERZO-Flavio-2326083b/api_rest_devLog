<?php

namespace controllers;

use views\ViewHomepage;

class ControllerHomepage
{
    public function execute(): void
    {
        // Vérifier si l'utilisateur est connecté
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        (new ViewHomepage())->show();
    }
}