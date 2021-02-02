/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
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
                DefaultMutableTreeNode pretNode = new DefaultMutableTreeNode(culoareCodPret.getPret());
                culoareNode.add(codNode);
                culoareNode.add(pretNode);
                dimensiuneCuloriRootNode.add(culoareNode);
            }

            root.add(dimensiuneCuloriRootNode);
        }
        form.jTree1.expandRow(0);
    }

    void reapplyVisuals(List<DimensiuneCulori> dimensiuneCulori) {
        form.jTree1.collapseRow(0);
        DefaultTreeModel treeModel = (DefaultTreeModel) form.jTree1.getModel();
        ((DefaultMutableTreeNode)treeModel.getRoot()).removeAllChildren();
        autoCompleteData(dimensiuneCulori);
        treeModel.reload();
    }

}
