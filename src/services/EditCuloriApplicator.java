/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import models.produs.Culoare;
import models.produs.InitialComplete;
import views.edit.EditCuloriForm;

/**
 *
 * @author Manel
 */
public class EditCuloriApplicator extends InitialComplete implements EditCuloriApplicatorInterface {

    EditCuloriForm form;

    public EditCuloriApplicator(JFrame form) {
        super(form);
        this.form = (EditCuloriForm) form;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        List<Culoare> culori = (List<Culoare>) formDataObject;
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        culori.forEach((Culoare culoare) -> {
            model.addElement(culoare.getNume());
        });
    }

    void clear() {
        DefaultListModel model = (DefaultListModel) form.jList1.getModel();
        model.clear();
    }
}

interface EditCuloriApplicatorInterface {
}
