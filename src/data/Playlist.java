package data;

import java.util.ArrayList;

import org.farng.mp3.MP3File;

public class Playlist {

    /* Data stage */
    private int index;
    private ArrayList<MP3File> list;
    
    /* Builder stage */
    public Playlist()
    {
        this.index = 0;
        this.list = new ArrayList<MP3File>();
        // TODO Gérer le cas où on envoie une liste de la bibliothèque
    }
    
    /* Implementation stage */
    public MP3File[] toArray()
    {
        return (MP3File[])list.toArray();
    }
    
    public int getIndex()
    {
        return index;
    }
}
