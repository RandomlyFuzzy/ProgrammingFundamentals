/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.Menu;

import com.Liamengine.Engine.Components.Transform;
import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Components.Vector;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

/**
 *
 * @author Liam Woolley 1748910
 */
public class Button extends IDrawable {

    private String Message = "";
    private Vector relpos = Vector.One();
    private HUDdelegate buttonDelegate;

    public Button() {
        super();
        UseTransforms(false);

    }

    public Button(String Message, HUDdelegate Logic) {
        super();
        this.Message = Message;
        buttonDelegate = Logic;
    }

    public Button(Vector relpos, String Message, HUDdelegate Logic) {
        super();
        this.Message = Message;
        this.buttonDelegate = Logic;
        this.relpos = relpos;
        GetSprite("/Images/Button.png");
    }

    @Override
    public void init() {

    }

    @Override
    public void doMove() {
        setPosition(new Vector(((Game.getScaledWidth())) * relpos.getX(), ((Game.getScaledHeight())) * relpos.getY()).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
        setScale(Game.ButtonDims());
    }

    @Override
    public void Update(Graphics2D g) {
        DrawLastLoadedImage(g);
        g.setColor(new Color(128, 128, 0));
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(Message, -metrics.stringWidth(Message) / 2, g.getFont().getSize() / 2);
        if (isColliding()) {
            Color c = g.getColor();
            g.setColor(new Color(200, 200, 200, 100));
            g.fillRect(-getSpriteWidth() / 2, -getSpriteHeight() / 2, getSpriteWidth(), getSpriteHeight());
            g.setColor(c);
        }
    }

    public void DoAction() {
        if (buttonDelegate != null) {
            buttonDelegate.OnClick(this);
        } else {
            System.err.println("error no delegate in this button");
        }
    }

    @Override
    public void onCollison(IDrawable im) {
        if (!(im instanceof Mouse)) {
            setIsColliding(false);
        }
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

}
