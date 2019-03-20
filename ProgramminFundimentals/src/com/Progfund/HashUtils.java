/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund;

import com.Liamengine.Engine.Components.Vector;
import com.Progfund.Object.inGame.IDestroyable;

/**
 *
 * this is used to generate unique hashs for each possable point on the screen
 * (it doesnt do every place as a different hash but is close enough to be usable by the game)
 * 
 * @author Liam Woolley 1748910
 */
public class HashUtils {
    
    
    
    
    /**
     * this is just simle bitwise hashing function nothing fancy
     * just uses position to obtain fairly unique identifier for 
     * each object that can be checked to see if an object is 
     * similar upon spawn 
     * @param a vector a oftern the position
     * @return a simi-unique hash
     */
    public static int hash(Vector a){
        //scaling to a certain point too much and it will just remove the firs bytes of the int and make it cycle more oftern
        int a0 = (int)(a.getX()*1000000f);
        int a1 = (int)(a.getY()*1000000f);
               
        int hash = a0^a1;
        return hash;
    }
    
    
    
    
    
}
