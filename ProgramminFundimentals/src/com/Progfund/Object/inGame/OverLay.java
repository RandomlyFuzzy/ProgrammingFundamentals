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
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * this is for the bars above objects
 * @author Liam Woolley 1748910
 */
public class OverLay extends IDrawable {

    /**
     * with of the bar
     */
    private int w = 70;
    /**
     * height of the bar
     */
    private int h = 25;

    /**
     * stops things from collding with it
     */
    @Override
    public void init() {
        setIsCollidable(false);
    }
    @Override
    public void doMove() { }

    /**
     * this draws all the bars relativly to the object
     * its similar to the HUD object but references are auto maticaly added to the manager and read from here
     * @param gd graphical context
     */
    @Override
    public void Update(Graphics2D gd) {
        for (IDestroyable obj : IDestroyableManager.getInstance()) {
            if (obj.getHealth() != obj.getMaxHealth()&&obj.getHealth()>0) {
                int x = (int) (obj.getPosition().getX() - (w / 2));
                int y = (int) (obj.getPosition().getY() - (h / 2)) - (int)new Vector(obj.getScaledSpriteWidth(),obj.getScaledSpriteHeight()).Lengthsqrt();;
                gd.setColor(Color.GRAY);
                gd.fillRect(x - 2, y - 2, w + 4, h + 4);
                gd.setColor(Color.red);
                gd.fillRect(x, y, (int) (w * ((float) obj.getHealth() / (float) obj.getMaxHealth())), h);
            }
        }
    }
    @Override
    public void onCollison(IDrawable id) {
    }

}
