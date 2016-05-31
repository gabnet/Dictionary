/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario;

import dizionario.utilita.Consolle;
import dizionario.utilita.GestoreParole;
import dizionario.modelli.TipoParola;
import dizionario.modelli.MappaParoleMultiple;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
                    frase = tieniFrase(frase, parola, indice);
                    stampaInput(frase, parola, indice, elencoParole.size());
                    
                    if (!nota(parola, mappaParole))
                        input = consolle.prendi();
                    else input = consolle.tastoSuccessivo();
                } while (!consolle.TipoParolaInputOk(input));

                if (!consolle.successivo(input) && !consolle.salta(input))
                    mappaParole.put(parola, input);

                if (consolle.salta(input))
                    indice = raccogliIndice(input, indice, elencoParole.size());
            
        }
        
        return mappaParole;
    }
    
    private void stampaInput(String frase, String parola, int corrente, int numeroTotale) {
        stampaManuale();

        System.out.printf("%s\n[%d/%d] %s -> ", frase, corrente, numeroTotale, parola);
    }

    private void stampaManuale() {
        System.out.printf("Specificare:\n");
        
        Arrays.stream(TipoParola.values()).forEach(tp -> System.out.printf("%s -> %s\n", tp, tp.sigla()));
    }

    private int raccogliIndice(String input, int corrente, int dimensioneMassima) {
        if (!consolle.salta(input))
            return corrente;
        
        int salto = consolle.prendiSalto(input);
        
        return salto < dimensioneMassima ? salto : dimensioneMassima;
    }

    private boolean nota(String parola, MappaParoleMultiple mappaParole) {
        return mappaParole.contiene(parola);
    }

    private String tieniFrase(String frase, String parola, int corrente) {
                
        frase = String.format("%s %s", frase, parola);
            
        if (corrente % 15 == 0)
            frase += "\n";
        
        return frase;
    }
}
