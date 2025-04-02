<?php

namespace views;

/**
 * ViewBaskets
 * @package views
 *
 * Permet d'afficher la liste des paniers et leurs détails et de passer une commande.
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
     * @var string
     */
    private string $userId;

    /**
     * ViewBaskets constructor.
     *
     * @param array $baskets Liste des paniers
     * @param array|null $basketDetails Détails d'un panier spécifique
     * @param string $userId ID de l'utilisateur
     */
    public function __construct(array $baskets, ?array $basketDetails = null, string $userId)
    {
        $this->baskets = $baskets;
        $this->basketDetails = $basketDetails;
        $this->userId = $userId;
    }

    /**
     * Affiche la vue des paniers avec leurs détails et permet de passer une commande.
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
                <th>✔</th>
                <th>N°</th>
                <th>Prix (€)</th>
                <th>Quantité Disponible</th>
                <th>Dernière Mise à Jour</th>
                <th>Détails</th>
            </tr>
            </thead>
            <tbody>

            <?php foreach ($this->baskets as $basket): ?>
                <tr id="panier-<?= $basket['id_panier'] ?>">
                    <td>
                        <input type="checkbox" class="select-basket" value="<?= $basket['id_panier'] ?>">
                    </td>
                    <td><?= htmlspecialchars($basket['id_panier']) ?></td>
                    <td><?= htmlspecialchars($basket['prix']) ?></td>
                    <td><?= htmlspecialchars($basket['qtt_panier_dispo']) ?></td>
                    <td><?= htmlspecialchars($basket['derniere_maj']) ?></td>
                    <td><a href="?action=baskets&id=<?= $basket['id_panier'] ?>" class="details-link">Voir Détails</a></td>
                </tr>

                <?php if ($this->basketDetails !== null && $this->basketDetails['id_panier'] == $basket['id_panier']): ?>
                    <tr id="details-panier-<?= $basket['id_panier'] ?>" class="details-row">
                        <td colspan="6" style="padding: 10px;">
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

        <!-- Formulaire pour la commande -->
        <div style="margin-top: 20px;">
            <label for="date-retrait">Date de retrait :</label>
            <input type="date" id="date-retrait" required>

            <label for="relai">Relai :</label>
            <input type="text" id="relai" placeholder="Lieu de retrait" required>

            <button id="submit-order" data-user-id="<?= $this->userId ?>">Passer commande</button>
        </div>

        <script>
            document.getElementById("submit-order").addEventListener("click", function () {
                const selectedBaskets = [];
                document.querySelectorAll(".select-basket:checked").forEach(checkbox => {
                    selectedBaskets.push(parseInt(checkbox.value));
                });

                console.log(selectedBaskets);

                if (selectedBaskets.length === 0) {
                    alert("Veuillez sélectionner au moins un panier.");
                    return;
                }

                const dateRetrait = document.getElementById("date-retrait").value;
                const relai = document.getElementById("relai").value;
                const userId = document.getElementById("submit-order").getAttribute("data-user-id");

                if (!dateRetrait || !relai) {
                    alert("Veuillez renseigner tous les champs.");
                    return;
                }

                const orderData = {
                    date_retrait: dateRetrait,
                    id_utilisateur: parseInt(userId),
                    relai: relai,
                    id_paniers: selectedBaskets
                };

                fetch("http://localhost:8080/commandes-1.0-SNAPSHOT/api/commandes", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(orderData)
                })
                    .then(response => {
                        if (!response.created) {
                            return;
                        }
                        return response.json();
                    })
                    .then(data => {
                        alert("Commande passée avec succès !");
                        console.log(data);
                    })
                    .catch(error => {
                        console.error("Erreur lors de la commande :", error);
                    });
            });
        </script>

        <?php
        // Récupère le contenu mis en tampon et l'affiche
        (new ViewLayout("Paniers", ob_get_clean()))->show();
    }
}
