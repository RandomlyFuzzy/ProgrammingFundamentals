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
import com.Progfund.Object.inGame.gunComponents.Gun;
import com.Progfund.Object.inGame.gunComponents.Pistol;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Liam Woolley 1748910
 */
public class Player extends IDestroyable {

    /**
     * the value that is added to Velocity each frame
     */
    private Vector Accerlation;
    /**
     * the amount that is added to the position each frame(multiplyed by delta time)
     */
    private Vector Velocity;
    /**
     * speed scaler of inputs
     */
    private Vector speed = new Vector(300, 400);
    private int horizontal = 0;
    private vertical = 0;
    private int damage = 25;
    private static int Score = 0;
    private static int scoreNeeded = 0;
    private Gun gun = new Pistol(this, 0.1f,damage);

    /**
     *
     * @param health
     * @param scoreneeded
     */
    public Player(int health, int scoreneeded) {
        super(health);
        Accerlation = new Vector(0, 0);
        Velocity = new Vector(0, 0);
        Score = 0;
        scoreNeeded = scoreneeded;
    }

    /**
     *
     */
    @Override
    public void init() {
        GetSprite("/images/player.png");
        SetHashParams();
    }

    /**
     *
     * @param val
     */
    public void SetVerticalDir(int val) {
        vertical = val;
    }

    /**
     *
     * @param val
     */
    public void SetHorizontalDir(int val) {
        horizontal = val;
    }

    /**
     *
     * @return
     */
    public static int getScoreNeeded() {
        return scoreNeeded;
    }

    /**
     *
     * @param scoreNeeded
     */
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

    /**
     *
     * @param Score
     */
    public static void setScore(int Score) {
        Player.Score = Score;
    }

    /**
     *
     * @param Score
     */
    public static void addScore(int Score) {
        Player.Score += Score;
    }

    /**
     *
     */
    @Override
    public void doMove() {
//        setHealth(getMaxHealth());
        if (!LevelOverOverlay.isFinished()) {
            Vector relpos = new Vector(Level().getMousePos()).add(new Vector(Game.getWindowWidth() / -2, Game.getWindowHeight() / -2));
            setRotation(Math.atan2(relpos.getX(), -relpos.getY()));
            addPosition(
                    new Vector(new Vector(GetUp()).mult(vertical * speed.getY())
                            .add(new Vector(GetRight()).mult(-horizontal * speed.getX())))
                            .mult(Game.getDelta()));
//        addPosition(new Vector(new Vector(0,-1).mult(vertical).add(new Vector(1,0).mult(-horizontal))));

            if ((Level().isClicking() || Level().isDragging())) {
                gun.fire();
            }
            Transform.setOffsetTranslation(new Vector(getPosition()).mult(-1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2)));
            if (getHealth() <= 0) {
                Level().AddObject(new LevelOverOverlay()).setIsCollidable(false);
            }
        }

    }

    /**
     *
     * @param gd
     */
    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
        gd.setColor(Color.red);
        gd.drawString("" + Level().GetObjectCount(), 0, -40);
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
        if (!(id instanceof IDestroyable)) {
            return;
        }
        Vector relpos
                = new Vector(id.getPosition())
                        .mult(-1)
                        .add(new Vector(getPosition()))
                        .mult(new Vector(id.getScaledSpriteWidth(), id.getScaledSpriteHeight()))
                        .Normalized();
        relpos.add(
                new Vector(new Vector(GetUp())
                        .mult(vertical * -speed.getY() * 2f)
                        .add(new Vector(GetRight())
                                .mult(-horizontal * -speed.getX() * 2f)))).mult(Game.getDelta());

        addPosition(relpos);

    }

}
