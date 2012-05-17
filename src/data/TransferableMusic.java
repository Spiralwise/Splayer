package data;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableMusic implements Transferable {

    public static DataFlavor musicFlavor;
    private Music music;
    
    public TransferableMusic(Music music)
    {
        String mime = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + Music.class.getName();
        try {
            musicFlavor = new DataFlavor(mime);
            this.music = music;
        } catch(ClassNotFoundException e) {
            System.err.println("Splayer:DataManager: Music transfer corrupted.");
            e.printStackTrace(System.err);
        }
        
    }
    
    @Override
    public Object getTransferData(DataFlavor df)
            throws UnsupportedFlavorException, IOException {
        if( df == null )
            throw new IOException();
        else if( df.equals(musicFlavor) )
            return music;
        else if( df.equals(DataFlavor.stringFlavor) )
            return music.toString();
        return null;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { musicFlavor, DataFlavor.stringFlavor };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor df) {
        return (df.equals(musicFlavor)) || (df.equals(DataFlavor.stringFlavor));
    }

}
