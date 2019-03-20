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
 *
 * the level that lets the player decide the level they want to play
 * @author Liam Woolley 1748910
 */
public class LevelSelect extends ILevel {

    /**
     * keeps the audio running in the background
     */
    public LevelSelect() {
        super();
        setStopAudioOnStart(false);
    }

    /**
     * matrix off the level paramiters
     * [n][0] is the score to win
     * [n][1] is the difficulty
     * [n][2] random seed
     * [n][3] player start health
     */
    private static final int[][] VALUES = {
        {1000, 6,  4, 600},
        {2000, 6, 51, 600},
        {3000, 6,  1, 500},
        {4000, 6, 47, 500},
        {1000, 5,  4, 600},
        {2000, 5, 51, 600},
        {3000, 5,  1, 500},
        {4000, 5, 47, 500},
        {1000, 4, 25, 600},
        {2000, 4, 20, 600},
        {3000, 4, 53, 500},
        {4000, 4, 20, 500},
        {1000, 3,  2, 600},
        {2000, 3,415, 600},
        {3000, 3,251, 500},
        {4000, 3, 45, 600},
        {1000, 2,454, 800},
        {2000, 2,455, 800},
        {3000, 2,222, 600},
        {4000, 2, 21, 600},
        {1000, 1, 28, 1000},
        {2000, 1, 29, 1000},
        {3000, 1, 60, 800},
        {4000, 1, 30, 800}
    };

    /**
     *
     * @return the matrix for the levels
     */
    public static int[][] getValues() {
        return VALUES;
    }

    /**
     *
     * @param i the level thats wanting to beloaded
     */
    public static void LoadLevelFromID(int i) {
        Game.SetLevelActive(new Level(VALUES[i][0], VALUES[i][1], VALUES[i][2], VALUES[i][3], i+1));
    }

    /**
     * sets up for the scene
     */
    @Override
    public void init() {
        //creats aall the level select button's level + 1-20 placed spaced out
        for (int i = 0; i < 20; i++) {
            AddObject(new Button(new Vector(((0.2f * (i % 5)) + 0.1f), ((0.2f * (i / 5)) + 0.1f)), ("Level" + (i + 1)), new HUDdelegate() {
                public void OnClick(Button b) {
                    String s = b.getMessage().substring(5);
                    System.out.println(".OnClick() " + s);
                    LevelSelect.LoadLevelFromID(Integer.parseInt(s.trim())-1);
                }
            }));
        }
        //back button
        AddObject(new Button(new Vector(0.9f, 0.9f), "Back", new HUDdelegate() {
            @Override
            public void OnClick(Button b) {
                Game.SetLevelActive(new MainMenu());
            }
        }));
        // adds a mouse
        AddObject(new Mouse());
        //default scene
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
