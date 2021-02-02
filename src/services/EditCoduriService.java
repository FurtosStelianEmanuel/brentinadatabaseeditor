/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.swing.JOptionPane;
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

    public void addNewDimensiune() throws Exception {
        String newColor = JOptionPane.showInputDialog(null, "Dimensiune : ");
        for (DimensiuneCulori dimensiuneCuloare : copy) {
            if (dimensiuneCuloare.getDimensiune().equals(newColor)) {
                throw new Exception("Aceasta dimensiune exista deja");
            }
        }
        copy.add(DimensiuneCulori.withDimensiuneName(newColor));
        applicator.reapplyVisuals(copy);
    }
}
