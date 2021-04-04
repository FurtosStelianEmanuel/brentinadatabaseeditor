/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import services.FactoryMethods;

/**
 *
 * @author Manel
 * @param <K>
 */
public abstract class Factory<K> implements FactoryMethods {

    protected K basicObject;

    public K getBasicObject() {
        return basicObject;
    }

    public String getVariableName(String... decorators) {
        if (decorators.length == 0) {
            return this.getClass().getName();
        }
        return String.format("%s%s", this.getClass().getName(), String.join("", decorators));
    }

    Factory() {
        createObjectInstances();
    }
}
