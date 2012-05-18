package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

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
	private JPanel searchPanel, libraryPanel;
	// Interactive components
    //private HashMap<String, JButton> buttonPlaylist; // TODO Les bouttons sont des actions et pour bien d�couper le code, il faudrait qu'ils se trouvent dans le SVM.
        // TODO Si � terme il ne reste que un ou deux boutons, autant ne pas faire de hashmap. Sauf si on fusionne tous les boutons dans le ViewManager.
	private JList playlist;
	private JTable bibliotheque;
	JTextField typingArea;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Components
            // Search
        JLabel labelSearch = new JLabel(new ImageIcon("./data/icon/media-search.png"));
        typingArea = new JTextField(20);
            // Library
        bibliotheque = new JTable(); // TODO From Seb : Attention !!! La bibliotheque doit etre creer par la DataManager !
        bibliotheque.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        bibliotheque.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 9));
        bibliotheque.setDragEnabled(true);
        bibliotheque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLib = new JScrollPane(bibliotheque);
        scrollLib.setPreferredSize(new Dimension(700,500));
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.NONE;
          
            // Search panel
        searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(labelSearch);
        searchPanel.add(typingArea);
            // Library panel
        libraryPanel = new JPanel(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0; 
        libraryPanel.add(searchPanel, layoutManager);
        layoutManager.gridy = 1;
        libraryPanel.add(scrollLib, layoutManager);
		
        
            // Layout building
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(libraryPanel, layoutManager);
        // Packing
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocation(500, 150);
    }

    public void setBibliotheque(Library biblio)
    {
    	this.bibliotheque.setModel(biblio);
        TableColumn col = bibliotheque.getColumnModel().getColumn(0);
        col.setPreferredWidth(200);
        col = bibliotheque.getColumnModel().getColumn(1);
        col.setPreferredWidth(150);
        col = bibliotheque.getColumnModel().getColumn(2);
        col.setPreferredWidth(150);
        col = bibliotheque.getColumnModel().getColumn(3);
        col.setPreferredWidth(50);
        col = bibliotheque.getColumnModel().getColumn(4);
        col.setPreferredWidth(100);
        col = bibliotheque.getColumnModel().getColumn(5);
        col.setPreferredWidth(50);
    }
    
    /* Interface stage */
    /**
     * Assoocie un bouton avec une action. Attention, ceci pourrait etre modifie si tous les boutons sont gere par le SplayerViewManager.
     * @param buttonName code bouton a associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction a associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        /*if( buttonPlaylist.containsKey(buttonName) )
            buttonPlaylist.get(buttonName).setAction(action);*/
    }
    
    public void setPlaylistHandler(TransferHandler handler)
    {
        playlist.setTransferHandler(handler);
    }
    
    public void setPlaylistListener(Object listener)
    {
        playlist.addMouseListener((MouseListener)listener);
    }
    
    public void setSearchListener(Object listener)
    {
    	typingArea.addKeyListener((KeyListener) listener);
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

	public void setLibrary(Library library) {
		bibliotheque.setModel(library);
		bibliotheque.repaint();
        //this.repaint();
     }

    public void setLiblistListener(Object listener)
    {
        bibliotheque.addMouseListener((MouseListener)listener);
    }
}
