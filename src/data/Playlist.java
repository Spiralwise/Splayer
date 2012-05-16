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
        // TODO GŽrer le cas o on envoie une liste de la bibliothque
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
     * Un index est incrŽmentŽ au fur et ˆ mesure de la lecture.
     * @return index de la musique en cours de lecture
     */
    public int getIndex()
    {
        return index;
    }
    
    /**
     * Passe ˆ la musique adjacente dans la playlist. Si l'index arrive ˆ la fin, il revient au dŽbut. Et inversement s'il remonte au dŽbut.
     * @param forward Si vraie, alors passe ˆ la musique suivante. Sinon passe ˆ la musique prŽcŽdente.
     */
    public void moveIndex(boolean forward)
    {
        if( forward )
            index++;
        else
            index --;
        
        if( index > list.getSize() )
            index = 0;
        else if( index < 0 )
            index = list.getSize() - 1;
    }
    
    /**
     * Ajoute une musique ˆ la fin de la playlist.
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
