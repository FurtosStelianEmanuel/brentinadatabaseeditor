/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import services.interfaces.EditSimpleStringListsInterface;

/**
 *
 * @author Manel
 */
public class EditSimpleStringListsService implements EditSimpleStringListsInterface {

    EditSimpleStringListsApplicator applicator;

    public void setApplicator(EditSimpleStringListsApplicator applicator) {
        this.applicator = applicator;
    }

    @Override
    public void addSimpleString(String dimensiune) throws Exception {
        applicator.addSimpleString(dimensiune);
    }

    @Override
    public void removeSimpleString(int i) {
        applicator.removeSimpleString(i);
    }

    @Override
    public void changeOrder(int focusedIndex, int direction) {
        applicator.changeOrder(focusedIndex, direction);
    }

}
