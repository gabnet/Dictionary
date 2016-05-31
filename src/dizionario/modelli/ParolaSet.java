/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import dizionario.modelli.parole.GuardianoDelleParole;
import dizionario.modelli.parole.Parola;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author gorig
 */
public class ParolaSet extends HashSet<Parola> {
    
    public ParolaSet(String valore){
        if (valore.contains(","))
            Arrays.stream(valore.split(",")).forEach(v -> aggiungiAllInsieme(v) );
        else
            aggiungiAllInsieme(valore);
    }
    
    @Override
    public String toString(){
        return this.stream().map(tp -> tp.sigla().toString()).reduce("", (output, tp) -> output = String.format("%s, %s", output, tp)).replaceFirst(", ", "");
    }
    
    private void aggiungiAllInsieme(String sigla) {
        sigla = sigla.trim();
        
        if (GuardianoDelleParole.accetta(sigla))
            this.add(GuardianoDelleParole.parolaPerSigla(sigla));
    }
}
