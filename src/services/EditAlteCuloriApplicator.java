/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.database.AltaCuloare;
import services.interfaces.InitialCompleteInterface;
import views.edit.EditAlteCuloriForm;

/**
 *
 * @author Manel
 */
public class EditAlteCuloriApplicator implements InitialCompleteInterface {

    public EditAlteCuloriForm form;

    public EditAlteCuloriApplicator(EditAlteCuloriForm form) {
        this.form = form;
    }

    public void updateTable(List<AltaCuloare> alteCulori) {
        DefaultTableModel model = (DefaultTableModel) form.jTable1.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        alteCulori.forEach(ac -> {
            model.addRow(new Object[]{ac.nume, ac.translateEn, ac.translateHu, ac.translateDe});
        });
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        updateTable((List<AltaCuloare>) formDataObject);
    }
}
