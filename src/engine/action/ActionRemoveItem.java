package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionRemoveItem extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionRemoveItem(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-remove.png"));
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Retirer musique de la playlist");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.removeSelectedMusic();
    }

}
