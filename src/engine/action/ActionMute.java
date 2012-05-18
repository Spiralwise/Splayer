package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionMute extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionMute(SplayerEngine engine)
    {
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Coupe/Met le son");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.toggleMute();
    }

}
