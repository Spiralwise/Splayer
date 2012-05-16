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
 * Classe repr�sentant la biblioth�que et la playlist.
 * @author S�bastien Poulmane & Lo�c Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewPlaylist extends JFrame {
        
    /* Interface stage */
    // Panels
    private JPanel panel;
    private JScrollPane scrollPlaylist; // N�cessaire pour rendre une liste visible
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
     * Sp�cifie / Met � jour la playlist.
     * @param list
     */
    public void setPlaylist(DefaultListModel list)
    {
        this.playlist.setModel(list);
    }
    
}
