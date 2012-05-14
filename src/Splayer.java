import data.SplayerDataManager;
import engine.SplayerEngine;
import view.SplayerViewBuilder;
import view.SplayerViewManager;


public class Splayer 
{
    public static void main(String [] args)
    {
        SplayerViewManager svm = new SplayerViewManager();
        SplayerViewBuilder.build(svm);
        
        SplayerDataManager sdm = new SplayerDataManager();
        SplayerEngine engine   = new SplayerEngine();
    }
}
