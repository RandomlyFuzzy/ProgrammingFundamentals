/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Progfund.Object.Menu;

import com.Liamengine.Engine.AbstractClasses.IDrawable;
import com.Liamengine.Engine.Components.Vector;
import com.Liamengine.Engine.Entry.Game;
import com.Liamengine.Engine.Utils.FileUtils;
import com.Liamengine.Engine.Utils.LevelLoader;
import com.Progfund.Object.inGame.Player;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author RandomlyFuzzy
 */
public class LevelOverOverlay extends IDrawable {

    private static boolean finished = false;
    private static boolean saved = false;
    private static Button saveButton;

    public static Button getSaveButton() {
        return saveButton;
    }

    public static boolean isFinished() {
        return finished;
    }

    public static void setFinished(boolean finished) {
        LevelOverOverlay.finished = finished;
    }

    public static boolean hassaved() {
        return saved;
    }

    public static void setsaved(boolean saved) {
        LevelOverOverlay.saved = saved;
    }

    @Override
    public void init() {
        setFinished(true);
        Level().AddObject(new Button(new Vector(0.25f, 0.8f), "replay", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(b.Level());
                System.out.println(".OnClick()");
            }
        }));
        saveButton = new Button(new Vector(0.5f, 0.8f), "save score", new HUDdelegate() {
            public void OnClick(Button b) {
                if (LevelOverOverlay.hassaved()) {
                    return;
                }

                JButton button = new JButton("click me");
                JTextField text = new JTextField("Please enter your save name");
                JLabel lable = new JLabel("Please input name");
                JFrame frame = new JFrame();
                JPanel panel = new JPanel();

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(false);
                        if (text.getText().trim().equals("Please enter your save name")) {
                            return;
                        }

                        String file = LevelOverOverlay.getSaveButton().Level().getClass().getSimpleName();
                        System.out.println(text.getText().trim());
                        LevelOverOverlay.setsaved(true);
                        LevelOverOverlay.getSaveButton().setMessage("Saved as " + text.getText().trim());
                        FileUtils.AppendToFile("resources/saveData/" + file + ".txt", text.getText().trim() + ":" + Player.getScore() + "\n");
                    }
                });

                frame.setBounds(Game.GetFrame().getX() + (int) (Game.GetFrame().getWidth() * 0.4f), Game.GetFrame().getY() + (int) (Game.GetFrame().getHeight() * 0.45f), Game.GetFrame().getWidth(), Game.GetFrame().getHeight());
                panel.add(lable);
                panel.add(text);
                panel.add(button);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
        Level().AddObject(saveButton);
        Level().AddObject(new Button(new Vector(0.75f, 0.8f), "Mainmenu", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(Game.getDefualtLevel());
            }
        }));
        Level().AddObject(new Mouse()).setScale(new Vector(4, 4));
    }

    @Override
    public void doMove() {
    }

    @Override
    public void Update(Graphics2D gd) {
    }

    @Override
    public void onCollison(IDrawable id) {
    }

    public void dispose() {
        super.dispose();
        saveButton = null;
        saved = false;
        finished = false;
    }

}
