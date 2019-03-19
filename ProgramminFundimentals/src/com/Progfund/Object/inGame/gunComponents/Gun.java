/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame.gunComponents;

import com.Liamengine.Engine.AbstractClasses.IComponent;
import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.Object.inGame.Bullet;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public abstract class Gun extends IComponent{

    private float delay = 0.1f;
    private float lastshotTime = 0;
    
    
    public Gun(IDrawable parent,float delay){
        super(parent);
        this.delay = delay;
        lastshotTime = (float)getParent().Level().getTime();
    }
    
    public void fire(){
        if ((lastshotTime + delay) <= getParent().Level().getTime()) {
            WhatToFire(getParent().Level(),getParent().getPosition(),getParent().getRotation());
            lastshotTime = (float)getParent().Level().getTime();
        }
    }
    
    
    abstract void WhatToFire(ILevel level,Vector pos,double rotation);
    
    @Override
    public void Init() {}

    @Override
    public void Update(Graphics2D gd) {}
    
}
