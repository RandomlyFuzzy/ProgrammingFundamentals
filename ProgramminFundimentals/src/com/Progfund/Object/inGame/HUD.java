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
 *
 * @author Liam Woolley 1748910
 */
public class HUD extends IDrawable {

    private static ArrayList<String> texts = new ArrayList<String>();
    private static ArrayList<Vector> textsPos = new ArrayList<Vector>();

    /**
     *
     */
    public HUD() {
        super();
    }

    /**
     *
     * @param text
     * @param position
     * @return
     */
    public static int AddText(String text, Vector position) {
        texts.add(text);
        textsPos.add(position);
        return texts.size() - 1;
    }

    /**
     *
     * @param ind
     * @param Text
     */
    public static void EditText(int ind, String Text) {
        if (texts.size() <= ind || ind < 0) {
            return;
        }
        texts.set(ind, Text);
    }

    /**
     *
     * @param ind
     * @param pos
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
    public void doMove() {
    }

    /**
     *
     * @param g
     */
    @Override
    public void Update(Graphics2D g) {

//        updateTime =  System.nanoTime();
        for (int i = 0; i < texts.size(); i++) {
            g.drawString(this.texts.get(i), this.textsPos.get(i).getX(),this.textsPos.get(i).getY());
        }
        
        
    }

    /**
     *
     * @param im
     */
    @Override
    public void onCollison(IDrawable im) {

    }

    /**
     *
     */
    @Override
    public void dispose() {
        super.dispose();
        texts = new ArrayList<String>();
        textsPos = new ArrayList<Vector>();
    }
}
