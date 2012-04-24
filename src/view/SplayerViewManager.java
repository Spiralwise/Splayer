package view;

import java.util.ArrayList;


public class SplayerViewManager {

    // NOTE La première vue est la main view.
    private ArrayList<ViewModule> views;
    
    /**
     * Ajoute une vue au SVM
     * @param module Vue à ajouter
     */
    public void add(ViewModule module) {
        views.add(module);
    }
      
}
