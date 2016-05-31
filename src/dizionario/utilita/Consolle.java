/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita;

import dizionario.modelli.parole.GuardianoDelleParole;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gorig
 */
public class Consolle {
    private final String TASTO_ESCI = "E";
    private final String TASTO_SUCCESSIVO = "SU";
    private final String TASTO_SALTA = "SA";
    private final String CONFERMATO = "S";
    
    public boolean salta(String input){
        Matcher m = Pattern.compile(TASTO_SALTA + "\\d+").matcher(input);
        
        return m.matches();
    }
    
    public String prendi() throws IOException {
        return (new BufferedReader(new InputStreamReader(System.in)).readLine()).toUpperCase();
    }
    
    public boolean tipoParolaInputOk(String input) {
        return ((input != null) && !("".equals(input)) && (successivo(input) || salta(input) || GuardianoDelleParole.accetta(input)));
    }
    
    public boolean inputOk(String input) {
        return ((input != null) && !("".equals(input)) && (successivo(input) || salta(input) || parolaLibera()));
    }
    
    public boolean inputOkVerbi(String input) {
        return ((input != null) && !("".equals(input)) && (successivo(input) || salta(input) || GuardianoDelleParole.accettaVerbi(input)));
    }
    
    public boolean successivo(String input){
        return input.equalsIgnoreCase(TASTO_SUCCESSIVO) || esci(input);
    }
    
    public boolean esci(String input){
        if (input.equalsIgnoreCase(""))
            return false;
        
        return input.equalsIgnoreCase(TASTO_ESCI);
    }
    
    public int prendiSalto(String input) {
        return Integer.valueOf(input.replace(TASTO_SALTA, ""));
    }
    
    public String tastoSuccessivo(){
        return TASTO_SUCCESSIVO;
    }

    private boolean parolaLibera() {
        return true;
    }

    public boolean confermato() throws IOException {
        String input = prendi();
        return ((input != null) && !("".equals(input)) && confermato(input));
    }

    private boolean confermato(String input) {
        return input.equalsIgnoreCase(CONFERMATO);
    }
}
