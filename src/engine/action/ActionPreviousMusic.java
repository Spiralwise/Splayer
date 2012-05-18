package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionPreviousMusic extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionPreviousMusic(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-previous.png"));
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Musique précédente");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        this.engine.previousMusic();
    }

}
