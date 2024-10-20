package com.badlogic.angrybirds.Screens;

import javax.swing.*;

public class SettingsScreen extends JPanel {

    public SettingsScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JCheckBox soundCheckbox = new JCheckBox("Sound", true);
        JCheckBox musicCheckbox = new JCheckBox("Music", true);
        JButton backButton = new JButton("Back to Main Menu");

        add(soundCheckbox);
        add(musicCheckbox);
        add(backButton);
    }
}

