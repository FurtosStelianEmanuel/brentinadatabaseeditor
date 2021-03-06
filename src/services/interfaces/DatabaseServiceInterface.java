/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interfaces;

import java.io.File;
import java.io.IOException;
import models.database.DatabaseModel;

/**
 *
 * @author Manel
 */
public interface DatabaseServiceInterface {

    DatabaseModel loadDatabase(File file) throws ClassNotFoundException, IOException;

    void saveDatabase(DatabaseModel model, File file) throws ClassNotFoundException, IOException;

    void migrateToUUID(DatabaseModel model) throws ClassNotFoundException, IOException;

    void migrateSimilareUUIDs(File file) throws ClassNotFoundException, IOException;

    void cleanUpScript(File json) throws ClassNotFoundException, IOException;
}
