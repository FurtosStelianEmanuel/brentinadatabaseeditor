/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import main.Main;
import models.produs.Culoare;
import models.produs.Produs;
import models.produs.TranslateCuloare;
import views.edit.ImageHolder;

/**
 *
 * @author Manel
 */
public class EditCuloareService {

    private Culoare original, copy;
    private EditCuloareApplicator applicator;
    Produs produsCopy;

    public EditCuloareService(Culoare originalCuloare, Produs produs) {
        this.original = originalCuloare;
        this.copy = new Culoare(originalCuloare);
        this.produsCopy = new Produs(produs);
    }

    public EditCuloareApplicator getApplicator() {
        return applicator;
    }

    public void unghiuriChanged(int value) {
        if (value < copy.getUnghiuri()) {
            applicator.removeLastImageHolder();
        } else if (value > copy.getUnghiuri()) {
            applicator.addNewEmptyImageHolder();
        }
        copy.setUnghiuri(value);
        applicator.repaint();
    }

    public Culoare getCuloareOriginal() {
        return original;
    }

    public Culoare getCuloareCopy() {
        return copy;
    }

    public void setApplicator(EditCuloareApplicator applicator) {
        this.applicator = applicator;
    }

    public void moveAllImagesToRightFolder(Component[] components) throws IOException {
        List<ImageHolder> imageHolders = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof ImageHolder) {
                imageHolders.add((ImageHolder) component);
            }
        }
        if (imageHolders.size() != copy.getUnghiuri()) {
            throw new UnsupportedOperationException("imaginile si unghiurile nu se potrivesc");
        }
        Path productImageBank = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString(), produsCopy.nume);
        if (copy.getUnghiuri() > 1) {
            for (int i = 0; i < imageHolders.size(); i++) {
                ImageHolder imageHolder = imageHolders.get(i);
                if (!imageHolder.getPathToImage().getParent().equals(productImageBank)) {
                    Path outputPath = Paths.get(
                            productImageBank.toString(),
                            String.format("%s_%d.jpg",
                                    copy.getNume(), i + 1)
                    );
                    Files.copy(
                            imageHolder.getPathToImage(),
                            outputPath,
                            StandardCopyOption.REPLACE_EXISTING
                    );
                    ImageHolder.writeCompressedImage(outputPath.toFile(), 0.7, 0.7);
                }
            }
        } else {
            throw new UnsupportedOperationException("implementeaza ma si pe mine");
        }
    }

    public void mapNewImagesToDatabase(Component[] components) {
        List<ImageHolder> imageHolders = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof ImageHolder) {
                imageHolders.add((ImageHolder) component);
            }
        }
        if (imageHolders.size() != copy.getUnghiuri()) {
            throw new UnsupportedOperationException("imaginile si unghiurile nu se potrivesc");
        }
        copy.setUnghiuri(imageHolders.size());
    }

    public void mapAllOtherFieldsToObject() {
        copy.setNume(applicator.form.numeCuloare.getText());
        TranslateCuloare tCuloare = TranslateCuloare.fromStrings(
                applicator.form.englezaCuloare.getText(),
                applicator.form.maghiaraCuloare.getText(),
                applicator.form.germanaCuloare.getText()
        );
        copy.setTranslate(tCuloare);
        copy.setRgb(applicator.form.culoarePaletar.getBackground());
        copy.setAlteCulori(copy.getAlteCulori());//AICI MAI TREBUIE LUCRAT !!!!, INCA de pe ui nu poti sa adaugi alte culori
    }

    public void mapAllImagesWithBaseColorName() {
        List<ImageHolder> imageHolders = new ArrayList<>();
        for (Component component : applicator.form.jPanel3.getComponents()) {
            if (component instanceof ImageHolder) {
                imageHolders.add((ImageHolder) component);
            }
        }
        if (imageHolders.size() != copy.getUnghiuri()) {
            throw new UnsupportedOperationException("imaginile si unghiurile nu se potrivesc");
        }
        if (copy.getUnghiuri() > 1) {
            for (int i = 0; i < imageHolders.size(); i++) {
                ImageHolder imageHolder = imageHolders.get(i);
                File f = imageHolder.getPathToImage().toFile();
                if (f.exists()) {
                    String newName = f.getName().replace(original.getNume(), copy.getNume());
                    if (!f.renameTo(Paths.get(f.getParent(), newName).toFile())) {
                        System.out.println("Nu am putut da rename " + f.toString());
                    }
                    File minImage = Paths.get(f.getPath().replace(".jpg", "-min.jpg")).toFile();
                    String newMinImageName = minImage.getName().replace(original.getNume(), copy.getNume());
                    if (minImage.exists()) {
                        if (!minImage.renameTo(Paths.get(minImage.getParent(), newMinImageName).toFile())) {
                            System.out.println("Nu am putut da rename la min file" + minImage.toString());
                        }
                    }
                } else {
                    System.out.println("fisier inexistent " + f.toString());
                }
            }
        } else {
            throw new UnsupportedOperationException("implementeaza ma si pe mine");
        }
    }
}
