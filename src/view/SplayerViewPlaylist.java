package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

/**
 * Classe représentant la bibliothèque et la playlist.
 * @author Sébastien Poulmane & Loïc Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewPlaylist extends JFrame {
        
    /* Interface stage */
    // Panels
    private JPanel panel;
    private JScrollPane scrollPlaylist; // Nécessaire pour rendre une liste visible
    // Interactive components
    private JList playlist;
    private JList library;
    
    private JButton fakeButton;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Components
        playlist = new JList();
        library = new JList();
        
        playlist.setDragEnabled(true);
        playlist.setDropMode(DropMode.INSERT);
        scrollPlaylist = new JScrollPane(playlist);
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(scrollPlaylist, layoutManager);
        
        // Packing
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    /* Interface stage */
    public void setPlaylistHandler(TransferHandler handler)
    {
        playlist.setTransferHandler(handler);
    }
    
    public void setPlaylistListener(Object listener)
    {
        playlist.addMouseListener((MouseListener)listener);
    }
    
    /* Implementation stage */
    /**
     * Spécifie / Met à jour la playlist.
     * @param list
     */
    public void setPlaylist(DefaultListModel list)
    {
        this.playlist.setModel(list);
    }
    
}
