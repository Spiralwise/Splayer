package data;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class MusicHandler extends TransferHandler {

    private SplayerDataManager sdm;
    
    /* Builder stage */
    public MusicHandler(SplayerDataManager sdm)
    {
        this.sdm = sdm;
    }
    
    /* Importation stage */
    public boolean canImport(TransferSupport info)
    {
        if(info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
            return true;
        return false;
    }
    
    public boolean importData(TransferHandler.TransferSupport info)
    {
        Transferable t = info.getTransferable();
        if(info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
            try {
                String filename = "";
                List files = (List)(t.getTransferData(DataFlavor.javaFileListFlavor));
                filename = ((File)files.get(0)).getAbsolutePath();
                sdm.addToPlaylist(new Music(filename));
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
