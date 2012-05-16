package data;

import javax.swing.DefaultListModel;


public class Playlist {

    /* Data stage */
    private int index;
    private DefaultListModel list;
    
    /* Builder stage */
    public Playlist()
    {
        this.index = -1; // -1 = Aucune musique en cours
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
        if( list.getSize() == 0 )
            return null;
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
     * Passe � la musique adjacente dans la playlist. Si l'index arrive � la fin, il revient au d�but. Et inversement s'il remonte au d�but.
     * @param forward Si vraie, alors passe � la musique suivante. Sinon passe � la musique pr�c�dente.
     */
    public void moveIndex(boolean forward)
    {
        if( forward )
            index++;
        else
            index --;
        
        if( index >= list.getSize() )
            index = 0;
        else if( index < 0 )
            index = list.getSize() - 1;
    }
    
    /**
     * Ajoute une musique � la fin de la playlist.
     * @param music
     */
    public void add(Music music)
    {
        list.addElement(music);
        // Si on charge la premi�re musique, on place l'index sur celle-ci.
        if( index == -1 ) index = 0;
    }
    
    /**
     * Retourne l'objet musique en cours de lecture.
     * @return l'objet musique en cours de lecture
     */
    public Music getCurrentMusic()
    {
        if( index != -1 )
            return (Music)list.getElementAt(index);
        else
            return null;
    }
    
    /**
     * Retourne le path de la musique en cours.
     * @return null si l'index ne correspond � aucune musique
     */
    public String getCurrentMusicPath()
    {
        if( index == -1 )
            return null;
        return ((Music)list.getElementAt(index)).getPath();
    }

    /**
     * S�lectionne une musique dans la playlist.
     * @param playlistIndex index de playlist de la musique � jouer
     * @return path de la musique s�lectionn�e, null si l'index ne correspond � aucune musique
     */
    public void selectMusic(int playlistIndex)
    {
        if( playlistIndex < 0 || playlistIndex >= list.getSize() )
            index = -1;
        index = playlistIndex;
    }
}
