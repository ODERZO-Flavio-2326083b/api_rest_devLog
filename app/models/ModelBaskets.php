<?php

namespace models;

/**
 * Class ModelBaskets
 * @package models
 * Cette classe est responsable de la gestion des paniers.
 */
class ModelBaskets
{
    /**
     * @var string URL de base de l'API pour récupérer les paniers
     */
    private string $apiUrl = "http://localhost:8080/paniers-1.0-SNAPSHOT/api";

    /**
     * Récupère la liste des paniers.
     * @return array
     */
    public function getBaskets(): array
    {
        return $this->fetchData($this->apiUrl . "/paniers");
    }

    /**
     * Récupère un panier spécifique avec ses produits à partir de son ID.
     * @param int $id
     * @return array
     */
    public function getBasket(int $id): array
    {
        return $this->fetchData($this->apiUrl . "/paniers/" . $id);
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
        if (!$response) {
            error_log("cURL Error: " . curl_error($curl));
            return [];
        }

        // Fermeture de la connexion
        curl_close($curl);

        // Transformation du JSON récupéré en tableau associatif
        $data = json_decode($response, true);
        if (json_last_error() !== JSON_ERROR_NONE) {
            error_log("JSON Error: " . json_last_error_msg());
            return [];
        }

        return $data;
    }
}
