<?php

namespace controllers;

use models\ModelProducts;
use views\ViewProducts;

/**
 * ControllerProducts
 * @package controllers
 */
class ControllerProducts
{
    /**
     * Execute le contrôleur.
     *
     * Démarre la session et vérifie si l'utilisateur est connecté.
     * Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
     * Affiche la vue des produits.
     *
     * @return void
     */
    public function execute(): void
    {

        // Vérification de la connexion de l'utilisateur
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        // Récupération des produits
        $model = new ModelProducts();
        $produits = $model->getProducts(); // Récupération des paniers depuis le modèle

        // Affichage de la vue des produits
        (new ViewProducts($produits))->show();
    }
}
