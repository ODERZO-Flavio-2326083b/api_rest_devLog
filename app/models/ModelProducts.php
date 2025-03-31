<?php

namespace models;

class ModelProducts
{
    private const API_URL = 'http://localhost:8080/paniers'; // URL de l'API

    public function getPaniers(): array
    {
        $ch = curl_init(self::API_URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Accept: application/json']); // Demande du JSON
        $response = curl_exec($ch);

        if ($response === false) {
            die("Erreur lors de la récupération des paniers : " . curl_error($ch));
        }

        curl_close($ch);
        return json_decode($response, true);
    }
}
