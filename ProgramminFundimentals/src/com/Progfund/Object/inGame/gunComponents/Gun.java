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
 * this is just an abstract class for the structure of a gun 
 * 
 * @author Liam Woolley 1748910
 */
public abstract class Gun extends IComponent{

    /**
     * next firing delay
     */
    private float delay = 0.1f;
    /**
     * last time the gun was fired
     */
    private float lastshotTime = 0;
    
    /**
     * defualt contructor of a the component
     */
    public Gun(IDrawable parent,float delay){
        super(parent);
        this.delay = delay;
        lastshotTime = (float)getParent().Level().getTime();
    }
    
    /**
     * simple fire links to an abstract function that does the creation of bullets
     * this just manages the delay and last fired time
     */
    public void fire(){
        if ((lastshotTime + delay) <= getParent().Level().getTime()) {
            WhatToFire(getParent().Level(),getParent().getPosition(),getParent().getRotation());
            lastshotTime = (float)getParent().Level().getTime();
        }
    }
    
    /**
     * what does the firing with easy accessor to key variables
     * @param level the level to load to 
     * @param pos the position of the thing firing it
     * @param rotation the current rotation of the object
     */
    public abstract void WhatToFire(ILevel level,Vector pos,double rotation);
    
    @Override
    public void Init() {}

    @Override
    public void Update(Graphics2D gd) {}
    
}
