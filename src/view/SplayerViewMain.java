package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import data.Music;

/**
 * Classe representant la fenetre principale
 * @author Sebastien Poulmane & Loic Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewMain extends JFrame {

    /* Interface stage*/
    // Panels
    private JPanel panel;
    private JPanel timePanel, infoPanel, volumePanel, controlerPanel;
    // Text
    private HashMap<String, JLabel> display;
    // Interactive components
    private HashMap<String, JButton> buttonPlayer; // TODO Les bouttons sont des actions et pour bien dÈcouper le code, il faudrait qu'ils se trouvent dans le SVM.
    private JSlider sliderPlayer, sliderVolume;
    
    public SplayerViewMain()
    {
        // Frame
        super("Splayer");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));	// Leaves a transparent margin without any associated drawing.

        // Components
        display = new HashMap<String, JLabel>();
        display.put("volume", new JLabel("0"));
        display.put("time", new JLabel("0:00"));
        display.put("left", new JLabel("59:59"));
        display.put("artist", new JLabel("---"));
        display.put("title", new JLabel("---"));
        display.put("album", new JLabel("---"));

        // TODO Cree des icones et si possible des boutons non-rectangulaires (LoÔc: Ckecker et supprimer)
        buttonPlayer = new HashMap<String, JButton>(); 
        // Icones simples en fond des rectangles
        // (loÔc: Attention ‡ l'odre pour le parcours de la hashmap)
        buttonPlayer.put("loop", new JButton(new ImageIcon("./data/icon/media-repeat.png")));
        buttonPlayer.put("previous", new JButton(new ImageIcon("./data/icon/media-previous.png")));
        buttonPlayer.put("rewind", new JButton(new ImageIcon("./data/icon/media-rewind.png")));
        buttonPlayer.put("play", new JButton(new ImageIcon("./data/icon/media-play-pause-resume.png")));
        buttonPlayer.put("forward", new JButton(new ImageIcon("./data/icon/media-forward.png")));
        buttonPlayer.put("next", new JButton(new ImageIcon("./data/icon/media-next.png")));
        buttonPlayer.put("random", new JButton(new ImageIcon("./data/icon/media-shuffle.png")));
        
        // TODO Trouver un meilleur emplacement
        buttonPlayer.put("playlist", new JButton(new ImageIcon("./data/icon/media-search.png")));
        
        sliderPlayer = new JSlider(0, 100, 0);
        sliderVolume = new JSlider(0, 100, 25);
                
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.BOTH;
        
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
        volumePanel = new JPanel();
        volumePanel.setLayout(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0;
        volumePanel.add(new JLabel("ICON"), layoutManager);
        layoutManager.gridx = 1;
        volumePanel.add(sliderVolume, layoutManager);
        layoutManager.gridx = 2;
        volumePanel.add(display.get("volume"), layoutManager);
        
            // Controler Panel
        controlerPanel = new JPanel(new FlowLayout());
        controlerPanel.add(buttonPlayer.get("loop"));
        controlerPanel.add(buttonPlayer.get("previous"));
        controlerPanel.add(buttonPlayer.get("rewind"));
        controlerPanel.add(buttonPlayer.get("play"));
        controlerPanel.add(buttonPlayer.get("forward"));
        controlerPanel.add(buttonPlayer.get("next"));
        controlerPanel.add(buttonPlayer.get("random"));
        controlerPanel.add(buttonPlayer.get("playlist"));
        
//        for (String mapKey : buttonPlayer.keySet()) {
//        	 controlerPanel.add(buttonPlayer.get(mapKey));
//        }
        
            // Layout building
        // TODO A travailler....
        layoutManager.gridx = layoutManager.gridy = 0;
        layoutManager.ipadx = 100;
        panel.add(timePanel, layoutManager);
        layoutManager.gridx = 1;
        layoutManager.ipadx = 0;
        panel.add(infoPanel, layoutManager);
        layoutManager.gridx = 2;
        layoutManager.gridwidth = GridBagConstraints.REMAINDER;
        layoutManager.ipadx = 200;
        panel.add(volumePanel, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 1;
        //layoutManager.gridwidth = 3;
        panel.add(sliderPlayer, layoutManager);
        layoutManager.gridy = 2;
        panel.add(controlerPanel, layoutManager);
                
        // Packing
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    /* Action stage */
    /**
     * Modifie le texte d'un élément du lecteur.
     * @param labelName code label à modifier (ex: "title" pour modifier le titre)
     * @param message
     */
    public void setDisplay(String labelName, String message)
    {
        display.get(labelName).setText(message);
    }
    
    /**
     * Modifie la position du slider de lecture.
     * /!\ Attention, cette méthode est réservée pour l'update induit par le player.
     * @param timeInMilliSec
     */
    public void setSlider(int timeInMilliSec)
    {
        sliderPlayer.setValue(timeInMilliSec);
    }
    
    /**
     * Modifie la position du slider de volume.
     *  /!\ Attention, cette méthode est réservée pour l'update induit par le player.
     * @param value
     */
    public void setvolume(int value)
    {
        sliderVolume.setValue(value);
    }
    
    /**
     * Assopcie un bouton avec une action. Attention, ceci pourrait être modifié si tous les boutons sont gérés par le SplayerViewManager.
     * @param buttonName code bouton à associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction à associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        buttonPlayer.get(buttonName).setAction(action);
    }
    
    public void addVolumeListener(ChangeListener listener)
    {
        sliderVolume.addChangeListener(listener);
    }

    public void addSliderListener(Object listener)
    {
        sliderPlayer.addChangeListener((ChangeListener) listener);
    }
    
    /* Implementation stage */
    /**
     * Met à jour les données du lecteur selon la musique passé en paramètre.
     * @param current musique dont les données sont à afficher dans le lecteur
     */
    public void updateData(Music current)
    {
        // TODO Regler le problème des caractères invisibles
        setDisplay("artist", current.getAuthor());
        setDisplay("title", current.getTitle());
        setDisplay("album", current.getAlbum());
        int timeInSec = current.getDurationInSec();
        setDisplay("left", (int)Math.floor(timeInSec/60) + ":" + String.format("%02d", timeInSec%60));
        sliderPlayer.setMaximum(current.getDuration());
    }    
}
