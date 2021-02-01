/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import main.Main;
import models.produs.Culoare;
import services.interfaces.InitialCompleteInterface;
import views.edit.EditCuloareForm;
import views.edit.ImageHolder;

/**
 *
 * @author Manel
 */
public class EditCuloareApplicator implements InitialCompleteInterface {

    public final EditCuloareForm form;

    public static ActionListener ADD_IMAGE_ACTIONLISTENER = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ImageHolder src = (ImageHolder) ae.getSource();
            JFileChooser chooser = new JFileChooser(Main.PathToDatabase.toFile());
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                src.setImageHolderIcon(chooser.getSelectedFile().toPath());
            }
            src.setText("");
        }
    };

    public EditCuloareApplicator(EditCuloareForm form) {
        this.form = form;
    }

    public void addNewEmptyImageHolder() {
        ImageHolder button = new ImageHolder("Adauga imagine");
        button.addActionListener(ADD_IMAGE_ACTIONLISTENER);
        form.jPanel3.add(button);
        form.jPanel3.revalidate();
    }

    public void removeLastImageHolder() {
        form.jPanel3.remove(form.jPanel3.getComponent(form.jPanel3.getComponentCount() - 1));
        form.jPanel3.revalidate();
    }

    public void setAvailableImages(Culoare c) {
        if (c != null) {
            if (c.getUnghiuri() > 1) {
                for (int i = 1; i <= c.getUnghiuri(); i++) {
                    File f = Paths.get(
                            Main.PathToDatabase.toString(),
                            "views",
                            "preview",
                            "imagini",
                            form.getService().produsCopy.nume,
                            String.format("%s_%d.jpg", c.getNume(), i)
                    ).toFile();
                    if (!f.exists()) {
                        System.out.println("Nu am gasit imagine pentru culoarea " + String.format("%s_%d.jpg", c.getNume(), i));
                    } else {
                        ImageHolder holder = new ImageHolder(f.toPath());
                        holder.addActionListener(ADD_IMAGE_ACTIONLISTENER);
                        form.jPanel3.add(holder);
                    }
                }
            } else if (c.getUnghiuri() == 1) {
                File f = Paths.get(
                        Main.PathToDatabase.toString(),
                        "views",
                        "preview",
                        "imagini",
                        form.getService().produsCopy.nume,
                        String.format("%s.jpg", c.getNume())
                ).toFile();
                if (!f.exists()) {
                    System.out.println("Nu am gasit imagine pentru culoarea)" + String.format("%s.jpg", c.getNume()));
                    //cand iti da asta, verifica daca jsonu e pus in folderu bun
                } else {
                    ImageHolder holder = new ImageHolder(f.toPath());
                    holder.addActionListener(ADD_IMAGE_ACTIONLISTENER);
                    form.jPanel3.add(holder);
                }
            }
        } else {
            ImageHolder holder = new ImageHolder("Adauga o imagine");
            holder.addActionListener(ADD_IMAGE_ACTIONLISTENER);
            form.jPanel3.add(holder);
        }
    }

    boolean isAutocompleteDone = true;

    public boolean isAutocompleteDone() {
        return isAutocompleteDone;
    }

    @Override
    public void autoCompleteData(Object formDataObject) {
        if (formDataObject != null) {
            isAutocompleteDone = false;
            Culoare culoare = (Culoare) formDataObject;
            form.numeCuloare.setText(culoare.getNume());
            form.englezaCuloare.setText(culoare.getTranslation().getEnglish());
            form.maghiaraCuloare.setText(culoare.getTranslation().getHungarian());
            form.germanaCuloare.setText(culoare.getTranslation().getGerman());
            form.jSpinner1.setValue(culoare.getUnghiuri());
            form.culoarePaletar.setBackground(culoare.getRGB());
            setAvailableImages(culoare);
            form.jPanel2.setVisible(culoare.getNume().equals("multi"));
            if (form.jPanel2.isVisible()) {
                if (culoare.getAlteCulori() != null) {
                    form.jSpinner1.setEnabled(false);
                    form.numeCuloare.setEnabled(false);
                    DefaultComboBoxModel model = (DefaultComboBoxModel) form.alteCulori.getModel();
                    for (String altaCuloare : culoare.getAlteCulori()) {
                        model.addElement(altaCuloare);
                    }
                }
            }
            isAutocompleteDone = true;
        } else {
            isAutocompleteDone = false;
            setAvailableImages(null);
            form.jPanel2.setVisible(false);
            isAutocompleteDone = true;
        }
    }

    void repaint() {
        form.repaint();
    }

    void alteCuloriChanged(List<String> alteCulori) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) form.alteCulori.getModel();
        model.removeAllElements();
        alteCulori.forEach(culoare -> {
            model.addElement(culoare);
        });
    }
}
