package fr.univamu.iut.paniers;

import java.util.List;

public interface ProduitClientInterface {

    public List<Produit> getProduits();

    public void close();
}
