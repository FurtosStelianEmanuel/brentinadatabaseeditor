/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.util.Arrays;
import models.produs.DimensiuneCulori;

/**
 *
 * @author Manel
 */
public final class DimensiuneCuloriFactory extends Factory<DimensiuneCulori> {

    private CuloareCodPretFactory culoareCodPretFactory;

    @Override
    public void createObjectInstances() {
        culoareCodPretFactory = new CuloareCodPretFactory();
        
        basicObject = DimensiuneCulori.emptyObject();
        basicObject.setDimensiune(getVariableName("10 cm"));
        basicObject.setData(Arrays.asList(culoareCodPretFactory.basicObject));
    }
}
