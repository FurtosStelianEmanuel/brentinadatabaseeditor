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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.database.DatabaseModel;
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

}
