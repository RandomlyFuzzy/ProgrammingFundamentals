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
 * identical to the Level class but has a limit 
 * 
 * @author Liam Woolley 1748910
 */
public class timedLevel extends Level {

    /**
     * 
     * amount of time to count down from
     */
    private static float totalTime = 10;
    /**
     * used to add the levelOverOvey once and only once
     */
    private boolean doneonce = false;
    /** 
     * stores the hud index
     */
    private int hudIndex = 0;

    /**
     * the params just set to the corrisponding variable in this class and inherited class
     * @param pointstowin
     * @param Difficuly
     * @param rndSeed
     * @param playerhealth
     * @param totalTime
     * @param Level
     */
    public timedLevel(int pointstowin, int Difficuly, int rndSeed, int playerhealth, float totalTime, int Level) {
        super(pointstowin, Difficuly, rndSeed, playerhealth, Level);
        this.totalTime = totalTime;
    }

    /**
     * just does the same as Level class but adds a hud an a ref to a string to display
     */
    @Override
    public void init() {
        super.init();
        AddObject(new HUD());
        hudIndex = HUD.AddText("", new Vector(Game.getWindowWidth()/2-25,50));
    }

    
    /**
     * check if elapsed time is greater than total time given
     * @param ae timer event
     */
    @Override
    public void Update(ActionEvent ae) {
        if (getTime()>totalTime&&!doneonce) {
            AddObject(new LevelOverOverlay()).setIsCollidable(false);
            doneonce = true;
        }
        HUD.EditText(hudIndex,""+String.format("%.2f",(totalTime-getTime())));
    }

    /**
     *
     * @param gd
     */
    @Override
    public void Draw(Graphics2D gd) {
    }

    /**
     *  just does the update in the parent class
     * @param ke key event 
     */
    @Override
    public void keyPress(KeyEvent ke) {
        super.keyPress(ke);

    }

    /**
     * just does the update in the parent class
     * @param ke
     */
    @Override
    public void keyRelease(KeyEvent ke) {
        super.keyRelease(ke);
    }

    /**
     * dispose of the static just incase
     */
    public void dispose(){
        totalTime  =0 ;        
    }

}
