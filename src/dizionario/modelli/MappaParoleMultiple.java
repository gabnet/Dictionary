/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import java.util.HashMap;

/**
 *
 * @author gorig
 */
public class MappaParoleMultiple extends HashMap<String, TipoParolaSet> {
    
    public void put(String chiave, String sigla) {
        
        chiave = chiave.toLowerCase();
        sigla = sigla.toLowerCase();
        
        TipoParolaSet presente = get(chiave);
        
        if (presente == null)
            presente = new TipoParolaSet(sigla);
        else
            presente.add(TipoParola.valoreDiSigla(sigla));
        
        put(chiave, presente);
    }
    
    public boolean contiene(String key) {
        key = key.toLowerCase();
        
        return containsKey(key);
    }
}
