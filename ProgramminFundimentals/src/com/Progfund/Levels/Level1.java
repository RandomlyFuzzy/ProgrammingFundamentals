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
public class Level1 extends ILevel {

    private Player p;
    private int ScoretoWin = 300;

    public Level1() {
        setSimpleCollison(false);
    }

    @Override
    public void init() {
        Game.setWorldrelDims(new Vector(0.6f, 0.6f));
        AddObject(new LevelGenerator(2,5));
        p = new Player(1000,30);
        p.setPosition(100.1f, 200f);
        AddObject(p);
        AddObject(new OverLay()).setIsCollidable(false);
        Mouse m = new Mouse();
        m.setScale(new Vector(4, 4));
        AddObject(m).setIsCollidable(false);
        AddObject(new ParticalGenerator());

//        AddObject(new Bullet(new Vector(new Vector(70,0)), -Math.PI/2, 1000));
    }

    @Override
    public void Update(ActionEvent ae) {
    }

    @Override
    public void Draw(Graphics2D gd) {
    }

    @Override
    public void keyPress(KeyEvent ke) {
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

    public int getScoretoWin() {
        return ScoretoWin;
    }

    public void setScoretoWin(int ScoretoWin) {
        this.ScoretoWin = ScoretoWin;
    }
}
