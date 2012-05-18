package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionLoop extends AbstractAction {

private SplayerEngine engine;
    
    public ActionLoop(SplayerEngine engine)
    {
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Jouer en boucle la playlist");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.toggleLoop();
    }

}
