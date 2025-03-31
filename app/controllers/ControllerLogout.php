<?php

namespace controllers;

class ControllerLogout
{
    public function execute(): void
    {
        // Déconnexion de l'utilisateur
        session_destroy();

        // Redirection vers la page de connexion
        header('Location: index.php?action=login');
        exit;
    }
}