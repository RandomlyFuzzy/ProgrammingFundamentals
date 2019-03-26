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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

/**
 * this is a delegate base button where the dev inputs the function they want to
 * call in the constructor and it does it in level
 * 
 * 
 * @author Liam Woolley 1748910
 */
public class Button extends IDrawable {

    /**
     * message that is displayed on the button
     */
    private String Message = "";
    /**
     * relative position on the sceen 0,0 is top left 1,1 is bottom right
     */
    private Vector relpos = Vector.One();
    /**
     * function wanted to be called when clicked on
     */
    private HUDdelegate buttonDelegate;


    /**
     *
     * @param relpos the position it will stay relative to the screen size
     * @param Message the text that will be displayed on the center of the button
     * @param Logic what runs when it is clicked
     */
    public Button(Vector relpos, String Message, HUDdelegate Logic) {
        super();
        //sets local variables and loads a default image
        this.Message = Message;
        this.buttonDelegate = Logic;
        this.relpos = relpos;
        GetSprite("/images/Button.png");
    }

    /**
     *
     */
    @Override
    public void init() {
        //sets the overall scale of the object (world scale is multiplyed by it so only needs to be set once
        setScale(new Vector(Game.ButtonDims()));
    }

    /**
     * contantly adjusts it position incase of a screen size chage
     */
    @Override
    public void doMove() {
        setPosition(new Vector(((Game.getScaledWidth())) * relpos.getX(), ((Game.getScaledHeight())) * relpos.getY()).add(new Vector(Transform.getOffsetTranslation()).mult(-1)));
    }

    /**
     * dras default image and draws text ontop
     * as well as a rectangle on top of the mouse is inside the button
     */
    @Override
    public void Update(Graphics2D g) {
        //draws last image
        DrawLastLoadedImage(g);
        //changes colour for the text
        g.setColor(new Color(255, 255, 255));
        //changes font size
        Font f = g.getFont();
        g.setFont(f.deriveFont(f.getSize() * (1 / Game.getWorldrelDims().getX()) * 1.3f));
        FontMetrics metrics = g.getFontMetrics();
        //draws the message centered to the button ( both x and y)
        g.drawString(Message, -metrics.stringWidth(Message) / 2, g.getFont().getSize() / 2);
        //if mouse is hovering over draw a coloured rect
        if (isColliding()) {
            Color c = g.getColor();
            g.setColor(new Color(200, 200, 200, 100));
            g.fillRect(-getSpriteWidth() / 2, -getSpriteHeight() / 2, getSpriteWidth(), getSpriteHeight());
            g.setColor(c);
        }
        g.setFont(f);
    }

    /**
     * this is where the delegate is running from
     */
    public void DoAction() {
        if (buttonDelegate != null) {
            buttonDelegate.OnClick(this);
        } else {
            System.err.println("error no delegate in this button");
        }
    }

    /**
     *
     * @param im
     */
    @Override
    public void onCollison(IDrawable im) {
        //if not a mouse then dont do anything with that collision
        if (!(im instanceof Mouse)) {
            setIsColliding(false);
        }
    }

    /**
     *
     * @return the current message displayed on the button
     */
    public String getMessage() {
        return Message;
    }

    /**
     *
     * @param Message sets the current message displayed on the button
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

}
