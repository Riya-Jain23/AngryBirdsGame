package com.badlogic.angrybirds.Screens;

import javax.swing.*;
    import java.awt.*;

public class MainMenuScreen extends JFrame {

    public MainMenuScreen() {
        setTitle("Angry Birds - Main Menu");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        // Play button
        JButton playButton = new JButton(new ImageIcon("assets/play.png"));
        playButton.setRolloverIcon(new ImageIcon("assets/play_hover.png"));

        // Settings button
        JButton settingsButton = new JButton(new ImageIcon("assets/settings.png"));
        settingsButton.setRolloverIcon(new ImageIcon("assets/settings_hover.png"));

        // About button
        JButton aboutButton = new JButton(new ImageIcon("assets/about.png"));
        aboutButton.setRolloverIcon(new ImageIcon("assets/about_hover.png"));

        // Add buttons to the panel
        panel.add(playButton);
        panel.add(settingsButton);
        panel.add(aboutButton);

        add(panel);
        setVisible(true);
    }

}
