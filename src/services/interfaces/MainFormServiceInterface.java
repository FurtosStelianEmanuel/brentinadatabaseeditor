/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.io.IOException;

/**
 *
 * @author Manel
 */
public interface MainFormServiceInterface {

    void save() throws ClassNotFoundException, IOException;

    void load() throws ClassNotFoundException, IOException;

    void reloadDatabase();
    
    void saveAndReload();

    void editProdus(int index);
}
