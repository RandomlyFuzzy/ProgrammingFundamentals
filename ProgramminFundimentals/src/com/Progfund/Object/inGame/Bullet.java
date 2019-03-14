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

    public Bullet(Vector start, double rot) {
        setPosition(start);
        addPosition(new Vector((float) rot).mult(25));
        this.Acc = new Vector((float) rot).mult(300);
        setRotation(rot);
    }

    @Override
    public void init() {
        GetSprite("/images/bullet.png");
    }

    @Override
    public void doMove() {
        addPosition(new Vector(Acc).mult(Game.getDelta()));
    }

    @Override
    public void Update(Graphics2D gd) {

        if (-Transform.getOffsetTranslation().getX() < getPosition().getX()
                && -Transform.getOffsetTranslation().getX() + Game.getScaledWidth() > getPosition().getX()
                && -Transform.getOffsetTranslation().getY() < getPosition().getY()
                && -Transform.getOffsetTranslation().getY() + Game.getScaledHeight() > getPosition().getY()) {
            DrawLastLoadedImage(gd);
        } else {
            Level().RemoveObject(this);
        }
    }

    @Override
    public void onCollison(IDrawable id) {
        System.out.println("com.Progfund.Object.inGame.Bullet.onCollison() " + id.getClass().getName().toString());
    }

}
