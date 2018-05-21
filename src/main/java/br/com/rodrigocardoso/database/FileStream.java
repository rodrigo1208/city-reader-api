package br.com.rodrigocardoso.database;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by rodri on 18/05/2018.
 */
public class FileStream {

    public static void open(String file, Consumer<BufferedReader> resolve) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            resolve.accept(br);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String file, Consumer<BufferedWriter> resolve) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            resolve.accept(bw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getLines(BufferedReader br) {
        List<String> lines = new ArrayList<>();
        String line;
        try {
            br.readLine();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void removeLineFromFile(String file, String lineToRemove) {

        try {

            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
