<?php

require __DIR__ . '/../vendor/autoload.php';
require __DIR__ . '/../app/Application.php';

use app\Application;

// Démarrage de l'application
(new Application())->run();