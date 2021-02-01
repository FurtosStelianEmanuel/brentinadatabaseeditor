/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.awt.event.ActionEvent;

/**
 *
 * @author Manel
 */
public interface ProdusEditFormInterface {

    void numeTextFieldChanged(String newValue);

    void umTextFieldChanged(String newValue);

    void descriereTextAreaChanged(String newValue);

    void translateButtonPressed(ActionEvent e);

    void dimensiuniPressed(ActionEvent e);
    
    void categoriiPressed(ActionEvent e);
    
    void similarePressed(ActionEvent e);
    
    void culoriPressed(ActionEvent e);
    
    void coduriPressed(ActionEvent e);
}
