/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import plugin.RequestSender;
import services.DatabaseService;
import services.MainFormApplicator;
import services.MainFormService;
import services.SystemManager;
import views.MainForm;

/**
 *
 * @author Manel
 */
public class Main {

    DatabaseService databaseService;
    MainFormService mainFormService;
    MainFormApplicator mainFormApplicator;
    RequestSender requestSender;
    MainForm mainForm;

    public static Path Path = Paths.get(SystemManager.get_path("BrentinaDatabaseEditor.jar"));
    public static Path PathToImageBank = Paths.get("views", "preview", "imagini");
    public static Path PathToDatabase;

    Main() {
        requestSender = new RequestSender();
        databaseService = new DatabaseService();
        mainFormService = new MainFormService(databaseService, requestSender);

        mainForm = new MainForm(mainFormService);
        mainFormApplicator = new MainFormApplicator(mainForm);
        mainFormService.setApplicator(mainFormApplicator);

        mainForm.setVisible(true);
        mainForm.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Main m = new Main();
        /*DatabaseService service=new DatabaseService();
        DatabaseModel model=service.loadDatabase(Paths.get("C:", "users","manel","desktop","produse.json").toFile());
        //service.saveDatabase(model,Paths.get("C:", "users","manel","desktop","test.json").toFile());*/
    }
}
