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
 * @author RandomlyFuzzy
 */
public class ParticalGenerator extends IDrawable {

    private static ArrayList<particalParams> particals = new ArrayList<particalParams>();

    /**
     *
     * @param id
     */
    public static void add(IDestroyable id) {
        particals.add(new particalParams("" + id.getScore(), id.getPosition(), 255));
        System.out.println("com.Progfund.Object.inGame.ParticalGenerator.add()");
    }

    /**
     *
     */
    @Override
    public void init() {
        System.out.println("com.Progfund.Object.inGame.ParticalGenerator.init()");
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
        if (particals.size() == 0) {
            return;
        }
        
        for (int i = particals.size() - 1; i >= 0; i--) {
            particalParams p = particals.get(i);
            if (p.getCurrentValue() <= 0) {
                particals.remove(p);
                continue;
            }
            System.out.println("com.Progfund.Object.inGame.ParticalGenerator.Update()");
            Color c = gd.getColor();
            Font f = gd.getFont();
            gd.setFont(f.deriveFont(f.getSize()*1.5f));
            int w = gd.getFontMetrics().stringWidth(p.getValue());
            
            
            gd.setColor(new Color(255, 215, 0,p.getCurrentValue()));
            gd.drawString(p.getValue(), (int)p.getPos().getX()-(w/2), (int)p.getPos().getY());
            p.addCurrentValue(-4);
            p.addPos(new Vector(0, -3f).mult(Game.getDelta()));
            
            
            gd.setFont(f);
            gd.setColor(c);

        }
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
    }

    private static class particalParams {

        private String Value = "";
        private Vector pos = Vector.Zero();
        private int CurrentValue = 255;

        public particalParams(String Val, Vector pos, int DisplayVal) {
            this.Value = Val;
            this.pos = pos;
            this.CurrentValue = DisplayVal;
        }

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
     *
     */
    public void dispose() {
        super.dispose();
        particals = new ArrayList<particalParams>();
    }
}
