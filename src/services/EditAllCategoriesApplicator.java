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

    @Override
    public void autoCompleteData(Object formDataObject) {
        List<Category> categories = (List<Category>) formDataObject;
        updateTree(categories);
    }

    private void buildPath(List<Category> descendants, List<Category> allCategories, DefaultMutableTreeNode node) {
        descendants.forEach(descendant -> {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(descendant.getName());
            node.add(newNode);
            List<Category> children = allCategories.stream().filter(cat -> cat.getParentId().equals(descendant.getId())).collect(Collectors.toList());
            if (children.size() > 0) {
                buildPath(children, allCategories, newNode);
            }
        });
    }

    public void updateTree(List<Category> categories) {
        DefaultTreeModel treeModel = (DefaultTreeModel) form.jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        root.removeAllChildren();
        for (Category category : categories.stream().filter(c -> c.getHierarchyIndex() == 0).collect(Collectors.toList())) {
            DefaultMutableTreeNode primaryCategory = new DefaultMutableTreeNode(category.getName());
            buildPath(categories.stream().filter(cat -> cat.getParentId().equals(category.getId())).collect(Collectors.toList()), categories, primaryCategory);
            root.add(primaryCategory);
        }
        treeModel.reload();
    }
}
