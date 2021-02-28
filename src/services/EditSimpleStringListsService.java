/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import services.interfaces.EditSimpleStringListsInterface;
import services.interfaces.ProductNotFoundException;

/**
 *
 * @author Manel
 */
public class EditSimpleStringListsService implements EditSimpleStringListsInterface {

    EditSimpleStringListsApplicator applicator;
    List<String> numeProduse;

    public List<String> getNumeProduse() {
        return numeProduse;
    }

    public EditSimpleStringListsService() {
    }

    public EditSimpleStringListsService(List<String> numeProduse) {
        this.numeProduse = numeProduse;
    }

    public void setApplicator(EditSimpleStringListsApplicator applicator) {
        this.applicator = applicator;
    }

    @Override
    public void addSimpleString(String simpleString) throws Exception {
        if (getNumeProduse() != null) {
            String productName = null;
            for (String numeProdus : getNumeProduse()) {
                if (simpleString.equals(numeProdus)) {
                    productName = numeProdus;
                    break;
                }
            }
            if (productName == null) {
                List<String> recomandate = new ArrayList<>();
                for (String pName : getNumeProduse()) {
                    if (pName.toLowerCase().trim().contains(simpleString.toLowerCase().trim())) {
                        recomandate.add(pName);
                    }
                }
                throw new ProductNotFoundException(recomandate);
            }
        }
        applicator.addSimpleString(simpleString);
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
