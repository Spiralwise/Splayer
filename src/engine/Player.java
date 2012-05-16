package engine;

import javazoom.jl.decoder.Equalizer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.LillePlayer;

public class Player {
    
    /* Enum stage */
    public final static int STOP = 0;
    public final static int LOAD = 1;
    public final static int PLAY = 2;
    
    /* Data stage */
    private LillePlayer player;
    private String currentPath;
    
    private int state;
    private float volume = 1;
    private int position = 0;

    /* Builder stage */
    public Player()
    {
        this.player = null;
        this.currentPath = "";
        this.state = STOP;
    }
    
    /* Player stage */
    public void Load(String path){
        if(state != STOP)
            Stop();
        
        try{
            currentPath = path;
            player = new LillePlayer(currentPath);
            player.setVolume(volume);
            Equalizer eq = new Equalizer();
            eq.getBand(0);
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
    
    public void Stop(){ // TODO Mhh... Il y a de la latence quand on arr�te la musique.
        if(state == LOAD || state == PLAY){
            player.close();
            state = STOP;
        }
    }
    
    /**
     * Modifie le path de la musique � jouer.
     * @param path
     */
    public void setPath(String path) {
        currentPath = path;
    }
    
    public int getPlayerState() {
        return state;
    }
    
    public float getVolume(){
        return volume;
    }
    
    public void setVolume(float level){
        volume = level;
        player.setVolume(level);
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
    
    class LaunchListenThread extends Thread{
        private LillePlayer playerInterne;
        public LaunchListenThread(LillePlayer p){
            playerInterne = p;
        }
        public void run(){
            try{            
                System.out.println("LaunchEvent");
                PlayThread pt = new PlayThread();
                pt.start();
                
                while(!playerInterne.isComplete()){
                    position = playerInterne.getPosition();
                    if(player == playerInterne)
                        //System.out.println("PositionEvent");
                    try{
                        Thread.sleep(200);
                    }catch(Exception e){ e.printStackTrace(); }
                }
                
                if(player == playerInterne)
                    System.out.println("EndEvent");
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

}
