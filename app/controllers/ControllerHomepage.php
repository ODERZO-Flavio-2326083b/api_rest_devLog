<?php

namespace controllers;

use views\ViewHomepage;

/**
 * ControllerHomepage
 * @package controllers
 */
class ControllerHomepage
{
    /**
     * Execute le contrôleur.
     *
     * Démarre la session et vérifie si l'utilisateur est connecté.
     * Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
     * Affiche la vue de la page d'accueil.
     *
     * @return void
     */
    public function execute(): void
    {
        // Vérifier si l'utilisateur est connecté
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        // Afficher la page d'accueil
        (new ViewHomepage())->show();
    }
}