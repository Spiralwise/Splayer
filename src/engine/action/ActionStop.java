package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import engine.SplayerEngine;

@SuppressWarnings("serial")
public class ActionStop extends AbstractAction {

    private SplayerEngine engine;
    
    public ActionStop(SplayerEngine engine)
    {
        super("", new ImageIcon("./data/icon/media-stop.png"));
        this.engine = engine;
        this.putValue(SHORT_DESCRIPTION, "Arrêter");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        engine.stop();
    }

}
