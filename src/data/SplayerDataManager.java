package data;

import java.io.IOException;
import java.util.Observable;

import javax.swing.DefaultListModel;

import org.farng.mp3.TagException;

public class SplayerDataManager extends Observable {
    
    /* Data stage */
    private Playlist playlist;
    
    /* Builder stage */
    public SplayerDataManager()
    {
        this.playlist = new Playlist();
        System.out.println("SplayerDataManager initialized.");
        try {
            playlist.add(new Music("./data/music/01 Act On Instinct.mp3"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        }
        this.setChanged();
    }
    
    /* Implementation stage */
    /**
     * Ajoute une musique ˆ la suite de la playlist courrante.
     * @param music
     */
    public void addToPlaylist(Music music)
    {
        playlist.add(music);
        setChanged();
        notifyObservers();
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
     * Renvoie le path de la musique en cours de lecture ˆ charger.
     * @return path de la musique en cours
     */
    public String getCurrentMusicPath()
    {
        return playlist.getCurrentMusic();
    }
}
