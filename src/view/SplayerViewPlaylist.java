package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

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
	private JPanel playlistPanel, libraryPanel;
	private JScrollPane scrollPlaylist; // Necessaire pour rendre une liste visible
	// Interactive components
    private HashMap<String, JButton> buttonPlaylist; // TODO Les bouttons sont des actions et pour bien d�couper le code, il faudrait qu'ils se trouvent dans le SVM.
        // TODO Si � terme il ne reste que un ou deux boutons, autant ne pas faire de hashmap. Sauf si on fusionne tous les boutons dans le ViewManager.
	private JList playlist;
	private JTable bibliotheque;
	// Ergo components
	private JTextArea tutoDnDPlaylist;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Components
            // Buttons
        buttonPlaylist = new HashMap<String, JButton>();
        buttonPlaylist.put("removeItem", new JButton());
        buttonPlaylist.put("empty", new JButton());
        buttonPlaylist.put("shuffle", new JButton());

            // Playlist
        playlist = new JList();
        playlist.setDragEnabled(true);
        playlist.setDropMode(DropMode.INSERT);
        scrollPlaylist = new JScrollPane(playlist);
        
            // Library
        bibliotheque = new JTable(new Library()); // TODO From Seb : Attention !!! La biblioth�que doit �tre cr�er par la DataManager !
        
            // Ergo
        // TODO Rendre plus joli et effacer si d�j� effectu� un Dnd
        tutoDnDPlaylist = new JTextArea("Cliquez-glissez un fichier mp3 dans la playlist depuis la biblioth�que ou votre explorateur de fichiers.");
        tutoDnDPlaylist.setEnabled(false);
        tutoDnDPlaylist.setLineWrap(true);
        tutoDnDPlaylist.setWrapStyleWord(true);
        tutoDnDPlaylist.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.BOTH;
        
            // Playlist panel
        playlistPanel = new JPanel(new GridBagLayout()); // TODO Il faurait aussi qu'on puisse afficher le temps.
        layoutManager.gridwidth = 5;
        playlistPanel.add(scrollPlaylist, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 1;
        playlistPanel.add(tutoDnDPlaylist, layoutManager);
        layoutManager.gridy = 2;
        layoutManager.gridwidth = 1;
        playlistPanel.add(buttonPlaylist.get("shuffle"), layoutManager);
        layoutManager.gridx = 3;
        playlistPanel.add(buttonPlaylist.get("removeItem"), layoutManager);
        layoutManager.gridx = 4;
        playlistPanel.add(buttonPlaylist.get("empty"), layoutManager);
        
            // Library panel
        libraryPanel = new JPanel();
		libraryPanel.add(new JScrollPane(bibliotheque));
        
            // Layout building
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(playlistPanel, layoutManager);
        layoutManager.gridx = 2;
        panel.add(libraryPanel, layoutManager);

        // Packing
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    /* Interface stage */
    /**
     * Assopcie un bouton avec une action. Attention, ceci pourrait �tre modifi� si tous les boutons sont g�r�s par le SplayerViewManager.
     * @param buttonName code bouton � associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction � associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        if( buttonPlaylist.containsKey(buttonName) )
            buttonPlaylist.get(buttonName).setAction(action);
    }
    
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
     * Specifie / Met a jour la playlist.
     * @param list
     */
    public void setPlaylist(DefaultListModel list)
    {
        this.playlist.setModel(list);
    }
    
}
