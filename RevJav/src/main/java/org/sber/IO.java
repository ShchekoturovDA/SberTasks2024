package org.sber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class IO implements FileInterface{

    @Override
    public void createLog() {
        File log = new File("log");
        if (!log.exists()){
            log.mkdir();
        }
    }

    @Override
    public void writeFile(String name, String str) {
        cleanLogDirectory();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "log" + timestamp + ".txt";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        writer.println(str);
        writer.close();
    }
    @Override
    public String readFile(String name) {
        return null;
    }

    public void cleanLogDirectory() {
        File logDir = new File("log");
        File[] files = logDir.listFiles();
        if (files != null && files.length > 2) {
            Arrays.stream(files)
                    .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                    .skip(2)
                    .forEach(File::delete);
        }
    }

}
