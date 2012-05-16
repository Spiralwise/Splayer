package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

import org.farng.mp3.TagException;

import data.Library;

/**
 * Classe representant la bibliotheque et la playlist.
 * @author Sebastien Poulmane & Loic Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewPlaylist extends JFrame {
        
	/* Interface stage */
	// Panels
	private JPanel panel;
	private JScrollPane scrollPlaylist; // Necessaire pour rendre une liste visible
	// Interactive components
	private JList playlist;
	private JTable bibliotheque;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Components
        playlist = new JList();
        playlist.setDragEnabled(true);
        playlist.setDropMode(DropMode.INSERT);
        scrollPlaylist = new JScrollPane(playlist);
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.gridx = layoutManager.gridy = 1;
        panel.add(scrollPlaylist, layoutManager);
        

//        getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
//        getContentPane().add(tableau, BorderLayout.CENTER);
        
		bibliotheque = new JTable(new Library());
        getContentPane().add(new JScrollPane(bibliotheque), BorderLayout.CENTER);
        this.pack();

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
    
    /* Implementation stage */
    /**
     * Specifie / Met a jour la playlist.
     * @param list
     */
    public void setPlaylist(DefaultListModel list)
    {
        this.playlist.setModel(list);
    }
    
}
