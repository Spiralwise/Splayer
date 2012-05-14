package view;

import java.util.ArrayList;

import javax.swing.JFrame;


public class SplayerViewManager {

    // NOTE La premi�re vue est la main view.
    private ArrayList<JFrame> views;
    
    public SplayerViewManager() {
        this.views = new ArrayList<JFrame>();
    }
    
    /**
     * Ajoute une vue au SVM
     * @param module Vue � ajouter
     */
    public void add(JFrame module) {
        views.add(module);
    }
      
}
