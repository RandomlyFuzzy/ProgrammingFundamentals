/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.Menu;

import com.Liamengine.Engine.Components.Transform;
import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Components.Vector;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * this is the mouse I considered using the OS mouse and checking bounds but 
 * I had this already set up and needed less world to do this than that
 * @author Liam Woolley 1748910
 */
public class Mouse extends IDrawable {

    /**
     * local variable to limit the amount of times it can do a button action per click to 1
     */
    private boolean clicked = false;


    /**
     *
     */
    @Override
    public void init() {
        //sets on the screen always relative to the screen but still can interact with the world if moved
        setPosition(new Vector(Level().getMousePos()).mult(new Vector(1f / Game.WorldScale().getX(), 1f / Game.WorldScale().getX())).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
        //sets default images
        GetSprite("/Images/Cursor.png");
    }

    /**
     *
     */
    @Override
    public void doMove() {
       
        //sets on the screen always relative to the screen but still can interact with the world if moved
        setPosition(new Vector(Level().getMousePos()).mult(new Vector(1f / Game.WorldScale().getX(), 1f / Game.WorldScale().getX())).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
        //locking toggle so it limits it to 1 press per toggle
        clicked = clicked != !Level().isClicking() && clicked;
    }

    /**
     *
     * @param g
     */
    @Override
    public void Update(Graphics2D g) {
        //draws the cursor
        DrawLastLoadedImage(g);
    }

    /**
     *
     * @param im
     */
    @Override
    public void onCollison(IDrawable im) {
        if (im == null) {
            return;
        }
        //interacts with UI
        if (im instanceof Button && Level().isClicking() && !clicked) {
            clicked = true;
            ((Button) im).DoAction();
        }
        if (im instanceof toggle && Level().isClicking() && !clicked) {
            clicked = true;
            ((toggle) im).DoAction();
        }
    }

}
