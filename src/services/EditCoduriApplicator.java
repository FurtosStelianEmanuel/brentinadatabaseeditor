/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import models.produs.CuloareCodPret;
import models.produs.DimensiuneCulori;
import models.produs.InitialComplete;
import views.edit.EditCoduriForm;

/**
 *
 * @author Manel
 */
public class EditCoduriApplicator extends InitialComplete {

    EditCoduriForm form;

    public EditCoduriApplicator(EditCoduriForm form) {
        super(form);
        this.form = form;
        DefaultTreeModel model = (DefaultTreeModel) form.jTree1.getModel();
        model.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent tme) {
                boolean modified = false;
                if (tme.getTreePath().getPath().length == 3) {
                    if (tme.getChildren().length == 1) {
                        DefaultMutableTreeNode changedNode = (DefaultMutableTreeNode) tme.getChildren()[0];//daca o sa ne folosim vreodata de preturi ele o sa fie al doilea prunc
                        DefaultMutableTreeNode culoareNode = (DefaultMutableTreeNode) changedNode.getParent();
                        DefaultMutableTreeNode dimensiuneNode = (DefaultMutableTreeNode) culoareNode.getParent();

                        String dimensiune = (String) dimensiuneNode.getUserObject();
                        String culoare = (String) culoareNode.getUserObject();
                        String cod = (String) changedNode.getUserObject();

                        try {
                            form.getService().codUpdated(tme.getTreePath(), dimensiune, culoare, cod);
                            modified = true;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            Logger.getLogger(EditCoduriApplicator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (!modified){
                    JOptionPane.showMessageDialog(null, "Modificarea ta nu a fost salvata in model,\n ai schimbat probabil numele la ceva ce nu trebuia (dimensiunea, culoarea etc..), \nda save si intra din nou in window");
                }
            }

            @Override
            public void treeNodesInserted(TreeModelEvent tme) {
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent tme) {
            }

            @Override
            public void treeStructureChanged(TreeModelEvent tme) {
            }
        });
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        List<DimensiuneCulori> dimensiuneCuloriList = (List<DimensiuneCulori>) formDataObject;

        DefaultTreeModel model = (DefaultTreeModel) form.jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        for (DimensiuneCulori dimensiuneCulori : dimensiuneCuloriList) {
            DefaultMutableTreeNode dimensiuneCuloriRootNode = new DefaultMutableTreeNode(dimensiuneCulori.getDimensiune());
            for (CuloareCodPret culoareCodPret : dimensiuneCulori.getData()) {
                DefaultMutableTreeNode culoareNode = new DefaultMutableTreeNode(culoareCodPret.getNume());
                DefaultMutableTreeNode codNode = new DefaultMutableTreeNode(culoareCodPret.getCod());
                //DefaultMutableTreeNode pretNode = new DefaultMutableTreeNode(culoareCodPret.getPret());
                culoareNode.add(codNode);
                //culoareNode.add(pretNode);
                dimensiuneCuloriRootNode.add(culoareNode);
            }
            root.add(dimensiuneCuloriRootNode);
        }
        form.jTree1.expandRow(0);
    }

    void reapplyVisuals(List<DimensiuneCulori> dimensiuneCulori) {
        DefaultTreeModel treeModel = (DefaultTreeModel) form.jTree1.getModel();
        ((DefaultMutableTreeNode) treeModel.getRoot()).removeAllChildren();
        autoCompleteData(dimensiuneCulori);
        treeModel.reload();
    }

    void expand(int row) {
        form.jTree1.expandRow(row);
    }

    /**
     * Cauta o culoare in dimensiunea specificata din JTree
     *
     * @param dimensiune
     * @param numeCuloare
     * @return
     * @throws Exception
     * @deprecated
     */
    @Deprecated
    boolean dimensiuneContainsCuloare(String dimensiune, String numeCuloare) throws Exception {
        DefaultTreeModel model = (DefaultTreeModel) form.jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        DefaultMutableTreeNode lookIntoNode = null;
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
            String nodeName = (String) node.getUserObject();
            if (nodeName.equals(dimensiune)) {
                lookIntoNode = node;
                break;
            }
        }
        if (lookIntoNode == null) {
            throw new Exception("Dimensiunea cautata nu a fost gasita");
        }
        for (int i = 0; i < lookIntoNode.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) lookIntoNode.getChildAt(i);
            String nodeName = (String) node.getUserObject();
            if (nodeName.equals(numeCuloare)) {
                return true;
            }
        }
        return false;
    }

    public int indexInParentForPath(TreePath path, String name) { // muta l in applicator
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
            return parent == null ? 0 : form.jTree1.getModel().getIndexOfChild(parent, p) + 1;
        }
        return -1;
    }
}
