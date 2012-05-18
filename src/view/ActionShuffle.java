package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

public class ActionShuffle extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionShuffle(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-shuffle.png"));
        this.engine = engine;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.shufflePlaylist();
    }

}
