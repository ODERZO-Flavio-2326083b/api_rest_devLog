<?php

namespace views;

class ViewProducts
{
    public function show(): void
    {
        ob_start();
        ?>
            <p>
                Products
            </p>

        <?php
        (new ViewLayout("Products", ob_get_clean()))->show();
    }
}