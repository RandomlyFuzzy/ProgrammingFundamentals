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
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

/**
 *
 * this is similar to a button but changes its state each time it is clicked
 * 
 * @author Liam Woolley 1748910
 */
public class toggle extends IDrawable {

    /**
     * current state of the toggle
     */
    private boolean isTicked = false;
    /**
     * relative position on the sceen 0,0 is top left 1,1 is bottom right
     */
    private Vector relpos = Vector.One();
    /**
     * function wanted to be called when clicked on
     */
    private HUDdelegate buttonDelegate;


    /**
     *
     * @param relpos the position it will stay relative to the screen size
     * @param Logic what runs when it is clicked
     */
    public toggle(Vector relpos, HUDdelegate Logic) {
        super();
        this.buttonDelegate = Logic;
        this.relpos = relpos;
        GetSprite("/images/toggle" + (isTicked ? "On" : "Off") + ".png");
    }

    /**
     * set its 
     */
    @Override
    public void init() {
        //sets the overall scale of the object (world scale is multiplyed by it so only needs to be set once
        setScale(new Vector(Game.ButtonDims()));
    }

    /**
     * contantly adjusts it position incase of a screen size chage
     */
    @Override
    public void doMove() {
        setPosition(new Vector(((Game.getScaledWidth())) * relpos.getX(), ((Game.getScaledHeight())) * relpos.getY()).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
    }

    /**
     *
     * @param g
     */
    @Override
    public void Update(Graphics2D g) {
        //draws current state image
        DrawLastLoadedImage(g);
        //if hovering show a rect on top
        if (isColliding()) {
            Color c = g.getColor();
            g.setColor(new Color(200, 200, 200, 100));
            g.fillOval(-getSpriteWidth() / 2, -getSpriteHeight() / 2, getSpriteWidth(), getSpriteHeight());
            g.setColor(c);
        }
    }

    /**
     *
     */
    public void DoAction() {
        if (buttonDelegate != null) {
            //if clicked on run delegate
            buttonDelegate.OnClick(this);
            //invert state and show the inversion
            isTicked = !isTicked;
            GetSprite("/images/toggle" + (isTicked ? "On" : "Off") + ".png");
        } else {
            System.err.println("error no delegate in this button");
        }
    }

    /**
     *
     * @param im
     */
    @Override
    public void onCollison(IDrawable im) {
        //if not a mouse then dont do anything with that collision
        if (!(im instanceof Mouse)) {
            setIsColliding(false);
        }
    }

    /**
     *
     * @return its current state
     */
    public boolean IsTicked() {
        return isTicked;
    }

}
