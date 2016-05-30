/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.utilita.Consolle;
import dizionario.utilita.GestoreParole;
import dizionario.utilita.GestoreFile;

/**
 *
 * @author gorig
 */
public class Dictionary {

    public static String mappaParoleFile = "text\\dizionario.properties";
    public static String mappaVerbiFile = "text\\dizionarioVerbi.properties";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int returnValue = -1;
        
        GestoreFile gestoreFile = new GestoreFile();
        GestoreParole gestoreParole = new GestoreParole(gestoreFile);
        Consolle consolle = new Consolle();
        
        if (args.length == 0)
            System.out.printf("Scegliere -ANalisi o -APprendimento o -RIassunto\n");
        
        if ("-AN".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
            returnValue = startAnalisi(args[1], gestoreParole);
        else if ("-AP".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
                returnValue = startApprendimento(args[1], gestoreParole, consolle);
        else if ("-RI".equalsIgnoreCase(args[0]))
                returnValue = startElaborazione(gestoreParole, consolle);
        else
            System.out.printf("Scegliere -ANalisi o -APprendimento o -RIassunto\n");
        
        System.exit(returnValue);
    }

    private static int startAnalisi(String file, GestoreParole gestoreParole) {
        Analizzatore a = new Analizzatore(mappaParoleFile, mappaVerbiFile, file, gestoreParole);
        
        return a.calcolaVerbi();
    }

    private static int startApprendimento(String file, GestoreParole gestoreParole, Consolle consolle) {
        Alunno a = new Alunno(mappaParoleFile, file, gestoreParole, consolle);
        
        return a.apprendi();
    }

    private static int startElaborazione(GestoreParole gestoreParole, Consolle consolle) {
        Bidello b = new Bidello(mappaParoleFile, mappaVerbiFile, gestoreParole, consolle);

        return b.rassettaVerbi();
    }
}
