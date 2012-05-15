package data;

import javax.swing.DefaultListModel;


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
        if( list.getSize() == 0 )
            return null;
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
    
    /**
     * Retourne le path de la musique en cours.
     * @return
     */
    public String getCurrentMusic()
    {
        return ((Music)list.getElementAt(index)).getPath();
    }
}
