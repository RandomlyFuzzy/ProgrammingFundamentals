/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perlin.implementation;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.LevelLoader;

/**
 *
 * @author RandomlyFuzzy
 */
public class entry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LevelLoader.LL.SetLevels(new ILevel[]{new PerlinNoiseShower()});
        new PerlinUtil();
        new Game(new PerlinNoiseShower());
        Game.toggleCursor();
        Game.GetFrame().setTitle("Brand brawl");
    }

}
