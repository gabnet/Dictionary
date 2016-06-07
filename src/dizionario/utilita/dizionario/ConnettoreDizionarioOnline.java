/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita.dizionario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author gorig
 */
public class ConnettoreDizionarioOnline {
    
    private static final String indirizzoDizionario = "http://www.italian-verbs.com/verbi-italiani/coniugazione.php?verbo=";
    private static final String prefix = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"><html><head><title>Verbi Italiani - Coniugazione verbo ";
    
    public static String prendiInfinito(String verbo){
        String risultato = chiamaDizionario(verbo);
        
        risultato = risultato.replace(prefix, "");
        
        return risultato.substring(0, risultato.indexOf("</title>")).trim();
    }

    private static String chiamaDizionario(String verbo) {
        
        String html = "";
        
        try {
            URL url = new URL(componiUrl(verbo));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader read = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = read.readLine();
            
            while(line!=null) {
                html += line;
                line = read.readLine();
            }
        } catch(IOException ex) {

        }
        
        return html;
    }

    private static String componiUrl(String verbo) {
        return String.format("%s%s", indirizzoDizionario, verbo);
    }
}
