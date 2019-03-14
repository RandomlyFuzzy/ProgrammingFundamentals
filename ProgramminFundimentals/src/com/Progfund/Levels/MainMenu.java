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
import com.Progfund.Object.inGame.SpawnPoints;
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

/**
 *
 * @author RandomlyFuzzy
 */
public class MainMenu extends ILevel {

    BufferedImage bg;

    @Override
    public void init() {
        imageUtils.T.setImage("background", null);
        Game.setWorldrelDims(new Vector(1, 1));
        setBackground(Color.BLACK);
        AddObject(new Button(new Vector(0.125f, 0.27f), "Play Level", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(new Level1());
            }
        }));
        AddObject(new Button(new Vector(0.125f, 0.4f), "LeaderBoard", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(new Leaderboard());
            }
        }));
        AddObject(new Button(new Vector(0.125f, 0.53f), "Exit", new HUDdelegate() {
            public void OnClick(Button b) {
                System.exit(0);
            }
        }));

        AddObject(new toggle(new Vector(0.95f, 0.88f), "hello world", new HUDdelegate() {
            public void OnClick(toggle b) {
                MusicUtils.SetVolume(b.IsTicked() ? 1 : 0);
            }
        }));
        AddObject(new Mouse());
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageUtils.T.setImage("background", getOnlineImage("https://picsum.photos/1050/730/?random"));
                bg = GetSprite("background");
            }
        }).start();

    }

    @Override
    public void Update(ActionEvent ae) {
    }

    @Override
    public void Draw(Graphics2D gd) {
        if (imageUtils.T.GetImage("background") == bg) {
            try {
                gd.drawImage(bg, (int) (Game.getWindowWidth() * 0.25f), (int) (Game.getWindowHeight() * 0.03f), (int) (bg.getWidth() * Game.WorldScale().getX()), (int) (bg.getHeight() * Game.WorldScale().getY()), null);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void keyPress(KeyEvent ke) {
    }

    @Override
    public void keyRelease(KeyEvent ke) {
    }

}
