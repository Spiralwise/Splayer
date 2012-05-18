package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionEmpty extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionEmpty(SplayerEngine engine)
    {
        super("I_EM");
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.emptyPlaylist();
    }

}
