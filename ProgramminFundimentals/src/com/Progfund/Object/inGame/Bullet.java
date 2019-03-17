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
import com.Progfund.Levels.Level;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public class Bullet extends IDrawable {

    private Vector Acc;
    private int damage = 0;

    /**
     *
     * @param start
     * @param rot
     * @param Damage
     */
    public Bullet(Vector start, double rot, int Damage) {
        setPosition(start);
        setRotation(rot);
        addPosition(new Vector((float) rot).mult(35));
        setScale(new Vector(3, 2));
        this.Acc = new Vector((float) rot).mult(300);
        this.damage = Damage;
    }

    /**
     *
     */
    @Override
    public void init() {
        GetSprite("/images/bullet.png");
    }

    /**
     *
     */
    @Override
    public void doMove() {
        if (((-Transform.getOffsetTranslation().getX() - (Game.getWindowWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getWindowWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getWindowHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getWindowHeight() * 2)) > getPosition().getY()))) {
            addPosition(new Vector(Acc).mult(Game.getDelta()));
        } else {
            Level().RemoveObject(this);
        }
    }

    /**
     *
     * @param gd
     */
    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    /**
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
        System.out.println("com.Progfund.Object.inGame.Bullet.onCollison() " + des.getHealth());
        System.out.println("com.Progfund.Object.inGame.Bullet.onCollison() " + id.getClass().getName().toString());
    }

}
