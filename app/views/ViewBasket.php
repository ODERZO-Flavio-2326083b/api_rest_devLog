<?php

namespace views;

class ViewBasket
{
    /*
    private array $commandeActuelle;

    public function __construct(array $commandeActuelle)
    {
        $this->commandeActuelle = $commandeActuelle;
    }
    */

    public function show(): void
    {
        ob_start();
        ?>
            <h1>Votre panier</h1>

        <?php if (!empty($this->commandeActuelle)): ?>
            <div class="panier-container">
                <h2>Panier #<?= htmlspecialchars($this->commandeActuelle['id']) ?></h2>
                <p><strong>Produits :</strong></p>
                <ul>
                    <?php foreach ($this->commandeActuelle['produits'] as $produit): ?>
                        <li><?= htmlspecialchars($produit['nom']) ?> - <?= $produit['quantite'] ?> <?= htmlspecialchars($produit['unite']) ?></li>
                    <?php endforeach; ?>
                </ul>
                <p><strong>Prix :</strong> <?= htmlspecialchars($this->commandeActuelle['prix_total']) ?> €</p>
                <p><strong>Date de retrait :</strong> <?= htmlspecialchars($this->commandeActuelle['date_retrait']) ?></p>
            </div>
        <?php else: ?>
            <p>Aucun produit dans le panier.</p>
        <?php endif; ?>

        <?php
        (new ViewLayout("Panier", ob_get_clean()))->show();
    }

}