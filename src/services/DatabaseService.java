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
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import models.database.DatabaseModel;
import models.produs.Produs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.interfaces.DatabaseServiceInterface;

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
}
