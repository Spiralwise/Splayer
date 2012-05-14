package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe représentant la bibliothèque et la playlist.
 * @author Sébastien Poulmane & Loïc Daara
 *
 */
public class SplayerViewPlaylist extends JFrame {
    
    /* Data stage */
    private boolean bLibrary; 
    
    /* Interface stage */
    // Panels
    private JPanel panel;
    // Interactive components
    private JList playlist;
    private JList library;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Data
        bLibrary = false;
        
        // Components
        playlist = new JList();
        library = new JList();
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(playlist, layoutManager);
        
        // Packing
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
