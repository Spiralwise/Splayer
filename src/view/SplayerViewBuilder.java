package view;

import javax.swing.JFrame;

/**
 * Classe sp�ciale charg�e de construire l'interface � l'initialisation.
 * @author S�bastien Poulmane & Lo�c Daara
 *
 */

public class SplayerViewBuilder {

    public static void build (SplayerViewManager svm) {
        /* Build Main */
        SplayerViewMain main = new SplayerViewMain();        
        svm.add(main);
        
        /* Build Playlist */
        JFrame playlist = new JFrame("Playlist");
        svm.add(playlist);
        
        
        /* Build Library */
        JFrame library = new JFrame("Library");
        svm.add(library);
    }
}
