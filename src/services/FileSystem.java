/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import main.Main;

/**
 *
 * @author Manel
 */
public class FileSystem {

    public static boolean createDirectoryInImageBank(String directoryName) throws Exception {
        File f = Paths.get(Main.PathToDatabase.toString(), Main.PathToImageBank.toString(), directoryName).toFile();
        if (f.exists()) {
            throw new Exception("Directory already exists");
        }
        return f.mkdir();
    }

    public static boolean renameDirectory(Path pathToDirectory, String newName) {
        File directory = pathToDirectory.toFile();
        if (!directory.exists()) {
            return false;
        }
        return directory.renameTo(Paths.get(pathToDirectory.getParent().toString(), newName).toFile());
    }
}
