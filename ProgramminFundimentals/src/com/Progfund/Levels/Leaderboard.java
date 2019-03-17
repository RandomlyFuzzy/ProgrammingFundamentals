/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Levels;

import com.Liamengine.Engine.AbstractClasses.ILevel;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.FileUtils;
import com.Progfund.Object.Menu.*;
import com.Progfund.Object.inGame.LevelGenerator;
import com.Progfund.Object.inGame.Player;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author RandomlyFuzzy
 */
public class Leaderboard extends ILevel {

    private String previousind = "";
    private static String Currentind = "Level1";
    private ArrayList<String> times = new ArrayList<String>();

    /**
     *
     * @return
     */
    public static String getCurrentind() {
        return Currentind;
    }

    /**
     *
     * @param Currentind
     */
    public static void setCurrentind(String Currentind) {
        Leaderboard.Currentind = Currentind;
    }

    /**
     *
     */
    public Leaderboard() {
        super();
        setStopAudioOnStart(false);
    }

    /**
     *
     */
    @Override
    public void init() {
        GetSprite("/Images/backgrounds/background1.png");

        for (int i = 0; i < 20; i++) {
            Button b = new Button(new Vector(((0.09f * (i % 10)) + 0.095f), ((0.075f * (i / 10)) + 0.05f)), "Level" + ((i + 1)), new HUDdelegate() {
                public void OnClick(Button b) {
                    Leaderboard.setCurrentind(b.getMessage());
                }
            });
            AddObject(b);
            b.setScale(new Vector(0.4f, 0.5f));
        }
        AddObject(new Button(new Vector(0.86f, 0.9f), "Back", new HUDdelegate() {
            @Override
            public void OnClick(Button b) {
                Game.SetLevelActive(new MainMenu());
            }
        }));
        AddObject(new Mouse());
    }

    /**
     *
     * @param ae
     */
    @Override
    public void Update(ActionEvent ae) {
        if (previousind != Currentind) {
            System.out.println("com.Progfund.Levels.Leaderboard.Update() loading " + Currentind);
            times = new ArrayList<String>();
            times.addAll(
                    FileUtils.GetFileSplit("Resources/savedata/" + Currentind + ".txt", "\n", true)
            );
            System.out.println("com.Progfund.Levels.Leaderboard.Update() with " + times.size()+" entrys");

            times.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (o2.equals(new String())) {
                        return -1;
                    }
                    if (o1.equals(new String())) {
                        return 1;
                    }
                    String val1 = o1.substring(o1.indexOf(":") + 1).trim();
                    String val2 = o2.substring(o2.indexOf(":") + 1).trim();

                    return Double.parseDouble(val2) > Double.parseDouble(val1) ? 1 : -1;
                }
            });
            if (times.size() > 5) {
                String set = "";
                for (int i = 0; i < 5; i++) {
                    set += times.get(i) + "\n";
                }
                FileUtils.SetFileContence("Resources/savedata/" + Currentind + ".txt", set);
            }
            previousind = Currentind;
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void Draw(Graphics2D g) {
        //g.drawImage(GetSprite("/Images/backgrounds/background1.png"), Game.getWindowWidth(), 0, (Game.getWindowWidth() * -1), (Game.getWindowHeight()), null);
        float y = 0.3f;
        Font f = g.getFont();
        Font f2 = f.deriveFont(1, Game.WorldScale().getY() * 13);
        g.setFont(f2);
        if (times.size() != 0&&!times.get(0).equals(new String())) {
            g.setColor(new Color(55, 55, 55, 150));

            g.setFont(f.deriveFont(1, f.getSize() + (Game.WorldScale().getY() * ((int) Math.pow(15 - 0, 2) / 10))));
            String s = times.get(0);
            String[] split = s.split(":");
            String str = "No " + (1) + " Place is " + split[0] + " with " + split[1] + " points";
            int w = (int) (g.getFontMetrics().stringWidth(str) * 1.05f);
            g.fillRect(
                    (int) ((Game.getWindowWidth() / 2) - w / 2),
                    (int) ((0.205f) * Game.getWindowHeight()),
                    (int) (w),
                    (int) (0.120f * Game.getWindowHeight()*(times.size() >= 5 ? 5 : times.size())));
            w = (int) (g.getFontMetrics().stringWidth(Currentind) * 1.05f);
            g.drawString(Currentind, (Game.getWindowWidth() / 2 - (w / 2)), y);

            g.setColor(Color.WHITE);
            int inc = 0;
            for (int i = 0; i < (times.size() >= 5 ? 5 : times.size()); i++) {
                g.setFont(f.deriveFont(1, f.getSize() + (Game.WorldScale().getY() * ((int) Math.pow(15 - i, 2) / 10))));
                s = times.get(i);
                split = s.split(":");
                str = "No " + (i + 1) + " Place is " + split[0] + " with " + split[1] + " score";
                w = g.getFontMetrics().stringWidth(str);
                g.drawString(str,
                        Game.getWindowWidth() / 2 - w / 2,
                        (((i % 20) * 0.12f) + 0.28f) * Game.getWindowHeight());
                y += 0.03f;
            }
        }
        g.setFont(f);
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPress(KeyEvent e) {

    }

    /**
     *
     * @param e
     */
    @Override
    public void keyRelease(KeyEvent e) {

    }

    /**
     *
     */
    public void dispose() {
        super.dispose();
        Currentind = "Level2Solo";
        previousind = "Level1Solo";
        times = new ArrayList<String>();
    }

}
