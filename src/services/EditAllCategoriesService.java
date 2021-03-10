/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import models.database.Category;
import models.produs.Produs;
import models.produs.TranslateCategory;
import services.interfaces.EventConfirmationListener;
import views.edit.EditCategoryForm;
import views.edit.EditDescendantsForm;

/**
 *
 * @author Manel
 */
public class EditAllCategoriesService {

    List<Category> categories;
    EditAllCategoriesApplicator applicator;
    List<Produs> produse;

    public EditAllCategoriesService(List<Category> categories, List<Produs> produse) {
        this.categories = new ArrayList<>();
        categories.forEach(category -> {
            this.categories.add(new Category(category));
        });
        this.produse = new ArrayList<>();
        produse.forEach(produs -> {
            this.produse.add(new Produs(produs));
        });
    }

    public void editCategory(TreePath path) throws Exception {
        if (path == null) {
            throw new Exception("Selecteaza o categorie/subcategorie");
        }
        if (path.getPathCount() < 2) {
            throw new Exception("Selecteaza o categorie/subcategorie");
        }
        Category selectedCategory = getCategoryFromPath(path);
        if (selectedCategory == null) {
            throw new Exception("Nu am putut gasi categoria/subcategoria selectata");
        }
        EditCategoryForm editCategoryForm = new EditCategoryForm(selectedCategory);
        editCategoryForm.autoCompleteData(selectedCategory);
        applicator.form.setEnabled(false);
        editCategoryForm.setVisible(true);
        editCategoryForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                Category editedCategory = (Category) p;
                boolean found = false;
                for (int i = 0; i < categories.size(); i++) {
                    Category cat = categories.get(i);
                    if (cat.getId().equals(editedCategory.getId())) {
                        categories.set(i, editedCategory);
                        applicator.updateTree(categories);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Nu am gasit categoria editata");
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish(Object o) {
                editCategoryForm.dispose();
                applicator.form.setEnabled(true);
                applicator.form.toFront();
            }
        });
    }

    public void addCategory(TreePath path) throws Exception {
        if (path == null) {
            throw new Exception("Selecteaza root");
        }
        String numeCategorie = JOptionPane.showInputDialog(null, "Introdu numele categoriei");
        if (numeCategorie == null) {
            throw new Exception("Anulare !");
        }
        if (numeCategorie.equals("")) {
            throw new Exception("Numele trebuie sa contina litere");
        }
        if (categories.stream().anyMatch(cat -> cat.getHierarchyIndex() == 0 && cat.getName().equals(numeCategorie))) {
            throw new Exception("Categoria deja exista");
        }
        Category newCategory = Category.emptyInstance();
        newCategory.setHierarchyIndex(0);
        newCategory.setName(numeCategorie);
        newCategory.setId(UUID.randomUUID());
        newCategory.setParentId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        newCategory.setTranslate(TranslateCategory.emptyInstance());
        categories.add(newCategory);
        applicator.updateTree(categories);
    }

    private boolean hasChildren(Category category) {
        return categories.stream().anyMatch(cat -> cat.getParentId().equals(category.getId()));
    }

    private void removeAllChildren(Category category) {
        for (Category child : categories.stream().filter(cat -> cat.getParentId().equals(category.getId())).collect(Collectors.toList())) {
            categories.remove(child);
            if (hasChildren(child)) {
                removeAllChildren(child);
            }
        }
    }

    public void removeCategorie(TreePath path) throws Exception {
        if (path == null) {
            throw new Exception("Selecteaza o categorie");
        }
        if (path.getPathCount() != 2) {
            throw new Exception("Nu ai selectat nicio categorie");
        }
        Category parentCategory = getCategoryFromPath(path);
        if (parentCategory == null) {
            throw new Exception("Nu am putut gasi parentCategory");
        }
        removeAllChildren(parentCategory);
        categories.remove(parentCategory);
        applicator.updateTree(categories);
    }

    private boolean categoryAlreadyExists(Category parentCategory, String nume) {
        return categories.stream().filter(cat -> cat.getParentId() == parentCategory.getId()).anyMatch(cat -> cat.getName().equals(nume));
    }

    private Category getCategoryFromPath(TreePath path) {
        Category toReturn = null;
        int hierarchyIndex = path.getPathCount() - 2;
        toReturn = categories.stream().filter(cat -> {
            String name = ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject().toString();
            return cat.getHierarchyIndex() == hierarchyIndex && cat.getName().equals(name);
        }).findAny().orElse(null);
        return toReturn;
    }

    public void addSubCategorie(TreePath path) throws Exception {
        if (path == null) {
            throw new Exception("Selecteaza o categorie/subcategorie");
        }
        if (path.getPathCount() < 2) {
            throw new Exception("Nu ai selectat nicio categorie/subcategorie");
        }
        Category parentCategory = getCategoryFromPath(path);
        if (parentCategory == null) {
            throw new Exception("Nu am putut gasi parentCategory");
        }
        String numeSubCategorie = JOptionPane.showInputDialog(null, "Introdu numele categoriei");
        if (numeSubCategorie == null) {
            throw new Exception("Anulare !");
        }
        if (numeSubCategorie.equals("")) {
            throw new Exception("Numele trebuie sa contina litere");
        }
        if (categoryAlreadyExists(parentCategory, numeSubCategorie)) {
            throw new Exception("Subcategoria deja exista pe categorie/subcategorie");
        }
        Category newCategory = Category.emptyInstance();
        newCategory.setHierarchyIndex(parentCategory.getHierarchyIndex() + 1);
        newCategory.setName(numeSubCategorie);
        newCategory.setId(UUID.randomUUID());
        newCategory.setParentId(parentCategory.getId());
        newCategory.setTranslate(TranslateCategory.emptyInstance());
        categories.add(newCategory);
        applicator.updateTree(categories);
    }

    public void removeSubCategorie(TreePath path) throws Exception {
        if (path == null) {
            throw new Exception("Selecteaza o subcategorie");
        }
        if (path.getPathCount() < 3) {
            throw new Exception("Nu ai selectat nicio categorie");
        }
        Category parentCategory = getCategoryFromPath(path);
        if (parentCategory == null) {
            throw new Exception("Nu am putut gasi parentCategory");
        }
        removeAllChildren(parentCategory);
        categories.remove(parentCategory);
        applicator.updateTree(categories);
    }

    public void setApplicator(EditAllCategoriesApplicator applicator) {
        this.applicator = applicator;
    }

    public List<Category> getCategorii() {
        return categories;
    }

    public void editDescendants(TreePath selectionPath) throws Exception {
        if (selectionPath == null) {
            throw new Exception("Alege o categorie/subcategorie");
        }
        EditDescendantsForm descendantsForm = new EditDescendantsForm(getCategoryFromPath(selectionPath), produse);
        descendantsForm.autoCompleteData(produse);
        descendantsForm.setVisible(true);
        applicator.form.setEnabled(false);
        descendantsForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                produse = (List<Produs>) p;
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish(Object o) {
                applicator.form.setEnabled(true);
                descendantsForm.dispose();
                applicator.form.toFront();
            }
        });
    }

    public List<Produs> getProduse() {
        return produse;
    }
}
