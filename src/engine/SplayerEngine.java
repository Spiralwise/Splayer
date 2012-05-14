package engine;

import view.SplayerViewManager;
import data.SplayerDataManager;

public class SplayerEngine {

    /* Data stage */
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    
    /* Builder stage */
    public SplayerEngine(SplayerDataManager sdm, SplayerViewManager svm)
    {
        this.sdm = sdm;
        this.svm = svm;
        System.out.println("SplayerEngine initialized.");
        System.out.println("Splayer ready to launch !");
    }
    
    /* Implementaton stage */
    /**
     * DŽmarre l'application.
     */
    public void launch()
    {
        svm.go();
    }
}
