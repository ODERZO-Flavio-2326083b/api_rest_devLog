<?php

namespace views;

/**
 * ViewOrders
 * @package views
 *
 * Permet d'afficher les commandes d'un utilisateur avec leurs détails.
 */
class ViewOrders
{
    /**
     * @var array
     */
    private array $orders;
    /**
     * @var array|null
     */
    private ?array $orderDetails;
    /**
     * @var array
     */
    private array $user;

    /**
     * ViewOrders constructor.
     *
     * @param array $orders Liste des commandes
     * @param array|null $orderDetails Détails d'une commande spécifique
     * @param array $user Informations sur l'utilisateur
     */
    public function __construct(array $orders, ?array $orderDetails = null, array $user)
    {
        $this->orders = $orders;
        $this->orderDetails = $orderDetails;
        $this->user = $user;
    }

    /**
     * Affiche la vue des commandes avec leurs détails.
     *
     * @return void
     */
    public function show(): void
    {
        ob_start();
        ?>

        <h1>Mes Commandes</h1>

        <?php if (!empty($this->orders)): ?>
        <table border="1" cellpadding="5" cellspacing="0" id="commandes-table">
            <thead>
            <tr>
                <th>ID Commande</th>
                <th>Date de retrait</th>
                <th>Détails</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($this->orders as $order):
                $isSelected = ($this->orderDetails !== null && $this->orderDetails['id_commande'] == $order['id_commande']);
                ?>
                <tr id="commande-<?= $order['id_commande'] ?>">
                    <td><?= htmlspecialchars($order['id_commande']) ?></td>
                    <td><?= htmlspecialchars(date('d/m/Y', strtotime($order['date_retrait']))) ?></td>
                    <td><a href="?action=orders&id=<?= $order['id_commande'] ?>" class="details-link">Voir Détails</a></td>
                </tr>
                <?php if ($isSelected): ?>
                <tr id="details-commande-<?= $order['id_commande'] ?>" class="details-row">
                    <td colspan="3" style="padding: 10px;">
                        <div class="details-content">
                            <h3>Paniers inclus dans la commande #<?= htmlspecialchars($this->orderDetails['id_commande']) ?></h3>
                            <?php if (!empty($this->orderDetails['paniers'])): ?>
                                <table border="1" cellpadding="5" cellspacing="0" style="width: 100%;">
                                    <thead>
                                    <tr>
                                        <th>N° Panier</th>
                                        <th>Prix (€)</th>
                                        <th>Quantité Disponible</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <?php foreach ($this->orderDetails['paniers'] as $panier): ?>
                                        <tr>
                                            <td><?= htmlspecialchars($panier['id_panier']) ?></td>
                                            <td><?= htmlspecialchars($panier['prix']) ?></td>
                                            <td><?= htmlspecialchars($panier['qtt_panier_dispo']) ?></td>
                                        </tr>
                                    <?php endforeach; ?>
                                    </tbody>
                                </table>
                            <?php else: ?>
                                <p>Aucun panier dans cette commande.</p>
                            <?php endif; ?>
                        </div>
                    </td>
                </tr>
            <?php endif; ?>
            <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>Vous n'avez aucune commande pour le moment.</p>
    <?php endif; ?>

        <style>
            .details-row td {
                background-color: #f9f9f9;
            }
        </style>

        <?php

        // Récupère le contenu du tampon de sortie et l'affiche
        (new ViewLayout("Mes Commandes", ob_get_clean()))->show();
    }
}