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
public class Facile implements IAnello{

    @Override
    public String metodo() {
        return "Facile";
    }

    @Override
    public Verbo proponi(String input, MappaVerbi mappaVerbi) {

        Verbo presente = null;
        
        if (mappaVerbi.contiene(input))
            presente = mappaVerbi.verbo(input);
            
        return presente;
    }
}
