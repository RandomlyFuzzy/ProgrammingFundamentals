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
import java.awt.Graphics2D;

/**
 *
 * basic enemy just rotates towards player moves in that direction and shoots
 * (but surprisingly deadly)
 *
 * @author Liam Woolley 1748910
 */
public class Enemy extends IDestroyable {


    /**
     * this is magnitude speed of the movement vector
     */
    private float speed = 140;
    
    /**
     * this is the gun that shoots bullets
     */
    private Gun gun = new Pistol(this,0.3f, 12);

    /**
     *
     * @param health is the health of the enemy is set to
     */
    public Enemy(int health) {
        super(health);
    }

    /**
     *
     */
    @Override
    public void init() {
        //loads the enemy image
        GetSprite("/images/enemy.png");
    }

    /**
     *
     */
    @Override
    public void doMove() {
        // if game over destroy self and remove hash from manager
        if (LevelOverOverlay.isFinished()) {
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
        }

        //if health is less than 0 destroy self, add score, add partical and remove hash reference from the manager
        if (getHealth() <= 0) {
            ParticalGenerator.add(this);
            Player.addScore(getScore());
            Level().RemoveObject(this);
            IDestroyableManager.remove(this);
            return;
        }
        //checks to see if outsides a range from the screen 
        //###
        //#S#
        //###
        //then destroy self and remove reference so can be respawned
        if (!((-Transform.getOffsetTranslation().getX() - (Game.getScaledWidth()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth() * 2)) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY() - (Game.getScaledHeight())) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight() * 2)) > getPosition().getY()))) {
            IDestroyableManager.remove(this);
            Level().RemoveObject(this);
            return;
        } //else rotate towards center of the screen(player pos)
        //move upwards
        else {
            Vector relpos = new Vector(getPosition()).mult(1).add(new Vector(Transform.getOffsetTranslation()).mult(1).add(new Vector(Game.getScaledWidth() / 2, Game.getScaledHeight() / 2).mult(-1)));
            setRotation(Math.atan2(relpos.getY(), relpos.getX()) - Math.PI / 2);
            addPosition(new Vector(GetUp()).mult(speed).mult(Game.getDelta()));
        }
        //if is on the screen then shoot
        if (((-Transform.getOffsetTranslation().getX()) < getPosition().getX()
                && (-Transform.getOffsetTranslation().getX() + (Game.getScaledWidth())) > getPosition().getX()
                && (-Transform.getOffsetTranslation().getY()) < getPosition().getY()
                && (-Transform.getOffsetTranslation().getY() + (Game.getScaledHeight())) > getPosition().getY())) {

            gun.fire();
        }
    }

    /**
     *
     * @param gd
     */
    @Override
    public void Update(Graphics2D gd) {
        //draw enemy sprite
        DrawLastLoadedImage(gd);
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
    }

}
