/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import models.produs.Culoare;

/**
 *
 * @author Manel
 */
public class EditCuloareService {

    Culoare culoare;
    EditCuloareApplicator applicator;

    public EditCuloareService(Culoare culoare) {
        this.culoare = culoare;
    }

    public void setApplicator(EditCuloareApplicator applicator) {
        this.applicator = applicator;
    }
}
