package data;

import java.io.IOException;
import java.util.Observable;

import javax.swing.DefaultListModel;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class SplayerDataManager extends Observable {
    
    /* Data stage */
    private Playlist playlist;
    private Library library;
    
    /* Builder stage */
    public SplayerDataManager()
    {
        // Data initialization
        this.playlist = new Playlist();
        this.library = new Library();
        
        // Loading music samples
         try {
			playlist.add(new Music("./data/music/01 Act On Instinct.mp3"));
			playlist.add(new Music("./data/music/01 Hell March.mp3"));
			playlist.add(new Music("./data/music/13 School.mp3"));
			playlist.add(new Music("./data/music/Sonic 3 - Hydrocity Zone Act 2 [Pitched Up].mp3"));
			playlist.add(new Music("./data/music/01 Head Like A Hole.mp3"));
        } catch (IOException e) {
            System.err.println("Splayer:DataManager: Can't open file.");
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            System.err.println("Splayer:DataManager: Unknown tag.");
            e.printStackTrace();
        } catch (InvalidDataException e) {
            System.err.println("Splayer:DataManager: Invalid data.");
            e.printStackTrace();
        }
         
        // Force init update
        this.setChanged();
        
        // Post-init messages
        System.out.println("Splayer:DataManager initialized.");
    }
    
    /* Implementation stage */
    
    /**
     * Connecte la librairie � la base sql
     * @throws TagException 
     * @throws IOException 
     */
    // TODO Pas de code ? V�rifier que �a existait d�j�. Sinon impl�menter

    /**
     * Ajoute une musique a la suite de la playlist courrante.
     * @param music
     */
    public void addToPlaylist(Music music)
    {
        playlist.add(music);
        setChanged();
        notifyObservers("playlistUpdate");
    }
    
    /**
     * Renvoie pour une JList un ListModel de la playlist.
     * @return un ListModel de la playlist
     */
    public DefaultListModel getPlaylist()
    {
        return playlist.getList();
    }

    /**
     * Renvoie le path de la musique en cours de lecture � charger.
     * @return path de la musique en cours, null si l'index ne correspond � aucune musique en cours
     */
    public String getCurrentMusicPath()
    {
        return playlist.getCurrentMusicPath();
    }
    
    /**
     * Retourne l'objet musique en cours de lecture.
     * @return l'objet musique en cours de lecture, null si l'index ne pointe sur aucune musique
     */
    public Music getCurrentMusic()
    {
        return playlist.getCurrentMusic();
    }
    
    /**
     * Passe � la musique suivante ou pr�c�dente.
     * @param forward Si vraie, passe � la musique suivante, sinon � la musique pr�c�dente.
     * @return path de la musique suivante/pr�c�dente
     */
    public String nextMusic(boolean forward)
    {
        playlist.moveIndex(forward);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }

    /**
     * S�lectionne une musique dans la playlist.
     * @param playlistIndex index de playlist de la musique � jouer
     * @return path de la musique s�lectionn�e, null si l'index ne correspond � aucune musique
     */
    public String selectMusic(int playlistIndex)
    {
        playlist.selectMusic(playlistIndex);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }
    
    /**
     * D�place une musique dans la playlist.
     * @param selectedindex index de la musique � d�placer
     * @param insertindex index de destination, la musique qui s'y trouvait se placera juste en dessous
     */
    public void moveMusic(int selectedindex, int insertindex)
    {
        playlist.move(selectedindex, insertindex);
    }
    
    public void removeMusic(int removeindex)
    {
        playlist.remove(removeindex);
    }

    public void shufflePlaylist()
    {
        playlist.shuffle();
    }
}
