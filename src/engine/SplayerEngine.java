package engine;

import view.SplayerViewManager;
import data.MusicHandler;
import data.SplayerDataManager;
import engine.action.ActionPlay;

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
    
    /* SPlayer stage */
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
        if( player.getPlayerState() == Player.STOP )
            player.setPath(sdm.getCurrentMusicPath());
        player.PlayPause();
    }
    
    /**
     * Passe à la musique suivante dans la playlist et la joue si la lecture est en cours.
     */
    public void nextMusic()
    {
        player.setPath( sdm.nextMusic(true) );
    } // TODO Si l'utilisateur a désactivé le bouclage, ça ne doit pas boucler !!!!!
    
    /**
     * Passe à la musique précédente dans la playlist et la joue si la lecture est en cours.
     */
    public void previousMusic()
    {
        player.setPath( sdm.nextMusic(false) );
    }
}
