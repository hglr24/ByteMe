package data.internal;

import data.external.DataManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataSandbox {

    public static void main(String[] args) {
        FileWriter fileWriter = null;
        System.out.println("user.dir");
        try {
            fileWriter =
                    new FileWriter(new File("data" + File.separator + "resources" + File.separator, "myTestFile"));
            fileWriter.write("Here is my string, I hope you like it");
        } catch (IOException exception){
            System.out.println("In catch block");
            exception.printStackTrace();
        } finally {
            System.out.println("In finally block");
            try {
                if (fileWriter != null){
                    fileWriter.close();
                }
            } catch (IOException e){
                System.out.println("Couldn't close file");
            }
        }

        DataManager dm = new DataManager();
        dm.createFolder("testGameFolder");

    }
}
