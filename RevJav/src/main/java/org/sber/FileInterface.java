package org.sber;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface FileInterface {
    void createLog();
    void writeFile(String name, String str);

    String readFile(String name);
    void cleanLogDirectory();
}
