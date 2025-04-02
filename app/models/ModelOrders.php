<?php

namespace models;

/**
 * ModelOrders
 * Modèle pour gérer les commandes.
 *
 * @package models
 */
class ModelOrders
{
    /**
     * URL de l'API pour récupérer les commandes.
     * @var string
     */
    private string $apiUrl = "http://localhost:8080/commandes-1.0-SNAPSHOT/api";

    /**
     * Récupère la liste des commandes.
     * @return array
     */
    public function getOrders(): array
    {
        return $this->fetchData($this->apiUrl . "/commandes");
    }

    /**
     * Récupère une commande spécifique par ID avec ses paniers.
     * @param int $id
     * @return array
     */
    public function getOrder(int $id): array
    {
        return $this->fetchData($this->apiUrl . "/commandes/" . $id);
    }

    /**
     * Récupère les commandes d'un utilisateur spécifique par ID.
     * @param int $userId
     * @return array
     */
    public function getOrdersByUserId(int $userId): array
    {
        $allOrders = $this->getOrders();
        return array_filter($allOrders, function($order) use ($userId) {
            return $order['id_utilisateur'] == $userId;
        });
    }

    /**
     * Vérifie si une commande appartient à un utilisateur spécifique.
     * @param int $orderId
     * @param int $userId
     * @return bool
     */
    public function isOrderOwnedByUser(int $orderId, int $userId): bool
    {
        $order = $this->getOrder($orderId);
        return !empty($order) && isset($order['id_utilisateur']) && $order['id_utilisateur'] == $userId;
    }

    /**
     * Effectue une requête CURL et retourne le résultat sous forme de tableau.
     * @param string $url
     * @return array
     */
    private function fetchData(string $url): array
    {
        // Initialisation de la connexion à l'API avec CURL
        $curl = curl_init();

        // Définition des paramètres de la requête CURL
        curl_setopt_array($curl, [
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER => ['Accept: application/json']
        ]);

        // Exécution de la requête HTTP avec CURL
        $response = curl_exec($curl);

        // Vérification des erreurs
        if (!$response) {
            error_log("cURL Error: " . curl_error($curl));
            return [];
        }

        // Fermeture de la connexion
        curl_close($curl);

        // Transformation du JSON récupéré en tableau associatif
        $data = json_decode($response, true);

        // Vérification que le décodage s'est bien passé
        if (json_last_error() !== JSON_ERROR_NONE) {
            error_log("JSON Error: " . json_last_error_msg());
            return [];
        }

        return $data;
    }
}