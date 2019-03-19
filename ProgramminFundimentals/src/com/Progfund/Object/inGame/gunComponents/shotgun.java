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
 * @author RandomlyFuzzy
 */
public class shotgun extends Gun{

    public int Damage = 1;
    
    public shotgun(IDrawable parent, float delay,int damage) {
        super(parent, delay);
        this.Damage = damage;
    }

    @Override
    void WhatToFire(ILevel Level,Vector pos, double rotation) {
        Level.AddObject(new Bullet(new Vector(pos), rotation, this.Damage));
        Level.AddObject(new Bullet(new Vector(pos), rotation, this.Damage));
    }
    
}
