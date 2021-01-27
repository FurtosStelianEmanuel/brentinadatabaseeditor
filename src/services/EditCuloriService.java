/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import models.produs.Culoare;

/**
 *
 * @author Manel
 */
public class EditCuloriService {

    List<Culoare> copy;
    List<Culoare> original;
    EditCuloriApplicator applicator;

    public List<Culoare> getCopy() {
        return copy;
    }

    public List<Culoare> getOriginal() {
        return original;
    }

    public EditCuloriService(List<Culoare> culori) {
        this.copy = Culoare.DeepCopy(culori);
        this.original = culori;
    }

    public void setApplicator(EditCuloriApplicator applicator) {
        this.applicator = applicator;
    }
}
