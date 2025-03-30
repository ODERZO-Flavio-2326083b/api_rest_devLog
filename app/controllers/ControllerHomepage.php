<?php

namespace controllers;

use views\ViewHomepage;

class ControllerHomepage
{
    public function execute(): void
    {
        (new ViewHomepage())->show();
    }
}