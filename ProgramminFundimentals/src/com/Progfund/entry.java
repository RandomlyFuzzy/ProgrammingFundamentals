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
import com.Progfund.Levels.LevelSelect;
import com.Progfund.Levels.LevelSelecttimed;
import com.Progfund.Levels.MainMenu;

/**
 * 
 * this is the first script that is ran on the application 
 * it creates the game window from my engine and sets up relative things about it
 * @author Liam Woolley 1748910
 */
public class entry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //sets the possable levels to be entered that doesn have arguments
        LevelLoader.LL.SetLevels(new ILevel[]{new MainMenu(),new LevelSelect(),new LevelSelecttimed(),new Leaderboard()});
        //creates the game window and sets up backend and sets the first level to be the MainMenu
        new Game(new MainMenu());
        //set the F10 shortcut to go back to the mainmenu
        Game.setDefualtLevel(new MainMenu());
        //dev definied scale
        Game.setWorldrelDims(new Vector(1,1));
        //sets the window title
        Game.GetFrame().setTitle("Brand brawl");
    }

}
