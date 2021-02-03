/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import models.produs.Culoare;
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
        String newColor = JOptionPane.showInputDialog(null, "Dimensiune : ");
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
            culoriDisponibile += culoare.getNume() + " ";//verifica daca nu a fost deja adaugata in tree
        }
        String newCuloare = JOptionPane.showInputDialog(null, String.format("Culori disponibile : %s", culoriDisponibile));
        //verifica daca nu exista deja in tree si daca exista in culori
        selectedDimensiune.addCuloareCodPret(newCuloare, "#", "7.00");
        int index = indexInParentForPath(applicator.form.jTree1, selectionPath, selectedDimensiune.getDimensiune());
        applicator.reapplyVisuals(copy);
        applicator.expand(index);
    }

    public static int indexInParentForPath(JTree aTree, TreePath path, String name) { // muta l in applicator
        Object p = null;
        Object parent = null;
        for (int i = 0; i < path.getPathCount(); i++) {
            if (path.getPathComponent(i).toString().contains(name)) {
                p = path.getPathComponent(i);
                parent = i > 0 ? path.getPathComponent(i - 1) : null;
                break;
            }
        }
        if (p != null) {
            return parent == null ? 0 : aTree.getModel().getIndexOfChild(parent, p) + 1;
        }
        return -1;
    }
}
