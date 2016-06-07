/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import dizionario.modelli.parole.GuardianoDelleParole;
import dizionario.modelli.parole.Parola;
import java.util.Arrays;

/**
 *
 * @author gorig
 */
public class Verbo extends Parola {

    private final String infinito;
    private final String radice;
    private final VerboSet verbiNoti;
    
    public Verbo(String infinito, String verbo){
        super(TipoParola.VERBO, GuardianoDelleParole.siglaPerTipoParola(TipoParola.VERBO));
        this.infinito = infinito;
        this.radice = radice(verbo);
        this.verbiNoti = verbo == null || "".equalsIgnoreCase(verbo) ? new VerboSet() : verbiNoti(verbo);
    }
    
    @Override
    public String toString() {
        return String.format("%s;%s", radice, verbiNoti.toString());
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
    
    public String radice(){
        return radice;
    }

    private String radice(String verbo) {
        return verbo.substring(0, verbo.indexOf(";"));
    }

    private VerboSet verbiNoti(String verbo) {
        VerboSet verboSet = new VerboSet();
        
        String verbiNotiPuliti = verbo.replaceFirst(radice(verbo) + ";", "");
        
        Arrays.asList(verbiNotiPuliti.split(",")).stream().forEach(v -> verboSet.add(v.trim()));
        
        return verboSet;
    }
}
