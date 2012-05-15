package view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionPlay extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionPlay(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.playPause();
    }
    
}
