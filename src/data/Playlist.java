package data;

import javax.swing.DefaultListModel;


public class Playlist {

    /* Data stage */
    private int index;
    private boolean loop;
    private DefaultListModel list;
    
    /* Builder stage */
    public Playlist()
    {
        this.index = -1; // -1 = Aucune musique en cours
        this.loop = false;
        this.list = new DefaultListModel();
        // TODO G≈Ωrer le cas oÔøΩ on envoie une liste de la bibliothÔøΩque
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
     * Un index est incremente au fur et a mesure de la lecture.
     * @return index de la musique en cours de lecture
     */
    public int getIndex()
    {
        return index;
    }
    
    public int getSize()
    {
        return list.getSize();
    }
    
    /**
     * @param forward Si vraie, alors passe ÀÜ la musique suivante. Sinon passe ÀÜ la musique pr≈Ωc≈Ωdente.
     */
    public void moveIndex(boolean forward)
    {
        if( forward )
            index++;
        else
            index --;
        
        if( index >= list.getSize() && loop )
            index = 0;
        else if( index < 0 )
            index = list.getSize() - 1;
        else if( index >= list.getSize() && !loop )
            index = -1;
    }
    
    /**
     * Ajoute une musique a la fin de la playlist.
     * @param music
     */
    public void add(Music music)
    {
        list.addElement(music);
        // Si on charge la première musique, on place l'index sur celle-ci.
        if( index == -1 ) index = 0;
    }
    
    /**
     * Ajoute une musique a l'emplacement indique.
     * @param music
     * @param insertindex
     */
    public void addAt(Music music, int insertindex)
    {
        list.add(insertindex, music);
        // Recaller l'index courrant en conséquence
        if( insertindex < index )
            index++;
    }
    
    /**
     * Retourne l'objet musique en cours de lecture.
     * @return l'objet musique en cours de lecture
     */
    public Music getCurrentMusic()
    {
        if( index < 0 )
            return null;
        return (Music)list.getElementAt(index);            
    }
    
    /**
     * Retourne le path de la musique en cours.
     * @return null si l'index ne correspond à aucune musique
     */
    public String getCurrentMusicPath()
    {
        if( index < 0 )
            return null;
        return ((Music)list.getElementAt(index)).getPath();
    }

    /**
     * Sélectionne une musique dans la playlist.
     * @param playlistIndex index de playlist de la musique à jouer
     * @return path de la musique sélectionnée, null si l'index ne correspond à aucune musique
     */
    public void selectMusic(int playlistIndex)
    {
        if( playlistIndex < 0 || playlistIndex >= list.getSize() )
            index = -1;
        index = playlistIndex;
    }

    /**
     * Déplace une musique dans la playlist.
     * @param selectedindex index de la musique à déplacer
     * @param insertindex index de destination, la musique qui s'y trouvait se placera juste en dessous
     */
    public void move(int selectedindex, int insertindex)
    {
        if( selectedindex != insertindex ) { // Condition initiale, les deux indexs doivent être différents, sinon ça ne sert à rien de déplacer.
            list.add(insertindex, (Music)list.get(selectedindex));
            if( insertindex < selectedindex ) {
                selectedindex++;
                index++;
            }
            else
                index--;
            list.remove(selectedindex);
        }
    }
    
    /**
     * Retire une musique de la playlist.
     * @param removeindex index de la musique à supprimer
     */
    public void remove(int removeindex)
    {
        if( removeindex > 0 || removeindex < list.getSize() ) {
            list.remove(removeindex);
            if( removeindex > index )
                index--;
            if( index < -1 && !list.isEmpty() )
                index = 0;
        }
    }
    
    /**
     * Vide la playlist.
     */
    public void empty()
    {
        list.removeAllElements();
        index = -1;
    }
    
    /**
     * Ordonne aléatoirement la liste.
     */
    public void shuffle()
    {
        DefaultListModel buffer = new DefaultListModel();
        Music bufferMusic = (Music)list.elementAt(index);
        int i;
        while(!list.isEmpty()) {
            i = (int)(Math.random() * list.getSize());
            buffer.addElement(list.remove(i));
        }
        while(!buffer.isEmpty()) { // TODO Je suis persuadé qu'il y a une méthode plus propre (je pensais qu'en référençant list avec buffer, ça suffirait....
            list.addElement(buffer.remove(0));
        }
        // Restauration de l'index
        index = list.indexOf(bufferMusic);
    }
    
    /**
     * Active/désactive le loop
     */
    public void toggleLoop()
    {
        loop = !loop;
    }

    /**
     * Renvoie true si la répétition est activée.
     * @return
     */
    public boolean isLoop()
    {
        return loop;
    }
}
