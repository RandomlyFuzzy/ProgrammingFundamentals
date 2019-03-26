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
import com.Liamengine.Engine.Utils.UtilManager;
import com.Progfund.Levels.Level;
import com.Progfund.Levels.LevelSelect;
import com.Progfund.Levels.timedLevel;
import com.Progfund.Object.inGame.Player;
import java.awt.Color;
import java.awt.Font;
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
 * handles all the saving of games if won and this is only ran once the level
 * has ended
 *
 * @author Liam Woolley 1748910
 */
public class LevelOverOverlay extends IDrawable {

    /**
     * if this is up then stop everything else
     */
    private static boolean finished = false;
    /**
     * stop entrys from being saved 2 times to a file 1 way toggle on the save
     * button
     */
    private static boolean saved = false;
    /**
     * the button that saves things just used to change its value later
     */
    private static Button saveButton;

    /**
     * the button that saves things just used to change its value later
     */
    private double loadedTime;
    
    
    /**
     *
     * @return the current levels save button
     */
    public static Button getSaveButton() {
        return saveButton;
    }

    /**
     *
     * @return whether or not the level has ended
     */
    public static boolean isFinished() {
        return finished;
    }

    /**
     *
     * @param finished this sets the level finished
     */
    public static void setFinished(boolean finished) {
        LevelOverOverlay.finished = finished;
    }

    /**
     *
     * @return whether or not the save on the save popup has been pressed
     */
    public static boolean hassaved() {
        return saved;
    }

    /**
     *
     * @param saved sets whether or not this has been saved once
     */
    public static void setsaved(boolean saved) {
        LevelOverOverlay.saved = saved;
    }

    /**
     * where all the logic is
     */
    @Override
    public void init() {
        //stops game
        setFinished(true);
        //makes things not collide with it just in case
        setIsCollidable(false);
        //lock its position to only the screen
        UseTransforms(false);

        // to the same level
        Level().AddObject(new Button(new Vector(0.25f, 0.8f), "replay", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelSelect.LoadLevelFromID(Level.GetLevel() - 1);
            }
        }));
        //if the required points habe been achieved
        if (Player.getScoreNeeded() < Player.getPlayerScore()) {
            //make it so the player can save their score
            saveButton = new Button(new Vector(0.5f, 0.8f), "save score", new HUDdelegate() {
                public void OnClick(Button b) {
                    //do nothing if already saved
                    if (LevelOverOverlay.hassaved()) {
                        return;
                    }

                    //creates the pop-up with lable textarea and button
                    JButton button = new JButton("click me");
                    JTextField text = new JTextField("Please enter your save name");
                    JLabel lable = new JLabel("Please input name");
                    JFrame frame = new JFrame();
                    JPanel panel = new JPanel();

                    //saves when the button is pressed
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            frame.setVisible(false);
                            if (text.getText().trim().equals("Please enter your save name")) {
                                return;
                            }

                            String file = "Level" + Level.GetLevel();
                            System.out.println(text.getText().trim());
                            LevelOverOverlay.setsaved(true);
                            LevelOverOverlay.getSaveButton().setMessage("Saved as " + text.getText().trim());
                            FileUtils.AppendToFile("resources/saveData/" + file + ".txt",
                                    text.getText().trim().replaceAll(":", "_")
                                    + ((Level() instanceof timedLevel) ? "(t)" : "") + ":" + Player.getPlayerScore() + "\n");

                            frame.dispose();
                        }
                    });
                    //gets rid of top bar
                    frame.setUndecorated(true);
                    //makes inside the screen
                    frame.setBounds(Game.GetFrame().getX() + (int) (Game.GetFrame().getWidth() * 0.4f), Game.GetFrame().getY() + (int) (Game.GetFrame().getHeight() * 0.45f), Game.GetFrame().getWidth(), Game.GetFrame().getHeight());
                    //adds (j)components
                    panel.add(lable);
                    panel.add(text);
                    panel.add(button);
                    frame.add(panel);
                    frame.pack();
                    frame.setVisible(true);
                }
            });
            //adds save button to screen
            Level().AddObject(saveButton);
        }
        //to main menu
        Level().AddObject(new Button(new Vector(0.75f, 0.8f), "Mainmenu", new HUDdelegate() {
            public void OnClick(Button b) {
                LevelLoader.LoadLevel(Game.getDefualtLevel());
            }
        }));
        //creates mouse
        Level().AddObject(new Mouse()).setScale(new Vector(4, 4));
        //saves the current time
        loadedTime = Level().getTime();
    }

    /**
     *
     */
    @Override
    public void doMove() {
    }

    /**
     *
     * @param gd draws the background box and whether or nothe player has won the game
     */
    @Override
    public void Update(Graphics2D gd) {
        Color c = gd.getColor();
        Font f = gd.getFont();

        gd.setColor(new Color(220, 0, 200, 150));
        gd.fillRect((int) (Game.getWindowWidth() * 0.05), (int) (Game.getWindowHeight() * 0.05), (int) (Game.getWindowWidth() * 0.9), (int) (Game.getWindowHeight() * 0.85));

        gd.setFont(f.deriveFont(f.getSize() * 3f));
        String Display = "";
        if (Player.getScoreNeeded() < Player.getPlayerScore()) {
            Display += "YOU WIN!!";
            gd.setColor(new Color(0, 255, 0));
        } else {
            Display += "You Lost :(";
            gd.setColor(new Color(255, 0, 0));
        }
        int w = gd.getFontMetrics().stringWidth(Display);
        gd.drawString(Display, (int) ((Game.getWindowWidth() / 2) - (w / 2)), (int) (Game.getWindowHeight() * 0.095) + gd.getFont().getSize() / 2);
        gd.setColor(new Color(255, 255, 255));
        gd.setFont(f.deriveFont(f.getSize() * 2f));
        
        //used string.formate to make a double go to 2 decimal places
        String display = String.format("with " + Player.getPlayerScore() + " points and a time of %.2f seconds",((float)loadedTime));
        
        w = gd.getFontMetrics().stringWidth(display);
        gd.drawString(display, (int) ((Game.getWindowWidth() / 2) - (w / 2)), (int) (Game.getWindowHeight() * 0.4) + gd.getFont().getSize() / 2);
        gd.setFont(f);
        gd.setColor(c);
    }

    /**
     *
     * @param id
     */
    @Override
    public void onCollison(IDrawable id) {
    }

    /**
     *
     */
    public void dispose() {
        super.dispose();
        saveButton = null;
        saved = false;
        finished = false;
    }

}
