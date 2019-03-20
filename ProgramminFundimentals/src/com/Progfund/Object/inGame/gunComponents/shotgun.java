
package com.Progfund.Object.inGame.gunComponents;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.Object.inGame.Bullet;

/**
 *
 * @see #Gun
 * @author Liam Woolley
 */
public class shotgun extends Gun{

    /**
     *   the default damage
     */
    private int Damage = 1;
    /**
     * constuctor
     * main sets damage and inital values of when this is made
     */
    public shotgun(IDrawable parent, float delay,int damage) {
        super(parent, delay);
        this.Damage = damage;
    }

    /**
     * what creates the bullets to be fired 
     * @see #Gun
     */
    @Override
    public void WhatToFire(ILevel Level,Vector pos, double rotation) {
        Level.AddObject(new Bullet(new Vector(pos), rotation-Math.PI/10, this.Damage));
        Level.AddObject(new Bullet(new Vector(pos), rotation, this.Damage));
        Level.AddObject(new Bullet(new Vector(pos), rotation+Math.PI/10, this.Damage));
    }
    
}
