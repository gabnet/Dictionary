/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.utilita.verbi;

import dizionario.modelli.MappaVerbi;
import dizionario.modelli.Verbo;
import dizionario.utilita.dizionario.ConnettoreDizionarioOnline;

/**
 *
 * @author gorig
 */
public class Dizionario implements IAnello {

    @Override
    public String metodo() {
        return "Dizionario";
    }

    @Override
    public Verbo proponi(String input, MappaVerbi mappaVerbi) {
        String infinito = ConnettoreDizionarioOnline.prendiInfinito(input);
        
        return mappaVerbi.get(infinito);
    }
}
