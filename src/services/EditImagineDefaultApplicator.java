/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Main;
import models.produs.Culoare;
import models.produs.InitialComplete;
import models.produs.Produs;
import views.edit.EditImagineDefaultForm;
import views.auxclasses.ImageHolder;

/**
 *
 * @author Manel
 */
public class EditImagineDefaultApplicator extends InitialComplete {

    EditImagineDefaultForm form;

    ActionListener changeImagineDefaultActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            int choice = JOptionPane.showConfirmDialog(null, "Vrei sa setezi aceasta imagine ca default?");
            if (choice == JOptionPane.OK_OPTION) {
                ImageHolder imageHolder = (ImageHolder) ae.getSource();
                form.service.imagineDefaultChanged(imageHolder.getPathToImage().getFileName().toString());
            }
        }
    };

    public EditImagineDefaultApplicator(JFrame form) {
        super(form);
        this.form = (EditImagineDefaultForm) form;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        Produs produs = (Produs) formDataObject;
        Path productImageBank = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString(), produs.id.toString());
        for (Culoare culoare : produs.culori) {
            if (culoare.getUnghiuri() == 1) {
                Path imagePath = Paths.get(
                        productImageBank.toString(),
                        String.format("%s.jpg", culoare.getNume())
                );
                form.jLabel3.setText(imagePath.getFileName().toString());
                ImageHolder imageHolder = new ImageHolder(imagePath);
                imageHolder.setFocusable(false);
                imageHolder.addActionListener(changeImagineDefaultActionListener);
                if (imagePath.getFileName().toString().equals(produs.imagineDefault)) {
                    imageHolder.setBackground(Color.BLUE);
                }
                form.jPanel3.add(imageHolder);
            } else if (culoare.getUnghiuri() > 1) {
                for (int i = 1; i <= culoare.getUnghiuri(); i++) {
                    Path imagePath = Paths.get(
                            productImageBank.toString(),
                            String.format("%s_%d.jpg", culoare.getNume(), i)
                    );
                    form.jLabel3.setText(imagePath.getFileName().toString());
                    ImageHolder imageHolder = new ImageHolder(imagePath);
                    imageHolder.addActionListener(changeImagineDefaultActionListener);
                    imageHolder.setFocusable(false);
                    if (imagePath.getFileName().toString().equals(produs.imagineDefault)) {
                        imageHolder.setBackground(Color.BLUE);
                    }
                    form.jPanel3.add(imageHolder);
                }
            }
        }
        if (produs.palleteType < 2) {
            form.jComboBox1.setSelectedIndex((int) produs.palleteType);
        } else {
            System.out.println("Unsupported palleteType");
        }
    }

    void imagineDefaultChanged(String imagineDefault) {
        form.jLabel3.setText(imagineDefault);
        for (Component component : form.jPanel3.getComponents()) {
            if (component instanceof ImageHolder) {
                ImageHolder imageHolder = (ImageHolder) component;
                if (imageHolder.getPathToImage().getFileName().toString().equals(imagineDefault)) {
                    imageHolder.setBackground(Color.blue);
                } else {
                    imageHolder.setBackground(Color.white);
                }
            }
        }
    }

}
