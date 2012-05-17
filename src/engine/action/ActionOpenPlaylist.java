package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.SplayerViewPlaylist;

@SuppressWarnings("serial")
public class ActionOpenPlaylist extends AbstractAction {

    private SplayerViewPlaylist view;
    
    public ActionOpenPlaylist(SplayerViewPlaylist view)
    {
        super("", new ImageIcon("./data/icon/media-search.png"));
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        view.setVisible( !view.isVisible() );
    }

}
