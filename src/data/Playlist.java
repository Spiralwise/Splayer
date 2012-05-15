package data;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import org.farng.mp3.MP3File;

public class Playlist {

    /* Data stage */
    private int index;
    private DefaultListModel list;
    
    /* Builder stage */
    public Playlist()
    {
        this.index = 0;
        this.list = new DefaultListModel();
        // TODO Gérer le cas où on envoie une liste de la bibliothèque
    }
    
    /* Implementation stage */
    public Music[] toArray()
    {
        return (Music[])list.toArray();
    }
    
    public DefaultListModel getList()
    {
        return list;
    }
    
    /**
     * Un index est incrémenté au fur et à mesure de la lecture.
     * @return index de la musique en cours de lecture
     */
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Ajoute une musique à la fin de la playlist.
     * @param music
     */
    public void add(Music music)
    {
        list.addElement(music);
    }
    
    public Music getCurrentMusic()
    {
        return (Music)list.getElementAt(index);
    }
}
