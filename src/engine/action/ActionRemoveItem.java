package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionRemoveItem extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionRemoveItem(SplayerEngine engine)
    {
        super("ICON_REM");
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.removeSelectedMusic();
    }

}
