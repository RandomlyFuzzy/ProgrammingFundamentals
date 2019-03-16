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
import com.Progfund.Levels.Level1;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public class Bullet extends IDrawable {

    private Vector Acc;
    private int damage = 0;

    public Bullet(Vector start, double rot, int Damage) {
        setPosition(start);
        setRotation(rot);
        addPosition(new Vector((float) rot).mult(35));
        setScale(new Vector(3, 2));
        this.Acc = new Vector((float) rot).mult(300);
        this.damage = Damage;
    }

    @Override
    public void init() {
        GetSprite("/images/bullet.png");
    }

    @Override
    public void doMove() {
        if (((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > getPosition().getY()))) {
            addPosition(new Vector(Acc).mult(Game.getDelta()));
        } else {
            Level().RemoveObject(this);
        }
    }

    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    @Override
    public void onCollison(IDrawable id) {
        if (!(id instanceof IDestroyable)) {
            return;
        }

        IDestroyable des = (IDestroyable) id;
        des.Damage(damage);
        System.out.println("com.Progfund.Object.inGame.Bullet.onCollison() " + des.getHealth());
        Level().RemoveObject(this);

        System.out.println("com.Progfund.Object.inGame.Bullet.onCollison() " + id.getClass().getName().toString());
    }

}
