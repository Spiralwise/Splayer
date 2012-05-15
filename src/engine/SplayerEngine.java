package engine;

import view.SplayerViewManager;
import view.action.ActionPlay;
import data.MusicHandler;
import data.SplayerDataManager;

public class SplayerEngine {

    /* Data stage */
    private Player player;
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    
    /* Builder stage */
    public SplayerEngine(SplayerDataManager sdm, SplayerViewManager svm)
    {
        // Init
        this.player = new Player();
        this.sdm = sdm;
        this.svm = svm;
        
        // MVC is magic !
        this.sdm.addObserver(this.svm);
        this.sdm.notifyObservers();
        
        // Action mapping
        this.svm.setAction("play", new ActionPlay(this));
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
        System.out.println("SplayerEngine initialized.");
        System.out.println("Splayer ready to launch !");
    }
    
    /* Implementaton stage */
    /**
     * Démarre l'application.
     */
    public void launch()
    {
        svm.go();
    }

    /**
     * Démarre/arrête la lecture de la musique en cours.
     */
    public void playPause()
    {
        if( player.getPlayerState() == 0 )
            player.setPath(sdm.getCurrentMusicPath());
        player.PlayPause();
    }
}
