/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultListModel;
import models.database.DatabaseModel;
import models.produs.Produs;
import services.interfaces.InitialCompleteInterface;
import views.edit.EditNewProductsForm;

/**
 *
 * @author Manel
 */
public class EditNewProductsApplicator implements InitialCompleteInterface {

    EditNewProductsForm form;

    public EditNewProductsApplicator(EditNewProductsForm form) {
        this.form = form;
    }

    private Produs getProductWithId(UUID id, List<Produs> produse) {
        for (Produs produs : produse) {
            if (produs.id.equals(id)) {
                return produs;
            }
        }
        return null;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        DatabaseModel databaseModel = (DatabaseModel) formDataObject;
        DefaultListModel listModel = (DefaultListModel) form.jList1.getModel();
        for (Object uuidString : databaseModel.newProducts) {
            UUID productId = UUID.fromString(uuidString.toString());
            Produs product = getProductWithId(productId, databaseModel.continut);
            if (product != null) {
                listModel.addElement(product.nume);
            } else {
                System.out.println("Nu am gasit produsul cu id " + productId);
            }
        }
    }

    public void removeNewProduct(int i) {
        if (i > -1) {
            DefaultListModel model = (DefaultListModel) form.jList1.getModel();
            model.remove(i);
        }
    }

    public void addNewProduct(String name) throws Exception {
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        if (name.equals("")) {
            throw new Exception("Nu ai adaugat numele produsului");
        }
        if (model.contains(name)) {
            throw new Exception("Produs deja adaugat");
        }
        model.addElement(name);
    }

    public void changeOrder(int focusedIndex, int direction) {
        if (focusedIndex != -1) {
            DefaultListModel model = (DefaultListModel) form.jList1.getModel();
            switch (direction) {
                case KeyEvent.VK_DOWN: {
                    if (focusedIndex < model.size() - 1) {
                        Object aux = model.get(focusedIndex + 1);
                        model.set(focusedIndex + 1, model.get(focusedIndex));
                        model.set(focusedIndex, aux);
                    }
                }
                break;
                case KeyEvent.VK_UP: {
                    if (focusedIndex > 0) {
                        Object aux = model.get(focusedIndex - 1);
                        model.set(focusedIndex - 1, model.get(focusedIndex));
                        model.set(focusedIndex, aux);
                    }
                }
                break;
            }
        }
    }
}
