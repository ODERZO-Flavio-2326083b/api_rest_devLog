<?php

namespace controllers;

use views\ViewLogin;

class ControllerLogin
{
    public function execute(): void
    {
        (new ViewLogin())->show();
    }
}