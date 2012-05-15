package engine;

import view.SplayerViewManager;
import view.action.ActionPlay;
import data.MusicHandler;
import data.SplayerDataManager;

public class SplayerEngine {

    /* Data stage */
    private Player player;
    private SplayerDataManager sdm;
    private SplayerViewManager svm;
    
    /* Builder stage */
    public SplayerEngine(SplayerDataManager sdm, SplayerViewManager svm)
    {
        this.player = new Player();
        this.sdm = sdm;
        this.svm = svm;
        
        this.sdm.addObserver(this.svm);
        this.sdm.notifyObservers();
        
        this.svm.setAction("play", new ActionPlay(player));
        this.svm.setPlaylistHandler(new MusicHandler(this.sdm));
        
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
