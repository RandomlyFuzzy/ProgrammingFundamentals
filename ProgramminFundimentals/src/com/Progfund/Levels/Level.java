/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Levels;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Progfund.Object.Menu.*;
import com.Progfund.Object.inGame.Bullet;
import com.Progfund.Object.inGame.LevelGenerator;
import com.Progfund.Object.inGame.OverLay;
import com.Progfund.Object.inGame.ParticalGenerator;
import com.Progfund.Object.inGame.Player;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.sound.sampled.Clip;

/**
 *
 * @author RandomlyFuzzy
 */
public class Level extends ILevel {

    private Player p;
    private int ScoretoWin = 2000;
    private int Difficuly = 2000;
    private int rndSeed = 2000;
    private int playerHealth = 2000;
    private static int level = 0;

    /**
     *
     * @return
     */
    public static int GetLevel() {
        return Level.level;
    }

    /**
     *
     * @param pointstowin
     * @param Difficuly
     * @param rndSeed
     * @param playerhealth
     * @param Level
     */
    public Level(int pointstowin, int Difficuly, int rndSeed, int playerhealth, int Level) {
        setSimpleCollison(false);
        this.ScoretoWin = pointstowin;
        this.Difficuly = Difficuly;
        this.rndSeed = rndSeed;
        this.playerHealth = playerhealth;
        this.level = Level;
    }

    /**
     *
     */
    @Override
    public void init() {
        Game.setWorldrelDims(new Vector(0.8f, 0.8f));
        AddObject(new LevelGenerator(rndSeed, Difficuly));
        p = new Player(playerHealth, ScoretoWin);
        p.setPosition(100.1f, 200f);
        AddObject(p);
        AddObject(new OverLay()).setIsCollidable(false);
        Mouse m = new Mouse();
        m.setScale(new Vector(4, 4));
        AddObject(m).setIsCollidable(false);
        AddObject(new ParticalGenerator());
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
     * @param gd
     */
    @Override
    public void Draw(Graphics2D gd) {
    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyPress(KeyEvent ke) {
        super.keytyped(ke);
        int code = ke.getKeyCode();
        if (code == KeyEvent.VK_W) {
            p.SetVerticalDir(1);
        } else if (code == KeyEvent.VK_S) {
            p.SetVerticalDir(-1);
        }
        if (code == KeyEvent.VK_A) {
            p.SetHorizontalDir(1);
        } else if (code == KeyEvent.VK_D) {
            p.SetHorizontalDir(-1);
        }
    }

    /**
     *
     * @param ke
     */
    public void keytyped(KeyEvent ke) {

    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyRelease(KeyEvent ke) {
        int code = ke.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
            p.SetVerticalDir(0);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            p.SetHorizontalDir(0);
        }
    }

    /**
     *
     * @return
     */
    public int getScoretoWin() {
        return ScoretoWin;
    }

    /**
     *
     * @param ScoretoWin
     */
    public void setScoretoWin(int ScoretoWin) {
        this.ScoretoWin = ScoretoWin;
    }
}
