/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.HashUtils;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public abstract class IDestroyable extends IDrawable {

    private int Health = 0;
    private int MaxHealth = 40;
    private int refHash = -1;
    private int scoreToAdd = 20;

    private Vector[] refs = new Vector[2];

    IDestroyable() {
        super();
        refHash = -1;
        IDestroyableManager.Add(this);
    }

    /**
     *
     * @param MaxHealth
     */
    public IDestroyable(int MaxHealth) {
        this();
        setMaxHealth(MaxHealth);
    }

    /**
     *
     * @return
     */
    public Vector[] getHashVecs() {
        return refs;
    }

    /**
     *
     * @return
     */
    public int getRefHash() {
        return refHash;
    }

    /**
     *
     * @param val
     */
    public void setHealth(int val) {
        Health = val;
    }

    /**
     *
     * @return
     */
    public int getHealth() {
        return Health;
    }

    /**
     *
     * @param damage
     */
    public void Damage(int damage) {
        this.Health -= damage;
    }

    /**
     *
     * @return
     */
    public int getMaxHealth() {
        return MaxHealth;
    }

    /**
     *
     * @param MaxHealth
     */
    public void setMaxHealth(int MaxHealth) {
        Health = MaxHealth;
        this.MaxHealth = MaxHealth;
    }

    /**
     *
     * @param scoreToAdd
     */
    public void setScoreToAdd(int scoreToAdd) {
        this.scoreToAdd = scoreToAdd;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return scoreToAdd;
    }

    /**
     *
     */
    public void SetHashParams() {
        refs[0] = getPosition();
        refs[1] = new Vector(getMaxHealth());
        refHash = HashUtils.hash(getHashVecs()[0], getHashVecs()[1]);
    }

    /**
     *
     */
    public abstract void init();

    /**
     *
     */
    public abstract void doMove();

    /**
     *
     * @param gd
     */
    public abstract void Update(Graphics2D gd);

    /**
     *
     * @param id
     */
    public abstract void onCollison(IDrawable id);

}
