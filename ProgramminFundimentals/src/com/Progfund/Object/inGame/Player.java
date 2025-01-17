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
    private Vector speed = new Vector(150, 200);
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
     * handles the up down left and right keys being pressed / released
     */
    private boolean[] Dirs = new boolean[4];

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
    public void SetVerticalDirActive(int val) {
        if (val == 1) {
            Dirs[0] = true;
        }

        if (val == -1) {
            Dirs[1] = true;
        }
    }

    /**
     *
     * @param val horizontal axis set
     */
    public void SetHorizontalDirActive(int val) {
        if (val == 1) {
            Dirs[2] = true;
        }

        if (val == -1) {
            Dirs[3] = true;
        }
    }

    /**
     *
     * @param val vertical axis unset
     */
    public void SetVerticalDirUnactive(int val) {
        if (val == 1) {
            Dirs[0] = false;
        }

        if (val == -1) {
            Dirs[1] = false;
        }
    }

    /**
     *
     * @param val horizontal axis unset
     */
    public void SetHorizontalDirUnactive(int val) {
        if (val == 1) {
            Dirs[2] = false;
        }

        if (val == -1) {
            Dirs[3] = false;
        }
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

            //decides what directions should be taken
            int vertical = Dirs[0] == Dirs[1] ? 0 : Dirs[0] ? 1 : Dirs[1] ? -1 : 0;
            int horizontal = Dirs[2] == Dirs[3] ? 0 : Dirs[2] ? 1 : Dirs[3] ? -1 : 0;
            //adds to the player current postion (Movement of player)
            addPosition(
                    //gets the relative up vector and scales apprioratly
                    new Vector(new Vector(0, -1)
                            .mult(vertical * speed.getY() * 2f)
                            //adds the right vector
                            .add(
                                    new Vector(1, 0)
                                            //scales apprioratly
                                            .mult(-horizontal * speed.getX() * 2f)))
                            //scales by the game speed so it stays constant move rate
                            .mult(Game.getDelta()));

            //for firing the guns 
            // if mouse is being pressed and/or moved
            if ((Level().isClicking() || Level().isDragging())) {
                //left click
                if (Level().GetMouseButtonDown(1)) {
                    //fires gun
                    pistol.fire();
                } else //right click
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
                Level().play("/music/Hit_Hurt.wav");
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
                        .mult((float) new Vector(id.getSpriteWidth(), id.getSpriteHeight()).Lengthsqrt());
        //decides what directions should be taken
        int vertical = Dirs[0] == Dirs[1] ? 0 : Dirs[0] ? 1 : Dirs[1] ? -1 : 0;
        int horizontal = Dirs[2] == Dirs[3] ? 0 : Dirs[2] ? 1 : Dirs[3] ? -1 : 0;
        //reversing the direction moving vector to push out
        relpos.add(
                new Vector(new Vector(0, -1)
                        .mult(vertical * -speed.getY() * 2f)
                        .add(new Vector(1, 0)
                                .mult(-horizontal * -speed.getX() * 2f)))).mult(Game.getDelta());
        //translates the player
        addPosition(relpos);

    }

}
