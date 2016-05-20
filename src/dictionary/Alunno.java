/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author gorig
 */
public class Alunno {

    private String inputFile;
    private final String mappaParoleFile = "text\\dizionario.properties";
    
    Alunno(String inputFile) {
        this.inputFile = inputFile;
    }

    public int apprendi() {

        try {        
            BufferedReader lettore;

            lettore = leggiFile(inputFile);
            
            List<String> linee = leggiLinee(lettore);
            
            ArrayList<String> elencoParole = leggiParole(linee);

            MappaParoleMultiple mappaParole = calcolaMappaParole(elencoParole);

            salvaProprieta(mappaParole);
        } catch (Exception ex) {
            return -1;
        }
        
        return 0;
    }

    private BufferedReader leggiFile(String inputFile) throws FileNotFoundException {
        if (!Files.exists(Paths.get(inputFile)))
            throw new FileNotFoundException(String.format("File: %s", inputFile));
            
        BufferedReader lettoreBufferizzato = new BufferedReader(new FileReader(new File(inputFile)));
        
        return lettoreBufferizzato;
    }

    private List<String> leggiLinee(BufferedReader reader) {
        List<String> linee = new ArrayList<>();
        
        reader.lines().forEach(linea -> linee.add(linea));
        
        return linee;
    }

    private ArrayList<String> leggiParole(List<String> linee) {
        
        ArrayList<String> insiemeParole = new ArrayList<>();
        
        linee.stream().forEach(l -> {
            l = ripulisciLinea(l);
            String[] parole = l.split(" ");
            Arrays.stream(parole).forEach(w -> {
                w = w.trim();
                if (!w.equals(" ") && !w.isEmpty())
                    insiemeParole.add(w);
                });
        });
        
        return insiemeParole;
    }

    private String ripulisciLinea(String linea) {
        return linea.replace(",", "").replace(";", "").replace(".", "").replace("'", "");
    }

    private MappaParoleMultiple calcolaMappaParole(ArrayList<String> elencoParole) throws IOException {
        MappaParoleMultiple mappaParole = leggiMappaParole();
        
        String frase = "";
        
        for(String parola: elencoParole){
            String input = new String();
            
            frase = String.format("%s %s", frase, parola);
            
            do {
                stampaInput(frase, parola);
                input = new BufferedReader(new InputStreamReader(System.in)).readLine();
                input = input.toUpperCase();
            } while (inputOk(input));
            
            if (!skipInput(input))
                mappaParole.put(parola, input);
        }
        
        return mappaParole;
    }
    
    private boolean inputOk(String input) {
        return (!input.isEmpty() && !skipInput(input) && !TipoParola.accetta(input));
    }
    
    private boolean skipInput(String input){
        return input.equalsIgnoreCase("X");
    }
    
    private void stampaInput(String frase, String parola) {
        stampaManuale();
        
        System.out.printf("%s\n%s -> ", frase, parola);
    }

    private void stampaManuale() {
        System.out.printf("Specificare:\n");
        
        Arrays.stream(TipoParola.values()).forEach(wt -> System.out.printf("%s -> %s\n", wt, wt.sigla()));
    }

    private void salvaProprieta(MappaParoleMultiple mappaParole) throws FileNotFoundException, IOException {
        Properties proprieta = calcolaProprieta(mappaParole);
        
        scriviFile(proprieta);
    }

    private Properties calcolaProprieta(MappaParoleMultiple mappaParole) {
        Properties proprieta = new Properties();
        
        mappaParole.keySet().stream().forEach(chiave -> proprieta.setProperty(chiave, mappaParole.get(chiave).toString()));
        
        return proprieta;
    }

    private void scriviFile(Properties proprieta) throws FileNotFoundException, IOException {
        proprieta.store(new FileOutputStream(new File(mappaParoleFile)), "Dizionario parole");
    }

    private MappaParoleMultiple leggiMappaParole() throws IOException {
        Properties proprieta = new Properties();
        
        proprieta.load(new FileInputStream(new File(mappaParoleFile)));
        
        return caricaMappaParole(proprieta);
    }

    private MappaParoleMultiple caricaMappaParole(Properties proprieta) {
        MappaParoleMultiple mappaParole = new MappaParoleMultiple();
        
        proprieta.entrySet().stream().forEach(p -> {
            TipoParolaSet valore = new TipoParolaSet(p.getValue().toString());
            mappaParole.put(p.getKey().toString(), valore);
        });
        
        return mappaParole;
    }
}
