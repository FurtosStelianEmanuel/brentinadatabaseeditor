/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.swing.JFrame;
import models.produs.InitialComplete;
import models.produs.Produs;
import views.ProdusEdit;

/**
 *
 * @author Manel
 */
public class ProdusEditApplicator extends InitialComplete {

    ProdusEdit form;

    public ProdusEditApplicator(JFrame form) {
        super(form);
        this.form = (ProdusEdit) form;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        Produs produs = (Produs) formDataObject;
        form.placeholderTextField1.setText(produs.nume);
        form.placeholderTextField3.setText(produs.um);
        form.jTextArea1.setText(produs.descriere);
        form.setTitle(produs.id.toString());
    }
}
