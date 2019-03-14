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
import com.Progfund.Object.inGame.LevelGenerator;
import com.Progfund.Object.inGame.Player;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author RandomlyFuzzy
 */
public class Leaderboard extends ILevel {

    @Override
    public void init() {
        setBackground(Color.BLACK);
        Game.setWorldrelDims(new Vector(1, 1));
        AddObject(new Mouse()).setIsCollidable(false);
    }

    @Override
    public void Update(ActionEvent ae) {
    }

    @Override
    public void Draw(Graphics2D gd) {
        gd.setColor(Color.red);
        Vector pos = (new Vector(10, Game.getWindowHeight() - 50));
    }

    @Override
    public void keyPress(KeyEvent ke) {

    }

    @Override
    public void keyRelease(KeyEvent ke) {

    }

}
