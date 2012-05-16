package engine;

import java.awt.event.MouseListener;

import view.SplayerViewManager;
import data.MusicHandler;
import data.SplayerDataManager;
import engine.action.*;

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
        this.sdm.notifyObservers("initialization");
        
        // Action mapping
        actionMouse = new ActionMouse(this);
        this.svm.setListener("PLAYLIST", actionMouse);
        this.svm.setAction("play", new ActionPlay(this));
        this.svm.setAction("next", new ActionNextMusic(this));
        this.svm.setAction("previous", new ActionPreviousMusic(this));
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
        System.out.println("Splayer:Engine initialized.");
        System.out.println("Splayer:Ready to launch !");
    }
    
    /* SPlayer stage */
    /**
     * D�marre l'application.
     */
    public void launch()
    {
        svm.go();
    }

    /**
     * D�marre/arr�te la lecture de la musique en cours.
     */
    public void playPause()
    {
        if( player.getPlayerState() == Player.STOP )
            player.setPath(sdm.getCurrentMusicPath());
        player.PlayPause();
    }
    
    /**
     * Passe � la musique suivante dans la playlist et la joue si la lecture est en cours.
     */
    public void nextMusic()
    {
        player.setPath( sdm.nextMusic(true) );
    } // TODO Si l'utilisateur a d�sactiv� le bouclage, �a ne doit pas boucler !!!!!
    
    /**
     * Passe � la musique pr�c�dente dans la playlist et la joue si la lecture est en cours.
     */
    public void previousMusic()
    {
        player.setPath( sdm.nextMusic(false) );
    }
    
    /**
     * Joue la musique se trouvant � l'index correspondant.
     * @param playlistIndex index de playlist de la musique � jouer
     */
    public void playThisMusic(int playlistIndex)
    {
        player.setPath( sdm.selectMusic(playlistIndex) );
    }
}
