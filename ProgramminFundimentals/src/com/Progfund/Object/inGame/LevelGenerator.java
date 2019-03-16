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
import com.Progfund.HashUtils;
import com.Progfund.Object.Menu.LevelOverOverlay;
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
    private Random spawns;
    private Vector Size = new Vector(1, 1).mult(500);
    private int difficulty = 1;
    private Vector[] places = {
        //for enemys
        Vector.Zero(),
        new Vector(1, 0),
        new Vector(1, 1),
        new Vector(0, 1),
        //for cars
        Vector.One().mult(0.25f),
        new Vector(0.75f, 0.25f),
        new Vector(0.75f, 0.75f),
        new Vector(0.25f, 0.75f),
        //for grass
        new Vector(0.5f, 0.25f),
        new Vector(0.75f, 0.5f),
        new Vector(0.5f, 0.75f),
        new Vector(0.25f, 0.5f)
    };

    public LevelGenerator(int seed, int difficulty) {
        r = new Random(seed);
        spawns = new Random(seed);
        this.difficulty = difficulty;
    }

    @Override
    public void init() {
        setIsCollidable(false);
        for (int i = 0; i < 500; i++) {
            Map.add(new ArrayList<Boolean>());
            for (int j = 0; j < 500; j++) {
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
                boolean inside
                        = (-Transform.getOffsetTranslation().getX() - Size.getX() < x
                        && -Transform.getOffsetTranslation().getX() + Game.getScaledWidth() + Size.getY() > x
                        && -Transform.getOffsetTranslation().getY() - Size.getY() < y
                        && -Transform.getOffsetTranslation().getY() + Game.getScaledHeight() + Size.getY() > y);
                //checks inside a 3 times the size of the screen rectangle that has the screen in the middle
                if (!((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < x
                        && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > x
                        && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < y
                        && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > y))) {
                    continue;
                }

                if (inside) {
                    if (Map.get(i).get(j)) {
                        GetSprite(getRightImageFromPos("roadsprites/road_", i, j));
                    } else {
                        GetSprite(getRightImageFromPos("grassSprites/", i, j));
                    }
                    gd.drawImage(getLastImage(), (int) ((i - (Map.size() / 2)) * Size.getX()), (int) ((j - (Map.size() / 2)) * Size.getY()), (int) Size.getX(), (int) Size.getY(), null);
                } else if (!LevelOverOverlay.isFinished()) {

                    int pos = spawns.nextInt(12);
                    if (pos <= 3) //places enemys
                    {
                        Vector Place = new Vector(x, y).add(new Vector(places[pos]).mult(Size));
                        if (IDestroyableManager.willBeUnique(Place, new Vector(100)) && HashUtils.hash(Place, new Vector(100)) % difficulty == 0) {
                            Enemy e = new Enemy(100);
                            e.setPosition(Place);
                            e.SetHashParams();
                            Level().AddObject(e);
                        }
                    } else //places other 
                    {
                        int sorrounings = CheckSorrounding(i, j);

                        if (Map.get(i).get(j)) //road 
                        {
                            boolean Rotate = (pos % 2 == 0
                                    && ((sorrounings & 8) == 8
                                    || (sorrounings & 4) == 4
                                    || (sorrounings & 2) != 2
                                    || (sorrounings & 1) != 1));

                            Vector Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size));
                            if (IDestroyableManager.willBeUnique(Place, new Vector(100)) && HashUtils.hash(Place, new Vector(50)) % difficulty == 0) {
                                if (sorrounings != 0) {
                                    RandomObject e = new RandomObject(50);
                                    e.setPosition(Place);
                                    e.setScale(new Vector(0.6f, 0.6f));
                                    e.SetHashParams();
                                    if (Rotate) {
                                        e.setRotation(Math.PI / 2);
                                    }
                                    e.GetSprite("/images/car" + (spawns.nextInt(3) + 1) + ".png");
                                    Level().AddObject(e);
                                } else {
                                    person p = new person(25,100);
                                    p.setPosition(Place);
                                    p.SetHashParams();
                                    p.GetSprite("/images/person.png");
                                    Level().AddObject(p);
                                }
                            }

                        } else//grass
                        {

                            Vector Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 4]).mult(Size));
                            if (IDestroyableManager.willBeUnique(Place, new Vector(100)) ) {
                                if (sorrounings != 15&& HashUtils.hash(Place, new Vector(50)) % difficulty == 0) {
                                    RandomObject e = new RandomObject(50);
                                    e.setPosition(Place);
                                    e.setScale(new Vector(2f, 2f));
                                    e.SetHashParams();
//                                    if (Rotate) {
//                                        e.setRotation(Math.PI / 2);
//                                    }
                                    e.GetSprite("/images/bin.png");
                                    Level().AddObject(e);
                                } else if (sorrounings == 15){
                                    person p = new person(25,100);
                                    p.setPosition(Place);
                                    p.SetHashParams();
                                    p.GetSprite("/images/person.png");
                                    Level().AddObject(p);
                                }
                            }
                        }
                    }
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
        int ret = ((up ? 1 : 0) << 3) + (((down ? 1 : 0) << 2) + ((left ? 1 : 0) << 1) + (right ? 1 : 0));
//        System.out.println(ret);
        return ret;
    }

    public String getRightImageFromPos(String prefix, int x, int y) {
        boolean up = false, left = false, down = false, right = false;
        up = y == 0 ? false : Map.get(x).get(y - 1);
        left = x == 0 ? false : Map.get(x - 1).get(y);
        down = y == Map.get(x > Map.size() ? Map.size() - 1 : x).size() - 1 ? false : Map.get(x).get(y + 1);
        right = x == Map.size() - 1 ? false : Map.get(x + 1).get(y);
        return "/images/" + prefix + (up ? "t" : "f") + (down ? "t" : "f") + (left ? "t" : "f") + (right ? "t" : "f") + ".png";
    }

    @Override
    public void onCollison(IDrawable id) {

    }

}
