/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import models.produs.TranslateCuloare;

/**
 *
 * @author Manel
 */
public class TranslateCuloareFactory extends Factory<TranslateCuloare> {

    @Override
    public void createObjectInstances() {
        basicObject = TranslateCuloare.emptyInstance();
        basicObject.setDe(getVariableName("German"));
        basicObject.setHu(getVariableName("Hungarian"));
        basicObject.setEn(getVariableName("English"));
    }
}
