package engine.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import engine.SplayerEngine;

public class SplayerKeyListener implements KeyListener
{
    private SplayerEngine engine;
//    private String world;
    
    public SplayerKeyListener(SplayerEngine engine)
    {
        this.engine = engine;
    }

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key pressed");
//		this.world+=e.getKeyChar();
//		System.out.println(world);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("je relache une touche");
		//System.out.println(((JTextField)e.getSource()).getText());
		//engine.search(((JTextField)e.getSource()).getText());
		engine.runTimer(((JTextField)e.getSource()).getText());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("key typed");
	}
    
}
