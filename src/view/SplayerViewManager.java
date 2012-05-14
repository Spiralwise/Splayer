package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import view.action.ActionOpenPlaylist;


public class SplayerViewManager implements Observer {

    /* Data stae */
    private SplayerViewMain viewMain;
    private SplayerViewPlaylist viewPlaylist;
    
    /* Builder stage */
    public SplayerViewManager()
    {
        // Building
        this.viewMain       = new SplayerViewMain();
        this.viewPlaylist   = new SplayerViewPlaylist();
        
        // Action mapping
        this.viewMain.setAction("PLAYLIST", new ActionOpenPlaylist(viewPlaylist));
        System.out.println("SplayerViewManager initialized.");
    }

    @Override
    public void update(Observable model, Object obj) {
        System.out.println("SVM:Perform update");
    }

    /* Implementation stage */
    public void go()
    {
        viewMain.setVisible(true);
    }
      
}
