package engine;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import view.SplayerViewManager;
import data.Library;
import data.Music;
import data.MusicHandler;
import data.SplayerDataManager;
import engine.Player.ForwardThread;
import engine.action.ActionEmpty;
import engine.action.ActionLoop;
import engine.action.ActionMute;
import engine.action.ActionNextMusic;
import engine.action.ActionPlay;
import engine.action.ActionPreviousMusic;
import engine.action.ActionRemoveItem;
import engine.action.ActionShuffle;
import engine.action.ActionStop;
import engine.listener.SplayerKeyListener;
import engine.listener.SplayerMouseListener;
import engine.listener.SplayerSliderListener;
import engine.listener.SplayerVolumeListener;
import engine.listener.SplayerWindowListener;

public class SplayerEngine implements Observer {

    /* Data stage */
    private Player player;
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    boolean TimerState;
    private TimerThread timer;
    
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
        
        // Listener mapping
        actionMouse = new SplayerMouseListener(this);
        this.svm.setListener("PLAYLIST", actionMouse);
        this.svm.setListener("LIBRARY", actionMouse);
        this.svm.setListener("WINDOW", new SplayerWindowListener(this));
        this.svm.setListener("VOLUME", new SplayerVolumeListener(this));
        this.svm.setListener("PLAYER", new SplayerSliderListener(this));
        
        // Action mapping
            // TODO gros todo ! Certaines actions sont relayees par l'engine vers le sdm alors que le sdm pourrait etre directement contacte (mais est-ce encore du MVC dans ce cas ?)
        this.svm.setAction("play", new ActionPlay(this));
        this.svm.setAction("stop", new ActionStop(this));
        this.svm.setAction("next", new ActionNextMusic(this));
        this.svm.setAction("previous", new ActionPreviousMusic(this));
        this.svm.setAction("shuffle", new ActionShuffle(this));
        this.svm.setAction("loop", new ActionLoop(this));
        this.svm.setAction("removeItem", new ActionRemoveItem(this));
        this.svm.setAction("empty", new ActionEmpty(this));
        this.svm.setAction("sound", new ActionMute(this));
            // Action by listener
        // Loading music samples

        // Action by listener
        this.svm.setListener("forward", actionMouse);
        this.svm.setListener("rewind", actionMouse);
        this.svm.setListener("typingArea", new SplayerKeyListener(this));
        
        
        // Handler mapping
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
        // MVC is magic !
        this.sdm.addObserver(this.svm);
        this.player.addObserver(this.svm);
        this.player.addObserver(this);
        this.sdm.notifyObservers("initialization");
        this.player.forceSetChanged(); // NŽcessaire ˆ cause des manipulations prŽcŽdentes.
        this.player.notifyObservers("playerInit");
        
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
            if( nextMusic() )
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
     * Demarre/arrete la lecture de la musique en cours.
     */
    public void playPause()
    {
        if( player.getPlayerState() == Player.STOP )
            player.setPath(sdm.getCurrentMusicPath());
        player.PlayPause();
    }
    
    /**
     * Arrete la musique en cours de lecture.
     */
    public void stop()
    {
        player.Stop();
    }
    
    /**
     * Passe ˆ la musique suivante dans la playlist et la joue si la lecture est en cours.
     * @return true si le player peut passer a la musique suivante.
     */
    public boolean nextMusic()
    {
        String next = sdm.nextMusic(true);
        player.setPath( next );
        return (next==null) ? false : true;
    }
    
    /**
     * Passe a la musique precedente dans la playlist et la joue si la lecture est en cours.
     */
    public void previousMusic()
    {
        player.setPath( sdm.nextMusic(false) );
    }
    
    /**
     * Joue la musique se trouvant Ë† l'index correspondant.
     * @param playlistIndex index de playlist de la musique Ë† jouer
     */
    public void playThisMusic(int playlistIndex)
    {
        player.setPathAndPlay( sdm.selectMusic(playlistIndex) );
    }

    /**
     * Modifie le volume
     * @param value valeur du volume de 0 ï¿½ 100 (0 ï¿½tant le son coupï¿½)
     */
    public void setVolume(int value)
    {
        player.setVolume( (float)(value/100.0) );
        //player.setVolume( (float) Math.sqrt((double)(value/100.0)) );
    }

    /**
     * Dï¿½place la tï¿½te de lecture ï¿½ la position indiquï¿½e en seconde
     * @param value position en milliseconde
     */
    public void moveReadHead(int positionInMilliSec)
    {
        player.setPosition(positionInMilliSec);
    }
    
    /**
     * Dï¿½clenche le forward ou le rewind.
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
     * Mï¿½lange la playlist.
     */
    public void shufflePlaylist()
    {
        sdm.shufflePlaylist();
    }

    /**
     * Coupe/Met le son.
     */
    public void toggleMute()
    {
        player.mute();
    }

    /**
     * Active/Desactive le boucle de playlist.
     */
    public void toggleLoop()
    {
        sdm.loop();  
    }
    
    /* Restricted stage */
    /**
     * Mï¿½thode rï¿½servï¿½e au SplayerEngine pour connaï¿½tre ï¿½ tout moment
     * quelle musique est sï¿½lectionnï¿½e dans la liste. Attention, ï¿½
     * ne pas confondre avec la musique en cours de lecture.
     * L'utilisateur peut cliquer sur une musique sans la jouer.
     * Cette mï¿½thode est uniquement appelï¿½e par un event pour renseigner le
     * SplayerEngine.
     * @param selectedIndex
     */
    public void setSelectedMusic(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }

    /**
     * Mï¿½thode rï¿½servï¿½e.
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
    
    /**
     * Recherche du mot en param dans la libraire
     * @param word le mot a rechercher dans la librairie
     * @throws TagException 
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws NumberFormatException 
     */
    public void search(String word)
    {
    	//System.out.println(word);
    	sdm.connect(word);
    }
    /**
     * DÃ©clanche un timer de 200ms avant d'effectuer la recherche
     */
	public void runTimer(String word)
	{
		if (timer==null || !timer.isAlive()) {
		    timer = new TimerThread(word);
		    timer.start();
		} else {
			timer.resetTimer(word);
		}
	}
	
    /**
     * Arrete le timer
     */
    public void stopTimer()
    {
        timer.interrupt();
    }
	
	class TimerThread extends Thread {
		private static final int TimerDuration = 200; // en ms
		private int currentTime, step;
		private String word;
		
		public TimerThread(String word)
		{
			this.word 	= word;
			currentTime	= 0;
			step		= TimerDuration/10;
		}
		
		public void resetTimer(String word)
		{
			currentTime=0;
			this.word=word;
		}
		
		public void run()
		{
			try {
				while (currentTime < TimerDuration)
				{
					Thread.sleep(step);
					currentTime+=step;
				}
				search(this.word);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(("c good"));
		}
	}

    public void addRandomMusic()
    {
        sdm.addRandomMusic();
    }
}
