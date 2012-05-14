package view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.SplayerViewPlaylist;

@SuppressWarnings("serial")
public class ActionOpenPlaylist extends AbstractAction {

    private SplayerViewPlaylist view;
    
    public ActionOpenPlaylist(SplayerViewPlaylist view)
    {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        view.setVisible( !view.isVisible() );
    }

}
