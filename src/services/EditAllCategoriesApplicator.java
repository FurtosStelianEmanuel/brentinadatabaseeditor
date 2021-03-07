/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import models.database.Category;
import services.interfaces.InitialCompleteInterface;
import views.edit.EditAllCategoriesForm;

/**
 *
 * @author Manel
 */
public class EditAllCategoriesApplicator implements InitialCompleteInterface {

    EditAllCategoriesForm form;

    public EditAllCategoriesApplicator(EditAllCategoriesForm form) {
        this.form = form;
    }

    /*
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
     */
    @Override
    public void autoCompleteData(Object formDataObject) {
        List<Category> categories = (List<Category>) formDataObject;
        DefaultTreeModel treeModel = (DefaultTreeModel) form.jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        for (Category category : categories.stream().filter(c -> c.getHierarchyIndex() == 0).collect(Collectors.toList())) {
            DefaultMutableTreeNode primaryCategory = new DefaultMutableTreeNode(category.getName());
            root.add(primaryCategory);
        }
    }
}
