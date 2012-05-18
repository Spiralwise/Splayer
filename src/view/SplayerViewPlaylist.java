package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	private JPanel libraryPanel;
	// Interactive components
    //private HashMap<String, JButton> buttonPlaylist; // TODO Les bouttons sont des actions et pour bien dÈcouper le code, il faudrait qu'ils se trouvent dans le SVM.
        // TODO Si à terme il ne reste que un ou deux boutons, autant ne pas faire de hashmap. Sauf si on fusionne tous les boutons dans le ViewManager.
	private JTable bibliotheque;

    public SplayerViewPlaylist()
    {
        // Frame
        super("Library");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Components
            // Library
        bibliotheque = new JTable(new Library()); // TODO From Seb : Attention !!! La bibliothèque doit être créer par la DataManager !
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.BOTH;
          
            // Library panel
        libraryPanel = new JPanel();
		libraryPanel.add(new JScrollPane(bibliotheque));
        
            // Layout building
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(libraryPanel, layoutManager);

        // Packing
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocation(500, 150);
    }
    
    /* Interface stage */
    /**
     * Assopcie un bouton avec une action. Attention, ceci pourrait être modifié si tous les boutons sont gérés par le SplayerViewManager.
     * @param buttonName code bouton à associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction à associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        /*if( buttonPlaylist.containsKey(buttonName) )
            buttonPlaylist.get(buttonName).setAction(action);*/
    }   
}
