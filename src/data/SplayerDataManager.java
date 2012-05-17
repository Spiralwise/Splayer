package data;

import java.io.IOException;
import java.util.Observable;

import javax.swing.DefaultListModel;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class SplayerDataManager extends Observable {
    
    /* Data stage */
    private Playlist playlist;
    private Library librairy;
    
    /* Builder stage */
    public SplayerDataManager()
    {
        this.playlist = new Playlist();
        this.librairy = new Library();
        System.out.println("SplayerDataManager initialized.");
         try {
			playlist.add(new Music("./data/music/Runaway.mp3"));
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
        this.setChanged();
        System.out.println("Splayer:DataManager initialized.");
    }
    
    /* Implementation stage */
    
    /**
     * Connecte la librairie ‡ la base sql
     * @throws TagException 
     * @throws IOException 
     */
    

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
     * Renvoie le path de la musique en cours de lecture à charger.
     * @return path de la musique en cours, null si l'index ne correspond à aucune musique en cours
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
     * Passe à la musique suivante ou précédente.
     * @param forward Si vraie, passe à la musique suivante, sinon à la musique précédente.
     * @return path de la musique suivante/précédente
     */
    public String nextMusic(boolean forward)
    {
        playlist.moveIndex(forward);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }

    /**
     * Sélectionne une musique dans la playlist.
     * @param playlistIndex index de playlist de la musique à jouer
     * @return path de la musique sélectionnée, null si l'index ne correspond à aucune musique
     */
    public String selectMusic(int playlistIndex)
    {
        playlist.selectMusic(playlistIndex);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }
}
