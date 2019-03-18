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
import com.Progfund.Object.Menu.LevelOverOverlay;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public class Enemy extends IDestroyable {

    private double current = 0;
    private double Delay = 0.8;

    /**
     *
     * @param i
     */
    public Enemy(int i) {
        super(i);
    }

    /**
     *
     */
    @Override
    public void init() {
        GetSprite("/images/enemy.png");
    }

    /**
     *
     */
    @Override
    public void doMove() {
        if (LevelOverOverlay.isFinished()) {
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
        }

        if (getHealth() <= 0) {
            ParticalGenerator.add(this);
            Player.addScore(getScore());
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
            return;
        }
        if (!((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > getPosition().getY()))) {
            IDestroyableManager.remove(this);
            Level().RemoveObject(this);
            return;
        } else {
            Vector relpos = new Vector(getPosition()).mult(1).add(new Vector(Transform.getOffsetTranslation()).mult(1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2).mult(-1)));
//
            setRotation(Math.atan2(relpos.getY(), relpos.getX()) - Math.PI / 2);
            addPosition(new Vector(GetUp()).mult(70).mult(Game.getDelta()));
        }
        if (((-Transform.getOffsetTranslation().getX()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth())) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY()) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight())) > getPosition().getY())) {

            if ((current + Delay) <= Level().getTime()) {
                Level().AddObject(new Bullet(new Vector(getPosition()), getRotation(), 14));
                current = Level().getTime();
            }
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
        System.out.println("com.Progfund.Object.inGame.Enemy.onCollison() " + id.getClass().getSimpleName());
    }

}
