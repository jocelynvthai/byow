package byow.Core;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SavedWorld {

    String path;

    public SavedWorld() {
        path = System.getProperty("user.dir") + "/byow/Core/SavedWorld.txt";
    }

    /**
     * @tag https://www.w3schools.com/java/java_files_create.asp
     * used to write writeToFile
     */
    public void writeToFile(String input) {
        try {
            FileWriter myWriter = new FileWriter(path, false);
            myWriter.write(input);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public String readFromFile() {
        String input = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                input = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
        return input;
    }
}
