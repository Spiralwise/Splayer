package engine.listener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.SplayerEngine;

public class SplayerSliderListener implements ChangeListener {

    private SplayerEngine engine;
    
    public SplayerSliderListener(SplayerEngine engine)
    {
        System.out.println("Create Slider Listener");
        this.engine = engine;
    }
    
    @Override
    public void stateChanged(ChangeEvent event)
    {
        if( ((JSlider)event.getSource()).getValueIsAdjusting() )
            engine.moveReadHead( (int) ((JSlider)event.getSource()).getValue() );
    }

}
