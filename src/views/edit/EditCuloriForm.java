/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import javax.swing.DefaultListModel;
import models.produs.Culoare;
import models.produs.Produs;
import services.EditCuloareApplicator;
import services.EditCuloareService;
import services.EditCuloriService;
import services.interfaces.EventConfirmationListener;
import services.interfaces.FormListenerInterface;

/**
 *
 * @author Manel
 */
public class EditCuloriForm extends javax.swing.JFrame implements FormListenerInterface {

    EditCuloriService service;
    EventConfirmationListener confirmationListener;

    /**
     * Creates new form EditCuloriForm
     */
    protected EditCuloriForm() {
        initComponents();
    }

    public EditCuloriForm(EditCuloriService service) {
        this.service = service;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jList1.setModel(new DefaultListModel());
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);

        jButton1.setText("Adauga o noua culoare");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tasta delete pentru a sterge o culoare");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        confirmationListener.onCancel();
        confirmationListener.onFinish(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        confirmationListener.onConfirm(service.getCopy());
        confirmationListener.onFinish(service.getCopy());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (jList1.getSelectedIndex() != -1) {
            culoareSelected(jList1.getSelectedIndex());
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newCuloare();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton6;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JList<String> jList1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setListener(EventConfirmationListener o) {
        this.confirmationListener = o;
    }

    public void newCuloare() {
        Culoare newCuloare = Culoare.emptyInstance();
        newCuloare.setUnghiuri(1);
        EditCuloareService culoareService = new EditCuloareService(newCuloare, service.getProdusCopy());
        EditCuloareForm culoareForm = new EditCuloareForm(culoareService);
        EditCuloareApplicator applicator = new EditCuloareApplicator(culoareForm);
        applicator.autoCompleteData(null);
        culoareService.setApplicator(applicator);
        setEnabled(false);
        culoareForm.setVisible(true);
        culoareForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                Culoare culoare = (Culoare) p;
                service.getCopy().add(culoare);
                service.reapplyVisuals();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish(Object o) {
                culoareForm.dispose();
                setEnabled(true);
                toFront();
            }
        });
    }

    public void culoareSelected(int index) {
        EditCuloareService culoareService = new EditCuloareService(service.getCopy().get(index), service.getProdusCopy());
        EditCuloareForm culoareForm = new EditCuloareForm(culoareService);
        EditCuloareApplicator applicator = new EditCuloareApplicator(culoareForm);
        culoareService.setApplicator(applicator);
        applicator.autoCompleteData(service.getCopy().get(index));
        setEnabled(false);
        culoareForm.setVisible(true);
        culoareForm.setListener(new EventConfirmationListener() {
            @Override
            public void onConfirm(Object p) {
                Culoare culoare = (Culoare) p;
                service.getCopy().set(index, culoare);
                service.reapplyVisuals();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onFinish(Object o) {
                culoareForm.dispose();
                setEnabled(true);
                toFront();
            }
        });
    }
}
