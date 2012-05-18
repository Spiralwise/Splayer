package engine;

import java.util.Observable;

import javazoom.jl.decoder.Equalizer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.LillePlayer;

public class Player extends Observable {
    
    /* Enum stage */
    public final static int STOP    = 0;
    public final static int LOAD    = 1;
    public final static int PLAY    = 2;
    public final static int FORWARD = 3;
    public final static int REWIND  = 4;
    
    /* Data stage */
    private final static int clockrate = 200;
    private LillePlayer player;
    private ForwardThread ft;
    private String currentPath;
    
    private int state;
    private float volume = (float) 0.4;
    private int position = 0;

    /* Builder stage */
    public Player()
    {
        this.player         = null;
        this.currentPath    = "";
        this.state          = STOP;
        this.setChanged();
    }

    /* Player stage */
    public void Load(String path){
        if(state != STOP)
            Stop();
        
        try{
            currentPath = path;
            if( path != null )
            {
                player = new LillePlayer(currentPath);
                player.setVolume(10);
                Equalizer eq = new Equalizer();
                eq.getBand(0);
            }
            else
                currentPath = "";
            state = LOAD;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void PlayPause(){
        if(state == STOP){
            Load(currentPath);
            PlayPause();
        }else if(state == LOAD){
            if( !currentPath.equals("") ) {
                LaunchListenThread llt = new LaunchListenThread(player);
                llt.start();
                state = PLAY;
            }
            else {
               System.out.println("No music loaded.");
                state = STOP;
            }
        }else if(state == PLAY){
            player.pause();
        }
    }
    
    /**
     * Relance une musique.
     */
    public void Play() {
        Load(currentPath);
        PlayPause();
    }
    
    public void Stop(){ // TODO Mhh... Il y a de la latence quand on arr�te la musique.
        if(state == LOAD || state == PLAY) {
            state = STOP;
            player.close();
        }
    }
    
    /**
     * Modifie le path de la musique ˆ jouer.
     * Si la musique est en cours de lecture, il passe ˆ celle-ci.
     * @param path
     */
    public void setPath(String path) {
        currentPath = path;
        if( state == PLAY )
            Play();
    }
    
    public int getPlayerState() {
        return state;
    }
    
    public float getVolume(){
        return volume;
    }
    
    // TODO Apparemment c'est en valeur logarithmique ! Conversion nŽcessaire pour �tre user-friendly
    public void setVolume(float level){
        volume = level;
        if( player != null)
            player.setVolume(level);
        setChanged();
        notifyObservers("volumeUpdate");
    }
    
    public int getDuration(){
        if(player == null)
            return 0;
        return player.getDuration();
    }
    
    public LillePlayer getMediaPlayer(){
        return player;
    }
    
    public int getPosition(){
        return position;
    }
    
    public void setPosition(int pos){
        player.setPosition(pos);
        position = pos;
    }
    
    /**
     * DŽclenche le forward
     * @param rewind si true, alors dŽclenche le rewind
     */
    public void doForward(boolean rewind)
    {
        ft = new ForwardThread(state, rewind);
        ft.start();
    }
    
    /**
     * Arr�te le forward ou le rewind
     */
    public void stopForward()
    {
        ft.interrupt();
        
    }
    
    class LaunchListenThread extends Thread{
        private LillePlayer playerInterne;
        public LaunchListenThread(LillePlayer p){
            playerInterne = p;
        }
        public void run(){
            try{            
                //System.out.println("LaunchEvent");
                PlayThread pt = new PlayThread();
                pt.start();
                
                while(!playerInterne.isComplete()){
                    position = playerInterne.getPosition();
                    if(player == playerInterne)
                        //System.out.println("PositionEvent");
                    try{
                        Thread.sleep(clockrate);
                        setChanged();
                        notifyObservers("timerUpdate");
                    }catch(Exception e){ e.printStackTrace(); }
                }
                
                if(player == playerInterne) {
                    System.out.println("EndEvent " + state);
                    if( state == PLAY ) { // Le player etait en train de jouer et n'a pas ete arrete par le bouton stop.
                        setChanged();
                        notifyObservers("engine.nextMusic");
                    }
                }
            }catch(Exception e){ e.printStackTrace(); }
        }
        
        class PlayThread extends Thread{
            public void run(){
                try {
                    playerInterne.play();
                }catch(JavaLayerException e){ e.printStackTrace(); }
            }
        }
    }

    /**
     * Gere le forward/rewind en thread pour une meilleure efficacite.
     */
    class ForwardThread extends Thread {
        
        private static final int forwardSpeed = 2000;
        private static final int forwardRate  = 50;
        
        private boolean rewind;
        private boolean wasPlaying;
        
        public ForwardThread(int internalState, boolean rewind)
        {
            this.rewind = rewind;
            Stop();
            System.out.println("Internal state : " + internalState);
            wasPlaying = (internalState == PLAY);
        }
        
        public void run()
        {
            try {
                while(!interrupted()) {
                    Thread.sleep(forwardRate);
                    if(rewind) {
                        position -= forwardSpeed;
                        if(position < 0)
                            position = 0;
                    }
                    else
                        position += forwardSpeed;
                    setChanged();
                    notifyObservers("timerUpdate");
                }
            } catch (InterruptedException e) {
                if(wasPlaying) {
                    Play();
                    setPosition(position); // TODO Passer a la musique suivante si on arrive au bout (ce qui n'est pour l'instant n'est pas facile ˆ dŽtecter ...
                }
                //e.printStackTrace();
            }
        }
    }
}
