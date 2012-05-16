package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionNextMusic extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionNextMusic(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) 
    {
        this.engine.nextMusic();
    }

}
