/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.produs.Produs;
import services.interfaces.EditNewProductsServiceInterface;
import services.interfaces.ProductNotFoundException;

/**
 *
 * @author Manel
 */
public class EditNewProductsService implements EditNewProductsServiceInterface {

    EditNewProductsApplicator applicator;
    List<Produs> produse;

    public List<Produs> getProduse() {
        return produse;
    }

    public EditNewProductsService(List<Produs> produse) {
        this.produse = produse;
    }

    @Override
    public void changeOrder(int selectedIndex, int keyCode) {
        applicator.changeOrder(selectedIndex, keyCode);
    }

    @Override
    public void removeFromNewProducts(int selectedIndex) {
        applicator.removeNewProduct(selectedIndex);
    }

    private List<String> createRecommendedProducts(String search) {
        List<String> toReturn = new ArrayList<>();
        for (Produs p : produse) {
            if (p.nume.toLowerCase().contains(search.toLowerCase())) {
                toReturn.add(p.nume);
            }
        }
        return toReturn;
    }

    @Override
    public void addToNewProducts(String productName) throws Exception {
        Produs desiredProduct = null;
        for (Produs p : produse) {
            if (p.nume.toLowerCase().equals(productName.toLowerCase())) {
                desiredProduct = p;
                break;
            }
        }
        if (desiredProduct == null) {
            throw new ProductNotFoundException(createRecommendedProducts(productName));
        }
        applicator.addNewProduct(desiredProduct.nume);
    }

    public void setApplicator(EditNewProductsApplicator applicator) {
        this.applicator = applicator;
    }
}
