/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

/**
 *
 * @author gorig
 */
public enum TipoParola {
    SOSTANTIVO("S"),
    VERBO("V"),
    AGGETTIVO("AG"),
    AVVERBIO("AV"),
    PRONOME("PRO"),
    CONGIUNZIONE("C"),
    PREPOSIZIONE("PRE"),
    INTERIEZIONE("I"),
    ARTICOLO("AR");

    private final String sigla;
    
    private TipoParola(String sigla){
        this.sigla = sigla;
    }
    
    public String sigla(){
        return this.sigla;
    }
    
    public static boolean accetta(String sigla) {
        try{
            for(TipoParola tp: TipoParola.values())
                if (tp.sigla().equalsIgnoreCase(sigla))
                    return true;
            return false;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
    
    public static TipoParola valoreDiSigla(String input) {
        for (TipoParola tp: TipoParola.values())
            if (tp.sigla().equalsIgnoreCase(input))
                    return tp;
        
        throw new IllegalArgumentException();
    }
}
