package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

/**
 * Classe representant la fenetre principale
 * @author Sebastien Poulmane & Loic Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewMain extends JFrame {

    /* Data stage */
    
    /* Interface stage*/
    // Panels
    private JPanel panel;
    private JPanel timePanel, infoPanel, controlerPanel;
    // Text
    private HashMap<String, JLabel> display;
    // Interactive components
    private HashMap<String, JButton> buttonPlayer; // TODO Les bouttons sont des actions et pour bien découper le code, il faudrait qu'ils se trouvent dans le SVM.
    private JSlider sliderPlayer, sliderVolume;
    
    public SplayerViewMain()
    {
        // Frame
        super("Splayer");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));	// Leaves a transparent margin without any associated drawing.
        
        // Data

        // Components
        display = new HashMap<String, JLabel>();
        display.put("time", new JLabel("0:00"));
        display.put("left", new JLabel("99:99"));
        display.put("artist", new JLabel("John Doe"));
        display.put("title", new JLabel("Soft Kitty"));
        display.put("album", new JLabel("Sweet Songs When Being Sick"));

        // TODO Cree des icones et si possible des boutons non-rectangulaires (Loïc: Ckecker et supprimer)
        buttonPlayer = new HashMap<String, JButton>();
//        buttonPlayer.put("play", new JButton(">"));
//        buttonPlayer.put("previous", new JButton("|<"));
//        buttonPlayer.put("next", new JButton(">|"));
//        buttonPlayer.put("rewind", new JButton("<<"));
//        buttonPlayer.put("forward", new JButton(">>"));
//        buttonPlayer.put("loop", new JButton("O"));
//        buttonPlayer.put("random", new JButton("R"));
        
        // Icones simples en fond des rectangles
        // (loïc: Attention à l'odre pour le parcours de la hashmap)
        buttonPlayer.put("loop", new JButton(new ImageIcon("./data/icon/media-repeat.png")));
        buttonPlayer.put("previous", new JButton(new ImageIcon("./data/icon/media-previous.png")));
        buttonPlayer.put("rewind", new JButton(new ImageIcon("./data/icon/media-rewind.png")));
        buttonPlayer.put("play", new JButton(new ImageIcon("./data/icon/media-play-pause-resume.png")));
        buttonPlayer.put("forward", new JButton(new ImageIcon("./data/icon/media-forward.png")));
        buttonPlayer.put("next", new JButton(new ImageIcon("./data/icon/media-next.png")));
        buttonPlayer.put("random", new JButton(new ImageIcon("./data/icon/media-shuffle.png")));
        
        // TODO Trouver un meilleur emplacement
        //buttonPlayer.put("PLAYLIST", new JButton("Playlist"));
        buttonPlayer.put("PLAYLIST", new JButton(new ImageIcon("./data/icon/media-search.png")));
        
        sliderPlayer = new JSlider(0, 100, 0);
        sliderVolume = new JSlider(0, 100, 25);
                
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.NONE;
        
            // Time panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0;
        timePanel.add(display.get("time"), layoutManager);
        layoutManager.gridy = 1;
        timePanel.add(display.get("left"), layoutManager);
        
            // Info Panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        layoutManager.gridy = 0;
        layoutManager.gridwidth = 3;
        infoPanel.add(display.get("title"), layoutManager);
        layoutManager.gridy = 1;
        layoutManager.gridwidth = 1;
        infoPanel.add(display.get("artist"), layoutManager);
        layoutManager.gridx = 1;
        infoPanel.add(new JLabel(" - "), layoutManager);
        layoutManager.gridx = 2;
        infoPanel.add(display.get("album"), layoutManager);
        
            // Sound Panel
        // ...
        
            // Controler Panel
        controlerPanel = new JPanel(new FlowLayout());
        // TODO Faire une boucle plutot (Loïc: Ckecker et supprimer)
        controlerPanel.add(buttonPlayer.get("loop"));
        controlerPanel.add(buttonPlayer.get("previous"));
        controlerPanel.add(buttonPlayer.get("rewind"));
        controlerPanel.add(buttonPlayer.get("play"));
        controlerPanel.add(buttonPlayer.get("forward"));
        controlerPanel.add(buttonPlayer.get("next"));
        controlerPanel.add(buttonPlayer.get("random"));
        controlerPanel.add(buttonPlayer.get("PLAYLIST"));
        
//        for (String mapKey : buttonPlayer.keySet()) {
//        	 controlerPanel.add(buttonPlayer.get(mapKey));
//        }
        
            // Layout building
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(timePanel, layoutManager);
        layoutManager.gridx = 1;
        panel.add(infoPanel, layoutManager);
        layoutManager.gridx = 2;
        panel.add(sliderVolume, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 1;
        layoutManager.gridwidth = 3;
        panel.add(sliderPlayer, layoutManager);
        layoutManager.gridy = 2;
        panel.add(controlerPanel, layoutManager);
                
        // Packing
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO remplacer l'action pour fermer le player proprement avant.
        this.pack();
        this.setLocationRelativeTo(null);
        //this.setVisible(true);
            // NOTE C'est le SplayerEngine qui donne le top.
    }
    
    /* Action stage */
    public void setAction(String buttonName, AbstractAction action)
    {
        buttonPlayer.get(buttonName).setAction(action);
    }
}
