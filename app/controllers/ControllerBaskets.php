<?php

namespace controllers;

use models\ModelBaskets;
use views\ViewBaskets;

/**
 * ControllerBaskets
 * @package controllers
 */
class ControllerBaskets
{
    /**
     * Execute le contrôleur.
     *
     * Démarre la session et vérifie si l'utilisateur est connecté.
     * Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
     * Récupère les paniers de l'utilisateur et les détails d'un panier spécifique si un ID est fourni.
     *
     * Affiche la vue des paniers avec les données récupérées et gère le passage de l'ID utilisateur pour passer commande.
     *
     * @var ModelBaskets
     */
    public function execute(): void
    {
        session_start();

        // Vérification de la connexion de l'utilisateur
        if (!isset($_SESSION['user_id'])) {
            header('Location: index.php?action=login');
            exit;
        }

        $userId = $_SESSION['user_id'];

        // Récupération des paniers
        $model = new ModelBaskets();
        $baskets = $model->getBaskets();

        // Récupération des détails d'un panier spécifique si un ID est fourni
        $basketDetails = null;
        if (isset($_GET['id']) && is_numeric($_GET['id'])) {
            $basketDetails = $model->getBasket((int)$_GET['id']);
        }

        // Affichage de la vue des paniers
        (new ViewBaskets($baskets, $basketDetails, $userId))->show();
    }
}
