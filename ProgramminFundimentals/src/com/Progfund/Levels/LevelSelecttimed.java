/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Levels;

import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Utils.LevelLoader;
import com.Liamengine.Engine.Components.Vector;
import com.Progfund.Object.Menu.Button;
import com.Progfund.Object.Menu.HUDdelegate;
import com.Progfund.Object.Menu.Mouse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;

/**
 * this is the level select for the timed levels
 * 
 * @author Liam Woolley 1748910
 */
public class LevelSelecttimed extends ILevel {

    /**
     * set music to continue when loaded
     */
    public LevelSelecttimed() {
        super();
        setStopAudioOnStart(false);
    }

    /**
     * TIMES matrix for all the levels
     */
    private static final float[] TIMES = {
        300,
        240,
        240,
        180,
        300,
        240,
        240,
        180,
        300,
        240,
        240,
        180,
        300,
        240,
        240,
        180,
        300,
        240,
        240,
        180
    };
    

    /**
     *
     * @param i the level index levelnum-1
     */
    public static void LoadLevelFromID(int i) {
        int[][] values = LevelSelect.getValues();
        System.out.println("com.Progfund.Levels.LevelSelect.LoadLevelFromID()");
        Game.SetLevelActive(new timedLevel(values[i][0], values[i][1], values[i][2], values[i][3],TIMES[i], i+1));
    }

    /**
     * sets up Level 
     */
    @Override
    public void init() {
        //creates all the level buttons from level 1 ... 20
        for (int i = 0; i < 20; i++) {
            AddObject(new Button(new Vector(((0.2f * (i % 5)) + 0.1f), ((0.2f * (i / 5)) + 0.1f)), ("Level" + (i + 1)), new HUDdelegate() {
                public void OnClick(Button b) {
                    String s = b.getMessage().substring(5);
                    System.out.println(".OnClick() " + s);
                    LevelSelecttimed.LoadLevelFromID(Integer.parseInt(s.trim())-1);
                }
            }));
        }
        //adds a backbutto
        AddObject(new Button(new Vector(0.9f, 0.9f), "Back", new HUDdelegate() {
            @Override
            public void OnClick(Button b) {
                Game.SetLevelActive(new MainMenu());
            }
        }));
        //add a mouse 
        AddObject(new Mouse());
        //sets background 
        setBackgroundimage(GetSprite("/Images/backgrounds/background1.png"));
    }

    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) {}

    /**
     *
     * @param g
     */
    @Override
    public void Draw(Graphics2D g) {}

    /**
     *
     * @param e
     */
    @Override
    public void keyPress(KeyEvent e) {}

    /**
     *
     * @param e
     */
    @Override
    public void keyRelease(KeyEvent e) {}

}
