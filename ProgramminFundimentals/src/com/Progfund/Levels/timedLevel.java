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
import com.Progfund.Object.inGame.HUD;
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
public class timedLevel extends Level {

    private static float TimeLeft = 10;
    private boolean doneonce = false;
    private int hudIndex = 0;
    /**
     *
     * @param pointstowin
     * @param Difficuly
     * @param rndSeed
     * @param playerhealth
     * @param Level
     */
    public timedLevel(int pointstowin, int Difficuly, int rndSeed, int playerhealth, float endTime, int Level) {
        super(pointstowin, Difficuly, rndSeed, playerhealth, Level);
        TimeLeft = endTime;
    }

    /**
     *
     */
    @Override
    public void init() {
        super.init();
        AddObject(new HUD());
        hudIndex = HUD.AddText("", new Vector(Game.getWindowWidth()/2-25,50));
    }

    
    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) {
        if (getTime()>TimeLeft&&!doneonce) {
            AddObject(new LevelOverOverlay()).setIsCollidable(false);
            doneonce = true;
        }
        HUD.EditText(hudIndex,""+String.format("%.2f",(TimeLeft-getTime())));
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
        super.keyPress(ke);

    }

    /**
     *
     * @param ke
     */
    @Override
    public void keyRelease(KeyEvent ke) {
        super.keyRelease(ke);

    }

}
