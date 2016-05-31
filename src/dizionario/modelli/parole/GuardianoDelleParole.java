/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli.parole;

import dizionario.modelli.TipoParola;
import dizionario.modelli.TipoParolaSigla;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author gorig
 */
public class GuardianoDelleParole {

    private static ArrayList<Parola> parole = new ArrayList<Parola>() {{

        add(new Parola(TipoParola.SOSTANTIVO, TipoParolaSigla.S));
        add(new Parola(TipoParola.VERBO, TipoParolaSigla.V));
        add(new Parola(TipoParola.AGGETTIVO, TipoParolaSigla.AG));
        add(new Parola(TipoParola.AVVERBIO, TipoParolaSigla.AV));
        add(new Parola(TipoParola.PRONOME, TipoParolaSigla.PRO));
        add(new Parola(TipoParola.CONGIUNZIONE, TipoParolaSigla.C));
        add(new Parola(TipoParola.INTERIEZIONE, TipoParolaSigla.I));
        add(new Parola(TipoParola.ARTICOLO, TipoParolaSigla.AR));
        add(new Parola(TipoParola.SCONOSCIUTO, TipoParolaSigla.SC));
        
    }};
            
    public static boolean accetta(String sigla) {
       try{
           siglaPerString(sigla);
       } catch (IllegalArgumentException | NullPointerException ex){
           return false;
       }
       
       return true;
    }
    
    public static Parola parolaPerSigla(String input) {
        if (accetta(input)){
            Optional<Parola> parolaOpzionale = parole.stream().filter(p -> p.sigla() == siglaPerString(input)).findFirst();
            if (parolaOpzionale.isPresent())
                return parolaOpzionale.get();
        }
        
        return new Sconosciuto();
        
    }
    
    public static TipoParolaSigla siglaPerTipoParola(TipoParola tipo){
        Optional<Parola> parolaOpzionale = parole.stream().filter((Parola p) -> p.tipoParola().equals(tipo)).findFirst();
        
        if (parolaOpzionale.isPresent())
            return parolaOpzionale.get().sigla();
        
        return TipoParolaSigla.SC;
    }
    
    public static ArrayList<Parola> parole(){
        return parole;
    }
    
    public static TipoParolaSigla siglaPerString(String sigla) {
        return TipoParolaSigla.valueOf(sigla);
    }

    public static boolean accettaVerbi(String input) {
        Parola parola = parolaPerSigla(input);
        
        return parola.sigla() == TipoParolaSigla.V;
    }
}
