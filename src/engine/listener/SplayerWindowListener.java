package engine.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import engine.SplayerEngine;

public class SplayerWindowListener extends WindowAdapter {

    private SplayerEngine engine;
    
    public SplayerWindowListener(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    public void windowClosing(WindowEvent event)
    {
        engine.stop();
        System.exit(0);
        // NOTE C'est ici que l'application se termine.
    }
}
