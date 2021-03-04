/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.migrations.models.produsnosimilareuuid;

import models.produs.*;
import javax.swing.JFrame;
import services.EditSimpleStringListsApplicator;
import services.interfaces.InitialCompleteInterface;

/**
 *
 * @author Manel
 */
public abstract class InitialComplete implements InitialCompleteInterface {

    /**
     *
     * @param form
     */
    public InitialComplete(JFrame form) {
        //ce sa pun aici
    }
    
    public InitialComplete(JFrame form, EditSimpleStringListsApplicator.Types type){
        
    }
}
