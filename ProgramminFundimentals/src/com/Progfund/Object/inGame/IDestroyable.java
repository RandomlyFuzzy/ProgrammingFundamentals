/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public abstract class IDestroyable extends IDrawable {

    private int Health = 0;
    private int MaxHealth = 40;
    private int refHash = -1;
    private static int Allrefs = 0;

    public int getHealth() {
        return Health;
    }

    public void Damage(int damage) {
        this.Health -= damage;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public void setMaxHealth(int MaxHealth) {
        Health = MaxHealth;
        this.MaxHealth = MaxHealth;
    }

    public IDestroyable() {
        super();
        refHash = -1;
        OverLay.AddObject(this);
    }

//    public IDestroyable(int hash) {
//        super();
//        refHash = hash;
//        setMaxHealth(MaxHealth);
//        OverLay.AddObject(this);
//    }

    public IDestroyable(int MaxHealth, int hash) {
        super();
        refHash = hash;
        setMaxHealth(MaxHealth);
        OverLay.AddObject(this);
    }

    public abstract void init();

    public abstract void doMove();

    public abstract void Update(Graphics2D gd);

    public abstract void onCollison(IDrawable id);

}
