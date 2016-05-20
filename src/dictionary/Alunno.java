/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    private String frase = "";
    private String input = "";
    private String parola = "";
    
    private final String TASTO_ESCI = "Q";
    private final String TASTO_SUCCESSIVO = "X";
    private final String TASTO_SALTA = "J";
    
    private final GestoreFile gestoreFile;
    private final Motore motore;
    
    Alunno(String inputFile, GestoreFile gestoreFile, Motore motore) {
        this.inputFile = inputFile;
        this.gestoreFile = gestoreFile;
        this.motore = motore;
    }

    public int apprendi() {

        try {        
            BufferedReader lettoreParole = gestoreFile.leggiFile(inputFile);
            
            ArrayList<String> elencoParole = motore.leggiParole(lettoreParole);

            FileReader lettoreMappa = gestoreFile.raccogliFileInput(mappaParoleFile);
            
            MappaParoleMultiple mappaParole = motore.caricaMappa(lettoreMappa);
            
            mappaParole = aggiornaMappaParole(elencoParole, mappaParole);

            Properties proprietaParole = motore.creaProprieta(mappaParole);
            
            gestoreFile.scriviFile(proprietaParole, mappaParoleFile);
        } catch (Exception ex) {
            return -1;
        }
        
        return 0;
    }

    private MappaParoleMultiple aggiornaMappaParole(ArrayList<String> elencoParole, MappaParoleMultiple mappaParole) throws IOException {
        input = "";
        
        for(int indice = 0; indice < elencoParole.size() && !esci(input); indice++){
            
            parola = elencoParole.get(indice);
            
            do {
                stampaInput(parola, indice, elencoParole.size());
                input = prendiDaConsole();
            } while (!inputOk(input));
            
            if (!successivo(input) && !salta(input))
                mappaParole.put(parola, input);
            
            if (salta(input))
                indice = raccogliIndice(input, indice, elencoParole.size());
        }
        
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
    
    private void stampaInput(String parola, int corrente, int numeroTotale) {
        stampaManuale();
        
        frase = String.format("%s %s", frase, parola);
            
        if (corrente % 15 == 0)
            frase += "\n";
        
        System.out.printf("%s\n[%d/%d] %s -> ", frase, corrente, numeroTotale, parola);
    }

    private void stampaManuale() {
        System.out.printf("Specificare:\n");
        
        Arrays.stream(TipoParola.values()).forEach(tp -> System.out.printf("%s -> %s\n", tp, tp.sigla()));
    }

    private int raccogliIndice(String input, int corrente, int dimensioneMassima) {
        if (!salta(input))
            return corrente;
        
        int salto = Integer.valueOf(input.replace(TASTO_SALTA, ""));
        
        return salto < dimensioneMassima ? salto : dimensioneMassima;
    }
}
