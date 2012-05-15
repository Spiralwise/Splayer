import data.SplayerDataManager;
import engine.SplayerEngine;
import view.SplayerViewManager;


public class Splayer 
{
    public static void main(String [] args)
    {
        /* Initialization stage */
        SplayerDataManager sdm = new SplayerDataManager();
        SplayerViewManager svm = new SplayerViewManager();        
        SplayerEngine engine   = new SplayerEngine(sdm, svm);
        
        /* Launch stage */
        engine.launch();
    }
}
