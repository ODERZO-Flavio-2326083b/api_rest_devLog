<?php

namespace views;

class ViewBaskets
{
    private array $paniers;

    public function __construct(array $paniers)
    {
        $this->paniers = $paniers;
    }

    public function show(): void
    {
        ob_start();
        ?>

        <h1>Paniers Disponibles</h1>

        <?php if (!empty($this->paniers)): ?>
        <div class="paniers-container">
            <?php foreach ($this->paniers as $panier): ?>
                <div class="panier">
                    <h2>Panier #<?= htmlspecialchars($panier['id']) ?></h2>
                    <p><strong>Produits :</strong></p>
                    <ul>
                        <?php foreach ($panier['produits'] as $produit): ?>
                            <li><?= htmlspecialchars($produit['nom']) ?> - <?= $produit['quantite'] ?> <?= htmlspecialchars($produit['unite']) ?></li>
                        <?php endforeach; ?>
                    </ul>
                    <p><strong>Prix :</strong> <?= htmlspecialchars($panier['prix_total']) ?> €</p>
                    <p><strong>Date de retrait :</strong> <?= htmlspecialchars($panier['date_retrait']) ?></p>
                </div>
            <?php endforeach; ?>
        </div>
    <?php else: ?>
        <p>Aucun panier disponible pour le moment.</p>
    <?php endif; ?>

        <?php
        (new ViewLayout("Produits", ob_get_clean()))->show();
    }
}
