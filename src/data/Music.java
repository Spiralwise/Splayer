package data;

import java.io.IOException;

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

    // Donnees formatees pour un usage facilite avec Splayer
    private String url;
    private String splayerAuthor;
    private String splayerTitle;
    private String splayerAlbum;
    private String splayerGenre;
    private int splayerDuration;
    private int splayerYear;
    
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
                    // TODO Retrouver la durŽe de la musique par un autre moyen subversif !
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
    }
    
    /**
     * 
     * @param Title 	titre de la chanson
     * @param Artist 	nom du groupe ou de l'artiste
     * @param Album 	titre de l'album
     * @param Year		année de sortie
     * @param Genre		style musical
     * @param Duration 	duree en ms
     */
    public Music(String Title, String Artist,String Album, int Year,String Genre, int Duration ){
        url="";
        splayerAuthor 	= Artist;
        splayerTitle  	= Title;
        splayerAlbum  	= Album;
        splayerGenre 	= Genre;
        splayerDuration	= Duration;
        splayerYear		= Year;
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
    
    public String getGenre()
    {
        return splayerGenre;
    }
    
    public int getYear()
    {
        return splayerYear;
    }
    
    public int getDuration()
    {
        return splayerDuration;
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
