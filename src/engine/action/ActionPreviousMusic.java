package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionPreviousMusic extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionPreviousMusic(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        this.engine.previousMusic();
    }

}
