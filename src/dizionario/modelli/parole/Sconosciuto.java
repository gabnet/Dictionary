/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli.parole;

import dizionario.modelli.TipoParola;
import dizionario.modelli.TipoParolaSigla;

/**
 *
 * @author gorig
 */
public class Sconosciuto extends Parola {
    
    public Sconosciuto() {
        super(TipoParola.SCONOSCIUTO, TipoParolaSigla.SC);
    }
    
}
