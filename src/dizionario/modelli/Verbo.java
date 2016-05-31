/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import dizionario.modelli.parole.GuardianoDelleParole;
import dizionario.modelli.parole.Parola;

/**
 *
 * @author gorig
 */
public class Verbo extends Parola {

    private final String infinito;
    private final String radice;
    private final VerboSet verbiNoti;
    
    public Verbo(String infinito, String radice){
        super(TipoParola.VERBO, GuardianoDelleParole.siglaPerTipoParola(TipoParola.VERBO));
        this.infinito = infinito;
        this.radice = radice;
        this.verbiNoti = new VerboSet();
    }
    
    @Override
    public String toString() {
        return String.format("%s; %s", radice, verbiNoti.toString());
    }
    
    public boolean coniugatoIn(String verbo) {
        return verbiNoti.contains(verbo);
    }
    
    public String infinito(){
        return infinito;
    }

    public void aggiungiVerbo(String input) {
        verbiNoti.add(input);
    }
}
