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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gorig
 */
public class Alunno {

    private final String inputFile;
    private final String mappaParoleFile = "text\\dizionario.properties";
    
    private final String TASTO_ESCI = "Q";
    private final String TASTO_SUCCESSIVO = "X";
    private final String TASTO_SALTA = "J";
    
    Alunno(String inputFile) {
        this.inputFile = inputFile;
    }

    public int apprendi() {

        try {        
            BufferedReader lettore = leggiFile(inputFile);
            
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
            
        return new BufferedReader(new FileReader(new File(inputFile)));
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
            
            Arrays.stream(l.split(" ")).forEach(w -> {
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
        String input = "";
        int aCapo = 0;
        
        String parola = "";
        
        for(int indice = 0; indice < elencoParole.size() && !esci(input); indice++){
            aCapo++;
            
            parola = elencoParole.get(indice);
            
            frase = String.format("%s %s", frase, parola);
            
            if (aCapo % 15 == 0)
                frase += "\n";
            
            do {
                stampaInput(frase, parola, indice, elencoParole.size());
                input = prendiDaConsole();
            } while (!inputOk(input));
            
            if (!successivo(input) && !salta(input))
                mappaParole.put(parola, input);
            
            if (salta(input))
                indice = raccogliIndice(input, indice, elencoParole.size());
        };
        
        return mappaParole;
    }
    
    private boolean esci(String input){
        if (input.equalsIgnoreCase(""))
            return false;
        
        return input.equalsIgnoreCase(TASTO_ESCI);
    }
    
    private boolean salta(String input){
        Matcher m = Pattern.compile(TASTO_SALTA + "\\d+").matcher(input);
        
        return m.matches();
    }
    
    private String prendiDaConsole() throws IOException {
        return (new BufferedReader(new InputStreamReader(System.in)).readLine()).toUpperCase();
    }
    
    private boolean inputOk(String input) {
        return ((input != null) && !("".equals(input)) && (successivo(input) || salta(input) || TipoParola.accetta(input)));
    }
    
    private boolean successivo(String input){
        return input.equalsIgnoreCase(TASTO_SUCCESSIVO) || esci(input);
    }
    
    private void stampaInput(String frase, String parola, int corrente, int numeroTotale) {
        stampaManuale();
        
        System.out.printf("%s\n[%d/%d] %s -> ", frase, corrente, numeroTotale, parola);
    }

    private void stampaManuale() {
        System.out.printf("Specificare:\n");
        
        Arrays.stream(TipoParola.values()).forEach(tp -> System.out.printf("%s -> %s\n", tp, tp.sigla()));
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

    private int raccogliIndice(String input, int corrente, int dimensioneMassima) {
        if (!salta(input))
            return corrente;
        
        int salto = Integer.valueOf(input.replace(TASTO_SALTA, ""));
        
        return salto < dimensioneMassima ? salto : dimensioneMassima;
    }
}
