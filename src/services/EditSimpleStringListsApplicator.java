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
import models.produs.InitialComplete;
import services.interfaces.EditSimpleStringListsInterface;
import views.edit.EditSimpleStringListsForm;

/**
 *
 * @author Manel
 */
public class EditSimpleStringListsApplicator extends InitialComplete implements EditSimpleStringListsInterface {

    EditSimpleStringListsForm form;

    public enum Types {
        Default,
        Categorii,
        Similare
    }

    private String mainField = "Introduceti o dimensiune";
    private String emptyMainField = "Dimensiune goala", alreadyAdded = "Dimensiune deja adaugata";
    private String message1 = "Puteti folosi si sagetile ca sa mutati in sus sau in jos dimensiunea selectata", message2 = "Sau tasta delete pentru a sterge o dimensiune";

    public EditSimpleStringListsApplicator(EditSimpleStringListsForm form, Types type) {
        super(form);
        this.form = form;

        switch (type) {
            case Categorii:
                emptyMainField = "Categorie goala";
                alreadyAdded = "Categorie deja adaugata";
                message1 = "Puteti folosi si sagetile ca sa mutati in sus sau in jos categoria selectata";
                message2 = "Sau tasta delete pentru a sterge o dimensiune";
                mainField = "Introduceti o categorie noua";
                break;
            case Similare:
                emptyMainField = "Produs similar gol";
                alreadyAdded = "Produs similar deja adaugat";
                message1 = "Puteti folosi si sagetile ca sa mutati in sus sau in jos produsul similar selectat";
                message2 = "Sau tasta delete pentru a sterge un produs similar";
                mainField = "Introduceti un produs similar";
                break;
            case Default:
                break;
            default:
                System.out.println("Unknown command");
        }

        form.jLabel1.setText(message1);
        form.jLabel2.setText(message2);
        form.placeholderTextField1.setPlaceholder(mainField);
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        List<String> dimensiuni = (List<String>) formDataObject;
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        dimensiuni.forEach((String dimensiune) -> {
            model.addElement(dimensiune);
        });
    }

    @Override
    public void addSimpleString(String dimensiune) throws Exception {
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        if (dimensiune.equals("")) {
            throw new Exception(emptyMainField);
        }
        if (model.contains(dimensiune)) {
            throw new Exception(alreadyAdded);
        }
        form.placeholderTextField1.setText("");
        model.addElement(dimensiune);
    }

    @Override
    public void removeSimpleString(int i) {
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        model.remove(i);
    }

    @Override
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

    @Override
    public UUID getIdOfProdus(String nume) {
        throw new UnsupportedOperationException("Method should not be called from here");
    }
}
