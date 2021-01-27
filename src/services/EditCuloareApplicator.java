/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.produs.Culoare;
import services.interfaces.InitialCompleteInterface;
import views.edit.EditCuloareForm;

/**
 *
 * @author Manel
 */
public class EditCuloareApplicator implements InitialCompleteInterface {

    private final EditCuloareForm form;

    public EditCuloareApplicator(EditCuloareForm form) {
        this.form = form;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        Culoare culoare = (Culoare) formDataObject;
        form.placeholderTextField1.setText(culoare.getNume());
        form.placeholderTextField2.setText(culoare.getTranslation().getEnglish());
        form.placeholderTextField3.setText(culoare.getTranslation().getHungarian());
        form.placeholderTextField4.setText(culoare.getTranslation().getGerman());
        form.jSpinner1.setValue(culoare.getUnghiuri());
        form.jButton1.setBackground(culoare.getRGB());
    }
}
