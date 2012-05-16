package engine.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import engine.SplayerEngine;

public class SplayerMouseListener extends MouseAdapter
{
    private SplayerEngine engine;
    
    public SplayerMouseListener(SplayerEngine engine)
    {
        this.engine = engine;
    }
    
    public void mouseClicked(MouseEvent event)
    {
        // Double-clic ...
        if( event.getClickCount() == 2 ) {
            // ... sur une JList (normalement, la playlist)
            if( event.getSource() instanceof JList )
                engine.playThisMusic( ((JList)event.getSource()).getSelectedIndex() );
        }
    }

}
