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
public class Player extends IDestroyable {

    private Vector Accerlation;
    private Vector Veclocity;
    private Vector speed = new Vector(3, 4);
    private double Delay = 0.1f, current = 0;
    private boolean hasSpawned = false;
    private int horizontal = 0, vertical = 0;
    private boolean HasFinished = false;
    private int damage = 25;
    private static int Score = 0;
    private static int scoreNeeded = 0;

    public Player(int health, int scoreneeded) {
        super(health);
        Accerlation = new Vector(0, 0);
        Veclocity = new Vector(0, 0);
        Score = 0;
        scoreNeeded = scoreneeded;
    }

    @Override
    public void init() {
        GetSprite("/images/player.png");
        SetHashParams();
    }

    public void SetVerticalDir(int val) {
        vertical = val;
    }

    public void SetHorizontalDir(int val) {
        horizontal = val;
    }

    public static int getScoreNeeded() {
        return scoreNeeded;
    }

    public static void setScoreNeeded(int scoreNeeded) {
        Player.scoreNeeded = scoreNeeded;
    }

    /**
     *
     * @return
     */
    public static int getPlayerScore() {
        return Player.Score;
    }

    public static void setScore(int Score) {
        Player.Score = Score;
    }

    public static void addScore(int Score) {
        Player.Score += Score;
    }

    @Override
    public void doMove() {

        if (!LevelOverOverlay.isFinished()) {

            Vector relpos = new Vector(Level().getMousePos()).add(new Vector(Game.getWindowWidth() / -2, Game.getWindowHeight() / -2));
            setRotation(Math.atan2(relpos.getX(), -relpos.getY()));
            addPosition(new Vector(new Vector(GetUp()).mult(vertical * speed.getY()).add(new Vector(GetRight()).mult(-horizontal * speed.getX()))));
//        addPosition(new Vector(new Vector(0,-1).mult(vertical).add(new Vector(1,0).mult(-horizontal))));

            if ((Level().isClicking() || Level().isDragging()) && (current + Delay) <= Level().getTime()) {
                Level().AddObject(new Bullet(new Vector(getPosition()), getRotation(), damage));
                current = Level().getTime();
            }
            Transform.setOffsetTranslation(new Vector(getPosition()).mult(-1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2)));
            if (getHealth() <= 0) {
                Level().AddObject(new LevelOverOverlay()).setIsCollidable(false);
            }
        }

    }

    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    @Override
    public void onCollison(IDrawable id) {
    }

}
