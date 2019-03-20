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
import com.Progfund.Object.inGame.gunComponents.shotgun;
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
     * the amount that is added to the position each frame(multiplyed by delta
     * time)
     */
    private Vector Velocity;
    /**
     * speed scaler of inputs
     */
    private Vector speed = new Vector(300, 400);
    /**
     * Left Right Axis reference
     */
    private int horizontal = 0;
    /**
     * Up Down Axis reference
     */
    private int vertical = 0;
    /**
     * damage the bullets do
     */
    private int damage = 25;
    /**
     * current player score
     */
    private static int Score = 0;
    /**
     * score that is needed to win
     */
    private static int scoreNeeded = 0;
    /**
     * the pistol that fires a single bullet at a time
     */
    private Gun pistol = new Pistol(this, 0.1f, damage);
    /**
     * the shotgun that fires a 3 bullets at a time
     */
    private Gun shotgun = new shotgun(this, 0.3f, damage);

    /**
     *
     * @param health base health and max health to be set to
     * @param scoreneeded local storage of score needed
     */
    public Player(int health, int scoreneeded) {
        super(health);
        Accerlation = new Vector(0, 0);
        Velocity = new Vector(0, 0);
        Score = 0;
        scoreNeeded = scoreneeded;
    }

    /**
     * sets the hash for continuity and gets the default image for the player
     */
    @Override
    public void init() {
        GetSprite("/images/player.png");
        SetHashParams();
    }

    /**
     *
     * @param val vertical axis set
     */
    public void SetVerticalDir(int val) {
        vertical = val;
    }

    /**
     *
     * @param val horrizontal axis set
     */
    public void SetHorizontalDir(int val) {
        horizontal = val;
    }

    /**
     *
     * @return the score needed for the level to be won by the player
     */
    public static int getScoreNeeded() {
        return scoreNeeded;
    }

    /**
     *
     * @return currently achieved player score
     */
    public static int getPlayerScore() {
        return Player.Score;
    }

    /**
     *
     * @param Score adds to the current player score
     */
    public static void addScore(int Score) {
        Player.Score += Score;
    }

    /**
     * moving the player and lets the player shot
     */
    @Override
    public void doMove() {
        if (!LevelOverOverlay.isFinished()) {
            //gets the relative position of the mouse to the player (offsets mouse by half screen width and height gets the angle from that vector)
            //because the player is in the middle
            Vector relpos = new Vector(Level().getMousePos()).add(new Vector(Game.getWindowWidth() / -2, Game.getWindowHeight() / -2));
            //sets the rotation from that vector
            setRotation(Math.atan2(relpos.getX(), -relpos.getY()));
            //adds to the player current postion (Movement of player)
            addPosition(
                    new Vector(
                        //gets the relative up vector and scales apprioratly
                        new Vector(GetUp()).mult(vertical * speed.getY())
                        //adds the relative right vector
                        .add(
                        //scales apprioratly
                        new Vector(GetRight()).mult(-horizontal * speed.getX())))
                        //scales by the game speed so it stays constant move rate
                        .mult(Game.getDelta()));

            //for firing the guns 
            // if mouse is being pressed and/or moved
            if ((Level().isClicking() || Level().isDragging())) {
                //left click
                if (Level().GetMouseButtonDown(1)) {
                    //fires gun
                    pistol.fire();
                } else 
                //right click
                if (Level().GetMouseButtonDown(3)) {
                    //fires gun
                    shotgun.fire();
                }
            }
            //sets the player to be in the center of the screen by offsetting everything by negative its relative position to 0 then centers to screen
            Transform.setOffsetTranslation(new Vector(getPosition()).mult(-1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2)));
            //game over
            if (getHealth() <= 0) {
                //adds the hud for when the game ends
                Level().AddObject(new LevelOverOverlay()).setIsCollidable(false);
            }
        }

    }

    /**
     *
     * @param gd draws player image
     */
    @Override
    public void Update(Graphics2D gd) {
        DrawLastLoadedImage(gd);
    }

    /**
     *
     * @param id check for collisons with objects and pushes out 
     */
    @Override
    public void onCollison(IDrawable id) {
        if (!(id instanceof IDestroyable)) {
            return;
        }
        //slow push out
        Vector relpos
                = new Vector(id.getPosition())
                        .mult(-1)
                        .add(new Vector(getPosition()))
                        .mult(new Vector(id.getScaledSpriteWidth(), id.getScaledSpriteHeight()))
                        .Normalized()
                        .mult((float)new Vector(id.getSpriteWidth(),id.getSpriteHeight()).Lengthsqrt());
        //reversing the direction moving vector to push out
        relpos.add(
                new Vector(new Vector(GetUp())
                        .mult(vertical * -speed.getY() * 2f)
                        .add(new Vector(GetRight())
                                .mult(-horizontal * -speed.getX() * 2f)))).mult(Game.getDelta());
        //translates the player
        addPosition(relpos);

    }

}
