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
 * this is pickup that the player can interact with to get points from 
 * 
 * @author Liam Woolley 1748910
 */
public class PickUp extends IDestroyable {

    /**
     *
     * @param Score
     */
    public PickUp( int Score) {
        super(1);
        setScoreToAdd(Score);
    }

    /**
     * sets the defualt image and scale
     */
    @Override
    public void init() {
        GetSprite("/images/pickup.png");
        setScale(new Vector(0.65f,0.65f));
    }

    /**
     *
     */
    @Override
    public void doMove() {
        //keeps it "alive" 
        if(this.getHealth() != this.getMaxHealth()){
            this.setHealth(this.getMaxHealth());
        }
        //destroys once game is over
        if (LevelOverOverlay.isFinished()) {
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
        }
        //destroys if out of the screen bounds * 2
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
     * @param id collision managing function
     */
    @Override
    public void onCollison(IDrawable id) {
        //if not a player return
        if (!(id instanceof Player)) {
            return;
        }
        //adds to player score, generates partical and removes from screen
        Player.addScore(getScore());
        ParticalGenerator.add(this);
        Level().RemoveObject(this);
        IDestroyableManager.remove(this);
        Level().play("/music/Pickup_Coin.wav");
    }

}
