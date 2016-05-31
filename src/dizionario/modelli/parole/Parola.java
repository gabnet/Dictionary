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
public class Parola {

    private final TipoParola tipo;
    private final TipoParolaSigla sigla;
    
    public Parola(TipoParola tipo, TipoParolaSigla sigla) {
        this.tipo = tipo;
        this.sigla = sigla;
    }
    
    public TipoParolaSigla sigla() {
        return sigla;
    }
    
    public TipoParola tipoParola() {
        return tipo;
    }
}
