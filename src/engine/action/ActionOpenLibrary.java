package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.SplayerViewPlaylist;

@SuppressWarnings("serial")
public class ActionOpenLibrary extends AbstractAction {

    private SplayerViewPlaylist view;
    
    public ActionOpenLibrary(SplayerViewPlaylist view)
    {
        super("", new ImageIcon("./data/icon/media-library.png"));
        this.view = view;
        this.putValue(SHORT_DESCRIPTION, "Bibliothèque de musique");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        view.setVisible( !view.isVisible() );
    }

}
