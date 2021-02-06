/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.util.List;

/**
 *
 * @author Manel
 */
public class ProductNotFoundException extends Exception {

    private final List<String> produseRecomandate;

    public List<String> getProduseRecomandate() {
        return produseRecomandate;
    }

    public ProductNotFoundException(List<String> produseRecomandate) {
        super("Nu am gasit produsul in lista de produse sau a fost deja adaugat ca produs similar");
        this.produseRecomandate = produseRecomandate;
    }
}
