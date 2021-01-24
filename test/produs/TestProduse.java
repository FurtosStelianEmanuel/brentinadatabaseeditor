/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import models.database.DatabaseModel;
import org.junit.Test;
import services.DatabaseService;

/**
 *
 * @author Manel
 */
public class TestProduse {

    DatabaseService dbService;
    File goodInputFile = Paths.get("c:", "users", "manel", "desktop", "produse.json").toFile();
    File outpootFile = Paths.get("c:", "users", "manel", "desktop", "output.json").toFile();

    public TestProduse() {
        dbService = new DatabaseService();
    }

    @Test
    public void test() throws ClassNotFoundException, IOException {
        DatabaseModel model = dbService.loadDatabase(goodInputFile);
        System.out.println(model.continut.get(0));
        dbService.saveDatabase(model, outpootFile);
    }
}
