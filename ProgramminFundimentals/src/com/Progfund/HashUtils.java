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
 * @author Liam Woolley 1748910
 */
public class HashUtils {
    
    
    
    
    /*
    * this is just simle bitwise hashing function nothing fancy
    * just uses position and uses a direction vector using health 
    * to obtain fairly unique identifier for each object that can be 
    * checked to see if an object is similar upon spawn 
    * @param id 
    */

    /**
     *
     * @param a
     * @param b
     * @return
     */

    public static int hash(Vector a,Vector b){
        int a0 = (int)(a.getX()*10000f);
        int a1 = (int)(a.getY()*10000f);
        int a2 = (int)(b.getX()*10000f);
        int a3 = (int)(b.getY()*10000f);
        int a4 = a1|a3;
        int a5 = a0|a2;
        int a6 = ((((((a4^a5)&a0)&a1)&a2)&a3)&a4);
               

        int hash = a0^a1;
        return hash;
    }
    
    
    
    
    
}
