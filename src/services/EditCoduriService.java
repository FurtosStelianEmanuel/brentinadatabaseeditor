/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import models.produs.Culoare;
import models.produs.CuloareCodPret;
import models.produs.DimensiuneCulori;
import models.produs.Produs;

/**
 *
 * @author Manel
 */
public class EditCoduriService {

    List<DimensiuneCulori> copy;
    List<DimensiuneCulori> original;
    EditCoduriApplicator applicator;
    Produs copyProdus;

    public EditCoduriService(List<DimensiuneCulori> original, Produs originalProdus) {
        this.original = original;
        this.copy = DimensiuneCulori.copyFromAnotherInstance(original);
        this.copyProdus = new Produs(originalProdus);
    }

    public void setApplicator(EditCoduriApplicator applicator) {
        this.applicator = applicator;
    }

    public void addNewDimensiune() throws Exception {
        String dimensiuniDisponibile = "";
        for (String dimensiune : copyProdus.dimensiuni) {
            boolean found = false;
            for (DimensiuneCulori dc : copy) {
                if (dc.getDimensiune().equals(dimensiune)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                dimensiuniDisponibile += dimensiune + " ";
            }
        }
        if (dimensiuniDisponibile.isEmpty()) {
            throw new Exception("Nu exista alte dimensiuni disponibile");
        }
        String newColor = JOptionPane.showInputDialog(null, "Dimensiuni disponibile : " + dimensiuniDisponibile);
        if (newColor == null) {
            throw new Exception("Ai renuntat la operatiunea de adaugare a dimensiunii");
        }
        if (newColor.isEmpty()) {
            throw new Exception("Dimensiunea trebuie sa contina caractere");
        }
        for (DimensiuneCulori dimensiuneCulori : copy) {
            if (dimensiuneCulori.getDimensiune().equals(newColor)) {
                throw new Exception("Aceasta dimensiune exista deja");
            }
        }
        copy.add(DimensiuneCulori.withDimensiuneName(newColor));
        applicator.reapplyVisuals(copy);
    }

    public void deleteDimensiune(TreePath selectionPath) throws Exception {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        Object b = node.getUserObject();
        boolean found = false;
        for (DimensiuneCulori dimensiuneCulori : copy) {
            if (dimensiuneCulori.getDimensiune().equals(b)) {
                copy.remove(dimensiuneCulori);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Nu am gasit dimensiunea selectata");
        }
        applicator.reapplyVisuals(copy);
    }

    public void addNewCuloare(TreePath selectionPath) throws Exception {
        if (selectionPath == null) {
            throw new NullPointerException("Nu ai selectat nicio dimensiune");
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        Object b = node.getUserObject();
        DimensiuneCulori selectedDimensiune = null;
        for (DimensiuneCulori dimensiuneCulori : copy) {
            if (dimensiuneCulori.getDimensiune().equals(b)) {
                selectedDimensiune = dimensiuneCulori;
                break;
            }
        }
        if (selectedDimensiune == null) {
            throw new Exception("Nu am gasit dimensiunea selectata");
        }
        String culoriDisponibile = "";
        for (Culoare culoare : copyProdus.culori) {
            if (!culoare.getNume().equals("multi")) {
                boolean shouldAddColor = true;
                for (CuloareCodPret ccp : selectedDimensiune.getData()) {
                    if (ccp.getNume().equals(culoare.getNume()) || culoare.getNume().equals("multi")) {
                        shouldAddColor = false;
                        break;
                    }
                }
                if (shouldAddColor) {
                    culoriDisponibile += culoare.getNume() + " ";
                }
            } else {
                for (String altaCuloare : culoare.getAlteCulori()) {
                    boolean found = false;
                    for (CuloareCodPret ccp : selectedDimensiune.getData()) {
                        if (altaCuloare.equals(ccp.getNume())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        culoriDisponibile += altaCuloare + " ";
                    }
                }
            }
        }
        if (culoriDisponibile.isEmpty()) {
            throw new Exception("Toate culorile pentru aceasta dimensiune au coduri");
        }
        int choice = JOptionPane.showConfirmDialog(null, "Lasi programul sa adauge automat culorile?");
        if (choice == JOptionPane.OK_OPTION) {
            List<String> toateCulorile = new ArrayList<>();
            for (Culoare c : copyProdus.culori) {
                if (!c.getNume().equals("multi")) {
                    boolean found = false;
                    for (CuloareCodPret ccp : selectedDimensiune.getData()) {
                        if (ccp.getNume().equals(c.getNume())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        toateCulorile.add(c.getNume());
                    }
                } else {
                    for (String altaCuloare : c.getAlteCulori()) {
                        boolean found = false;
                        for (CuloareCodPret ccp : selectedDimensiune.getData()) {
                            if (ccp.getNume().equals(altaCuloare)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            toateCulorile.add(altaCuloare);
                        }
                    }

                }
            }
            for (String culoare : toateCulorile) {
                selectedDimensiune.addCuloareCodPret(culoare, "#", "7.00");
            }
        } else {
            String newCuloare = JOptionPane.showInputDialog(null, String.format("Culori disponibile : %s", culoriDisponibile));

            if (newCuloare == null) {
                throw new Exception("Ai abandonat adaugarea culorii");
            }
            if (newCuloare.isEmpty()) {
                throw new Exception("Nu ai introdus nimic");
            }
            for (CuloareCodPret ccp : selectedDimensiune.getData()) {
                if (ccp.getNume().equals(newCuloare)) {
                    throw new Exception("Culoarea deja exista");
                }
            }
            List<String> toateCulorile = new ArrayList<>();
            for (Culoare c : copyProdus.culori) {
                if (!c.getNume().equals("multi")) {
                    toateCulorile.add(c.getNume());
                } else {
                    for (String altaCuloare : c.getAlteCulori()) {
                        toateCulorile.add(altaCuloare);
                    }
                }
            }
            if (!toateCulorile.contains(newCuloare)) {
                throw new Exception("Aceasta culoare nu exista");
            }
            selectedDimensiune.addCuloareCodPret(newCuloare, "#", "0.00");
        }
        int index = applicator.indexInParentForPath(selectionPath, selectedDimensiune.getDimensiune());
        applicator.reapplyVisuals(copy);
        applicator.expand(index);
    }
}
