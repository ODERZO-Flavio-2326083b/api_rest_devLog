<?php

namespace views;

/**
 * Class ViewProducts
 * @package views
 *
 * Permet de visualiser les produits disponibles.
 */
class ViewProducts
{
    /**
     * ViewProducts constructor.
     *
     * @param array $produits Liste des produits disponibles
     */
    private array $produits;

    /**
     * ViewProducts constructor.
     *
     * @param array $produits Liste des produits
     */
    public function __construct(array $produits)
    {
        $this->produits = $produits;
    }

    /**
     * Affiche la vue des produits disponibles.
     *
     * @return void
     */
    public function show(): void
    {
        // Démarre la mise en tampon de sortie
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
        // Récupère le contenu mis en tampon et l'affiche
        (new ViewLayout("Produits", ob_get_clean()))->show();
    }
}
