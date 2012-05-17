package engine.listener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.SplayerEngine;

public class SplayerSliderListener implements ChangeListener {

    private SplayerEngine engine;
    
    public SplayerSliderListener(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    @Override
    public void stateChanged(ChangeEvent event)
    {
        if( ((JSlider)event.getSource()).getValueIsAdjusting() ) // Emp�che de rappeler le listener r�cursivement (puisque c'est un changement d'�tat)
            engine.moveReadHead( (int) ((JSlider)event.getSource()).getValue() );
    }

}
