/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.util.Arrays;
import java.util.UUID;
import models.produs.Produs;

/**
 *
 * @author Manel
 */
public final class ProdusFactory extends Factory<Produs> {

    private DimensiuneCuloriFactory dimensiuneCuloriFactory;
    private CuloareFactory culoareFactory;
    private TranslateProdusFactory translateProdusFactory;

    @Override
    public void createObjectInstances() {
        dimensiuneCuloriFactory = new DimensiuneCuloriFactory();
        culoareFactory = new CuloareFactory();
        translateProdusFactory = new TranslateProdusFactory();

        basicObject = Produs.emptyInstance();
        basicObject.categorii = Arrays.asList(UUID.randomUUID());
        basicObject.coduriSiPreturi = Arrays.asList(dimensiuneCuloriFactory.basicObject);
        basicObject.culori = Arrays.asList(culoareFactory.getBasicObject());
        basicObject.descriere = getVariableName("Produs basic pentru test");
        basicObject.dimensiuni = Arrays.asList(getVariableName("5mm"));
        basicObject.id = UUID.randomUUID();
        basicObject.imagineDefault = getVariableName("imagine_default.jpg");
        basicObject.nume = getVariableName("ProdusTest");
        basicObject.palleteType = 0;
        basicObject.pret = 15.03d;
        basicObject.similare = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        basicObject.translate = translateProdusFactory.getBasicObject();
        basicObject.um = getVariableName("UnitOfMeasure");
    }
}
