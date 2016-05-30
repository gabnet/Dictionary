/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.utilita.GestoreParole;
import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.MappaVerbi;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author gorig
 */
public class Analizzatore {
    
    private String mappaParoleFile;
    private String mappaVerbiFile;
    private String inputFile;
    private GestoreParole gestoreParole;
    
    public Analizzatore(String mappaParoleFile, String mappaVerbiFile, String inputFile, GestoreParole gestoreParole){
        this.mappaParoleFile = mappaParoleFile;
        this.mappaVerbiFile = mappaVerbiFile;
        this.inputFile = inputFile;
        this.gestoreParole = gestoreParole;
    }

    public int calcolaVerbi() {

        try {        
            ArrayList<String> elencoParole = gestoreParole.leggiParole(inputFile);
            
            MappaParoleMultiple mappaParole = gestoreParole.caricaMappaParoleMultiple(mappaParoleFile);
            
            MappaVerbi mappaVerbi = gestoreParole.caricaMappaVerbi(mappaVerbiFile);
            
            elencoParole = filtraParoleNote(elencoParole, mappaParole);
            
            mappaParole = inferisciVerbi(elencoParole, mappaVerbi);
            
            gestoreParole.salvaMappaParoleMultiple(mappaParole, mappaParoleFile);
        } catch (Exception ex) {
            return -1;
        }
        
        return 0;
    }

    private ArrayList<String> filtraParoleNote(ArrayList<String> elencoParole, MappaParoleMultiple mappaParole) {
        return elencoParole.stream().filter(p -> mappaParole.containsKey(p)).collect(Collectors.toCollection(ArrayList::new));
    }

    private MappaParoleMultiple inferisciVerbi(ArrayList<String> elencoParole, MappaVerbi mappaParole) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
