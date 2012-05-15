package view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import engine.Player;

@SuppressWarnings("serial")
public class ActionPlay extends AbstractAction {

    private Player player;
    
    public ActionPlay(Player player)
    {
        this.player = player;
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        player.start();
    }
    
}
