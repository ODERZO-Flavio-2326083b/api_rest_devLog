<?php

namespace controllers;

use models\ModelOrders;
use models\ModelUser;
use views\ViewOrders;

/**
 * ControllerOrders
 * @package controllers
 */
class ControllerOrders
{
    /**
     * Execute le contrôleur.
     *
     * Démarre la session et vérifie si l'utilisateur est connecté.
     * Si l'utilisateur n'est pas connecté, il est redirigé vers la page de connexion.
     * Récupère les commandes de l'utilisateur et les détails d'une commande spécifique si un ID est fourni.
     * Affiche la vue des commandes.
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

        // Récupère l'utilisateur connecté
        $userId = $_SESSION['user_id'];
        $userModel = new ModelUser();
        $user = $userModel->getUserById($userId);

        // Récupérer les commandes de l'utilisateur
        $ordersModel = new ModelOrders();
        $userOrders = $ordersModel->getOrdersByUserId($userId);

        // Récupérer les détails d'une commande spécifique si un ID est fourni
        $orderDetails = null;
        if (isset($_GET['id'])) {
            $orderId = (int)$_GET['id'];

            $orderDetails = $ordersModel->getOrder($orderId);

        }

        // Afficher la vue des commandes
        (new ViewOrders($userOrders, $orderDetails, $user))->show();
    }
}