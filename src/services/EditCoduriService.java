/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import models.produs.DimensiuneCulori;

/**
 *
 * @author Manel
 */
public class EditCoduriService {

    List<DimensiuneCulori> copy;
    List<DimensiuneCulori> original;
    EditCoduriApplicator applicator;

    public EditCoduriService(List<DimensiuneCulori> original) {
        this.original = original;
        this.copy = DimensiuneCulori.copyFromAnotherInstance(original);
    }

    public void setApplicator(EditCoduriApplicator applicator) {
        this.applicator = applicator;
    }
}
