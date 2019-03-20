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
 * this creates the entire vissable level and spawns obejcts in the sceen like enemys people and ianamate ones too
 * 
 * 
 * @author Liam Woolley 1748910
 */
public class LevelGenerator extends IDrawable {


    /**
     * storage of the values in the map 
     * booleans either true(road) or false(grass)
     */
    private ArrayList<ArrayList<Boolean>> Map = new ArrayList<ArrayList<Boolean>>();
    /** 
     * random object to populate the map with and for further spawning of things 
     */
    private Random r;
    /**
     * size to show each tile on the map
     */
    private Vector Size = new Vector(1, 1).mult(500);
    /**
     * difficalty of the game 
     * (used with modulas and checks if the anwser = (n))
     */
    private int difficulty = 1;
    /**
     * place to spawn things relative to the top left of the tile * the size of the tile
     */
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
     * @param seed the random seed of the object
     * @param difficulty the object difficult of the level (lower value is harder but below 0 is easier)(0 is the hardest 100% spawn rate)
     */
    public LevelGenerator(int seed, int difficulty) {
        r = new Random(seed);
        this.difficulty = difficulty * 2 + 1;
    }

    /**
     *populates the map and sets this to uncollidable
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

    @Override
    public void doMove() {}

    /**
     * this is what shows the map and generates the objects in the level
     * @param gd graphical context
     */
    @Override
    public void Update(Graphics2D gd) {

        // goes through all the values in a 2D array (map event though 2D Collection)
        for (int i = 0; i < Map.size(); i++) {
            for (int j = 0; j < Map.get(i).size(); j++) {
                //coverts to actual X and Y coord
                int x = (int) ((i - (Map.size() / 2)) * Size.getX());
                int y = (int) ((j - (Map.size() / 2)) * Size.getY());
                //this if statment makes only things that are able to be seen displayed 
                boolean inside
                        = (-Transform.getOffsetTranslation().getX() - Size.getX() < x
                        && -Transform.getOffsetTranslation().getX() + Game.getScaledWidth() + Size.getY() > x
                        && -Transform.getOffsetTranslation().getY() - Size.getY() < y
                        && -Transform.getOffsetTranslation().getY() + Game.getScaledHeight() + Size.getY() > y);
                //checks inside a 3 times the size of the screen rectangle that has the screen in the middle
                //###
                //#S#
                //###
                // if x or y is outside skip (super preformance boost)
                if (!((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < x
                        && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > x
                        && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < y
                        && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > y))) {
                    continue;
                }

                // if on the screen draw the map
                if (inside) {
                    if (Map.get(i).get(j)) {
                        GetSprite("/images/roadsprites/road_" + getRightImageFromPos(i, j));
                    } else {
                        GetSprite("/images/grassSprites/" + getRightImageFromPos(i, j));
                    }
                    gd.drawImage(getLastImage(), (int) ((i - (Map.size() / 2)) * Size.getX()), (int) ((j - (Map.size() / 2)) * Size.getY()), (int) Size.getX(), (int) Size.getY(), null);
                } else 
                //if the game is not over
                if (!LevelOverOverlay.isFinished()) {
                    //get new random int between 0 ... 12
                    int pos = r.nextInt(13);
                    if (pos <= 3) //places enemys
                    {
                        //gets the vector to place it on the screen
                        Vector Place = new Vector(x, y).add(new Vector(places[pos]).mult(Size));
                        //will be uniquely placed(only one spawned can be spawned on that spot that currently exsist)
                        if (IDestroyableManager.willBeUnique(Place) && HashUtils.hash(Place) % difficulty == 0) {
                            //sets health
                            Enemy e = new Enemy(100);
                            //sets position 
                            e.setPosition(Place);
                            //updates hash
                            e.SetHashParams();
                            //adds to level
                            Level().AddObject(e);
                        }
                    } else if (pos <= 11) //places other 
                    {
                        //gets the 4 sides as a 4 bit number 
                        //num & 8 for up
                        //num & 4 for down
                        //num & 2 for left
                        //num & 1 for right
                        //num = 0 for non
                        //num = 15 for all
                        //else any other combination
                        short sorrounings = CheckSorrounding(i, j);
                        if (Map.get(i).get(j)) //is a road 
                        {
                            //simple check as places has some certain placment of vectors
                            boolean Rotate = (pos % 2 == 0);

                            //set the inital values
                            Vector V1 = Vector.Zero();

                            //precheck to make it look nicer 
                            //used to get the vectors for a hash
                            //if there isnt just a road bellow this tile && has some roads around it
                            if (sorrounings != 0 && sorrounings != 4) {
                                //Car spawn or road
                                V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size));
                            } else 
                            //only has a road below it 
                            if (sorrounings == 4) {
                                // bottom right of the tile
                                if (pos % 4 == 2) {
                                    //car spawn on driveway
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size).add(new Vector(-0f, 0.3f).mult(Size)));
                                } else 
                                //bottom left of the tile
                                if (pos % 4 == 3) {
                                    //mailbox on garden
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size)).add(new Vector(-0.1f, 0.3f).mult(Size));
                                } else {
                                    //people on house
                                    V1 = new Vector(x, y).add(new Vector(places[(pos) % 4 + 8]).mult(Size)).add(new Vector(0.3f, -0.30f).mult(Size));
                                }
                            }
                            //checks for a unique spawn location
                            //same as above where if statments are  but this \/ creates ~ N(number or possable spawn locations)/difficulty amount 
                            if (IDestroyableManager.willBeUnique(V1) && HashUtils.hash(V1) % difficulty == 0) {
                                if (sorrounings != 0 && sorrounings != 4) {
                                    StaticObject e = new StaticObject(50);
                                    e.setPosition(V1);
                                    e.setScale(new Vector(0.6f, 0.6f));
                                    e.SetHashParams();
                                    if (Rotate) {
                                        e.setRotation(Math.PI / 2);
                                    }
                                    e.GetSprite("/images/car" + (r.nextInt(3) + 1) + ".png");
                                    Level().AddObject(e);
                                } else if (sorrounings != 0 && HashUtils.hash(V1) % difficulty == 0) {
//                                    Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 4]).mult(Size));
                                    if (pos % 4 == 2) {
                                        StaticObject p = new StaticObject(25, 100);
                                        p.setPosition(V1);
                                        p.setScale(new Vector(0.6f, 0.6f));
                                        p.setRotation(Math.PI / 2);
                                        p.SetHashParams();
                                        p.GetSprite("/images/car" + (r.nextInt(3) + 1) + ".png");
                                        Level().AddObject(p);
                                    } else if (pos % 4 == 3) {
                                        StaticObject p = new StaticObject(25, 20);
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
                            //gets the indeted vectors(not on the corners)
                            Vector Place = new Vector(x, y).add(new Vector(places[(pos) % 4 + 4]).mult(Size));
                            //check to see if wil be unique
                            if (IDestroyableManager.willBeUnique(Place)) {
                                //if hash % diffult is N stop all the other objects from spawning
                                if (sorrounings != 15 && HashUtils.hash(Place) % difficulty == 0) {
                                    StaticObject e = new StaticObject(50);
                                    e.setPosition(Place);
                                    e.setScale(new Vector(1.5f, 1.5f));
                                    e.SetHashParams();
                                    e.GetSprite("/images/bin.png");
                                    Level().AddObject(e);
                                } else if (sorrounings == 15 && HashUtils.hash(Place) % difficulty == 0) {
                                    person p = new person(25, 100);
                                    p.setPosition(Place);
                                    p.SetHashParams();
                                    p.GetSprite("/images/person.png");
                                    Level().AddObject(p);
                                }
                            }
                        }
                    } else {
                        //spawning the bins on the grass
                        Vector Place = new Vector(x, y).add(new Vector(places[(pos)]).mult(Size));
                        if (IDestroyableManager.willBeUnique(Place)
                                && HashUtils.hash(Place) % difficulty == 0) {
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
     * @param x position in the map
     * @param y position on the map
     * @return a short of the sides with each bit is a side
     * 8 = up
     * 4 = down
     * 2 = left
     * 1 = right
     */
    public short CheckSorrounding(int x, int y) {
        boolean up = false, left = false, down = false, right = false;
        up = y == 0 ? false : Map.get(x).get(y - 1);
        left = x == 0 ? false : Map.get(x - 1).get(y);
        down = y == Map.get(x > Map.size() ? Map.size() - 1 : x).size() - 1 ? false : Map.get(x).get(y + 1);
        right = x == Map.size() - 1 ? false : Map.get(x + 1).get(y);
        int ret = ((up ? 1 : 0) << 3) + (((down ? 1 : 0) << 2) + ((left ? 1 : 0) << 1) + (right ? 1 : 0));
//        System.out.println(ret);
        return (short)ret;
    }

    /**
     *
     * @param x position in map
     * @param y position in the map
     * @return the result in a t & f formate so if it has road on all sides it will be tttt.png same for all other combinations
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
    public void onCollison(IDrawable id) { }

}
