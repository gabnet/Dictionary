/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import dizionario.modelli.parole.GuardianoDelleParole;
import java.util.HashMap;

/**
 *
 * @author gorig
 */
public class MappaParoleMultiple extends HashMap<String, ParolaSet> {
    
    public void put(String chiave, String sigla) {
        
        chiave = chiave.toLowerCase();
        sigla = sigla.toLowerCase();
        
        ParolaSet presente = get(chiave);
        
        if (presente == null)
            presente = new ParolaSet(sigla);
        else
            presente.add(GuardianoDelleParole.parolaPerSigla(sigla));
        
        put(chiave, presente);
    }
    
    public boolean contiene(String key) {
        key = key.toLowerCase();
        
        return containsKey(key);
    }
}
