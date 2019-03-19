/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Levels;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.LevelLoader;
import com.Liamengine.Engine.Utils.MusicUtils;
import com.Liamengine.Engine.Utils.imageUtils;
import com.Progfund.Object.Menu.*;
import com.sun.imageio.plugins.common.ImageUtil;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;

/**
 * first menu level in the game 
 * 
 * 
 * @author Liam Woolley 1748910
 */
public class MainMenu extends ILevel {

    BufferedImage bg;

    /**
     * contrcuctor set music to coninue when loaded
     */
    public MainMenu() {
        setStopAudioOnStart(false);
    }

    /**
     * 
     */
    @Override
    public void init() {
        //adds an entry
        imageUtils.T.setImage("background", null);
        Game.setWorldrelDims(new Vector(1, 1));
        //background colour
        setBackground(Color.BLACK);
        //to levelselect
        AddObject(new Button(new Vector(0.125f, 0.27f), "Play Level", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(new LevelSelect());//new com.Progfund.Levels.Level(1000,3,20,300));
            }
        }));
        //to levelselecttimed
        AddObject(new Button(new Vector(0.125f, 0.40f), "Time Trials", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(new LevelSelecttimed());//new com.Progfund.Levels.Level(1000,3,20,300));
            }
        }));
        //to leaderboard
        AddObject(new Button(new Vector(0.125f, 0.53f), "LeaderBoard", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(new Leaderboard());
            }
        }));
        //exit out the game
        AddObject(new Button(new Vector(0.125f, 0.66f), "Exit", new HUDdelegate() {
            public void OnClick(Button b) {
                System.exit(0);
            }
        }));
        // turns music on and off
        AddObject(new toggle(new Vector(0.95f, 0.88f), new HUDdelegate() {
            public void OnClick(toggle b) {
                MusicUtils.SetVolume(b.IsTicked() ? 1 : 0);
            }
        }));
        //adds a mouse
        AddObject(new Mouse());
        //my take on asynrouse loading
        new Thread(new Runnable() {
            @Override
            public void run() {
                //trys to get the image from online
                imageUtils.T.setImage("background", getOnlineImage("https://picsum.photos/1050/730/?random"));
                //sets the displayed image to the loaded asset
                bg = GetSprite("background");
            }
        }).start();
        //play music for ever (until stoped)
//        play("/music/music.wav", 0, Clip.LOOP_CONTINUOUSLY);

    }

    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) { }

    /**
     *
     * @param gd graphical context
     * just draws image
     */
    @Override
    public void Draw(Graphics2D gd) {
        //does not like checking to see if it is equal to null
        if (imageUtils.T.GetImage("background") == bg) {
            try {
                gd.drawImage(bg, (int) (Game.getWindowWidth() * 0.25f), (int) (Game.getWindowHeight() * 0.03f), (int) (bg.getWidth() * Game.WorldScale().getX()), (int) (bg.getHeight() * Game.WorldScale().getY()), null);
            } catch (Exception e) {

            }
        }
    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyPress(KeyEvent ke) {
    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyRelease(KeyEvent ke) {
    }

}
