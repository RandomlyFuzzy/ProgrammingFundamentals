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
public class RandomObject extends IDestroyable {

    private double current = 0;
    private double Delay = 0.8;

    public RandomObject(int StartingHealth) {
        super(StartingHealth);
    }

    @Override
    public void init() {
    }

    @Override
    public void doMove() {
        if (LevelOverOverlay.isFinished()) {
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
        }

        if (getHealth() <= 0) {
            IDestroyableManager.remove(this);
            Player.addScore(getScore());
            Level().RemoveObject(this);
            ParticalGenerator.add(this);
            return;
        }
        if (!((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > getPosition().getY()))) {
            IDestroyableManager.remove(this);
            Level().RemoveObject(this);
            return;
        }
    }

    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    @Override
    public void onCollison(IDrawable id) {

    }

}
