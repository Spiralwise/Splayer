import data.SplayerDataManager;
import engine.SplayerEngine;
import view.SplayerViewManager;


public class Splayer 
{
    public static void main(String [] args)
    {
        /* Initialization stage */
        SplayerViewManager svm = new SplayerViewManager();        
        SplayerDataManager sdm = new SplayerDataManager();
        SplayerEngine engine   = new SplayerEngine(sdm, svm);
        
        /* Launch stage */
        engine.launch();
    }
}
