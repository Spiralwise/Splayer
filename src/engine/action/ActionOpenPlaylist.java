package engine.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.SplayerViewMain;

@SuppressWarnings("serial")
public class ActionOpenPlaylist extends AbstractAction {

    private SplayerViewMain view;
    
    public ActionOpenPlaylist(SplayerViewMain view)
    {
        super("", new ImageIcon("./data/icon/media-playlist.png"));
        this.view = view;
        this.putValue(SHORT_DESCRIPTION, "Playlist");
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        view.togglePlaylist();
    }

}
