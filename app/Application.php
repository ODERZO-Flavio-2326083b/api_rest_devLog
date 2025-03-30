<?php

namespace app;

use controllers\ControllerHomepage;
use Throwable;

class Application
{
    private function validateControllerName(string $name): string
    {
        return preg_replace('/[^a-zA-Z0-9]/', '', $name);
    }
    private function getControllerClass(): string
    {
        if (!empty($_GET['ctrl'])) {
            return 'controllers\\Controller' . ucfirst($this->validateControllerName($_GET['ctrl']));
        }

        return ControllerHomepage::class;
    }


    private function sendError(string $message, int $code): void
    {
        http_response_code($code);
        echo json_encode(['error' => $message, 'code' => $code]);
        exit();
    }
    public function run(): void
    {
        ob_start();
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