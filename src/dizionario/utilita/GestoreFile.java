/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author gorig
 */
public class GestoreFile {
    
    public BufferedReader leggiFile(String inputFile) throws FileNotFoundException {
        
        return new BufferedReader(raccogliFileInput(inputFile));
    }
    
    public void scriviFile(Properties proprieta, String outputFile) throws FileNotFoundException, IOException {
        proprieta.store(raccogliFileOutput(outputFile), "Dizionario parole");
    }

    public FileReader raccogliFileInput(String inputFile) throws FileNotFoundException {
        if (!Files.exists(Paths.get(inputFile)))
            throw new FileNotFoundException(String.format("File: %s", inputFile));
        
        return new FileReader(new File(inputFile));
    }
    
    private FileOutputStream raccogliFileOutput(String inputFile) throws FileNotFoundException {
        return new FileOutputStream(new File(inputFile));
    }
}
