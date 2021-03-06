/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import main.Main;
import models.database.DatabaseModel;
import models.produs.Culoare;
import models.produs.Produs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.interfaces.DatabaseServiceInterface;
import views.edit.ImageHolder;

/**
 *
 * @author Manel
 */
public class DatabaseService implements DatabaseServiceInterface {

    @Override
    public DatabaseModel loadDatabase(File file) throws ClassNotFoundException, IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(input);
            return DatabaseModel.fromJSONObject(json);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void saveDatabase(DatabaseModel model, File file) throws ClassNotFoundException, IOException {
        try (FileWriter output = new FileWriter(file)) {
            output.write(model.toJSONObject().toJSONString());
        }
    }

    @Override
    public void migrateToUUID(DatabaseModel databaseModel) throws ClassNotFoundException, IOException {
        for (Produs produs : databaseModel.continut) {
            UUID produsId = UUID.randomUUID();
            produs.id = produsId;
            FileSystem.renameDirectory(Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString(), produs.nume), produsId.toString());
        }
        saveDatabase(databaseModel, Paths.get(Main.PathToDatabase.toString(), "produse.json").toFile());
    }

    private String getIdProdus(List<services.migrations.models.produsnosimilareuuid.Produs> continut, String nume) {
        for (services.migrations.models.produsnosimilareuuid.Produs produs : continut) {
            if (produs.nume.equals(nume)) {
                return produs.id.toString();
            }
        }
        return "00000000-0000-0000-0000-000000000000";
    }

    private services.migrations.models.databasenosimilareuuid.DatabaseModel loadDatabaseNoUUIDSimilare(File file) throws IOException {
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(input);
            return services.migrations.models.databasenosimilareuuid.DatabaseModel.fromJSONObject(json);
        } catch (ParseException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void saveDatabaseNoUUIDSimilare(services.migrations.models.databasenosimilareuuid.DatabaseModel model, File f) throws IOException {
        try (FileWriter output = new FileWriter(f)) {
            output.write(model.toJSONObject().toJSONString());
        }
    }

    @Override
    public void migrateSimilareUUIDs(File file) throws ClassNotFoundException, IOException {
        services.migrations.models.databasenosimilareuuid.DatabaseModel model = loadDatabaseNoUUIDSimilare(file);
        for (services.migrations.models.produsnosimilareuuid.Produs produs : model.continut) {
            for (int i = produs.similare.size() - 1; i >= 0; i--) {
                String id = getIdProdus(model.continut, produs.similare.get(i));
                if (!id.equals("00000000-0000-0000-0000-000000000000")) {
                    produs.similare.set(i, id);
                } else {
                    System.out.println(String.format("%s a pierdut referinta la %s", produs.nume, produs.similare.get(i)));
                    produs.similare.remove(i);
                }
            }
        }
        saveDatabaseNoUUIDSimilare(model, Paths.get(Main.PathToDatabase.toString(), "produse.json").toFile());
    }

    public void showFileSizes(File json) throws ClassNotFoundException, IOException {
        DatabaseModel model = loadDatabase(json);
        Path imageBankPath = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString());
        double max = 0;
        for (Produs p : model.continut) {
            File produsDirectory = Paths.get(imageBankPath.toString(), p.id.toString()).toFile();
            File[] files = produsDirectory.listFiles();
            System.out.println(p.nume);
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                double kb = Files.size(file.toPath()) * .001d;
                if (kb > max) {
                    max = kb;
                }
                System.out.println(p.nume + " " + file.getName() + " " + kb);
            }
        }
        System.out.println("max " + max);
    }

    private boolean shouldDeleteFile(Produs p, File f) {
        if (f.isDirectory()) {
            return false;
        }
        String fileName = f.getName();
        for (Culoare c : p.culori) {
            if (c.getUnghiuri() == 1) {
                Pattern highRes = Pattern.compile(c.getNume() + "\\.jpg");
                Pattern lowRes = Pattern.compile(c.getNume() + "-min\\.jpg");
                if (highRes.matcher(fileName).matches() || lowRes.matcher(fileName).matches()) {
                    return false;
                }
            } else if (c.getUnghiuri() > 1) {
                for (int i = 1; i <= c.getUnghiuri(); i++) {
                    Pattern highRes = Pattern.compile(c.getNume() + "_" + i + "\\.jpg");
                    Pattern lowRes = Pattern.compile(c.getNume() + "_" + i + "-min\\.jpg");
                    if (highRes.matcher(fileName).matches() || lowRes.matcher(fileName).matches()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void removeImagesThatAreNotUsed(DatabaseModel model) {
        Path imageBankPath = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString());
        for (Produs p : model.continut) {
            File produsDirectory = Paths.get(imageBankPath.toString(), p.id.toString()).toFile();
            File[] files = produsDirectory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                if (shouldDeleteFile(p, file)) {
                    if (!file.delete()) {
                        System.out.println("Nu am putut sterge " + file.toString());
                    } else {
                        System.out.println("Am sters " + file.toString());
                    }
                }
            }
        }
    }

    private void compressImagesAboveKb(DatabaseModel model, double kbThreshold) throws IOException {
        Path imageBankPath = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString());
        for (Produs p : model.continut) {
            File produsDirectory = Paths.get(imageBankPath.toString(), p.id.toString()).toFile();
            File[] files = produsDirectory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                double kb = Files.size(file.toPath()) * .001d;
                if (kb > kbThreshold) {
                    System.out.println("Compressing " + file.toString());
                    System.out.println("Initial size : " + kb);
                    double compress = 0;
                    double resize = 0;
                    if (kb <= 500) {
                        compress = 0.5;
                        resize = 0.3;
                    } else if (kb >= 500 && kb <= 800) {
                        compress = 0.55;
                        resize = 0.35;
                    } else if (kb >= 800 && kb <= 1100) {
                        compress = 0.6;
                        resize = 0.4;
                    } else if (kb > 1100 && kb < 9000) {
                        compress = 0.7;
                        resize = 0.5;
                    } else {
                        compress = 0.8;
                        resize = 0.7;
                    }
                    ImageHolder.writeCompressedImage(file, compress, resize);
                    double newkb = Files.size(file.toPath()) * .001d;
                    System.out.println("After " + compress + " compression and " + resize + " resize -> " + newkb + " dropped " + (kb - newkb) + "kb");
                } else {
                    System.out.println("Ignorat " + file.toString() + " -> " + kb);
                }
            }
        }
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private void removeFoldersThatArentUsed(DatabaseModel model) {
        File imageBank = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString()).toFile();
        for (File f : imageBank.listFiles()) {
            Produs produs = model.continut.stream().filter(p -> p.id.toString().equals(f.getName())).findAny().orElse(null);
            if (produs == null) {
                if (!deleteDirectory(f)) {
                    System.out.println("Nu am putut sterge " + f.toString());
                } else {
                    System.out.println("Am sters " + f.toString());
                }
            }
        }
    }

    @Override
    public void cleanUpScript(File json) throws ClassNotFoundException, IOException {
        DatabaseModel model = loadDatabase(json);
        removeImagesThatAreNotUsed(model);
        compressImagesAboveKb(model, 600);
        removeFoldersThatArentUsed(model);
    }
}
