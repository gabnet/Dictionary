/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita;

import dizionario.modelli.MappaParoleMultiple;
import dizionario.modelli.Verbo;
import dizionario.modelli.parole.GuardianoDelleParole;
import java.io.IOException;

/**
 *
 * @author gorig
 */
public class Stampante {
    
    public static void stampaInputConferma(String coniugato, String infinito) {
        System.out.printf("propone: %s --> %s? [S/N] ", coniugato, infinito);
    }

    public static boolean conferma(String coniugato, Verbo proposta, Consolle consolle) throws IOException {
        stampaInputConferma(coniugato, proposta.infinito());
            
        return consolle.confermato();
    }

    public static void stampaInputConfermato(String coniugato, String infinito) {
        System.out.printf("\nConfermato %s --> %s\n", coniugato, infinito);
    }

    public static void stampaInputNonTrovato(String coniugato) {
        System.out.printf("\nNessun indizio per %s :/", coniugato);
    }

    public static void stampaMetodo(String metodo) {
        System.out.printf("\nIl metodo %s", metodo);
    }

    public static void stampaNessunaProposta() {
        System.out.printf(": nessuna proposta.");
    }
    
    public static void stampaManuale() {
        System.out.printf("\nSpecificare:\n");
        
        GuardianoDelleParole.parole().forEach(tp -> System.out.printf("%s -> %s\n", tp.tipoParola().toString(), tp.sigla()));
    }
    
    public static void stampaInput(String frase, String parola, int corrente, int numeroTotale) {
        stampaManuale();

        System.out.printf("%s\n[%d/%d] %s -> ", frase, corrente, numeroTotale, parola);
    }

    public static int raccogliIndice(String input, int corrente, int dimensioneMassima, Consolle consolle) {
        if (!consolle.salta(input))
            return corrente;
        
        int salto = consolle.prendiSalto(input);
        
        return salto < dimensioneMassima ? salto : dimensioneMassima;
    }

    public static boolean nota(String parola, MappaParoleMultiple mappaParole) {
        return mappaParole.contiene(parola);
    }

    public static String tieniFrase(String frase, String parola, int corrente) {
                
        frase = String.format("%s %s", frase, parola);
            
        if (corrente % 15 == 0)
            frase += "\n";
        
        return frase;
    }
    
    public static void stampaInputInfinito(String parola, int corrente, int numeroTotale) {
        System.out.printf("\n[%d/%d] %s --infinito-> ", corrente, numeroTotale, parola);
    }
    
    public static void stampaInputRadice(String parola, int corrente, int numeroTotale) {
        System.out.printf("\n[%d/%d] %s --radice-> ", corrente, numeroTotale, parola);
    }
    
    public static void stampaInput(String parola, int corrente, int numeroTotale) {
        System.out.printf("\n[%d/%d] %s -> ", corrente, numeroTotale, parola);
    }
}
