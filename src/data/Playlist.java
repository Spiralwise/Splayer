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
        // TODO G�rer le cas o� on envoie une liste de la biblioth�que
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
     * Un index est incr�ment� au fur et � mesure de la lecture.
     * @return index de la musique en cours de lecture
     */
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Ajoute une musique � la fin de la playlist.
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
