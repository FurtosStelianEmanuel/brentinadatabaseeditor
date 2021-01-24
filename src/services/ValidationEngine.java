/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import models.produs.Produs;

/**
 *
 * @author Manel
 */
public class ValidationEngine {

    public String[] validateProdus(Produs p) {
        final List<String> problems = new ArrayList<>();
        //validate that required fields are filled
        
        return problems.toArray(new String[0]);
    }
}
