/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

/**
 *
 * @author gorig
 */
public class Dictionary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int returnValue = -1;
        
        GestoreFile gestoreFile = new GestoreFile();
        GestoreParole gestoreParole = new GestoreParole(gestoreFile);
        Consolle consolle = new Consolle();
        
        if (args.length == 0)
            System.out.printf("Scegliere -ANalisi o -APprendimento\n");
        
        if ("-AN".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
            returnValue = startAnalisi(args[1]);
        else if ("-AP".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
                returnValue = startApprendimento(args[1], gestoreFile, gestoreParole, consolle);
        else
            System.out.printf("Scegliere -ANalisi o -APprendimento\n");
        
        System.exit(returnValue);
    }

    private static int startAnalisi(String file) {
        Analizzatore a = new Analizzatore(file);
        
        return a.analizza();
    }

    private static int startApprendimento(String file, GestoreFile gestoreFile, GestoreParole gestoreParole, Consolle consolle) {
        Alunno a = new Alunno(file, gestoreFile, gestoreParole, consolle);
        
        return a.apprendi();
    }
}
