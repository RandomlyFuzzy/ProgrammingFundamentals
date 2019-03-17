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
        new Vector(0.25f, 0.5f),
        //for the pickups
        new Vector(0.5f, 0.5f)
    };

    /**
     *
     * @param seed
     * @param difficulty
     */
    public LevelGenerator(int seed, int difficulty) {
        r = new Random(seed);
        spawns = new Random(seed);
        this.difficulty = difficulty * 2 + 1;
    }

    /**
     *
     */
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
                        GetSprite("/images/roadsprites/road_" + getRightImageFromPos(i, j));
                    } else {
                        GetSprite("/images/grassSprites/" + getRightImageFromPos(i, j));
                    }
                    gd.drawImage(getLastImage(), (int) ((i - (Map.size() / 2)) * Size.getX()), (int) ((j - (Map.size() / 2)) * Size.getY()), (int) Size.getX(), (int) Size.getY(), null);
                } else if (!LevelOverOverlay.isFinished()) {

                    int pos = spawns.nextInt(13);
                    if (pos <= 3) //places enemys
                    {
                        Vector Place = new Vector(x, y).add(new Vector(places[pos]).mult(Size));
                        if (IDestroyableManager.willBeUnique(Place, new Vector(100)) && HashUtils.hash(Place, new Vector(100)) % difficulty == 0) {
                            Enemy e = new Enemy(100);
                            e.setPosition(Place);
                            e.SetHashParams();
                            Level().AddObject(e);
                        }
                    } else if (pos <= 11) //places other 
                    {
                        int sorrounings = CheckSorrounding(i, j);

                        if (Map.get(i).get(j)) //is a road 
                        {
                            //
                            boolean Rotate = (pos % 2 == 0);

                            Vector V1 = Vector.Zero();
                            Vector V2 = Vector.Zero();

                            if (sorrounings != 0 && sorrounings != 4) {
                                //Car spawn or road
                                V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size));
                                V2 = new Vector(50);
                            } else if (sorrounings != 0) {
                                if (pos % 4 == 2) {
                                    //car spawn on driveway
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size).add(new Vector(-0f, 0.3f).mult(Size)));
                                    V2 = new Vector(25);
                                } else if (pos % 4 == 3) {
                                    //mailbox on garden
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size)).add(new Vector(-0.1f, 0.3f).mult(Size));
                                    V2 = new Vector(25);
                                } else {
                                    //people on house
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size)).add(new Vector(0.3f, -0.30f).mult(Size));
                                    V2 = new Vector(25);
                                }
                            }

                            if (IDestroyableManager.willBeUnique(V1, V2) && HashUtils.hash(V1, V2) % difficulty == 0) {
                                if (sorrounings != 0 && sorrounings != 4) {
                                    RandomObject e = new RandomObject(50);
                                    e.setPosition(V1);
                                    e.setScale(new Vector(0.6f, 0.6f));
                                    e.SetHashParams();
                                    if (Rotate) {
                                        e.setRotation(Math.PI / 2);
                                    }
                                    e.GetSprite("/images/car" + (spawns.nextInt(3) + 1) + ".png");
                                    Level().AddObject(e);
                                } else if (sorrounings != 0 && HashUtils.hash(V1, V2) % difficulty == 0) {
//                                    Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 4]).mult(Size));
                                    if (pos % 4 == 2) {
                                        RandomObject p = new RandomObject(25, 100);
                                        p.setPosition(V1);
                                        p.setScale(new Vector(0.6f, 0.6f));
                                        p.setRotation(Math.PI / 2);
                                        p.SetHashParams();
                                        p.GetSprite("/images/car" + (spawns.nextInt(3) + 1) + ".png");
                                        Level().AddObject(p);
                                    } else if (pos % 4 == 3) {
                                        RandomObject p = new RandomObject(25, 20);
                                        p.setPosition(V1);
//                                        p.setScale(new Vector(0.6f, 0.6f));
                                        p.SetHashParams();
                                        p.GetSprite("/images/mailbox.png");
                                        Level().AddObject(p);
                                    } else {
                                        person p = new person(25, 100);
                                        p.setPosition(V1);
                                        p.SetHashParams();
                                        p.GetSprite("/images/person.png");
                                        Level().AddObject(p);
                                    }
                                }
                            }

                        } else// is grass
                        {
                            Vector Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 4]).mult(Size));
                            if (IDestroyableManager.willBeUnique(Place, new Vector(100))) {
                                if (sorrounings != 15 && HashUtils.hash(Place, new Vector(50)) % difficulty == 0) {
                                    RandomObject e = new RandomObject(50);
                                    e.setPosition(Place);
                                    e.setScale(new Vector(2f, 2f));
                                    e.SetHashParams();
//                                    if (Rotate) {
//                                        e.setRotation(Math.PI / 2);
//                                    }
                                    e.GetSprite("/images/bin.png");
                                    Level().AddObject(e);
                                } else if (sorrounings == 15 && HashUtils.hash(Place, new Vector(25)) % difficulty == 0) {
                                    person p = new person(25, 100);
                                    p.setPosition(Place);
                                    p.SetHashParams();
                                    p.GetSprite("/images/person.png");
                                    Level().AddObject(p);
                                }
                            }
                        }
                    } else {
                        Vector Place = new Vector(x, y).add(new Vector(places[(pos)]).mult(Size));
                        if (IDestroyableManager.willBeUnique(Place, new Vector(100))
                                && HashUtils.hash(Place, new Vector(300)) % difficulty == 0) {
                            PickUp e = new PickUp(300);
                            e.setPosition(Place);
                            e.setScale(new Vector(2f, 2f));
                            e.SetHashParams();
                            e.GetSprite("/images/bin.png");
                            Level().AddObject(e);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
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

    /**
     *
     * @param prefix
     * @param x
     * @param y
     * @return
     */
    public String getRightImageFromPos(int x, int y) {
        boolean up = false, left = false, down = false, right = false;
        up = y == 0 ? false : Map.get(x).get(y - 1);
        left = x == 0 ? false : Map.get(x - 1).get(y);
        down = y == Map.get(x > Map.size() ? Map.size() - 1 : x).size() - 1 ? false : Map.get(x).get(y + 1);
        right = x == Map.size() - 1 ? false : Map.get(x + 1).get(y);
        return (up ? "t" : "f") + (down ? "t" : "f") + (left ? "t" : "f") + (right ? "t" : "f") + ".png";
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {

    }

}
