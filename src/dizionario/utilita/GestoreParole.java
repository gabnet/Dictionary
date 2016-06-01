/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita;

import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.MappaVerbi;
import dizionario.modelli.ParolaSet;
import dizionario.modelli.Verbo;
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
    
    public MappaParoleMultiple caricaMappaParoleMultiple(String inputFile) throws IOException{
        
        FileReader lettoreMappa = gestoreFile.raccogliFileInput(inputFile);
        
        Properties proprieta = new Properties();
        
        proprieta.load(lettoreMappa);
        
        MappaParoleMultiple mappaParole = new MappaParoleMultiple();
        
        proprieta.entrySet().stream().forEach(p -> {
            ParolaSet valore = new ParolaSet(p.getValue().toString());
            mappaParole.put(p.getKey().toString(), valore);
        });
        
        return mappaParole;
    }
    
    public MappaVerbi caricaMappaVerbi(String inputFile) throws IOException{
        
        FileReader lettoreMappa = gestoreFile.raccogliFileInput(inputFile);
        
        Properties proprieta = new Properties();
        
        proprieta.load(lettoreMappa);
        
        MappaVerbi mappaVerbi = new MappaVerbi();
        
        proprieta.entrySet().forEach(v -> {
            Verbo verbo = new Verbo(v.getKey().toString(), v.getValue().toString());
            mappaVerbi.put(v.getKey().toString(), verbo);
        });
        
        return mappaVerbi;
    }

    public void salvaMappaParoleMultiple(MappaParoleMultiple mappaParole, String mappaParoleFile) throws IOException {
        Properties proprietaParole = creaProprietaParoleMultiple(mappaParole);
            
        gestoreFile.scriviFile(proprietaParole, mappaParoleFile);
    }
    
    public void salvaMappaVerbi(MappaVerbi mappaVerbi, String mappaVerbiFile) throws IOException {
        Properties proprietaParole = creaProprietaVerbi(mappaVerbi);
            
        gestoreFile.scriviFile(proprietaParole, mappaVerbiFile);
    }
    
    private Properties creaProprietaParoleMultiple(MappaParoleMultiple mappaParole) {
        Properties proprieta = new Properties();
        
        mappaParole.keySet().stream().forEach(chiave -> proprieta.setProperty(chiave, mappaParole.get(chiave).toString()));
        
        return proprieta;
    }
    
    private Properties creaProprietaVerbi(MappaVerbi mappaVerbi) {
        Properties proprieta = new Properties();
        
        mappaVerbi.keySet().stream().forEach(chiave -> proprieta.setProperty(chiave, mappaVerbi.get(chiave).toString()));
        
        return proprieta;
    }
    
    private List<String> leggiLinee(BufferedReader lettore) {
        List<String> linee = new ArrayList<>();
        
        lettore.lines().forEach(linea -> linee.add(linea));
        
        return linee;
    }
    
    private String ripulisciLinea(String linea) {
        return linea.replace(",", " ").replace(";", " ").replace(".", " ").replace("'", " ").replace("’", " ");
    }
}
