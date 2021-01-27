/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import main.Main;
import models.database.DatabaseModel;
import services.interfaces.FormListenerInterface;
import views.MainForm;
import views.ProdusEdit;
import services.interfaces.EventConfirmationListener;
import services.interfaces.MainFormServiceInterface;

/**
 *
 * @author Manel
 */
public class MainFormService implements MainFormServiceInterface {

    DatabaseService databaseService;
    MainFormApplicator mainFormApplicator;
    DatabaseModel model;

    public MainFormService(DatabaseService databaseService) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        this.databaseService = databaseService;
    }

    public void setApplicator(MainFormApplicator mainFormApplicator) {
        this.mainFormApplicator = mainFormApplicator;
    }

    @Override
    public void save() throws ClassNotFoundException, IOException {
        databaseService.saveDatabase(model, Paths.get("C:","users","manel","desktop","output.json").toFile());
    }

    @Override
    public void load() throws ClassNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser(Main.Path.toFile());
        int choice = chooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile() != null) {
                model = databaseService.loadDatabase(chooser.getSelectedFile());
                mainFormApplicator.updateTable(model);
            }
        }
    }

    void backToMainForm() {
        synchronized (mainFormApplicator.form.watcher) {
            mainFormApplicator.form.watcher.notify();
            mainFormApplicator.form.toFront();
        }
    }

    @Override
    public void editProdus(int index) {
        ProdusEditService produsEditService = new ProdusEditService(model.continut.get(index));
        ProdusEdit produsEditForm = new ProdusEdit(produsEditService);
        ProdusEditApplicator applicator = new ProdusEditApplicator(produsEditForm);
        applicator.autoCompleteData(model.continut.get(index));
        produsEditForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object produsObject) {
                System.out.println("Confirmare mare");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish(Object produsObject) {
                backToMainForm();
                produsEditForm.dispose();
            }
        });
        produsEditForm.setVisible(true);
    }
}
