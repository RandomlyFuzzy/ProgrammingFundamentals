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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author RandomlyFuzzy
 */
public class LevelGenerator extends IDrawable {

    private ArrayList<ArrayList<Boolean>> Map = new ArrayList<ArrayList<Boolean>>();
    private Random r;
    private Vector Size = new Vector(1,1).mult(400);

    public LevelGenerator(int seed) {
        r = new Random(seed);
    }

    @Override
    public void init() {
        setIsCollidable(false);
        for (int i = 0; i < 400; i++) {
            Map.add(new ArrayList<Boolean>());
            for (int j = 0; j < 400; j++) {
                Map.get(i).add(r.nextBoolean());
            }
        }

    }

    @Override
    public void doMove() {
    }

    @Override
    public void Update(Graphics2D gd) {

        for (int i = 0; i < Map.size(); i++) {
            for (int j = 0; j < Map.get(i).size(); j++) {
                int x = (int) ((i - (Map.size() / 2)) * Size.getX());
                int y = (int) ((j - (Map.size() / 2)) * Size.getY());
                //this if statment makes only things that are able to be seen displayed 
                if (-Transform.getOffsetTranslation().getX() - Size.getX() < x
                        && -Transform.getOffsetTranslation().getX() + Game.getScaledWidth() + Size.getY() > x
                        && -Transform.getOffsetTranslation().getY() - Size.getY() < y
                        && -Transform.getOffsetTranslation().getY() + Game.getScaledHeight() + Size.getY() > y) {
                    if (Map.get(i).get(j)) {
                        GetSprite(getRightImageFromPos("roadsprites/road_", i, j));
                    } else {
                        GetSprite(getRightImageFromPos("grassSprites/", i, j));
                    }

                    CheckSorrounding(i,j);

                    gd.drawImage(getLastImage(), (int) ((i - (Map.size() / 2)) * Size.getX()), (int) ((j - (Map.size() / 2)) * Size.getY()), (int) Size.getX(), (int) Size.getY(), null);
                }
            }
        }
    }

    public int CheckSorrounding(int x, int y) {
        boolean up = false, left = false, down = false, right = false;
        up = y == 0 ? false : Map.get(x).get(y - 1);
        left = x == 0 ? false : Map.get(x - 1).get(y);
        down = y == Map.get(x > Map.size() ? Map.size() - 1 : x).size() - 1 ? false : Map.get(x).get(y + 1);
        right = x == Map.size() - 1 ? false : Map.get(x + 1).get(y);
        int ret =  ((up?1:0)<<3)+(((down?1:0)<<2)+((left?1:0)<<1)+(right?1:0));
        System.out.println(ret);
        return ret;
    }

    public String getRightImageFromPos(String prefix, int x, int y) {
        boolean up = false, left = false, down = false, right = false;
        up = y == 0 ? false : Map.get(x).get(y - 1);
        left = x == 0 ? false : Map.get(x - 1).get(y);
        down = y == Map.get(x > Map.size() ? Map.size() - 1 : x).size() - 1 ? false : Map.get(x).get(y + 1);
        right = x == Map.size() - 1 ? false : Map.get(x + 1).get(y);
        return "/Images/" + prefix + (up ? "t" : "f") + (down ? "t" : "f") + (left ? "t" : "f") + (right ? "t" : "f") + ".png";
    }

    @Override
    public void onCollison(IDrawable id) {

    }

}
