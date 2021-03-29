/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import java.awt.Color;
import java.util.Arrays;
import models.produs.Culoare;

/**
 *
 * @author Manel
 */
public class CuloareFactory extends Factory<Culoare> {
    
    private TranslateCuloareFactory translateCuloareFactory;
    
    @Override
    public void createObjectInstances() {
        translateCuloareFactory = new TranslateCuloareFactory();
        
        basicObject = Culoare.emptyInstance();
        basicObject.setAlteCulori(Arrays.asList(getVariableName("Alta culoare #1"), getVariableName("Alta culoare #2")));
        basicObject.setNume("multi");
        basicObject.setRgb(Color.red);
        basicObject.setTranslate(translateCuloareFactory.getBasicObject());
        basicObject.setUnghiuri(1);
    }
}
