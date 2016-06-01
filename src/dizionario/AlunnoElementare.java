/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.utilita.Consolle;
import dizionario.utilita.GestoreParole;
import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.parole.GuardianoDelleParole;
import dizionario.utilita.Stampante;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author gorig
 */
public class AlunnoElementare {

    private final String inputFile;
    private final String mappaParoleFile;
    
    private final GestoreParole gestoreParole;
    private final Consolle consolle;
    
    public AlunnoElementare(String mappaParoleFile, String inputFile, GestoreParole gestoreParole, Consolle consolle) {
        this.mappaParoleFile = mappaParoleFile;
        this.inputFile = inputFile;
        this.gestoreParole = gestoreParole;
        this.consolle = consolle;
    }

    public int apprendi() {

        try {        
            ArrayList<String> elencoParole = gestoreParole.leggiParole(inputFile);

            MappaParoleMultiple mappaParole = gestoreParole.caricaMappaParoleMultiple(mappaParoleFile);
            
            mappaParole = aggiornaMappaParole(elencoParole, mappaParole);

            gestoreParole.salvaMappaParoleMultiple(mappaParole, mappaParoleFile);
            
        } catch (Exception ex) {
            System.err.printf("Errore! %s", ex.getMessage());
            return -1;
        }
        
        return 0;
    }

    private MappaParoleMultiple aggiornaMappaParole(ArrayList<String> elencoParole, MappaParoleMultiple mappaParole) throws IOException {
        String input = "";
        String parola = "";
        String frase = "";

        for(int indice = 0; indice < elencoParole.size() && !consolle.esci(input); indice++){
            
            parola = elencoParole.get(indice);

                do {
                    frase = Stampante.tieniFrase(frase, parola, indice);
                    Stampante.stampaInput(frase, parola, indice, elencoParole.size());
                    
                    if (!Stampante.nota(parola, mappaParole))
                        input = consolle.prendi();
                    else input = consolle.tastoSuccessivo();
                } while (!consolle.tipoParolaInputOk(input));

                if (!consolle.successivo(input) && !consolle.salta(input))
                    mappaParole.put(parola, input);

                if (consolle.salta(input))
                    indice = Stampante.raccogliIndice(input, indice, elencoParole.size(), consolle);
        }
        
        return mappaParole;
    }
}
