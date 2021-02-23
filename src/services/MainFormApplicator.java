/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.swing.table.DefaultTableModel;
import models.database.DatabaseModel;
import models.produs.Produs;
import services.interfaces.MainFormApplicatorInterface;
import views.MainForm;

/**
 *
 * @author Manel
 */
public class MainFormApplicator implements MainFormApplicatorInterface {

    public MainForm form;

    public MainFormApplicator(MainForm form) {
        this.form = form;
        this.form.jTable1.getColumn("Nr.").setMaxWidth(50);
    }

    @Override
    public void updateTable(DatabaseModel databaseModel) {
        DefaultTableModel model = (DefaultTableModel) form.jTable1.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        for (int i = 0; i < databaseModel.continut.size(); i++) {
            Produs produs = databaseModel.continut.get(i);
            model.addRow(new Object[]{i, produs.nume, produs.similare, produs.categorii});
        }
    }
}
