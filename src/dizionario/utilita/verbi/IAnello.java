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
public interface IAnello {
    
    public String metodo();
    public Verbo proponi(String input, MappaVerbi mappaVerbi);
}
