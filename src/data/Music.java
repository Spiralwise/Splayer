package data;

import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class Music extends MP3File {

    public Music(String filename) throws IOException, TagException
    {
        super(filename);
    }

    public String toString()
    {
        return this.getID3v2Tag().getSongTitle();
    }
}
