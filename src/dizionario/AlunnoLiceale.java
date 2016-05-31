/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.utilita.GestoreParole;
import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.MappaVerbi;
import dizionario.modelli.Verbo;
import dizionario.utilita.Consolle;
import dizionario.utilita.verbi.Facile;
import dizionario.utilita.verbi.IAnello;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author gorig
 */
public class AlunnoLiceale {
    
    private final String mappaParoleFile;
    private final String mappaVerbiFile;
    private final String inputFile;
    private final GestoreParole gestoreParole;
    private final Consolle consolle;
    
    private ArrayList<IAnello> anelli = new ArrayList<IAnello>(){
        {
            new Facile();
        }
    };
    
    public AlunnoLiceale(String mappaParoleFile, String mappaVerbiFile, String inputFile, GestoreParole gestoreParole, Consolle consolle){
        this.mappaParoleFile = mappaParoleFile;
        this.mappaVerbiFile = mappaVerbiFile;
        this.inputFile = inputFile;
        this.gestoreParole = gestoreParole;
        this.consolle = consolle;
    }

    public int calcolaVerbi() {

        try {        
            ArrayList<String> elencoParole = gestoreParole.leggiParole(inputFile);
            
            MappaParoleMultiple mappaParole = gestoreParole.caricaMappaParoleMultiple(mappaParoleFile);
            
            MappaVerbi mappaVerbi = gestoreParole.caricaMappaVerbi(mappaVerbiFile);
            
            elencoParole = filtraParoleNote(elencoParole, mappaParole);
            
            trovaVerbi(elencoParole, mappaParole, mappaVerbi);
            
            gestoreParole.salvaMappaParoleMultiple(mappaParole, mappaParoleFile);
            
            gestoreParole.salvaMappaVerbi(mappaVerbi, mappaVerbiFile);
        } catch (Exception ex) {
            System.err.printf("Errore! %s", ex.getMessage());
            return -1;
        }
        
        return 0;
    }

    private ArrayList<String> filtraParoleNote(ArrayList<String> elencoParole, MappaParoleMultiple mappaParole) {
        return elencoParole.stream().filter(p -> mappaParole.contiene(p)).collect(Collectors.toCollection(ArrayList::new));
    }

    private void trovaVerbi(ArrayList<String> elencoParole, MappaParoleMultiple mappaParole, MappaVerbi mappaVerbi) throws IOException {
        String parola = "";
        String input = "";
        
        for(int indice = 0; indice < elencoParole.size() && !consolle.esci(input); indice++){
            
            parola = elencoParole.get(indice);
            
            do {
                stampaInput(elencoParole.get(indice), indice + 1, elencoParole.size());
            
                input = consolle.prendi();

            } while (!consolle.inputOk(input) && !consolle.inputOkVerbi(input));
            
            if (!consolle.successivo(input) && !consolle.salta(input)) {
                mappaParole.put(parola, input);
                mappaVerbi = trovaVerbo(input, mappaVerbi);
            }
        }
    }

    private void stampaInput(String parola, int corrente, int numeroTotale) {
        System.out.printf("[%d/%d] %s -> ", corrente, numeroTotale, parola);
    }

    private MappaVerbi trovaVerbo(String coniugato, MappaVerbi mappaVerbi) throws IOException {
        
        Verbo proposto = null;
        
        for(IAnello anello : anelli){
            proposto = anello.proponi(coniugato, mappaVerbi);
            
            if (conferma(coniugato, proposto))
                break;
        }
        
        return aggiornaMappaVerbi(coniugato, proposto, mappaVerbi);
    }

    private MappaVerbi aggiornaMappaVerbi(String input, Verbo confermato, MappaVerbi mappaVerbi) {
            
        mappaVerbi.put(confermato.infinito(), confermato);
        
        return mappaVerbi;
    }

    private void stampaInputConferma(String coniugato, String infinito) {
        System.out.printf("%s --> %s", coniugato, infinito);
    }

    private boolean conferma(String coniugato, Verbo proposta) throws IOException {
        stampaInputConferma(coniugato, proposta.infinito());
            
        return consolle.confermato();
    }
}
