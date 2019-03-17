/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.LevelLoader;
import com.Progfund.Levels.Leaderboard;
import com.Progfund.Levels.Level;
import com.Progfund.Levels.MainMenu;

/**
 *
 * @author RandomlyFuzzy
 */
public class entry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LevelLoader.LL.SetLevels(new ILevel[]{new MainMenu(),new Leaderboard()});
        new PerlinUtil();
        new Game(new MainMenu());
        Game.setDefualtLevel(new MainMenu());
        Game.setWorldrelDims(new Vector(1,1));
//        Game.toggleCursor();
        Game.GetFrame().setTitle("Brand brawl");
    }

}
