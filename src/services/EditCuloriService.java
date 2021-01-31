/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import models.produs.Culoare;
import models.produs.Produs;

/**
 *
 * @author Manel
 */
public class EditCuloriService {

    List<Culoare> copy;
    List<Culoare> original;
    EditCuloriApplicator applicator;
    Produs produsCopy;

    public List<Culoare> getCopy() {
        return copy;
    }

    public List<Culoare> getOriginal() {
        return original;
    }

    public EditCuloriService(List<Culoare> culori, Produs produs) {
        this.copy = Culoare.DeepCopy(culori);
        this.original = culori;
        this.produsCopy = new Produs(produs);
    }

    public Produs getProdusCopy() {
        return produsCopy;
    }

    public void setApplicator(EditCuloriApplicator applicator) {
        this.applicator = applicator;
    }

    public void reapplyVisuals() {
        applicator.clear();
        applicator.autoCompleteData(copy);
    }
}
