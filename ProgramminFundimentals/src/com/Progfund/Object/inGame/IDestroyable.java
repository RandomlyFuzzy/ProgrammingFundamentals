package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.HashUtils;
import java.awt.Graphics2D;

/**
 * this is used on everything that bullet can damage 
 * it also has a score to add to the player inside it 
 * 
 * @author Liam Woolley 1748910
 */
public abstract class IDestroyable extends IDrawable {


    /**
     * current health value of the object
     */
    private int Health = 0;
    /**
     * max health that was set
     */
    private int MaxHealth = 40;

    /**
     * hash of the object
     * @see #HashUtils
     * @see #LevelGenerator
     */
    private int refHash = -1;
    /**
     * score that should be added to the player
     */
    private int scoreToAdd = 20;

    /**
     * what the hashing should use to create the hash
     */
    private Vector[] refs = new Vector[2];

    /**
     * default contructor
     */
    IDestroyable() {
        super();
        refHash = -1;
        IDestroyableManager.Add(this);
    }

    /**
     * 
     * @param MaxHealth the max health of the object and current health of the objec
     */
    public IDestroyable(int MaxHealth) {
        this();
        setMaxHealth(MaxHealth);
    }

    /**
     *
     * @return the hash vectors
     */
    public Vector[] getHashVecs() {
        return refs;
    }

    /**
     *
     * @return the actual hash value
     */
    public int getRefHash() {
        return refHash;
    }

    /**
     *
     * @param val the health is set to this value
     */
    public void setHealth(int val) {
        Health = val;
    }

    /**
     *
     * @return the current health
     */
    public int getHealth() {
        return Health;
    }

    /**
     *
     * @param damage the amount to subtract from the object
     */
    public void Damage(int damage) {
        this.Health -= damage;
    }

    /**
     *
     * @return the objects maxhealth
     */
    public int getMaxHealth() {
        return MaxHealth;
    }

    /**
     *
     * @param MaxHealth the maxhealth should be set t
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
     * @return the score that should be added to the 
     */
    public int getScore() {
        return scoreToAdd;
    }

    /**
     * sets te hash params 
     */
    public void SetHashParams() {
        refs[0] = getPosition();
        refs[1] = new Vector(getMaxHealth());
        refHash = HashUtils.hash(getHashVecs()[0], getHashVecs()[1]);
    }

    /**
     * kept abstract because this is just an extention not a usable class
     */
    public abstract void init();

    /**
     * kept abstract because this is just an extention not a usable class
     */
    public abstract void doMove();

    /**
     * kept abstract because this is just an extention not a usable class
     * @param gd
     */
    public abstract void Update(Graphics2D gd);

    /**
     * kept abstract because this is just an extention not a usable class
     * @param id IDrawable
     */
    public abstract void onCollison(IDrawable id);

}
