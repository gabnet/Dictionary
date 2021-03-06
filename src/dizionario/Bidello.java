/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.MappaVerbi;
import dizionario.modelli.TipoParola;
import dizionario.modelli.Verbo;
import dizionario.modelli.parole.Parola;
import dizionario.utilita.Consolle;
import dizionario.utilita.GestoreParole;
import dizionario.utilita.Stampante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 *
 * @author gorig
 */
public class Bidello {

    private final String mappaParoleFile;
    private final String mappaVerbiFile;
    private final GestoreParole gestoreParole;
    private final Consolle consolle;
    
    private String inputInfinito;
    private String inputRadice;
   
    public Bidello(String mappaParoleFile, String mappaVerbiFile, GestoreParole gestoreParole, Consolle consolle){
        this.mappaParoleFile = mappaParoleFile;
        this.mappaVerbiFile = mappaVerbiFile;
        this.gestoreParole = gestoreParole;
        this.consolle = consolle;
    }
    
    public int rassettaVerbi() {
        
        try {
            MappaParoleMultiple mappaParole = gestoreParole.caricaMappaParoleMultiple(mappaParoleFile);

            ArrayList<String> verbi = filtraVerbi(mappaParole);
            
            MappaVerbi mappaVerbi = gestoreParole.caricaMappaVerbi(mappaVerbiFile);
            
            mappaVerbi = imparaVerbi(verbi, mappaVerbi);
            
            gestoreParole.salvaMappaVerbi(mappaVerbi, mappaVerbiFile);

        } catch (Exception ex) {
            System.err.printf("Errore! %s", ex.getMessage());
            return -1;
        }
        
        return 0;
    }

    private ArrayList<String> filtraVerbi(MappaParoleMultiple mappaParole) {
        return mappaParole.keySet().stream().filter(k -> ((HashSet<Parola>)mappaParole.get(k)).contains(TipoParola.VERBO)).collect(Collectors.toCollection(ArrayList::new));
    }

    private MappaVerbi imparaVerbi(ArrayList<String> verbi, MappaVerbi mappaVerbi) throws IOException {
        inputInfinito = "";
        inputRadice = "";
        
        for(int indice = 0; indice < verbi.size() && !consolle.esci(inputInfinito) && !consolle.esci(inputRadice); indice++){
            
            do {
                Stampante.stampaInputInfinito(verbi.get(indice), indice + 1, verbi.size());
            
                inputInfinito = consolle.prendi();

                if (!consolle.successivo(inputInfinito)) {
                    Stampante.stampaInputRadice(verbi.get(indice), indice + 1, verbi.size());

                    inputRadice = consolle.prendi();
                }
            } while (!consolle.inputOk(inputInfinito) && !consolle.inputOk(inputRadice));
            
            if (!consolle.successivo(inputInfinito) && !consolle.salta(inputInfinito) && !consolle.successivo(inputRadice) && !consolle.salta(inputRadice))
                mappaVerbi.put(inputInfinito, new Verbo(inputInfinito, inputRadice));
        }
        
        return mappaVerbi;
    }
}