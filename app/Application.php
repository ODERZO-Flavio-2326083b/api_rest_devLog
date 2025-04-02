<?php

namespace app;

use controllers\ControllerHomepage;
use Throwable;

/**
 * Classe principale de l'application
 *
 * Cette classe gère le démarrage de l'application, la gestion des erreurs et la validation des contrôleurs.
 * @package app
 */
class Application
{
    /**
     * Valide le nom du contrôleur
     *
     * @param string $name
     * @return string
     */
    private function validateControllerName(string $name): string
    {
        return preg_replace('/[^a-zA-Z0-9]/', '', $name);
    }

    /**
     * Récupère le nom de la classe du contrôleur à exécuter
     *
     * @return string
     */
    private function getControllerClass(): string
    {
        if (!empty($_GET['action'])) {
            return 'controllers\\Controller' . ucfirst($this->validateControllerName($_GET['action']));
        }

        return ControllerHomepage::class;
    }

    /**
     * Envoie une réponse d'erreur au format JSON
     *
     * @param string $message Message d'erreur
     * @param int $code Code d'erreur HTTP
     * @return void
     */
    private function sendError(string $message, int $code): void
    {
        http_response_code($code);
        echo json_encode(['error' => $message, 'code' => $code]);
        exit();
    }

    /**
     * Démarre l'application
     *
     * Cette méthode démarre la session, gère les erreurs et exécute le contrôleur approprié.
     *
     * @return void
     */
    public function run(): void
    {
        ob_start();
        session_start();

        try {
            $controllerClass = $this->getControllerClass();


            if (!class_exists($controllerClass)) {
                $this->sendError('Le contrôleur demandé n\'existe pas.', 404);
            }


            $controller = new $controllerClass();
            $controller->execute();
        } catch(Throwable $e) {
            $this->sendError($e->getMessage(), $e->getCode());
        }

        $content = ob_get_contents();
        ob_end_clean();

        echo $content;
    }
}