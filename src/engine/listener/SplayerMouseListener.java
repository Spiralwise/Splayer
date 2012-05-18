package engine.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;

import engine.Player;
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
        // Simple clic ...
        if( event.getClickCount() == 1) {
            // .. sur une JList (normalement, la playlist)
            if( event.getSource() instanceof JList )
                engine.setSelectedMusic( ((JList)event.getSource()).getSelectedIndex() );
        }
        // Double-clic ...
        if( event.getClickCount() == 2 ) {
            // ... sur une JList (normalement, la playlist)
            if( event.getSource() instanceof JList )
                engine.playThisMusic( ((JList)event.getSource()).getSelectedIndex() );
            // ... sur une JTable (normalement, la bibliotheque)
            else if( event.getSource() instanceof JTable )
                engine.addRandomMusic();
        }
    }
    
    public void mousePressed(MouseEvent event)
    {
        JComponent component = (JComponent)event.getSource();
        if( component.getName().equals("forward") )
            engine.triggerForward(Player.FORWARD);
        else if( component.getName().equals("rewind") )
            engine.triggerForward(Player.REWIND);
    }
    
    public void mouseReleased(MouseEvent event) throws NullPointerException
    {
        JComponent component = (JComponent)event.getSource();
        if( component.getName().equals("forward")
                || component.getName().equals("rewind") )
            engine.triggerForward(0);
    }

}
