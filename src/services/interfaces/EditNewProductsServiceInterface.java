/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

/**
 *
 * @author Manel
 */
public interface EditNewProductsServiceInterface {

    void changeOrder(int selectedIndex, int keyCode);

    void removeFromNewProducts(int selectedIndex);

    void addToNewProducts(String productName) throws Exception;
}
