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
     * this just adds 2 hashes together 
     * @param a vector a often the position
     * @return the hash of the X component with the Y component added together
     */
    public static int hash(Vector a){

        return Double.hashCode(a.getX())+Double.hashCode(a.getY());
    }
    
    
    
    
    
}
