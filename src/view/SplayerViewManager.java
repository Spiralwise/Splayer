package view;

import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.TransferHandler;

import data.Music;
import data.SplayerDataManager;
import engine.action.ActionOpenPlaylist;


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
        this.viewMain.setAction("playlist", new ActionOpenPlaylist(viewPlaylist));
        System.out.println("Splayer:ViewManager initialized.");
    }

    @Override
    public void update(Observable model, Object obj)
    {
        SplayerDataManager sdm = (SplayerDataManager)model;
        String argument = (String)obj;
        
        // Mise à jour de la playlist
        if( argument.equals("playlistSelection") ) {
            Music current = sdm.getCurrentMusic();
            if( current != null ) {
                this.viewMain.updateData(current);
            }
        }
        if( argument.equals("playlistUpdate") ) { // TODO créer un enum pour les updates ?
            this.viewPlaylist.setPlaylist(sdm.getPlaylist());
        }
        // Initialisation de l'application
        else if( argument.equals("initialization") ) {
            this.viewPlaylist.setPlaylist(sdm.getPlaylist());
            Music current = sdm.getCurrentMusic();
            if( current != null ) {
                this.viewMain.updateData(current);
            }
        }
    }

    /* Interface stage */
    public void setAction(String buttonName, AbstractAction action)
    {
        viewMain.setAction(buttonName, action);
    }
    
    public void setListener(String componentName, Object listener)
    {   
        // Listener pour la playlist
        if( componentName.equals("PLAYLIST") )
            if( listener instanceof MouseListener )
                viewPlaylist.setPlaylistListener(listener);
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
