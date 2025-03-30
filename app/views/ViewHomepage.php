<?php

namespace views;

use views\ViewLayout;

class ViewHomepage
{
    public function show(): void
    {
        ob_start();
        ?>
            <p>
                Hello World !
            </p>

        <?php
        (new ViewLayout("Homepage", ob_get_clean()))->show();
    }
}