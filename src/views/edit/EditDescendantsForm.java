/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import models.database.Category;
import models.produs.Produs;
import services.interfaces.EventConfirmationListener;
import services.interfaces.FormListenerInterface;
import services.interfaces.InitialCompleteInterface;

/**
 * Cum si acest form e relativ simplu, nu am mai adaugat service si applicator
 *
 * @author Manel
 */
public class EditDescendantsForm extends javax.swing.JFrame implements InitialCompleteInterface, FormListenerInterface {

    Category category;
    private final List<Produs> produse;

    /**
     * Creates new form EditDescendentiForm
     *
     * @param category
     * @param produse
     */
    public EditDescendantsForm(Category category, List<Produs> produse) {
        initComponents();
        this.category = new Category(category);
        this.produse = new ArrayList<>();
        produse.forEach(produs -> {
            this.produse.add(new Produs(produs));
        });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placeholderTextField1 = new views.PlaceholderTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        placeholderTextField1.setPlaceholder("Introdu numele unui produs");
        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });

        jList1.setModel(new DefaultListModel());
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(placeholderTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(placeholderTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean existsInProductList(String name) {
        return produse.stream().anyMatch(produs -> produs.nume.equals(name));
    }

    private List<Produs> getRecommendedProducts(String nume) {
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        List<String> alreadyAdded = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            alreadyAdded.add((String) model.get(i));
        }
        return produse.stream().filter(p -> p.nume.toLowerCase().contains(nume.toLowerCase())).filter(p -> !alreadyAdded.contains(p.nume)).collect(Collectors.toList());
    }

    private Produs getSelectedProdus(String nume) {
        return produse.stream().filter(p -> p.nume.equals(nume)).findAny().orElse(null);
    }

    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        String toAdd = placeholderTextField1.getText();
        if (!existsInProductList(toAdd)) {
            Object[] productNames = getRecommendedProducts(toAdd).stream().map(p -> p.nume).collect(Collectors.toList()).toArray();
            if (productNames.length > 0) {
                Object choice = JOptionPane.showInputDialog(null, "Produse disponibile",
                        "Alegeti un produs", JOptionPane.INFORMATION_MESSAGE, null,
                        productNames, productNames[0]);
                if (choice == null) {
                    JOptionPane.showMessageDialog(null, "Ai anulat operatia");
                    return;
                }
                toAdd = (String) choice;
            } else {
                JOptionPane.showMessageDialog(null, "Nu a fost gasit niciun produs care sa poata fi adaugat");
                return;
            }
        }
        Produs selectedProdus = getSelectedProdus(toAdd);
        if (selectedProdus == null) {
            JOptionPane.showMessageDialog(null, "Nu am gasit produsul " + toAdd);
            return;
        }
        selectedProdus.categorii.clear();
        selectedProdus.categorii.add(UUID.fromString(category.getId().toString()));
        boolean found = false;
        for (int i = 0; i < produse.size(); i++) {
            if (produse.get(i).id.equals(selectedProdus.id)) {
                found = true;
                produse.set(i, selectedProdus);
                updateList(produse);
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Nu am gasit produsul " + toAdd);
        }
    }//GEN-LAST:event_placeholderTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        listener.onCancel();
        listener.onFinish(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listener.onConfirm(produse);
        listener.onFinish(produse);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JList<String> jList1;
    public javax.swing.JScrollPane jScrollPane1;
    public views.PlaceholderTextField placeholderTextField1;
    // End of variables declaration//GEN-END:variables

    private void updateList(List<Produs> produseUpdated) {
        List<String> descendants = produseUpdated.stream().filter(p -> p.categorii.size() > 0)
                .filter(p -> p.categorii.get(0).equals(category.getId().toString()))
                .map(produs -> produs.nume)
                .collect(Collectors.toList());
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        model.clear();
        descendants.forEach((descendant) -> {
            model.addElement(descendant);
        });
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        List<Produs> initialProduse = (List<Produs>) formDataObject;
        updateList(initialProduse);
    }

    EventConfirmationListener listener;

    @Override
    public void setListener(EventConfirmationListener e) {
        this.listener = e;
    }
}
