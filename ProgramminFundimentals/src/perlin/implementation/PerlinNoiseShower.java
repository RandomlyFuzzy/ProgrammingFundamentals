/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perlin.implementation;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Entry.Game;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author RandomlyFuzzy
 */
public class PerlinNoiseShower extends ILevel {

    @Override
    public void init() {
    }

    @Override
    public void Update(ActionEvent ae) {

    }
    double[] Cuttoffs = {0.74, 0.715, 0.485, 0.45, 0.375, 0.35, 0.325, 0.3, 0.275, 0.25, 0.225, 0.2, 0};
    Color[] CuttoffColours = {Color.green, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.green, Color.ORANGE, Color.blue, Color.ORANGE, Color.blue, Color.ORANGE, Color.blue, Color.ORANGE, Color.blue, Color.black};

    //perlin scalers
    double ScaleFactor = 2;
    double streach = 2;
    double Magnification = 400;

    @Override
    public void Draw(Graphics2D g) {
        float x = getMousePos().getX() * (float) ScaleFactor;
        float y = getMousePos().getY() * (float) ScaleFactor;
        for (double j = 0.0; j < (double) Game.getWindowHeight() / (streach / ScaleFactor); j += ScaleFactor * streach) {
            for (double i = 0.0; i < (double) Game.getWindowWidth() / (streach / ScaleFactor); i += ScaleFactor * streach) {
                double noise = PerlinUtil.noise((i * streach) / Magnification + x / 10, (j * streach) / Magnification + y / 10, (Math.cos(getTime() * 2)) / 10.0);
                noise += 2.0;
                noise /= 4.0;
//                noise *= 4.0;
//                noise = Math.floor(noisse);
                for (int k = 0; k < Cuttoffs.length; k++) {
                    double f = Cuttoffs[k];
                    if (noise >= f) {
                        g.setColor(CuttoffColours[k]);
                        break;
                    }
                }
//                   g.setColor(new Color((float)noise,(float)noise,(float)noise));
//                noise /= 4;
//                g.setColor(new Color((float) noise, (float) noise, (float) noise));

                g.fillRect((int) (i * (streach / ScaleFactor)), (int) (j * (streach / ScaleFactor)), (int) (ScaleFactor * streach), (int) (ScaleFactor * streach));
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
