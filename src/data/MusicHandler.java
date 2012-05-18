package data;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class MusicHandler extends TransferHandler {

    private SplayerDataManager sdm;
    private int selectedindex;
    
    /* Builder stage */
    public MusicHandler(SplayerDataManager sdm)
    {
        this.sdm = sdm;
        this.selectedindex = 0;
    }
    
    /* Exportation stage */
    public int getSourceActions(JComponent c)
    {
        return MOVE;
    }
    
    public Transferable createTransferable(JComponent c)
    {
        DefaultListModel listModel = (DefaultListModel) ((JList)c).getModel();
        selectedindex = ((JList)c).getSelectedIndex();
        return new TransferableMusic((Music)listModel.getElementAt(selectedindex));
    }
    
    /* Importation stage */
    public boolean canImport(TransferSupport info)
    {
        if(info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
                || info.isDataFlavorSupported(TransferableMusic.musicFlavor)
                || info.isDataFlavorSupported(DataFlavor.stringFlavor))
            return true;
        return false;
    }
    
    public boolean importData(TransferHandler.TransferSupport info)
    {
        Transferable t = info.getTransferable();
        // Importation d'une musique depuis l'explorateur de fichiers
        if(info.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
            JList list = ((JList)info.getComponent());
            try {
                String filename = "";
                List files = (List)(t.getTransferData(DataFlavor.javaFileListFlavor));
                filename = ((File)files.get(0)).getAbsolutePath();
                sdm.addToPlaylistAt(new Music(filename), list.getDropLocation().getIndex());
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        // Importation en interne (d'une musique au sein de la playlist ou depuis la bibliothèque)
        else if(info.isDataFlavorSupported(TransferableMusic.musicFlavor))
        {// NOTE Mode avancée pas développé.
            JList list = ((JList)info.getComponent());
            int insertindex = list.getDropLocation().getIndex();
            if( selectedindex != insertindex)
                sdm.moveMusic(selectedindex, insertindex);
            return true;
        }
        // Importation basique depuis la playlist
        else if(info.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            sdm.addRandomMusic();
        }
        return false;
    }
}
