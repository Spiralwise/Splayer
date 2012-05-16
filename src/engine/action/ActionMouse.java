package engine.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import engine.SplayerEngine;

public class ActionMouse extends MouseAdapter
{
    private SplayerEngine engine;
    
    public ActionMouse(SplayerEngine engine)
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
