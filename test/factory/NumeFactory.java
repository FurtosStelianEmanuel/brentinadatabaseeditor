/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import models.produs.Nume;

/**
 *
 * @author Manel
 */
public class NumeFactory extends Factory<Nume> {

    @Override
    public void createObjectInstances() {
        basicObject = Nume.emptyInstance();
        basicObject.setDe(getVariableName("GermanName"));
        basicObject.setHu(getVariableName("HungarianName"));
        basicObject.setEn(getVariableName("EnglishName"));
    }
}
