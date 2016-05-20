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
        
        GestoreFile gestore = new GestoreFile();
        
        if (args.length == 0)
            System.out.printf("Scegliere -anALISI o -apPRENDIMENTO\n");
        
        if ("-an".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
            returnValue = startAnalisi(args[1]);
        else if ("-ap".equalsIgnoreCase(args[0]) && !args[1].isEmpty())
                returnValue = startApprendimento(args[1], gestore);
        else
            System.out.printf("Scegliere -anALISI o -apPRENDIMENTO\n");
        
        System.exit(returnValue);
    }

    private static int startAnalisi(String file) {
        Analizzatore a = new Analizzatore(file);
        
        return a.analizza();
    }

    private static int startApprendimento(String file, GestoreFile gestore) {
        Alunno a = new Alunno(file, gestore);
        
        return a.apprendi();
    }
}
