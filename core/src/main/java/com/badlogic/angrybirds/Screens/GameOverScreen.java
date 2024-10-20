package com.badlogic.angrybirds.Screens;

import javax.swing.*;

public class GameOverScreen extends JPanel {

    public GameOverScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel(new ImageIcon("assets/over.png"));
        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main Menu");

        add(gameOverLabel);
        add(restartButton);
        add(mainMenuButton);
    }
}

