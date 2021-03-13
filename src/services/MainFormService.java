/*
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
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
import views.edit.EditAllCategoriesForm;
import views.edit.EditNewProductsForm;

/**
 *
 * @author Manel
 */
public class MainFormService implements MainFormServiceInterface {

    DatabaseService databaseService;
    MainFormApplicator applicator;
    DatabaseModel model;
    DatabaseModel filteredModel;
    private final RequestSender requestSender;
    boolean runUUIDMigration = false;
    boolean runSimilareUUIDMigration = false;
    boolean runCleanupScript = false;
    boolean runDescriptionPortingFromFile = false;

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
        this.applicator = mainFormApplicator;
    }

    @Override
    public void save() throws ClassNotFoundException, IOException {
        databaseService.saveDatabase(model, Paths.get(Main.PathToDatabase.toString(), "produse.json").toFile());
    }

    private void checkMigrations(DatabaseModel model, File f) throws ClassNotFoundException, IOException {
        boolean migrationRan = false;
        if (runUUIDMigration) {
            databaseService.migrateToUUID(model);
            migrationRan = true;
        }
        if (runSimilareUUIDMigration) {
            databaseService.migrateSimilareUUIDs(f);
            migrationRan = true;
        }
        if (runCleanupScript) {
            databaseService.cleanUpScript(f);
            migrationRan = true;
        }
        if (runDescriptionPortingFromFile) {
            databaseService.portDescriptionsFromFile(model, Paths.get("c:", "users", "manel", "desktop", "backup.json").toFile());
            migrationRan = true;
        }
        if (migrationRan) {
            JOptionPane.showMessageDialog(null, "Migrations were executed, set runMigration flags to false and restart");
            System.exit(0);
        }
    }

    @Override
    public void load() throws ClassNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser(Main.Path.toFile());
        int choice = chooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile() != null) {
                Main.PathToDatabase = Paths.get(chooser.getSelectedFile().getParent());
                model = databaseService.loadDatabase(chooser.getSelectedFile());
                checkMigrations(model, chooser.getSelectedFile());
                filteredModel = DatabaseModel.emptyInstance();
                sortProducts();
                model.continut.forEach((Produs produs) -> {
                    filteredModel.continut.add(produs);
                });
                applicator.updateTable(model);
            }
        }
    }

    void backToMainForm() {
        synchronized (applicator.form.watcher) {
            applicator.form.watcher.notify();
            applicator.form.toFront();
        }
    }

    @Override
    public void editProdus(int index) {
        ProdusEditService produsEditService = new ProdusEditService(filteredModel.continut.get(index), model.continut);
        ProdusEdit produsEditForm = new ProdusEdit(produsEditService);
        ProdusEditApplicator produsEditApplicator = new ProdusEditApplicator(produsEditForm);
        produsEditApplicator.autoCompleteData(filteredModel.continut.get(index));
        produsEditForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object produsObject) {
                Produs produs = (Produs) produsObject;

                filteredModel.continut.set(index, produs);
                for (int i = 0; i < model.continut.size(); i++) {
                    Produs modelProdus = model.continut.get(i);
                    if (modelProdus.id == produs.id) {
                        model.continut.set(i, produs);
                        break;
                    }
                }

                sortProductsNoUpdateOnUI();
                sortFilteredProducts();
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
        produsToAdd.id = UUID.randomUUID();
        produsToAdd.nume = nume;
        boolean directoryCreated = FileSystem.createDirectoryInImageBank(produsToAdd.id.toString());
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
                filteredModel.continut.add(produs);
                sortProductsNoUpdateOnUI();
                sortFilteredProducts();
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

    public Produs getProdus(int selectedIndex) {
        if (selectedIndex < 0) {
            return null;
        }
        return model.continut.get(selectedIndex);
    }

    @Override
    public void deleteProdus(Produs produs) throws IndexOutOfBoundsException, UnsupportedOperationException {
        if (produs == null) {
            throw new IndexOutOfBoundsException("Index < 0");
        }
        if (!model.continut.contains(produs)) {
            throw new UnsupportedOperationException("Nu am putut scoate produsul " + produs.nume);
        }
        if (!filteredModel.continut.contains(produs)) {
            throw new UnsupportedOperationException("Nu l am putut scoate nici din filteredModel");
        }

        filteredModel.continut.remove(produs);
        model.continut.remove(produs);

        if (filterProducts(applicator.getFilter()) == 0) {
            applicator.updateTable(model);
            applicator.clearFilter();
        } else {
            applicator.updateTable(filteredModel);
        }
    }

    private void sortProducts() {
        model.continut.sort(Produs.NameComparator.getInstance());
        applicator.updateTable(model);
//        model.continut.sort((t, t1) -> {
//            return 0; //To change body of generated lambdas, choose Tools | Templates.
//        });
    }

    private void sortProductsNoUpdateOnUI() {
        model.continut.sort(Produs.NameComparator.getInstance());
    }

    private void sortFilteredProducts() {
        filteredModel.continut.sort(Produs.NameComparator.getInstance());
        applicator.updateTable(filteredModel);
    }

    private boolean matchesFilter(String filter, Produs p) {
        return p.nume.toLowerCase().contains(filter.toLowerCase());
    }

    void handleFilterMatch(Produs p) {
        if (filteredModel.continut.contains(p)) {
            return;
        }
        filteredModel.continut.add(p);
    }

    void handleFilterNonMatch(Produs p) {
        if (!filteredModel.continut.contains(p)) {
            return;
        }
        filteredModel.continut.remove(p);
    }

    @Override
    public int filterProducts(String search) {
        for (Produs produs : model.continut) {
            if (matchesFilter(search, produs)) {
                handleFilterMatch(produs);
            } else {
                handleFilterNonMatch(produs);
            }
        }
        sortFilteredProducts();
        return filteredModel.continut.size();
    }

    @Override
    public Produs getProdusWithFilter(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index < 0");
        }
        if (index >= filteredModel.continut.size()) {
            throw new IndexOutOfBoundsException("Index >= " + filteredModel.continut.size());
        }
        return filteredModel.continut.get(index);
    }

    @Override
    public void editNewProducts() {
        EditNewProductsService newProductsService = new EditNewProductsService(model.continut);
        EditNewProductsForm editNewProductsForm = new EditNewProductsForm(newProductsService);
        EditNewProductsApplicator editNewProductsApplicator = new EditNewProductsApplicator(editNewProductsForm);
        newProductsService.setApplicator(editNewProductsApplicator);
        applicator.form.setEnabled(false);
        editNewProductsApplicator.autoCompleteData(model);
        editNewProductsForm.setVisible(true);
        editNewProductsForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                List<UUID> uuids = (List<UUID>) p;
                model.newProducts = uuids;
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish(Object o) {
                backToMainForm();
                editNewProductsForm.dispose();
                applicator.form.setEnabled(true);
            }
        });
    }

    @Override
    public void editCategorii() {
        EditAllCategoriesService allCategoriesService = new EditAllCategoriesService(model.categories, model.continut);
        EditAllCategoriesForm allCategoriesForm = new EditAllCategoriesForm(allCategoriesService);
        EditAllCategoriesApplicator allCategoriesApplicator = new EditAllCategoriesApplicator(allCategoriesForm);
        allCategoriesService.setApplicator(allCategoriesApplicator);
        applicator.form.setEnabled(false);
        allCategoriesForm.setVisible(true);
        allCategoriesApplicator.autoCompleteData(model.categories);
        allCategoriesForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                EditAllCategoriesForm.EditAllCategoriesOutput output = (EditAllCategoriesForm.EditAllCategoriesOutput) p;
                model.categories = output.getCategorii();
                model.continut = output.getProduse();
                applicator.updateTable(model);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish(Object o) {
                backToMainForm();
                allCategoriesForm.dispose();
                applicator.form.setEnabled(true);
            }
        });
    }
}
