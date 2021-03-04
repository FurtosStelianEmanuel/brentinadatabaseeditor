/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.util.UUID;

/**
 *
 * @author Manel
 */
public interface EditSimpleStringListsInterface {

    void addSimpleString(String dimensiune) throws Exception;

    void removeSimpleString(int i);

    void changeOrder(int focusedIndex, int direction);
    
    UUID getIdOfProdus(String nume);
}
