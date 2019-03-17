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
 *
 * @author RandomlyFuzzy
 */
public class OverLay extends IDrawable {

    private int w = 70;
    private int h = 25;

    /**
     *
     */
    @Override
    public void init() {
        setIsCollidable(false);

    }

    /**
     *
     */
    @Override
    public void doMove() {

    }

    /**
     *
     * @param gd
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
//        gd.drawLine((int)(-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth())),(int)(-Transform.getOffsetTranslation().getY()- (Game.getScaledHeight())),(int)(-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth()*2)),(int)(-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight()*2)));
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
    }

    /**
     *
     */
    public void dispose() {
        super.dispose();

    }

}
