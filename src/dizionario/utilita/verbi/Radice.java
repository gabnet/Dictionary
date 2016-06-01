/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita.verbi;

import dizionario.modelli.MappaVerbi;
import dizionario.modelli.Verbo;

/**
 *
 * @author gorig
 */
public class Radice implements IAnello {

    @Override
    public String metodo() {
        return "Radice";
    }

    
    @Override
    public Verbo proponi(String input, MappaVerbi mappaVerbi) {
        
        Verbo presente = null;
            
        for(Verbo v : mappaVerbi.values())
            if (input.startsWith(v.radice()))
                presente = v;
                    
        return presente;
    }
}
