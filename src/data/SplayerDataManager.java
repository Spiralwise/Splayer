package data;

import java.io.IOException;
import java.util.Observable;

import javax.swing.DefaultListModel;

import org.farng.mp3.MP3File;
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
    
    public void addToPlaylist(Music music)
    {
        playlist.add(music);
        setChanged();
        notifyObservers();
    }
    
    public DefaultListModel getPlaylist()
    {
        System.out.println("dbg: SDM: current=" + playlist.getCurrentMusic());
        return playlist.getList();
    }

}
