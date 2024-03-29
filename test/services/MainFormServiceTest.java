/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import factory.ProdusFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import javax.swing.JFileChooser;
import main.Main;
import models.database.DatabaseModel;
import models.produs.Produs;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.Mockito;
import plugin.RequestSender;

/**
 *
 * @author Manel
 */
public class MainFormServiceTest {

    MainFormService service;
    ProdusFactory produsFactory;

    public MainFormServiceTest() {
        service = new MainFormService(Mockito.mock(DatabaseService.class), Mockito.mock(RequestSender.class));
        produsFactory = new ProdusFactory();
    }

    @Test
    public void When_FilterProducts_FilterMatch_Then_FilteredModelUpdated() {
        service.model = DatabaseModel.emptyInstance();
        service.filteredModel = DatabaseModel.emptyInstance();
        service.model.continut = Arrays.asList(produsFactory.getBasicObject());
        service.applicator = Mockito.mock(MainFormApplicator.class);
        service.filterProducts("Test");

        assertTrue(service.filteredModel.continut.size() == 1);
    }

    @Test
    public void When_DeleteProdus_Then_ProdusDeletedFromContinut() {
        Produs toBeDeleted = produsFactory.getBasicObject();
        toBeDeleted.id = UUID.randomUUID();
        toBeDeleted.nume = produsFactory.getVariableName("Delete me");

        service.applicator = Mockito.mock(MainFormApplicator.class);
        Mockito.when(service.applicator.getFilter()).thenReturn("");
        service.model = DatabaseModel.emptyInstance();
        service.filteredModel = DatabaseModel.emptyInstance();
        service.filteredModel.continut = new ArrayList<>(Arrays.asList(produsFactory.getBasicObject(), toBeDeleted));
        service.model = DatabaseModel.emptyInstance();
        service.model.continut = new ArrayList<>(Arrays.asList(produsFactory.getBasicObject(), toBeDeleted));

        service.deleteProdus(toBeDeleted);

        assertTrue(service.model.continut.size() == service.filteredModel.continut.size());
        assertTrue(service.filteredModel.continut.size() == 1);
        assertEquals(service.model.continut.get(0).nume, service.filteredModel.continut.get(0).nume);
        assertEquals(produsFactory.getBasicObject().nume, service.model.continut.get(0).nume);
    }

    @Test
    public void When_DeleteProdus_Then_ProdusDeletedFromUI() {
        Produs toBeDeleted = produsFactory.getBasicObject();
        toBeDeleted.id = UUID.randomUUID();
        toBeDeleted.nume = produsFactory.getVariableName("Delete me");

        service.applicator = Mockito.mock(MainFormApplicator.class);
        Mockito.when(service.applicator.getFilter()).thenReturn("");
        service.model = DatabaseModel.emptyInstance();
        service.filteredModel = DatabaseModel.emptyInstance();
        service.filteredModel.continut = new ArrayList<>(Arrays.asList(produsFactory.getBasicObject(), toBeDeleted));
        service.model = DatabaseModel.emptyInstance();
        service.model.continut = new ArrayList<>(Arrays.asList(produsFactory.getBasicObject(), toBeDeleted));

        service.deleteProdus(toBeDeleted);
        Mockito.verify(service.applicator, Mockito.times(2)).updateTable(service.filteredModel);

        assertTrue(service.model.continut.size() == service.filteredModel.continut.size());
        assertTrue(service.filteredModel.continut.size() == 1);
        assertEquals(service.model.continut.get(0).nume, service.filteredModel.continut.get(0).nume);
        assertEquals(produsFactory.getBasicObject().nume, service.model.continut.get(0).nume);
    }

    @Test
    public void When_DeleteProdus_Then_ProdusReferencesRemoved() {
        Produs toBeDeleted = produsFactory.getBasicObject();
        toBeDeleted.id = UUID.randomUUID();
        toBeDeleted.nume = produsFactory.getVariableName("Delete me");

        Produs withReferenceToDeletedProdus = produsFactory.getBasicObject();
        withReferenceToDeletedProdus.similare = new ArrayList<>(Arrays.asList(toBeDeleted.id));

        service.applicator = Mockito.mock(MainFormApplicator.class);
        Mockito.when(service.applicator.getFilter()).thenReturn("");
        service.model = DatabaseModel.emptyInstance();
        service.filteredModel = DatabaseModel.emptyInstance();
        service.filteredModel.continut = new ArrayList<>(Arrays.asList(withReferenceToDeletedProdus, toBeDeleted));
        service.model = DatabaseModel.emptyInstance();
        service.model.continut = new ArrayList<>(Arrays.asList(withReferenceToDeletedProdus, toBeDeleted));

        service.deleteProdus(toBeDeleted);

        assertTrue(service.filteredModel.continut.size() == service.model.continut.size());
        assertEquals(service.filteredModel.continut.get(0).nume, withReferenceToDeletedProdus.nume);
        assertTrue(service.filteredModel.continut.get(0).similare.isEmpty());
        assertTrue(service.model.continut.get(0).similare.isEmpty());
    }

    @Test
    public void When_Save_Then_DatabaseServiceMethodCalled() throws ClassNotFoundException, IOException {
        service.model = DatabaseModel.emptyInstance();
        Main.PathToDatabase = Paths.get("home", "manel", "db");

        service.save();

        Mockito.verify(service.databaseService, Mockito.times(1)).saveDatabase(service.model, Paths.get(Main.PathToDatabase.toString(), "produse.json").toFile());
    }

    public void When_Load_Then_ShowOpenDialog() throws ClassNotFoundException, IOException {
        //daca mergeam pe factory pattern puteam sa fac mock la JFileChooser si puteam face testul asta, dar cum e acum, nu pot cu mockito simplu
    }
}
