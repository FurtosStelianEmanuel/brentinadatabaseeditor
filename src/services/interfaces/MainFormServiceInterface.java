/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.io.IOException;
import javax.naming.directory.SchemaViolationException;
import models.produs.Produs;

/**
 *
 * @author Manel
 */
public interface MainFormServiceInterface {

    void save() throws ClassNotFoundException, IOException;

    void load() throws ClassNotFoundException, IOException, SchemaViolationException;

    void reloadDatabase();

    void saveAndReload();

    void addProdus(String nume) throws Exception;

    void deleteProdus(Produs produs) throws IndexOutOfBoundsException, UnsupportedOperationException;

    int filterProducts(String search);

    Produs getProdusWithFilter(int index) throws IndexOutOfBoundsException;

    void editNewProducts();

    void editProdus(int index);
}
