/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import factory.ProdusFactory;
import java.util.Arrays;
import models.database.DatabaseModel;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
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
}
