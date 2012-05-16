package data;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;


/**
 * Extension de l'objet MP3File afin de faciliter l'extraction d'information.
 * @author Sebastien Poulmane & Loic Daara
 *
 */
public class Music extends Mp3File {

    // Données formatées pour un usage facilité avec Splayer
    private String url;
    private String splayerAuthor;
    private String splayerTitle;
    private String splayerAlbum;
    private int duration; // En ms
    
    /**
     * Constructeur principal
     * @param filename
     * @throws IOException
     * @throws InvalidDataException 
     * @throws UnsupportedTagException 
     */
    public Music(String filename) throws IOException, UnsupportedTagException, InvalidDataException
    {
        super(filename);
        this.url = filename;
        // Extraction des information selon le tag
            // ID3v2
        if( this.hasId3v2Tag() ) {
            ID3v2 tag = this.getId3v2Tag();
            if( tag != null ) {
                this.splayerAuthor = tag.getArtist();
                this.splayerTitle = tag.getTitle();
                this.splayerAlbum = tag.getAlbum();
            }
        }
            // ID3v1
        else if( this.hasId3v1Tag() ) {
            ID3v1 tag = this.getId3v1Tag();
            if( tag != null ) {
                try {
                    this.splayerAuthor = tag.getArtist();
                    this.splayerTitle = tag.getTitle();
                    this.splayerAlbum = tag.getAlbum();
                    // TODO Retrouver la durée de la musique par un autre moyen subversif !
                } catch (Exception e) {
                    System.out.println("Music entity:Unable to read tag ID3v1.");
                    e.printStackTrace(System.err);
                }
            }
        }
            // Aucune information
        else {
            // TODO Que faire en cas d'absence de tags ID3 ?
        }
        // Récupération de la durée
        try {
            AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(new File(filename));
            Map properties = baseFileFormat.properties();
            duration = (int) ( ((Long)properties.get("duration")).intValue() / 1000 );
            //duration = (int) ( frames / format.getFrameRate() * 1000 );
        } catch (UnsupportedAudioFileException e) {
            duration = 0;
            e.printStackTrace();
        }
    }
    
    /* Getter stage */
    public String getAuthor()
    {
        return splayerAuthor;
    }
    
    public String getTitle()
    {
        return splayerTitle;
    }
    
    public String getAlbum()
    {
        return splayerAlbum;
    }
    
    /**
     * Durée en milliseconde
     * @return
     */
    public int getDuration()
    {
        return duration;
    }
    
    /**
     * Durée en seconde
     * @return
     */
    public int getDurationInSec()
    {
        return (int) (duration/1000);
    }
    
    /**
     * 
     * @return chemin relatif du fichier mp3
     */
    public String getPath()
    {
        return url;
        //return this.getMp3file().getAbsolutePath();
    }

    public String toString()
    {
        return this.getId3v2Tag().getTitle();
    }
}
