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
 *
 * @author Liam Woolley 1748910
 */
public class Mouse extends IDrawable {

    private boolean clicked = false;

    /**
     *
     */
    public Mouse() {
        super();
    }

    /**
     *
     */
    @Override
    public void init() {
        setPosition(new Vector(Level().getMousePos()).mult(new Vector(1f / Game.WorldScale().getX(), 1f / Game.WorldScale().getX())).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
        GetSprite("/Images/Cursor.png");
    }
    float ind = 0;

    /**
     *
     */
    @Override
    public void doMove() {
//        Rad += Game.g.getDelta() * 100;
        if (Level().isClicking()) {
            ind = 1f;
            ind = ind % 2;
        } else {
            ind = 0;
        }

//        setScale(new Vector(Game.ButtonDims()).mult(1.5f));
        setPosition(new Vector(Level().getMousePos()).mult(new Vector(1f / Game.WorldScale().getX(), 1f / Game.WorldScale().getX())).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
//        setRotation(Rad);
        clicked = clicked != !Level().isClicking() && clicked;
    }

    /**
     *
     * @param g
     */
    @Override
    public void Update(Graphics2D g) {
//        GetSprite("/Images/cursor_" + (int) ind + ".png");
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
