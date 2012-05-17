package engine.listener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.SplayerEngine;

public class SplayerVolumeListener implements ChangeListener {

    private SplayerEngine engine;
    
    public SplayerVolumeListener(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    @Override
    public void stateChanged(ChangeEvent event)
    {
        if( ((JSlider)event.getSource()).getValueIsAdjusting() )
            engine.setVolume( (int)((JSlider)event.getSource()).getValue() );
    }

}
