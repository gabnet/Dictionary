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
public class MappaVerbi extends HashMap<String, Verbo>{

    public boolean contiene(String verbo){
        return values().stream().anyMatch(v -> v.coniugatoIn(verbo));
    }
}
