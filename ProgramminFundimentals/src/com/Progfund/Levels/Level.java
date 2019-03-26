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
import com.Progfund.Object.inGame.IDestroyableManager;
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
 * this is the main game level it uses random seeds to keep copys different but simlar
 * 
 * 
 * 
 * @author Liam Woolley 1748910
 */
public class Level extends ILevel {

    /**
     * player refernese so key's pressed can be passed onto
     */
    private Player p;
    /**
     * defined score to win
     * @see #LevelSelect
     */
    private int ScoretoWin = 2000;
    /**
     * used to detemin the frequency of things shown
     */
    private int Difficuly = 2000;
    /**
     * the see that generates things
     */
    private int rndSeed = 2000;
    /**
     * iniital player health
     */
    private int playerHealth = 2000;
    /**
     * level number for storing to file
     */
    private static int level = 0;



    /**
     *
     * the params just set to the corrisponding variable
     * @param pointstowin 
     * @param Difficuly
     * @param rndSeed
     * @param playerhealth
     * @param Level
     */
    public Level(int pointstowin, int Difficuly, int rndSeed, int playerhealth, int Level) {
        //uses more than one collision perframe
        setSimpleCollison(false);
        this.ScoretoWin = pointstowin;
        this.Difficuly = Difficuly;
        this.rndSeed = rndSeed;
        this.playerHealth = playerhealth;
        this.level = Level;
    }
    /**
     *
     * @return the current level index
     */
    public static int GetLevel() {
        return Level.level;
    }
    /**
     * defines all the objects used in the level
     */
    @Override
    public void init() {
        IDestroyableManager.Reset();
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
        play("/music/LevelMusic.wav", 0, -1);
    }

    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) { }

    /**
     *
     * @param gd
     */
    @Override
    public void Draw(Graphics2D gd) {  }

    /**
     *
     * @param ke the key that was pressed
     */
    @Override
    public void keyPress(KeyEvent ke) {
        //this is all for the player movement
        int code = ke.getKeyCode();
        if (code == KeyEvent.VK_W) {
            p.SetVerticalDirActive(1);
        } else if (code == KeyEvent.VK_S) {
            p.SetVerticalDirActive(-1);
        }
        if (code == KeyEvent.VK_A) {
            p.SetHorizontalDirActive(1);
        } else if (code == KeyEvent.VK_D) {
            p.SetHorizontalDirActive(-1);
        }
    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyRelease(KeyEvent ke) {
        int code = ke.getKeyCode();
        //this is all for the player  movement
        if (code == KeyEvent.VK_W) {
            p.SetVerticalDirUnactive(1);
        } else if (code == KeyEvent.VK_S) {
            p.SetVerticalDirUnactive(-1);
        }
        if (code == KeyEvent.VK_A) {
            p.SetHorizontalDirUnactive(1);
        } else if (code == KeyEvent.VK_D) {
            p.SetHorizontalDirUnactive(-1);
        }
    }

    /**
     *
     * @return the score to win used to compair if the player has won
     */
    public int getScoretoWin() {
        return ScoretoWin;
    }

    /**
     *
     * @param ScoretoWin sets the score to win
     */
    public void setScoretoWin(int ScoretoWin) {
        this.ScoretoWin = ScoretoWin;
    }
}
