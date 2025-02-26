package com.kdroid.kmplog;

import javax.swing.JButton;
import javax.swing.JFrame;

fun main() {
    Log.setDevelopmentMode(true);
    Log.enableBroadcastingMode();
    // Crée la fenêtre principale
    val frame = JFrame("Logger Example");
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    frame.setSize(400, 500);
    frame.layout = null;

    val tag = "SwingButton";

    // Bouton pour le log DEBUG
    val debugButton = JButton("Show DEBUG Log");
    debugButton.setBounds(140, 20, 160, 30);
    frame.add(debugButton);
    debugButton.addActionListener {
        Log.d(tag, "This is a DEBUG message");
    };

    // Bouton pour le log ERROR
    val errorButton = JButton("Show ERROR Log");
    errorButton.setBounds(140, 70, 160, 30);
    frame.add(errorButton);
    errorButton.addActionListener {
        Log.e(tag, "This is an ERROR message");
    };

    // Bouton pour le log WARN
    val warnButton = JButton("Show WARN Log");
    warnButton.setBounds(140, 120, 160, 30);
    frame.add(warnButton);
    warnButton.addActionListener {
        Log.w(tag, "This is a WARN message");
    };

    // Bouton pour le log INFO
    val infoButton = JButton("Show INFO Log");
    infoButton.setBounds(140, 170, 160, 30);
    frame.add(infoButton);
    infoButton.addActionListener {
        Log.i(tag, "This is an INFO message");
    };

    // Bouton pour le log WTF
    val wtfButton = JButton("Show WTF Log");
    wtfButton.setBounds(140, 220, 160, 30);
    frame.add(wtfButton);
    wtfButton.addActionListener {
        Log.wtf(tag, "This is a WTF message");
    };

    // Bouton qui fait crasher l'application
    val crashButton = JButton("Crash App");
    crashButton.setBounds(140, 270, 160, 30);
    frame.add(crashButton);
    crashButton.addActionListener {
        throw RuntimeException("Intentional Crash");
    };
    // Affiche la fenêtre
    frame.isVisible = true;
}
