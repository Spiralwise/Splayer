package view;

import javax.swing.JFrame;

/**
 * Classe spéciale chargée de construire l'interface à l'initialisation.
 * @author Sébastien Poulmane & Loïc Daara
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
