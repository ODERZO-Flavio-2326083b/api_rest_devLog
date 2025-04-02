<?php

namespace models;

class ModelUser
{
    private $users = [];

    public function __construct()
    {
        // Charger les utilisateurs depuis l'API
        $this->users = $this->fetchUsersFromApi();
    }

    private function fetchUsersFromApi()
    {
        // URL de l'API qui fournit les données utilisateurs
        $apiUrl = "http://localhost:8080/produitsutilisateurs-1.0-SNAPSHOT/api/utilisateurs";

        // Initialisation de la connexion à l'API avec CURL
        $curlConnection = curl_init();

        // Définition des paramètres de la requête CURL
        $params = array(
            CURLOPT_URL => $apiUrl,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER => array('accept: application/json')
        );
        curl_setopt_array($curlConnection, $params);

        // Exécution de la requête HTTP avec CURL
        $response = curl_exec($curlConnection);

        // Vérification des erreurs
        if (!$response) {
            error_log("cURL Error: " . curl_error($curlConnection));
        }

        // Fermeture de la connexion
        curl_close($curlConnection);

        // Transformation du JSON récupéré en tableau associatif
        $users = json_decode($response, true);

        // Vérification que le décodage s'est bien passé
        if (json_last_error() !== JSON_ERROR_NONE) {
            error_log("JSON Error: " . json_last_error_msg());
            return [];
        }

        return $users;
    }

    public function authenticate(string $pseudo, string $password)
    {
        foreach ($this->users as $user) {
            if ($user['pseudo'] === $pseudo && $user['mdp'] === $password) {
                return [
                    'id' => $user['id'],
                    'pseudo' => $user['pseudo']
                ];
            }
        }
        return null; // Authentification échouée
    }

    public function getUserById($id)
    {
        foreach ($this->users as $user) {
            if ($user['id'] == $id) {
                return $user;
            }
        }

        return null;
    }
}