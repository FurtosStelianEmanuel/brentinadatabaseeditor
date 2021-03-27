/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import models.produs.CuloareCodPret;

/**
 *
 * @author Manel
 */
public class CuloareCodPretFactory extends Factory<CuloareCodPret> {

    @Override
    public void createObjectInstances() {
        basicObject = CuloareCodPret.emptyInstance();
        basicObject.setNume(getVariableName("Nume"));
        basicObject.setCod(getVariableName("Cod"));
        basicObject.setPret(getVariableName("Pret"));
    }
}
