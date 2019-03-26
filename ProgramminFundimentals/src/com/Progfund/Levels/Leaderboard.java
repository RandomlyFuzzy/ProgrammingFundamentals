/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Levels;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.FileUtils;
import com.Progfund.Object.Menu.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Leaderboard scene of the game Displays the top 5 scores as per the brief
 *
 * @author Liam Woolley 1748910
 */
public class Leaderboard extends ILevel {

    /**
     * used to compair to currently checking leaderboard value
     */
    private String previousind = "1";
    /**
     * currently checking file trimed
     */
    private static String Currentind = "Level1";

    /**
     * storage of all the times loaded as a collection because of the sort
     * function
     */
    private ArrayList<String> times = new ArrayList<String>();

    /**
     * default constructor
     */
    public Leaderboard() {
        super();
        //keeps the music playing the background
        setStopAudioOnStart(false);
    }

    /**
     *
     * @return the current file its checking
     */
    public static String getCurrentind() {
        return Currentind;
    }

    /**
     *
     * @param Currentind sets the currently checking file to that value
     */
    public static void setCurrentind(String Currentind) {
        Leaderboard.Currentind = Currentind;
    }

    /**
     * creates all objects used in the level
     */
    @Override
    public void init() {
        // for the background
//        GetSprite("/Images/backgrounds/background1.png");

        //creates a seachable button for each level
        for (int i = 0; i < 20; i++) {
            Button b = new Button(new Vector(((0.09f * (i % 10)) + 0.095f), ((0.075f * (i / 10)) + 0.05f)), "Level" + ((i + 1)), new HUDdelegate() {
                public void OnClick(Button b) {
                    //sets the current file to be reading from
                    Leaderboard.setCurrentind(b.getMessage());
                }
            });
            AddObject(b);
            b.setScale(new Vector(0.4f, 0.5f));
        }
        //back button to the main menu
        AddObject(new Button(new Vector(0.86f, 0.9f), "Back", new HUDdelegate() {
            @Override
            public void OnClick(Button b) {
                Game.SetLevelActive(new MainMenu());
            }
        }));
        //adds a mouse
        AddObject(new Mouse());
    }

    /**
     *
     * @param ae timer event
     */
    @Override
    public void Update(ActionEvent ae) {
        //if a button has been presed update once
        if (previousind != Currentind) {
            //say what file is being read from s
            System.out.println("com.Progfund.Levels.Leaderboard.Update() loading " + Currentind);
            //resets the collection
            times = new ArrayList<String>();
            //adds to the collection 
            times.addAll(
                    FileUtils.GetFileSplit("Resources/savedata/" + Currentind + ".txt", "\n", true)
            );
            //sorts the leaderboard entry 
            times.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    //check to see if not an empy entry
                    if (o2.equals(new String())) {
                        return -1;
                    }
                    //check to see if not an empy entry
                    if (o1.equals(new String())) {
                        return 1;
                    }
                    //gets the strings stored for the score
                    String val1 = o1.substring(o1.indexOf(":") + 1).trim();
                    String val2 = o2.substring(o2.indexOf(":") + 1).trim();
                    //compaires them in order sort them
                    return Double.parseDouble(val2) > Double.parseDouble(val1) ? 1 : -1;
                }
            });
            //if there are more than 5 entrys
            if (times.size() > 5) {
                //save those 5 back to a file
                //remove the rest
                String set = "";
                for (int i = 0; i < 5; i++) {
                    set += times.get(i) + "\n";
                }
                FileUtils.SetFileContence("Resources/savedata/" + Currentind + ".txt", set);
            }
            previousind = Currentind;
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void Draw(Graphics2D g) {
        //g.drawImage(GetSprite("/Images/backgrounds/background1.png"), Game.getWindowWidth(), 0, (Game.getWindowWidth() * -1), (Game.getWindowHeight()), null);
        //keeps the current font for later as is a relative to the screen scale(world scale)
        Font f = g.getFont();
        //create a scaled font 
        Font f2 = f.deriveFont(1, Game.WorldScale().getY() * 13);
        g.setFont(f2);
        g.setColor(new Color(55, 55, 55, 150));
        g.setFont(f.deriveFont(1, f.getSize() * (Game.WorldScale().getY() / (1f / ((int) Math.pow((15 ) + 1, 2) / 100f)))));
        //if their are times
        int w = (int) (g.getFontMetrics().stringWidth(Currentind) * 1.05f);
        g.drawString(Currentind, (Game.getWindowWidth() / 2 - (w / 2)), (0.23f) * Game.getWindowHeight());
        //only draws if theirs is data and the first piece of data is not empty
        if (times.size() != 0 && !times.get(0).equals(new String())) {
            //get the size of the firs entry(the biggest one)
            String s = times.get(0);
            String[] split = s.split(":");
            String str = "No " + (1) + " Place is " + split[0] + " with " + split[1] + " points";
            w = (int) (g.getFontMetrics().stringWidth(str) * 1.05f);
            //create the rectangle to that dimentions scaled to the amount of entrys
            g.fillRect(
                    (int) ((Game.getWindowWidth() / 2) - (w / 2)),
                    (int) ((0.255f) * Game.getWindowHeight()),
                    (int) (w),
                    (int) (0.12f * Game.getWindowHeight() * (times.size() >= 5 ? 5 : times.size())));

            //draw title            
            g.setColor(Color.WHITE);
            //draw all the entrys found/saved at verying sizes
            for (int i = 0; i < (times.size() >= 5 ? 5 : times.size()); i++) {
                //have to scale the text with the equation (((15-(3i))+1)^2)/2 (all while keeping the scale realative to the screen size) look at ILevel.paintComponent for how the original scale is set
                g.setFont(f.deriveFont(1, f.getSize() * 2.5f * ((float)Game.WorldScale().getY() / (i+1))));
                s = times.get(i);
                split = s.split(":");
                str = "No " + (i + 1) + " Place is " + split[0] + " with " + split[1] + " score";
                //gets the string width to center it 
                w = g.getFontMetrics().stringWidth(str);
                //draws the times in assending order moving down each time a set amount (could have added the font size each time but no need
                g.drawString(str,
                        Game.getWindowWidth() / 2 - w / 2,
                        (((i % 20) * 0.12f) + 0.33f) * Game.getWindowHeight());
            }
        }
        g.setFont(f);
    }

    @Override
    public void keyPress(KeyEvent e) {
    }

    @Override
    public void keyRelease(KeyEvent e) {
    }

    /**
     * set back to defualt variables when closed
     */
    public void dispose() {
        super.dispose();
        Currentind = "Level1";
        previousind = "1";
        times = new ArrayList<String>();
    }

    @Override
    public void keytyped(KeyEvent ke) {
    }

}
