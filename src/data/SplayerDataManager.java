package data;

import java.util.Observable;

public class SplayerDataManager extends Observable {
    
    /* Data stage */
    private Playlist playlist;
    
    /* Builder stage */
    public SplayerDataManager()
    {
        this.playlist = new Playlist();
        System.out.println("SplayerDataManager initialized.");
    }

}
