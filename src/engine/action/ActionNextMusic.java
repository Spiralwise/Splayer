package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionNextMusic extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionNextMusic(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-next.png"));
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Musique suivante");
    }
    
    @Override
    public void actionPerformed(ActionEvent event) 
    {
        this.engine.nextMusic();
    }

}
