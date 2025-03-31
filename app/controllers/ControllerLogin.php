<?php

namespace controllers;

use models\ModelUser;
use views\ViewLogin;

class ControllerLogin
{
    public function execute(): void
    {
        /*
        // Si l'utilisateur est déjà connecté, le rediriger vers l'accueil
        if (isset($_SESSION['user_id'])) {
            header('Location: index.php?action=homepage');
            exit;
        }

        $error = null;

        // Traitement du formulaire de connexion
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $pseudo = $_POST['pseudo'] ?? '';
            $password = $_POST['password'] ?? '';

            $model = new ModelUser();
            $user = $model->authenticate($pseudo, $password);

            if ($user) {
                // Authentification réussie
                $_SESSION['user_id'] = $user['id'];
                $_SESSION['user_pseudo'] = $user['pseudo'];
                // $_SESSION['is_admin'] = $user['is_admin'] ?? false;

                header('Location: index.php?action=homepage');
                exit;
            } else {
                $error = 'Identifiants incorrects';
            }
        }
        */

        (new ViewLogin())->show();
    }
}