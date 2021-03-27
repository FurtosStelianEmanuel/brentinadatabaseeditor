/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import models.produs.TranslateProdus;

/**
 *
 * @author Manel
 */
public class TranslateProdusFactory extends Factory<TranslateProdus> {

    private NumeFactory numeFactory;

    @Override
    public void createObjectInstances() {
        numeFactory = new NumeFactory();

        basicObject = TranslateProdus.emptyInstance();
        basicObject.setDescriereDe(getVariableName("GermanDescription"));
        basicObject.setDescriereHu(getVariableName("HungarianDescription"));
        basicObject.setDescriereEn(getVariableName("EnglishDescription"));
        basicObject.setNume(numeFactory.getBasicObject());
    }
}
