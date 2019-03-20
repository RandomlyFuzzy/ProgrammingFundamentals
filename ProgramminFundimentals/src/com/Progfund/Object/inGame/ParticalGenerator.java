 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * simple partical system but shows text instead of particals
 *
 * @author Liam Woolley 1748910
 */
public class ParticalGenerator extends IDrawable {

    /**
     * collection of all things that should be displayed
     */
    private static ArrayList<particalParams> particals = new ArrayList<particalParams>();

    /**
     *
     * @param id thing to be added to be displayed
     */
    public static void add(IDestroyable id) {
        particals.add(new particalParams("" + id.getScore(), id.getPosition(), 255));
    }

    @Override
    public void init() {
    }

    @Override
    public void doMove() {
    }

    /**
     *
     * @param gd the graphical context
     */
    @Override
    public void Update(Graphics2D gd) {
        //if partical amount is greater than 0
        if (particals.size() == 0) {
            return;
        }
        //get variable to revert
        Color c = gd.getColor();
        Font f = gd.getFont();
        gd.setFont(f.deriveFont(f.getSize() * 1.5f));
        // for each parical decending (because it deletes some)
        for (int i = particals.size() - 1; i >= 0; i--) {
            particalParams p = particals.get(i);
            //checks to see if it should be kept
            if (p.getCurrentValue() <= 0) {
                particals.remove(p);
                continue;
            }
            int w = gd.getFontMetrics().stringWidth(p.getValue());
            //this makes it fade
            gd.setColor(new Color(255, 215, 0, p.getCurrentValue()));
            //draw things that should be displayed            
            gd.drawString(p.getValue(), (int) p.getPos().getX() - (w / 2), (int) p.getPos().getY());
            //remove some amount from it
            p.addCurrentValue(-4);
            //move it slowly so it fa
            p.addPos(new Vector(0, -3f).mult(Game.getDelta()));

        }
        gd.setFont(f);
        gd.setColor(c);
    }

    @Override
    public void onCollison(IDrawable id) {
    }

    private static class particalParams {

        /**
         * thing to be shown
         */
        private String Value = "";
        /**
         * position shown at
         */
        private Vector pos = Vector.Zero();
        /**
         * value to minipulate
         */
        private int CurrentValue = 255;

        /**
         * @param val value to display
         * @param pos place it draws at
         * @param DisplayVal onboard variable for lifespan
         */
        public particalParams(String Val, Vector pos, int DisplayVal) {
            this.Value = Val;
            this.pos = pos;
            this.CurrentValue = DisplayVal;
        }

        //getters and setters
        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public Vector getPos() {
            return pos;
        }

        public void setPos(Vector pos) {
            this.pos = pos;
            pos = null;
        }

        public void addPos(Vector pos) {
            this.pos.add(pos);
            pos = null;
        }

        public int getCurrentValue() {
            return CurrentValue;
        }

        public void setCurrentValue(int CurrentValue) {
            this.CurrentValue = CurrentValue;
        }

        public void addCurrentValue(int CurrentValue) {
            this.CurrentValue += CurrentValue;
        }
    }

    /**
     * resets the static variable
     */
    public void dispose() {
        super.dispose();
        particals = new ArrayList<particalParams>();
    }
}
