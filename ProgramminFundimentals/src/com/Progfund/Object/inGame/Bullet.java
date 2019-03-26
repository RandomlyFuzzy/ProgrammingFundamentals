/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Transform;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.MusicUtils;
import com.Progfund.Levels.Level;
import java.awt.Graphics2D;

/**
 * bullet it damages IDestroyables
 *
 * @author Liam Woolley 1748910
 */
public class Bullet extends IDrawable {

    /**
     * the amount added to the position each tim
     */
    private Vector Velocity;
    private int damage = 0;

    /**
     * @param start start position
     * @param rot start rotation
     * @param Damage bullet damage
     */
    public Bullet(Vector start, double rot, int Damage) {
        //sets the postition
        setPosition(start);
        //sets the rotation
        setRotation(rot);
        //offset so its slightly foward
        addPosition(new Vector((float) rot).mult(35));
        //scales the bullet to be more visable
        setScale(new Vector(3, 2));
        //set the position added increment
        this.Velocity = new Vector((float) rot).mult(600);
        //set the damage that is delt upon hit
        this.damage = Damage;
    }

    /**
     * loads the image for ti
     */
    @Override
    public void init() {
        GetSprite("/images/bullet.png");
        MusicUtils.StopASounds("/music/Laser_Shoot.wav");
        Level().play("/music/Laser_Shoot.wav");
    }

    /**
     * check the bounds if within move else destroys it and removes from ILevel
     * Collection
     */
    @Override
    public void doMove() {
        if (((-Transform.getOffsetTranslation().getX() - (Game.getWindowWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getWindowWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getWindowHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getWindowHeight() * 2)) > getPosition().getY()))) {
            addPosition(new Vector(Velocity).mult(Game.getDelta()));
        } else {
            Level().RemoveObject(this);
        }
    }

    /**
     * draws the bubllet
     *
     * @param gd
     */
    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    /**
     *
     * damages IDestroyables then deletes self
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
        if (!(id instanceof IDestroyable) || (id instanceof PickUp)) {
            return;
        }
        IDestroyable des = (IDestroyable) id;
        des.Damage(damage);
        Level().RemoveObject(this);
    }

}
