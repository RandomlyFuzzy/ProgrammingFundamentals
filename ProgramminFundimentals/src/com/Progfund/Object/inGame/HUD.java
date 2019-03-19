/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Vector;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * generic hud for drawing text to the screen at 
 * certain points and editing the location or the text value
 * 
 * @author Liam Woolley 1748910
 */
public class HUD extends IDrawable {

    /**
     * collection of all the texts
     */
    private static ArrayList<String> texts = new ArrayList<String>();
    /**
     * collection of all the texts draw location
     */
    private static ArrayList<Vector> textsPos = new ArrayList<Vector>();


    /**
     *
     * @param text the inital text value
     * @param position the inital text location
     * @return the reference of the text to edit later
     */
    public static int AddText(String text, Vector position) {
        texts.add(text);
        textsPos.add(position);
        return texts.size() - 1;
    }

    /**
     *
     * @param ind the reference location
     * @param Text the text tp change it to
     */
    public static void EditText(int ind, String Text) {
        if (texts.size() <= ind || ind < 0) {
            return;
        }
        texts.set(ind, Text);
    }

    /**
     *
     * @param ind the reference location
     * @param pos the text position to be changed to
     */
    public static void EditText(int ind, Vector pos) {
        textsPos.set(ind, pos);
    }

    /**
     *
     */
    @Override
    public void init() {
        setIsCollidable(false);
        UseTransforms(false);
    }

    /**
     *
     */
    @Override
    public void doMove() {}

    /**
     *
     * @param g
     */
    @Override
    public void Update(Graphics2D g) {
        //draws all the strings at the points specified
        for (int i = 0; i < texts.size(); i++) {
            g.drawString(this.texts.get(i), this.textsPos.get(i).getX(),this.textsPos.get(i).getY());
        }
        
        
    }

    /**
     *
     * @param im
     */
    @Override
    public void onCollison(IDrawable im) { }

    /**
     *reset the object
     */
    @Override
    public void dispose() {
        super.dispose();
        texts = new ArrayList<String>();
        textsPos = new ArrayList<Vector>();
    }
}
