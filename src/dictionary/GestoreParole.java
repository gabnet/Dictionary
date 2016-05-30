/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author gorig
 */
public class GestoreParole {

    private final GestoreFile gestoreFile;
    
    public GestoreParole(GestoreFile gestoreFile) {
        this.gestoreFile = gestoreFile;
    }
    
    public ArrayList<String> leggiParole(String inputFile) throws FileNotFoundException {
        
        BufferedReader lettoreParole = gestoreFile.leggiFile(inputFile);
        
        List<String> linee = leggiLinee(lettoreParole);
        
        ArrayList<String> insiemeParole = new ArrayList<>();
        
        linee.stream().forEach(l -> {
            l = ripulisciLinea(l);
            
            Arrays.stream(l.split(" ")).forEach(w -> {
                w = w.trim();
                if (!w.equals(" ") && !w.isEmpty())
                    insiemeParole.add(w);
            });
        });
        
        return insiemeParole;
    }
    
    public MappaParoleMultiple caricaMappa(String inputFile) throws IOException {
        
        FileReader lettoreMappa = gestoreFile.raccogliFileInput(inputFile);
        
        Properties proprieta = new Properties();
        
        proprieta.load(lettoreMappa);
        
        return caricaProprieta(proprieta);
    }

    public void salvaMappa(MappaParoleMultiple mappaParole, String mappaParoleFile) throws IOException {
        Properties proprietaParole = creaProprieta(mappaParole);
            
        gestoreFile.scriviFile(proprietaParole, mappaParoleFile);
    }
    
    private Properties creaProprieta(MappaParoleMultiple mappaParole) {
        Properties proprieta = new Properties();
        
        mappaParole.keySet().stream().forEach(chiave -> proprieta.setProperty(chiave, mappaParole.get(chiave).toString()));
        
        return proprieta;
    }
    
    private List<String> leggiLinee(BufferedReader lettore) {
        List<String> linee = new ArrayList<>();
        
        lettore.lines().forEach(linea -> linee.add(linea));
        
        return linee;
    }
    
    private String ripulisciLinea(String linea) {
        return linea.replace(",", "").replace(";", "").replace(".", "").replace("'", "");
    }
        private MappaParoleMultiple caricaProprieta(Properties proprieta) {
        MappaParoleMultiple mappaParole = new MappaParoleMultiple();
        
        proprieta.entrySet().stream().forEach(p -> {
            TipoParolaSet valore = new TipoParolaSet(p.getValue().toString());
            mappaParole.put(p.getKey().toString(), valore);
        });
        
        return mappaParole;
    }
}
