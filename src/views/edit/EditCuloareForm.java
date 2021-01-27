/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.edit;

import java.awt.Graphics;
import java.nio.file.Paths;
import services.interfaces.EventConfirmationListener;
import services.interfaces.FormListenerInterface;

/**
 *
 * @author Manel
 */
public class EditCuloareForm extends javax.swing.JFrame implements FormListenerInterface {

    /**
     * Creates new form EditCuloriForm
     */
    public EditCuloareForm() {
        initComponents();
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));
        jPanel3.add(new ImageHolder(Paths.get("c:", "users", "manel", "desktop", "argint_2.jpg")));

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        placeholderTextField1 = new views.PlaceholderTextField();
        placeholderTextField2 = new views.PlaceholderTextField();
        placeholderTextField3 = new views.PlaceholderTextField();
        placeholderTextField4 = new views.PlaceholderTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(getBackground());
                g.fillRect(0,0,15,15);
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Unghiuri : ");
        jPanel1.add(jLabel1);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        jPanel1.add(jSpinner1);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1);

        jButton1.setText("Culoare paletar");
        jPanel1.add(jButton1);

        jScrollPane2.setViewportView(jPanel3);
        jScrollPane2.getHorizontalScrollBar().setUnitIncrement(16);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(placeholderTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(placeholderTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(placeholderTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(placeholderTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(placeholderTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeholderTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeholderTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeholderTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        placeholderTextField1.setPlaceholder("Nume culoare");
        placeholderTextField2.setPlaceholder("Traducerea in engleza");
        placeholderTextField3.setPlaceholder("Traducerea in maghiara");
        placeholderTextField4.setPlaceholder("Traducerea in germana");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        listener.onCancel();
        listener.onFinish(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        listener.onConfirm(null);
        listener.onFinish(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSpinner jSpinner1;
    public views.PlaceholderTextField placeholderTextField1;
    public views.PlaceholderTextField placeholderTextField2;
    public views.PlaceholderTextField placeholderTextField3;
    public views.PlaceholderTextField placeholderTextField4;
    // End of variables declaration//GEN-END:variables

    EventConfirmationListener listener;

    @Override
    public void setListener(EventConfirmationListener e) {
        this.listener = e;
    }
}
