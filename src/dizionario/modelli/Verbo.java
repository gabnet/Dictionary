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
public class Verbo {

    private String radice;
    private VerboSet verbiNoti;
    
    @Override
    public String toString() {
        return String.format("%s; %s", radice, verbiNoti.toString());
    }
}
