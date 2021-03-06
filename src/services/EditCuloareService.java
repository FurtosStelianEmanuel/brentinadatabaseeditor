/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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

    public Culoare getCuloareOriginal() {
        return original;
    }

    public Culoare getCuloareCopy() {
        return copy;
    }

    public void setApplicator(EditCuloareApplicator applicator) {
        this.applicator = applicator;
    }

    private void writeCompressedImages(Path image) throws IOException {
        double sizeInKb = Files.size(image) * .001d;

        double compressHighRes = 0;
        double resizeHighRes = 0;

        double compressLowRes = 0.2;
        double resizeLowRes = 0.4;

        if (sizeInKb >= 200 && sizeInKb <= 500) {
            compressHighRes = 0.3;
            resizeHighRes = 0.6;

            compressLowRes = 0.4;
            resizeLowRes = 0.6;
        } else if (sizeInKb >= 500 && sizeInKb <= 5000) {
            compressHighRes = 0.5;
            resizeHighRes = 0.6;

            compressLowRes = 0.6;
            resizeLowRes = 0.7;
        } else if (sizeInKb >= 5000 && sizeInKb < 10000) {
            compressHighRes = 0.6;
            resizeHighRes = 0.6;

            compressLowRes = 0.7;
            resizeLowRes = 0.6;
        } else if (sizeInKb > 10000) {
            compressHighRes = 0.7;
            resizeHighRes = 0.7;

            compressLowRes = 0.8;
            resizeLowRes = 0.8;
        }

        ImageHolder.writeCompressedImage(image.toFile(), compressLowRes, resizeLowRes, "-min.jpg");
        ImageHolder.writeCompressedImage(image.toFile(), compressHighRes, resizeHighRes, ".jpg");
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
        Path productImageBank = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString(), produsCopy.id.toString());
        if (copy.getUnghiuri() > 1) {
            for (int i = 0; i < imageHolders.size(); i++) {
                ImageHolder imageHolder = imageHolders.get(i);
                Path outputPath = Paths.get(
                        productImageBank.toString(),
                        String.format("%s_%d.jpg",
                                applicator.form.numeCuloare.getText(), i + 1)
                );
                Files.copy(
                        imageHolder.getPathToImage(),
                        outputPath,
                        StandardCopyOption.REPLACE_EXISTING
                );
                writeCompressedImages(outputPath);
            }
        } else if (copy.getUnghiuri() == 1) {
            ImageHolder imageHolder = imageHolders.get(0);
            Path outputPath = Paths.get(
                    productImageBank.toString(),
                    String.format("%s.jpg",
                            applicator.form.numeCuloare.getText())
            );
            Files.copy(
                    imageHolder.getPathToImage(),
                    outputPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            writeCompressedImages(outputPath);
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

    private List<String> getAlteCulori() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) applicator.form.alteCulori.getModel();
        List<String> toReturn = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            toReturn.add((String) model.getElementAt(i));
        }
        return toReturn;
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
        copy.setAlteCulori(getAlteCulori());
    }

    /**
     * Aducea behaviour nasol uneori, inlocuit de alte functii mai simple care tot asta fac (nu mai stiu care). Deprecated since 02/25/2021-11:10PM
     *
     * @deprecated
     */
    @Deprecated
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
        } else if (imageHolders.size() == 1) {
            ImageHolder imageHolder = imageHolders.get(0);
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
    }

    public void paletarColorChanged(Color showDialogColor) {
        copy.setRgb(showDialogColor);
    }

    public void alteCuloriChanged(String newCuloare) throws Exception {
        if (!copy.getAlteCulori().contains(newCuloare)) {
            List<String> alteCulori = copy.getAlteCulori();
            alteCulori.add(newCuloare);
            copy.setAlteCulori(alteCulori);
            applicator.alteCuloriChanged(alteCulori);
        } else {
            throw new Exception("Aceasta culoare este deja adaugata");
        }
    }

    public void removeUnghi(ImageHolder holder) {
        applicator.removeUnghi(holder);
        copy.setUnghiuri(applicator.form.getImageHolderCount());
    }

    public void addUnghi() {
        applicator.addUnghi();
        copy.setUnghiuri(applicator.form.getImageHolderCount());
    }
}
