package com.badlogic.angrybirds.Screens;

import javax.swing.*;

public class PauseScreen extends JPanel {

    public PauseScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton resumeButton = new JButton(new ImageIcon("assets/pause.png"));
        JButton mainMenuButton = new JButton("Main Menu");

        add(resumeButton);
        add(mainMenuButton);
    }
}

