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
 * @author Liam Woolley 1748910
 */
public class LevelSelect extends ILevel {

    /**
     *
     */
    public LevelSelect() {
        super();
        setStopAudioOnStart(false);
    }

    private static final int[][] values = {
        {1000, 6,  4, 300},
        {2000, 6, 51, 300},
        {3000, 6,  1, 200},
        {4000, 6, 47, 200},
        {1000, 5,  4, 300},
        {2000, 5, 51, 300},
        {3000, 5,  1, 200},
        {4000, 5, 47, 200},
        {1000, 4, 25, 300},
        {2000, 4, 20, 300},
        {3000, 4, 53, 200},
        {4000, 4, 20, 200},
        {1000, 3,  2, 300},
        {2000, 3,415, 300},
        {3000, 3,251, 200},
        {4000, 3, 45, 200},
        {1000, 2,454, 400},
        {2000, 2,455, 400},
        {3000, 2,222, 300},
        {4000, 2, 21, 300},
        {1000, 1, 28, 500},
        {2000, 1, 29, 500},
        {3000, 1, 60, 400},
        {4000, 1, 30, 400}
    };

    /**
     *
     * @return
     */
    public static int[][] getValues() {
        return values;
    }

    /**
     *
     * @param i
     */
    public static void LoadLevelFromID(int i) {
        i-=1;
        System.out.println("com.Progfund.Levels.LevelSelect.LoadLevelFromID()");
        Game.SetLevelActive(new Level(values[i][0], values[i][1], values[i][2], values[i][3], i+1));
    }

    /**
     *
     */
    @Override
    public void init() {
        for (int i = 0; i < 20; i++) {
            AddObject(new Button(new Vector(((0.2f * (i % 5)) + 0.1f), ((0.2f * (i / 5)) + 0.1f)), ("Level" + (i + 1)), new HUDdelegate() {
                public void OnClick(Button b) {
                    String s = b.getMessage().substring(5);
                    System.out.println(".OnClick() " + s);
                    LevelSelect.LoadLevelFromID(Integer.parseInt(s.trim()));
                }
            }));
        }
        AddObject(new Button(new Vector(0.9f, 0.9f), "Back", new HUDdelegate() {
            @Override
            public void OnClick(Button b) {
                Game.SetLevelActive(new MainMenu());
            }
        }));
        AddObject(new Mouse());
        setBackgroundimage(GetSprite("/Images/backgrounds/background1.png"));
    }

    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) {
    }

    /**
     *
     * @param g
     */
    @Override
    public void Draw(Graphics2D g) {
        g.setColor(Color.WHITE);
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPress(KeyEvent e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyRelease(KeyEvent e) {
    }

}
