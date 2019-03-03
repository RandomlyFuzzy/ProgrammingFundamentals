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
    double inc = 5;
    double streach = 5;
    double Magnification = 100;

    @Override
    public void Draw(Graphics2D g) {
        float x = getMousePos().getX();
        float y = getMousePos().getY();
        for (double i = 0.0; i < (double) Game.getWindowWidth()/(inc/streach); i += inc*streach) {
            for (double j = 0.0; j < (double) Game.getWindowHeight()/(inc/streach); j += inc*streach) {
                double noise = PerlinUtil.noise((i*streach) / Magnification+x/10, (j*streach) / Magnification+y/10, 255.0);
                noise += 1.0;
                noise /= 2.0;
                noise *=4.0;
                noise = Math.floor(noise);
                if(noise == 1){
                    noise = 0;
                }
                if(noise == 2){
                    noise = 3;
                }
                noise /=3.0;
                
                g.setColor(new Color((float) noise, (float) noise, (float) noise));
                g.fillRect((int) (i*inc /streach), (int) (j * inc/streach), (int) (inc * streach), (int) (inc *streach));
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
