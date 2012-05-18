package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class SplayerDataManager extends Observable {
    
    /* Data stage */
    private Playlist playlist;
    private Library library;
    
    private String [] cache; // Pour simuler des musiques sur le syst�me de fichier. 
    
    /* Builder stage */
    public SplayerDataManager()
    {
        // Data initialization
        this.playlist = new Playlist();
        this.library = new Library();
        
        // Loading music samples
        this.connect("");
        
        cache = new String[7];
         try {
            cache[0] = "./data/music/01 Act On Instinct.mp3";
            cache[1] = "./data/music/Sonic 3 - Credits [Pitched Up].mp3";
            cache[2] = "./data/music/01 Hell March.mp3";
            cache[3] = "./data/music/01 Head Like A Hole.mp3";
            cache[4] = "./data/music/02 Quake Main Theme.mp3";
            cache[5] = "./data/music/Sonic 3 - Hydrocity Zone Act 2 [Pitched Up].mp3";
            cache[6] = "./data/music/13 School.mp3";
            for(int i=0; i<3; i++) // On ne met qu les 3 premi�res
                playlist.add(new Music(cache[i]));
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
    
    public void connect(String word)
    {
		Connection connection = null;

    	try {
        	// load the sqlite-JDBC driver using the current class loader
    		Class.forName("org.sqlite.JDBC");
    		
    		// create a database connection
    		connection = DriverManager.getConnection("jdbc:sqlite:" + "./data/mp3database.sqlite");
    		Statement statement = connection.createStatement();
    		statement.setQueryTimeout(30);  // set timeout to 30 sec.
    		ResultSet rs;
    		if ( word.equals("") )
    			rs = statement.executeQuery("select * from songs");
    		else
    			rs = statement.executeQuery("select * from songs where title like \"%"+word+"%\"");
//    			rs = statement.executeQuery("select * from songs where title=\""+word+"\"");
//    					"" OR artist=\""+word+"\" OR album=\""+word+"\" OR genre=\""+word+"\"");
		
    		this.library.ClearAll();
    		while(rs.next())
			{
				// transfere les donnees dans le tableau
				this.library.addSong(new Music(
							rs.getString("title"),
							rs.getString("artist"),
							rs.getString("album"),
							Integer.valueOf(rs.getString("year")),
							rs.getString("genre"),
							Integer.valueOf(MntoMs(rs.getString("duration")))));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
    		System.err.println(e.getMessage());
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
    		try
    		{
    			if(connection != null)
    				connection.close();
    		}
    		catch(SQLException e)
    		{
    			// connection close failed.
    			System.err.println(e);
    		}
    	}
    	setChanged();
    	notifyObservers("libraryUpdate");
    }

    private int MntoMs(String value){
    	StringTokenizer token = new StringTokenizer(value,":");
    	int minutes = Integer.valueOf(token.nextToken());
//    	System.out.println("les minutes "+minutes);
    	int secondes = Integer.valueOf(token.nextToken());
//    	System.out.println("les secondes "+secondes);
    	return (minutes*60+secondes)*1000;
    }
    
    /**
     * Ajoute une musique ˆ la playlist courrante ˆ l'emplacement indiquŽ.
     * @param music
     * @param index
     */
    public void addToPlaylistAt(Music music, int index)
    {
        playlist.addAt(music, index);
        setChanged();
        notifyObservers("playlistUpdate");
    }
    
    public void addRandomMusic()
    {
        int randomindex = (int)(Math.random() * cache.length);
        try {
        addToPlaylistAt(new Music(cache[randomindex]), playlist.getSize());
        } catch (Exception e) {
            System.err.println("Splayer:DataMaanger: Unable to load music from " + cache[randomindex] );
        }
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
     * Retourne la librairie
     * @return
     */
    public Library getLibrary() {
    	return this.library;
    }

    /**
     * Renvoie le path de la musique en cours de lecture ˆ charger.
     * @return path de la musique en cours, null si l'index ne correspond ˆ aucune musique en cours
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
     * Passe ˆ la musique suivante ou prŽcŽdente.
     * @param forward Si vraie, passe ˆ la musique suivante, sinon ˆ la musique prŽcŽdente.
     * @return path de la musique suivante/prŽcŽdente
     */
    public String nextMusic(boolean forward)
    {
        playlist.moveIndex(forward);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }

    /**
     * SŽlectionne une musique dans la playlist.
     * @param playlistIndex index de playlist de la musique ˆ jouer
     * @return path de la musique sŽlectionnŽe, null si l'index ne correspond ˆ aucune musique
     */
    public String selectMusic(int playlistIndex)
    {
        playlist.selectMusic(playlistIndex);
        setChanged();
        notifyObservers("playlistSelection");
        return playlist.getCurrentMusicPath();
    }
    
    /**
     * DŽplace une musique dans la playlist.
     * @param selectedindex index de la musique ˆ dŽplacer
     * @param insertindex index de destination, la musique qui s'y trouvait se placera juste en dessous
     */
    public void moveMusic(int selectedindex, int insertindex)
    {
        playlist.move(selectedindex, insertindex);
    }
    
    /**
     * Retire une musique de la playlist courrante.
     * @param removeindex
     */
    public void removeMusic(int removeindex)
    {
        playlist.remove(removeindex);
        setChanged();
        notifyObservers("playlistSelection");
    }
    
    /**
     * Vide toute la playlist en cours.
     */
    public void emptyPlaylist()
    {
        playlist.empty();
        setChanged();
        notifyObservers("playlistSelection");
    }

    /**
     * M�lange la playlist en cours.
     */
    public void shufflePlaylist()
    {
        playlist.shuffle();
    }

    /**
     * Active/d�sactive le loop
     */
    public void loop()
    {
        playlist.toggleLoop();
        setChanged();
        notifyObservers("toggleLoop");
    }
    
    /**
     * Renvoie true si la r�p�tition est activ�e pour la playlist courrante.
     * @return
     */
    public boolean isLoop()
    {
        return playlist.isLoop();
    }
}
