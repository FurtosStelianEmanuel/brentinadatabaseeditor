/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.EmptyStackException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import main.Main;
import models.database.DatabaseModel;
import models.produs.Produs;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.json.simple.JSONObject;
import plugin.RequestSender;
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
    private final RequestSender requestSender;

    public MainFormService(DatabaseService databaseService, RequestSender requestSender) {
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
        this.requestSender = requestSender;
    }

    public void setApplicator(MainFormApplicator mainFormApplicator) {
        this.mainFormApplicator = mainFormApplicator;
    }

    @Override
    public void save() throws ClassNotFoundException, IOException {
        databaseService.saveDatabase(model, Paths.get(Main.PathToDatabase.toString(), "produse.json").toFile());
    }

    @Override
    public void load() throws ClassNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser(Main.Path.toFile());
        int choice = chooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile() != null) {
                model = databaseService.loadDatabase(chooser.getSelectedFile());
                Main.PathToDatabase = Paths.get(chooser.getSelectedFile().getParent());
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
        ProdusEditService produsEditService = new ProdusEditService(model.continut.get(index), model.continut);
        ProdusEdit produsEditForm = new ProdusEdit(produsEditService);
        ProdusEditApplicator applicator = new ProdusEditApplicator(produsEditForm);
        applicator.autoCompleteData(model.continut.get(index));
        produsEditForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object produsObject) {
                Produs produs = (Produs) produsObject;
                model.continut.set(index, produs);
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

    @Override
    public void reloadDatabase() {
        requestSender.sendPostRequest("http://localhost/reload", new JSONObject(), new RequestSender.RequestHandler() {
            @Override
            public void onSucces(String body, CloseableHttpResponse response) {
                System.out.println(body);
                JOptionPane.showMessageDialog(null, "Succes ! ");
            }

            @Override
            public void onError(IOException ex) {
                Logger.getLogger(MainFormService.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Nu am putut aplica modificarile server-ului, incercati sa il reporniti manual");
            }
        });
    }

    @Override
    public void saveAndReload() {
        try {
            save();
            reloadDatabase();
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(MainFormService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isAlreadyAddedToDatabase(String nume) {
        for (Produs p : model.continut) {
            if (p.nume.toLowerCase().equals(nume.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addProdus(String nume) throws Exception {
        if (nume == null) {
            throw new Exception("Ai anulat operatia");
        }
        if (nume.equals("")) {
            throw new Exception("Nu ai tastat niciun nume");
        }
        if (isAlreadyAddedToDatabase(nume)) {
            throw new Exception("Produsul a fost deja adaugat");
        }
        Produs produsToAdd = Produs.emptyInstance();
        produsToAdd.nume = nume;
        boolean directoryCreated = FileSystem.createDirectoryInImageBank(nume);
        if (!directoryCreated) {
            JOptionPane.showMessageDialog(null, "Nu am putut asigna un nou folder pentru acest produs");
        }
        ProdusEditService produsEditService = new ProdusEditService(produsToAdd, model.continut);
        ProdusEdit produsEditForm = new ProdusEdit(produsEditService);
        ProdusEditApplicator produsEditApplicator = new ProdusEditApplicator(produsEditForm);
        produsEditApplicator.autoCompleteData(produsToAdd);
        produsEditForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object produsObject) {
                Produs produs = (Produs) produsObject;
                model.continut.add(produs);
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
