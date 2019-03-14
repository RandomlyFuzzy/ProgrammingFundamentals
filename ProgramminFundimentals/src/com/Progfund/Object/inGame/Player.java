/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.inGame;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Transform;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Progfund.Object.Menu.LevelOverOverlay;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author RandomlyFuzzy
 */
public class Player extends IDrawable {

    private Vector Accerlation;
    private Vector Veclocity;
    private Vector speed = new Vector(3, 4);
    private double Delay = 0.1f, current = 0;

    private int horizontal = 0, vertical = 0;

    public Player() {
        super();
        Accerlation = new Vector(0, 0);
        Veclocity = new Vector(0, 0);

    }

    @Override
    public void init() {
        GetSprite("/images/player.png");

    }

    public void SetVerticalDir(int val) {
        vertical = val;
    }

    public void SetHorizontalDir(int val) {
        horizontal = val;
    }

    private boolean hasSpawned = false;

    @Override
    public void doMove() {
        if (!LevelOverOverlay.isFinished()) {

            Vector relpos = new Vector(Level().getMousePos()).add(new Vector(Game.getWindowWidth() / -2, Game.getWindowHeight() / -2));
            setRotation(Math.atan2(relpos.getX(), -relpos.getY()));
            addPosition(new Vector(new Vector(GetUp()).mult(vertical * speed.getY()).add(new Vector(GetRight()).mult(-horizontal * speed.getX()))));
//        addPosition(new Vector(new Vector(0,-1).mult(vertical).add(new Vector(1,0).mult(-horizontal))));

            if ((Level().isClicking() || Level().isDragging()) && (current + Delay) <= Level().getTime()) {
                Level().AddObject(new Bullet(new Vector(getPosition()), getRotation()));
                current = Level().getTime();
            }
        }
        Transform.setOffsetTranslation(new Vector(getPosition()).mult(-1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2)));
    }

    @Override
    public void Update(Graphics2D gd) {

        DrawLastLoadedImage(gd);
    }

    @Override
    public void onCollison(IDrawable id) {
    }

}
