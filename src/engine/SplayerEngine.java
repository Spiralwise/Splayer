package engine;

import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import view.ActionShuffle;
import view.SplayerViewManager;
import data.MusicHandler;
import data.SplayerDataManager;
import engine.action.ActionEmpty;
import engine.action.ActionNextMusic;
import engine.action.ActionPlay;
import engine.action.ActionPreviousMusic;
import engine.action.ActionRemoveItem;
import engine.listener.SplayerMouseListener;
import engine.listener.SplayerSliderListener;
import engine.listener.SplayerVolumeListener;
import engine.listener.SplayerWindowListener;

public class SplayerEngine implements Observer {

    /* Data stage */
    private Player player;
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    
        // Internal data
    private int selectedIndex;
    
    /* Action stage */
    private MouseListener actionMouse;
    
    /* Builder stage */
    public SplayerEngine(SplayerDataManager sdm, SplayerViewManager svm)
    {
        // Init
        this.player = new Player();
        this.player.Load(sdm.getCurrentMusicPath());
        this.player.Stop();
        this.sdm = sdm;
        this.svm = svm;
        this.selectedIndex = -1;
        
        // MVC is magic !
        this.sdm.addObserver(this.svm);
        this.player.addObserver(this.svm);
        this.player.addObserver(this);
        this.sdm.notifyObservers("initialization");
        this.player.notifyObservers("playerInit");
        
        // Listener mapping
        actionMouse = new SplayerMouseListener(this);
        this.svm.setListener("PLAYLIST", actionMouse);
        this.svm.setListener("WINDOW", new SplayerWindowListener(this));
        this.svm.setListener("VOLUME", new SplayerVolumeListener(this));
        this.svm.setListener("PLAYER", new SplayerSliderListener(this));
        
        // Action mapping
            // TODO gros todo ! Certaines actions sont relayées par l'engine vers le sdm alors que le sdm pourrait être directement contacté (mais est-ce encore du MVC dans ce cas ?)
        this.svm.setAction("play", new ActionPlay(this));
        this.svm.setAction("next", new ActionNextMusic(this));
        this.svm.setAction("previous", new ActionPreviousMusic(this));
        this.svm.setAction("shuffle", new ActionShuffle(this));
        this.svm.setAction("removeItem", new ActionRemoveItem(this));
        this.svm.setAction("empty", new ActionEmpty(this));
            // Action by listener
        this.svm.setListener("forward", actionMouse);
        this.svm.setListener("rewind", actionMouse);
        
        // Handler mapping
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
        // Post-init messages
        System.out.println("Splayer:Engine initialized.");
        System.out.println("Splayer:Ready to launch !");
    }
    
    /* Observer stage */
    /**
     * Le SplayerEngine est un observeur afin qu'il sache si son player a
     * terminer de lire une musique et puisse lui envoyer la suivante.
     */
    @Override
    public void update(Observable obs, Object o)
    {
        String argument = (String)o;
        // Lecture jusqu'a la fin
        if( argument.equals("engine.nextMusic") ) {
            nextMusic();
            playPause();
        }
    }
    
    /* SPlayer stage */
    /**
     * Demarre l'application.
     */
    public void launch()
    {
        svm.go();
    }

    /**
     * D≈Ωmarre/arrete la lecture de la musique en cours.
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
    } // TODO Si l'utilisateur a desactive le bouclage, ca ne doit pas boucler !!!!!
    
    /**
     * Passe ÀÜ la musique pr≈Ωc≈Ωdente dans la playlist et la joue si la lecture est en cours.
     */
    public void previousMusic()
    {
        player.setPath( sdm.nextMusic(false) );
    }
    
    /**
     * Joue la musique se trouvant ÀÜ l'index correspondant.
     * @param playlistIndex index de playlist de la musique ÀÜ jouer
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
    
    /**
     * Déclenche le forward ou le rewind.
     * @param way Player.FORWARD ou Player.REWIND
     */
    public void triggerForward(int way)
    {
        switch(way) {
        case Player.FORWARD:
            player.doForward(false);
            break;
        case Player.REWIND:
            player.doForward(true);
            break;
        default:
            player.stopForward();
        }
    }
    
    /**
     * Mélange la playlist.
     */
    public void shufflePlaylist()
    {
        sdm.shufflePlaylist();
    }

    /* Restricted stage */
    /**
     * Méthode réservée au SplayerEngine pour connaître à tout moment
     * quelle musique est sélectionnée dans la liste. Attention, à
     * ne pas confondre avec la musique en cours de lecture.
     * L'utilisateur peut cliquer sur une musique sans la jouer.
     * Cette méthode est uniquement appelée par un event pour renseigner le
     * SplayerEngine.
     * @param selectedIndex
     */
    public void setSelectedMusic(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }

    /**
     * Méthode réservée.
     */
    public void removeSelectedMusic()
    {
        if( selectedIndex != -1 )
            sdm.removeMusic(selectedIndex);
        selectedIndex = -1;
    }

    /**
     * Vide la playlist courrante.
     */
    public void emptyPlaylist()
    {
        sdm.emptyPlaylist();
    } 
    
}
