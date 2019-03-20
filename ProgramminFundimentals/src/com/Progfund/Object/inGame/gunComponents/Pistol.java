/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame.gunComponents;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.Object.inGame.Bullet;

/**
 *
 * this is the a pistol it fires a single accurate shot to wards the rotation provided
 * 
 * @see #Gun
 * @author Liam Woolley 1748910
 */
public class Pistol extends Gun{
    /**
     *   the default damage
     */  
    private int Damage = 1;
    
    /**
     * constuctor
     * main sets damage and inital values of when this is made
     */
    public Pistol(IDrawable parent, float delay,int damage) {
        super(parent, delay);
        this.Damage = damage;
    }

    /**
     * what creates the bullets to be fired 
     * @see #Gun
     */
    @Override
    public void WhatToFire(ILevel Level,Vector pos, double rotation) {
        Level.AddObject(new Bullet(new Vector(pos), rotation, this.Damage));
    }
    
}
