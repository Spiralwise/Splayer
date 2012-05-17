package engine;

import java.awt.event.MouseListener;

import view.SplayerViewManager;
import data.MusicHandler;
import data.SplayerDataManager;
import engine.action.ActionNextMusic;
import engine.action.ActionPlay;
import engine.action.ActionPreviousMusic;
import engine.listener.SplayerMouseListener;
import engine.listener.SplayerSliderListener;
import engine.listener.SplayerVolumeListener;
import engine.listener.SplayerWindowListener;

public class SplayerEngine {

    /* Data stage */
    private Player player;
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    
    /* Action stage */
    private MouseListener actionMouse;
    
    /* Builder stage */
    public SplayerEngine(SplayerDataManager sdm, SplayerViewManager svm)
    {
        // Init
        this.player = new Player();
        this.sdm = sdm;
        this.svm = svm;
        
        // MVC is magic !
        this.sdm.addObserver(this.svm);
        this.player.addObserver(this.svm);
        this.sdm.notifyObservers("initialization");
        this.player.notifyObservers("playerInit");
        
        // Listener mapping
        actionMouse = new SplayerMouseListener(this);
        this.svm.setListener("PLAYLIST", actionMouse);
        this.svm.setListener("WINDOW", new SplayerWindowListener(this));
        this.svm.setListener("VOLUME", new SplayerVolumeListener(this));
        this.svm.setListener("PLAYER", new SplayerSliderListener(this));
        
        // Action mapping
        this.svm.setAction("play", new ActionPlay(this));
        this.svm.setAction("next", new ActionNextMusic(this));
        this.svm.setAction("previous", new ActionPreviousMusic(this));
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
        // Post-init messages
        System.out.println("Splayer:Engine initialized.");
        System.out.println("Splayer:Ready to launch !");
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
     * Arrête la musique en cours de lecture.
     */
    public void stop()
    {
        player.Stop();
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
    
    /**
     * Joue la musique se trouvant à l'index correspondant.
     * @param playlistIndex index de playlist de la musique à jouer
     */
    public void playThisMusic(int playlistIndex)
    {
        player.setPath( sdm.selectMusic(playlistIndex) );
    }

    /**
     * Modifie le volume
     * @param value valeur du volume de 0 à 100 (0 étant le son coupé)
     */
    public void setVolume(int value)
    {
        player.setVolume( (float) (value/100.0) );
    }

    /**
     * Déplace la tête de lecture à la position indiquée en seconde
     * @param value position en milliseconde
     */
    public void moveReadHead(int positionInMilliSec)
    {
        player.setPosition(positionInMilliSec);
    }
}
