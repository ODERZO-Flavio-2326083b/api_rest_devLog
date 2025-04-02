<?php

namespace controllers;

/**
 * ControllerLogout
 * Gère la déconnexion de l'utilisateur.
 * @package controllers
 */
class ControllerLogout
{
    /**
     * Execute le contrôleur.
     *
     * Déconnecte l'utilisateur en détruisant la session et redirige vers la page de connexion.
     *
     * @return void
     */
    public function execute(): void
    {
        // Déconnexion de l'utilisateur
        session_destroy();

        // Redirection vers la page de connexion
        header('Location: index.php?action=login');
        exit;
    }
}