/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import com.sun.glass.events.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import models.produs.Produs;
import services.EditNewProductsService;
import services.EditSimpleStringListsService;
import services.interfaces.FormListenerInterface;
import services.interfaces.EventConfirmationListener;
import services.interfaces.ProductNotFoundException;

/**
 *
 * @author Manel
 */
public class EditNewProductsForm extends javax.swing.JFrame implements FormListenerInterface {
    
    EditNewProductsService service;
    EventConfirmationListener listener;

    /**
     * Creates new form EditDimensiuniForm
     */
    protected EditNewProductsForm() {
        initComponents();
    }
    
    public EditNewProductsForm(EditNewProductsService service) {
        initComponents();
        this.service = service;
    }
    
    @Override
    public void setListener(EventConfirmationListener listener) {
        this.listener = listener;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placeholderTextField1 = new views.PlaceholderTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        placeholderTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeholderTextField1ActionPerformed(evt);
            }
        });

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel model=new DefaultListModel<>();
        jList1.setModel(model);
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Mai sus in lista");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        jButton5.setText("Mai jos in lista");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Puteti folosi si sagetile ca sa mutati in sus sau in jos produsul selectat");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sau tasta delete pentru a sterge un produs din lista de produse noi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(placeholderTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(placeholderTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        placeholderTextField1.setPlaceholder("Introdu numele unui produs");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            service.changeOrder(jList1.getSelectedIndex(), evt.getKeyCode());
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            service.removeFromNewProducts(jList1.getSelectedIndex());
        }
    }//GEN-LAST:event_jList1KeyPressed

    private void placeholderTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeholderTextField1ActionPerformed
        try {
            service.addToNewProducts(placeholderTextField1.getText());
        } catch (Exception ex) {
            if (ex instanceof ProductNotFoundException) {
                ProductNotFoundException productNotFound = (ProductNotFoundException) ex;
                List<String> recomandate = productNotFound.getProduseRecomandate();
                Object[] recomandateArray = new Object[recomandate.size()];
                for (int i = 0; i < recomandate.size(); i++) {
                    recomandateArray[i] = recomandate.get(i);
                }
                if (recomandateArray.length > 0) {
                    Object choice = JOptionPane.showInputDialog(null, "Produse disponibile",
                            "Alegeti un produs", JOptionPane.INFORMATION_MESSAGE, null,
                            recomandateArray, recomandateArray[0]);
                    if (choice != null) {
                        try {
                            service.addToNewProducts((String) choice);
                        } catch (Exception ex1) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nu a fost gasit niciun produs cu acel nume");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_placeholderTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listener.onCancel();
        listener.onFinish(null);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private UUID getIdOfProduct(String productName) {
        for (Produs p : service.getProduse()) {
            if (productName.equals(p.nume)) {
                return p.id;
            }
        }
        return null;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List<UUID> uuids = new ArrayList<>();
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        for (int i = 0; i < model.size(); i++) {
            UUID productId = getIdOfProduct(model.get(i).toString());
            if (productId != null) {
                uuids.add(productId);
            } else {
                JOptionPane.showMessageDialog(null, "Nu am gasit id-ul produsului " + model.get(i));
            }
        }
        listener.onConfirm(uuids);
        listener.onFinish(uuids);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        service.changeOrder(jList1.getSelectedIndex(), KeyEvent.VK_UP);
        if (jList1.getSelectedIndex() > 0) {
            jList1.setSelectedIndex(jList1.getSelectedIndex() - 1);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        service.changeOrder(jList1.getSelectedIndex(), KeyEvent.VK_DOWN);
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        if (jList1.getSelectedIndex() < model.size() - 1) {
            jList1.setSelectedIndex(jList1.getSelectedIndex() + 1);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        service.removeFromNewProducts(jList1.getSelectedIndex());
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JList<String> jList1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane1;
    public views.PlaceholderTextField placeholderTextField1;
    // End of variables declaration//GEN-END:variables
}
