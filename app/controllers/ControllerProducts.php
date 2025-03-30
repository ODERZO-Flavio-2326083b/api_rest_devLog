<?php

namespace controllers;

class ControllerProducts
{
    public function execute(): void
    {
        (new \views\ViewProducts())->show();
    }

}