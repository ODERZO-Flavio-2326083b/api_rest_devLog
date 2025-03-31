<?php

namespace views;

class ViewOrder
{
    /*
    private array $commandes;

    public function __construct(array $commandes)
    {
        $this->commandes = $commandes;
    }
    */

    public function show(): void
    {
        ob_start();
        ?>

        <h1>Commandes</h1>

        <?php if (!empty($this->commandes)): ?>
            <div class="commandes-container">
                <?php foreach ($this->commandes as $commande): ?>
                    <div class="commande">
                        <h2>Commande #<?= htmlspecialchars($commande['id']) ?></h2>
                        <p><strong>Produits :</strong></p>
                        <ul>
                            <?php foreach ($commande['produits'] as $produit): ?>
                                <li><?= htmlspecialchars($produit['nom']) ?> - <?= $produit['quantite'] ?> <?= htmlspecialchars($produit['unite']) ?></li>
                            <?php endforeach; ?>
                        </ul>
                        <p><strong>Prix :</strong> <?= htmlspecialchars($commande['prix_total']) ?> €</p>
                        <p><strong>Date de retrait :</strong> <?= htmlspecialchars($commande['date_retrait']) ?></p>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php else: ?>
            <p>Aucune commande disponible pour le moment.</p>
        <?php endif; ?>

        <?php
        (new ViewLayout("Commandes", ob_get_clean()))->show();
    }

}