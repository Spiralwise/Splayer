package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.TransferHandler;

import data.SplayerDataManager;

import view.action.ActionOpenPlaylist;
import view.action.ActionPlay;


public class SplayerViewManager implements Observer {

    /* Data stage */
    private SplayerViewMain viewMain;
    private SplayerViewPlaylist viewPlaylist;
    
    /* Builder stage */
    public SplayerViewManager()
    {
        // Building
        this.viewMain       = new SplayerViewMain();
        this.viewPlaylist   = new SplayerViewPlaylist();
        
        // Action mapping
        //this.viewMain.setAction("play", new ActionPlay());
        this.viewMain.setAction("PLAYLIST", new ActionOpenPlaylist(viewPlaylist));
        System.out.println("SplayerViewManager initialized.");
    }

    @Override
    public void update(Observable model, Object obj) {
        System.out.println("SVM:Perform update");
        this.viewPlaylist.setPlaylist(((SplayerDataManager)model).getPlaylist());
    }

    /* Interface stage */
    public void setAction(String buttonName, AbstractAction action)
    {
        viewMain.setAction(buttonName, action);
    }
    
    public void setPlaylistHandler(TransferHandler handler)
    {
        viewPlaylist.setPlaylistHandler(handler);
    }
    
    /* Implementation stage */
    public void go()
    {
        viewMain.setVisible(true);
    }
      
}
