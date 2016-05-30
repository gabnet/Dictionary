/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dizionario.modelli;

import java.util.HashSet;

/**
 *
 * @author gorig
 */
public class VerboSet extends HashSet<String> {
    
    @Override
    public String toString(){
        return this.stream().reduce("", (output, vs) -> output = String.format("%s, %s", output, vs)).replaceFirst(", ", "");
    }
}
