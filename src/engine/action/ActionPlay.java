package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionPlay extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionPlay(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-play-pause-resume.png"));
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.playPause();
    }
    
}
