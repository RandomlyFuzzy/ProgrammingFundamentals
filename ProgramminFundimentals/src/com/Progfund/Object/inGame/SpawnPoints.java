/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.Components.Vector;
import java.io.Serializable;

/**
 *
 * @author RandomlyFuzzy
 */
public class SpawnPoints implements Serializable{
    
    private Vector pos = new Vector(0,0);

    public SpawnPoints(Vector pos){
        this.pos = pos;
    }

    public String toString(){
        return "pos:{"+pos.toString()+"}";
    }
    
}
