<?php

namespace views;

/**
 * ViewBaskets
 * @package views
 *
 * Permet d'afficher la liste des paniers et leurs détails.
 */
class ViewBaskets
{
    /**
     * @var array
     */
    private array $baskets;
    /**
     * @var array|null
     */
    private ?array $basketDetails;

    /**
     * ViewBaskets constructor.
     *
     * @param array $baskets Liste des paniers
     * @param array|null $basketDetails Détails d'un panier spécifique
     */
    public function __construct(array $baskets, ?array $basketDetails = null)
    {
        $this->baskets = $baskets;
        $this->basketDetails = $basketDetails;
    }

    /**
     * Affiche la vue des paniers avec leurs détails.
     *
     * @return void
     */
    public function show(): void
    {
        // Démarre la mise en tampon de sortie
        ob_start();
        ?>

        <h1>Liste des Paniers</h1>

        <?php if (!empty($this->baskets)): ?>
        <table border="1" cellpadding="5" cellspacing="0" id="paniers-table">
            <thead>
            <tr>
                <th>N°</th>
                <th>Prix (€)</th>
                <th>Quantité Disponible</th>
                <th>Dernière Mise à Jour</th>
                <th>Détails</th>
            </tr>
            </thead>
            <tbody>

            <?php foreach ($this->baskets as $basket):
                $isSelected = ($this->basketDetails !== null && $this->basketDetails['id_panier'] == $basket['id_panier']);
                ?>
                <tr id="panier-<?= $basket['id_panier'] ?>">
                    <td><?= htmlspecialchars($basket['id_panier']) ?></td>
                    <td><?= htmlspecialchars($basket['prix']) ?></td>
                    <td><?= htmlspecialchars($basket['qtt_panier_dispo']) ?></td>
                    <td><?= htmlspecialchars($basket['derniere_maj']) ?></td>
                    <td><a href="?action=baskets&id=<?= $basket['id_panier'] ?>" class="details-link">Voir Détails</a></td>
                </tr>
                <?php if ($isSelected): ?>
                <tr id="details-panier-<?= $basket['id_panier'] ?>" class="details-row">
                    <td colspan="5" style="padding: 10px;">
                        <div class="details-content">
                            <h3>Produits inclus dans le panier #<?= htmlspecialchars($this->basketDetails['id_panier']) ?></h3>
                            <?php if (!empty($this->basketDetails['produits'])): ?>
                                <table border="1" cellpadding="5" cellspacing="0" style="width: 100%;">
                                    <thead>
                                    <tr>
                                        <th>Nom du Produit</th>
                                        <th>Quantité</th>
                                        <th>Unité</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php foreach ($this->basketDetails['produits'] as $produit): ?>
                                        <tr>
                                            <td><?= htmlspecialchars($produit['nom']) ?></td>
                                            <td><?= $produit['quantite'] ?></td>
                                            <td><?= htmlspecialchars($produit['unite']) ?></td>
                                        </tr>
                                    <?php endforeach; ?>
                                    </tbody>
                                </table>
                            <?php else: ?>
                                <p>Aucun produit dans ce panier.</p>
                            <?php endif; ?>
                        </div>
                    </td>
                </tr>
            <?php endif; ?>
            <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>Aucun panier disponible pour le moment.</p>
    <?php endif; ?>

        <?php

        // Récupère le contenu mis en tampon et l'affiche
        (new ViewLayout("Paniers", ob_get_clean()))->show();
    }
}