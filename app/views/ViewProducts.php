<?php

namespace views;

class ViewProducts
{
    private array $produits;

    public function __construct(array $produits)
    {
        $this->produits = $produits;
    }

    public function show(): void
    {
        ob_start();
        ?>

        <h1>Produits Disponibles</h1>

        <?php if (!empty($this->produits)): ?>
        <table border="1">
            <thead>
            <tr>
                <th>Nom</th>
                <th>Quantité</th>
                <th>Unité</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($this->produits as $produit): ?>
                <tr>
                    <td><?= htmlspecialchars($produit['nom']) ?></td>
                    <td><?= $produit['quantite'] ?></td>
                    <td><?= htmlspecialchars($produit['unite']) ?></td>
                </tr>
            <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>Aucun produit disponible pour le moment.</p>
    <?php endif; ?>

        <?php
        (new ViewLayout("Produits", ob_get_clean()))->show();
    }
}
