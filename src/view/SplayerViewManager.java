package view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;

import data.Library;
import data.Music;
import data.SplayerDataManager;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import engine.Player;
import engine.action.ActionOpenLibrary;
import engine.action.ActionOpenPlaylist;
import engine.listener.SplayerKeyListener;
import engine.listener.SplayerSliderListener;
import engine.listener.SplayerVolumeListener;
import engine.listener.SplayerWindowListener;


public class SplayerViewManager implements Observer {

    /* Data stage */
    private SplayerViewMain viewMain;
    private SplayerViewPlaylist viewLibrary;
    
    /* Builder stage */
    public SplayerViewManager()
    {
        try {
            UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Building
        this.viewMain       = new SplayerViewMain();
        this.viewLibrary   = new SplayerViewPlaylist();
        
        // Action mapping
        this.viewMain.setAction("library", new ActionOpenLibrary(viewLibrary));
        this.viewMain.setAction("playlist", new ActionOpenPlaylist(viewMain));
        
        // Post-init messages
        System.out.println("Splayer:ViewManager initialized.");
    }

    @Override
    public void update(Observable model, Object obj)
    {
        String argument = (String)obj;
        
        // Mise a jour du timer
        if( argument.equals("timerUpdate") )
    		updateTimer( Math.round( ((Player)model).getPosition() ) ); 
        // Lancement de la musique
        else if( argument.equals("play") ) {
            this.viewMain.setPlayButton(false);
        }
        // Met en pause la musique
        else if( argument.equals("pause")) {
            this.viewMain.setPlayButton(true);
        }
        // Arret complet de la musique
        else if( argument.equals("stop")) {
            this.viewMain.setPlayButton(true);
            updateTimer(0);
        }
        // Mise � jour de la s�lection (changement de musique)
        else if( argument.equals("playlistSelection") ) {
            Music current = ((SplayerDataManager)model).getCurrentMusic();
            this.viewMain.updateData(current);
        }
        // Ajout d'une musique a playlist
        else if( argument.equals("playlistUpdate") ) { // TODO cette update semble inutile ?
            this.viewMain.setPlaylist( ((SplayerDataManager)model).getPlaylist());
        }
        else if ( argument.equals("libraryUpdate") ) {
        	this.viewLibrary.setLibrary( ((SplayerDataManager)model).getLibrary() );
        }
        	
        // Modification du volume
        else if( argument.equals("volumeUpdate") ) {
            int volume = Math.round( ((Player)model).getVolume()*100 );
            this.viewMain.setDisplay("volume",  "" + volume);
            this.viewMain.setvolume(volume);
            this.viewMain.setVolumeIcon((int) Math.ceil(volume/40.0));
            if( ((Player)model).isMute() )
                this.viewMain.setVolumeIcon(-1);
        }
        // Active/desactive le loop
        else if( argument.equals("toggleLoop") ) {
            this.viewMain.toggleLoopIcon( ((SplayerDataManager)model).isLoop() );
        }
        // Initialisation de l'application
        else if( argument.equals("initialization") ) {
            this.viewMain.setPlaylist( ((SplayerDataManager)model).getPlaylist());
            this.viewLibrary.setBibliotheque( ((SplayerDataManager)model).getLibrary() );
            Music current = ((SplayerDataManager)model).getCurrentMusic();
            this.viewMain.toggleLoopIcon( ((SplayerDataManager)model).isLoop() );
            this.viewMain.updateData(current);
            this.viewMain.pack();
        }
        // Initialisation du player
        else if( argument.equals("playerInit") ) {
            int volume = Math.round(((Player)model).getVolume()*100);
            this.viewMain.setDisplay("volume", "" + volume);
            this.viewMain.setVolumeIcon((int) Math.ceil(volume/40.0));
            this.viewMain.setvolume( (int) (((Player)model).getVolume() * 100) );
            this.viewMain.setPlayButton(true);
            this.viewMain.pack();
        }
    }
    
    public void updateTimer(int timeInMilliSec)
    {
        int timeInSec = (int) (timeInMilliSec/1000);
        viewMain.setDisplay("time", (int)Math.floor(timeInSec/60) + ":" + String.format("%02d", timeInSec%60));
        viewMain.setSlider(timeInMilliSec);
    }

    /* Interface stage */
    /**
     * Associe un bouton avec une action. Attention, ceci pourrait �tre modifiŽ si tous les boutons sont gŽrŽs par le SplayerViewManager.
     * @param buttonName code bouton ˆ associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction ˆ associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        viewMain.setAction(buttonName, action);
        viewLibrary.setAction(buttonName, action);
    }
    
    public void setListener(String componentName, Object listener)
    {   
        // Listener pour la playlist
        if( listener instanceof MouseListener ) {
            if( componentName.equals("PLAYLIST") )
                viewMain.setPlaylistListener(listener);
            else if( componentName.equals("LIBRARY") )
                viewLibrary.setLiblistListener(listener);
            else
                viewMain.setListener(componentName, listener);
        }
        // Listener pour le slider de volume
        else if( listener instanceof  SplayerVolumeListener )
            viewMain.addVolumeListener((ChangeListener) listener);
        // Listener pour le slider de lecture
        else if( listener instanceof SplayerSliderListener )
            viewMain.addSliderListener(listener);
        // Listener de fenetre principale
        else if( listener instanceof SplayerWindowListener )
            viewMain.addWindowListener((WindowListener)listener);
        // Listener de barre de recherche
        else if( listener instanceof SplayerKeyListener )
            viewLibrary.setSearchListener((KeyListener)listener);
    }
    
    public void setPlaylistHandler(TransferHandler handler)
    {
        viewMain.setPlaylistHandler(handler);
    }
    
    /* Implementation stage */
    public void go()
    {
        viewMain.setVisible(true);
    }
      
}
