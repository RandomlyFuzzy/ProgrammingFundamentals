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
 * this is for the objects that are around the world like cars and bins 
 * these will not move so all that need to chenge is health and the image
 * (can be done externaly to this object)
 * 
 * @author Liam Woolley 1748910
 */
public class StaticObject extends IDestroyable {


    /**
     * hooks into the basic constructor
     * can be used for object that block the player
     * @param StartingHealth
     */
    public StaticObject(int StartingHealth) {
        super(StartingHealth);
    }

    /**
     * this is a object that give the player some points
     * @param StartingHealth
     * @param points
     */
    public StaticObject(int StartingHealth,int points) {
        super(StartingHealth);
        setScoreToAdd(points);
    }

    @Override
    public void init() {}

    /**
     * same thing in all the other object that are spawned in the level (bounds checking and self culling)
     */
    @Override
    public void doMove() {
        if (LevelOverOverlay.isFinished()) {
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
            return;
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

    }

}
