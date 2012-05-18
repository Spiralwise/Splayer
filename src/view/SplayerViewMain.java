package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;
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
    private JPanel accessPanel, infoPanel, volumePanel, sliderPanel, controlerPanel;
    private JPanel playlistPanel, buttonPlaylistPanel;
    
    // Text
    private HashMap<String, JLabel> display;
    // Icons
    private ImageIcon iconPlay, iconPause, iconMute, iconLoopOff, iconLoopOn;
    private ImageIcon [] iconVolume;
    // Interactive components
    private HashMap<String, JButton> buttonPlayer; // TODO Les bouttons sont des actions et pour bien découper le code, il faudrait qu'ils se trouvent dans le SVM.
    private JSlider sliderPlayer, sliderVolume;
    private JList playlist;
    private JScrollPane scrollPlaylist; // Necessaire pour rendre une liste visible
    // Ergo components
    private JTextArea tutoDnDPlaylist;
    
    public SplayerViewMain()
    {
        // Frame
        super("Splayer");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));	// Leaves a transparent margin without any associated drawing.

        // Components
            // Display
        display = new HashMap<String, JLabel>();
        display.put("volume", new JLabel("0"));
        display.put("time", new JLabel("0:00"));
        display.put("left", new JLabel("59:59"));
        display.put("artist", new JLabel("---"));
        display.put("title", new JLabel("---"));
        display.put("album", new JLabel("---"));
        
        display.get("volume").setPreferredSize(new Dimension(35, 10));
        display.get("title").setPreferredSize(new Dimension(200, 30));
        display.get("artist").setMinimumSize(new Dimension(100,20));
        display.get("album").setMinimumSize(new Dimension(100,20));
        
            // Icon
        iconPlay    = new ImageIcon("./data/icon/media-play.png");
        iconPause   = new ImageIcon("./data/icon/media-pause.png");
        iconMute    = new ImageIcon("./data/icon/media-mute.png");
        iconLoopOff = new ImageIcon("./data/icon/media-loop.png");
        iconLoopOn  = new ImageIcon("./data/icon/media-loop-active.png");
        iconVolume  = new ImageIcon[4];
        for(int i=0; i<iconVolume.length; i++)
            iconVolume[i] = new ImageIcon("./data/icon/media-sound_" + i + ".png");

            // Buttons
                // Player buttons
        buttonPlayer = new HashMap<String, JButton>(); 
        buttonPlayer.put("sound", new JButton());
        buttonPlayer.put("loop", new JButton());
        buttonPlayer.put("previous", new JButton());
        buttonPlayer.put("play", new JButton());
        buttonPlayer.put("stop", new JButton());
        buttonPlayer.put("next", new JButton());
        buttonPlayer.put("playlist", new JButton());
        buttonPlayer.put("library", new JButton());
                // Cas particulier pour forward et rewind qui ne sont pas dŽfinis par des actions mais par des listeners
        JButton forward = new JButton(new ImageIcon("./data/icon/media-forward.png"));
        JButton rewind = new JButton(new ImageIcon("./data/icon/media-rewind.png"));
        forward.setName("forward"); // Les setName permettent au listener de distinguer les boutons entre eux.
        forward.setToolTipText("Avance rapide");
        buttonPlayer.put("forward", forward);
        rewind.setToolTipText("REWIND !!!");
        rewind.setName("rewind");
        buttonPlayer.put("rewind", rewind);
                // Playlist buttons
        buttonPlayer.put("removeItem", new JButton());
        buttonPlayer.put("empty", new JButton());
        buttonPlayer.put("shuffle", new JButton());
        buttonPlayer.get("shuffle").setPreferredSize(new Dimension(80, 35));

            // Sliders
        sliderPlayer = new JSlider(0, 100, 0); // Fonctionne et mis ˆ jour en milliseconde
        sliderPlayer.setMajorTickSpacing(60000);
        sliderPlayer.setMinorTickSpacing(10000);
        sliderPlayer.setPaintTicks(true);
        sliderPlayer.setPreferredSize(new Dimension(250,50));
        sliderVolume = new JSlider(0, 100, 25);
        sliderVolume.setPreferredSize(new Dimension(100, 20));
        
            // Playlist
        playlist = new JList();
        playlist.setDragEnabled(true);
        playlist.setDropMode(DropMode.INSERT);
        scrollPlaylist = new JScrollPane(playlist);
        scrollPlaylist.setPreferredSize(new Dimension(300,300));
        
            // Ergo
        // TODO Rendre plus joli et effacer si dŽjˆ effectuŽ un Dnd
        tutoDnDPlaylist = new JTextArea("Cliquez-glissez un fichier mp3 dans la playlist depuis la bibliothque ou votre explorateur de fichiers.");
        tutoDnDPlaylist.setEnabled(false);
        tutoDnDPlaylist.setLineWrap(true);
        tutoDnDPlaylist.setWrapStyleWord(true);
        tutoDnDPlaylist.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));      
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.BOTH;
        
            // Access panel
        accessPanel = new JPanel();
        accessPanel.setLayout(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0;
        accessPanel.add(buttonPlayer.get("library"), layoutManager);
        layoutManager.gridx = 1;
        accessPanel.add(buttonPlayer.get("playlist"), layoutManager);
        
            // Info Panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0;
        layoutManager.gridwidth = GridBagConstraints.REMAINDER;
        display.get("title").setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        infoPanel.add(display.get("title"), layoutManager);
        layoutManager.gridy = 1;
        layoutManager.gridwidth = 1;
        display.get("artist").setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        infoPanel.add(display.get("artist"), layoutManager);
        layoutManager.gridx = 1;
        infoPanel.add(new JLabel(" - "), layoutManager);
        layoutManager.gridx = 2;
        display.get("album").setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        infoPanel.add(display.get("album"), layoutManager);
        
            // Sound Panel
        volumePanel = new JPanel();
        volumePanel.setLayout(new FlowLayout());
        volumePanel.add(buttonPlayer.get("sound"), layoutManager);
        volumePanel.add(sliderVolume, layoutManager);
        volumePanel.add(display.get("volume"), layoutManager);
        
            // Slider Panel
        sliderPanel = new JPanel(new FlowLayout());
        sliderPanel.add(display.get("time"));
        sliderPanel.add(sliderPlayer);
        sliderPanel.add(display.get("left"));
        
            // Controler Panel
        controlerPanel = new JPanel(new FlowLayout());
        controlerPanel.add(buttonPlayer.get("previous"));
        controlerPanel.add(buttonPlayer.get("rewind"));
        controlerPanel.add(buttonPlayer.get("play"));
        controlerPanel.add(buttonPlayer.get("stop"));
        controlerPanel.add(buttonPlayer.get("forward"));
        controlerPanel.add(buttonPlayer.get("next"));
        
            // Playlist panel
        playlistPanel = new JPanel(new GridBagLayout()); // TODO Il faurait aussi qu'on puisse afficher le temps.
        layoutManager.gridx = layoutManager.gridy = 0;
        layoutManager.gridwidth = GridBagConstraints.REMAINDER;
        playlistPanel.add(scrollPlaylist, layoutManager);
        layoutManager.gridy = 1;
        playlistPanel.add(tutoDnDPlaylist, layoutManager);
            // Button playlist panel
        buttonPlaylistPanel = new JPanel(new FlowLayout());
        buttonPlaylistPanel.add(buttonPlayer.get("loop"));
        buttonPlaylistPanel.add(buttonPlayer.get("shuffle"));
        buttonPlaylistPanel.add(buttonPlayer.get("removeItem"));
        buttonPlaylistPanel.add(buttonPlayer.get("empty"));
        layoutManager.gridy = 2;
        playlistPanel.add(buttonPlaylistPanel, layoutManager);
        /*layoutManager.gridy = 2;
        layoutManager.gridwidth = 1;
        layoutManager.fill = GridBagConstraints.NONE;
        playlistPanel.add(buttonPlayer.get("loop"), layoutManager);
        layoutManager.gridx = 1;
        layoutManager.gridwidth = 1;
        playlistPanel.add(buttonPlayer.get("shuffle"), layoutManager);
        layoutManager.gridx = 3;
        layoutManager.gridwidth = 1;
        playlistPanel.add(buttonPlayer.get("removeItem"), layoutManager);
        layoutManager.gridx = 4;
        //layoutManager.gridwidth = GridBagConstraints.REMAINDER;
        playlistPanel.add(buttonPlayer.get("empty"), layoutManager);
        */
            // Layout building
        // TODO A travailler....
        layoutManager.gridx = layoutManager.gridy = 0;
        layoutManager.gridwidth = 1;
        panel.add(accessPanel, layoutManager);
        layoutManager.gridx = 1;
        layoutManager.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(infoPanel, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 1;
        panel.add(sliderPanel, layoutManager);
        layoutManager.gridy = 2;
        layoutManager.fill = GridBagConstraints.BOTH;
        panel.add(controlerPanel, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 3;
        layoutManager.fill = GridBagConstraints.NONE;
        panel.add(volumePanel, layoutManager);
        layoutManager.gridy = 4;
        layoutManager.fill = GridBagConstraints.BOTH;
        panel.add(playlistPanel, layoutManager);
                
        // Packing
        this.add(panel);
        //this.pack(); // Le pack est relancŽ par la notification "initialization" du SDM.
        this.setResizable(false);
        //this.setLocationRelativeTo(null);
        this.setLocation(200, 100);
    }
    
    /* UI stage */
    /**
     * Modifie le texte d'un ŽlŽment du lecteur.
     * @param labelName code label ˆ modifier (ex: "title" pour modifier le titre)
     * @param message
     */
    public void setDisplay(String labelName, String message)
    {
        display.get(labelName).setText(message);
    }
    
    /**
     * Modifie la position du slider de lecture.
     * /!\ Attention, cette mŽthode est rŽservŽe pour l'update induit par le player.
     * @param timeInMilliSec
     */
    public void setSlider(int timeInMilliSec)
    {
        sliderPlayer.setValue(timeInMilliSec);
    }
    
    /**
     * Modifie la position du slider de volume.
     *  /!\ Attention, cette mŽthode est rŽservŽe pour l'update induit par le player.
     * @param value
     */
    public void setvolume(int value)
    {
        sliderVolume.setValue(value);
    }
    
    /**
     * Modifie l'ic™ne de volume de son.
     * @param volume code volume : 0=no sound --> 3=louder
     */
    public void setVolumeIcon(int volume)
    {
        if( volume > 0 && volume < iconVolume.length )
            buttonPlayer.get("sound").setIcon(iconVolume[volume]);
        else if( volume == -1 )
            buttonPlayer.get("sound").setIcon(iconMute);
    }
    
    /**
     * Transforme le bouton play en pause et vice-versa.
     *  /!\ Attention, methode reservee ˆ la SDM.
     * @param playMode si faux, alors le bouton devient un bouton pause. Play sinon.
     */
    public void setPlayButton(boolean playMode)
    {
        if(playMode)
            buttonPlayer.get("play").setIcon(iconPlay);
        else
            buttonPlayer.get("play").setIcon(iconPause);
    }
    
    /* Interface stage */
    /**
     * Assopcie un bouton avec une action. Attention, ceci pourrait tre modifiŽ si tous les boutons sont gŽrŽs par le SplayerViewManager.
     * @param buttonName code bouton ˆ associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction ˆ associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        if( buttonPlayer.containsKey(buttonName) )
            buttonPlayer.get(buttonName).setAction(action);
    }
    
    public void setListener(String buttonName, Object listener)
    {
        if( buttonPlayer.containsKey(buttonName) )
            buttonPlayer.get(buttonName).addMouseListener((MouseListener) listener);
    }    
    
    public void addVolumeListener(ChangeListener listener)
    {
        sliderVolume.addChangeListener(listener);
    }

    public void addSliderListener(Object listener)
    {
        sliderPlayer.addChangeListener((ChangeListener) listener);
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
     * Met ˆ jour les donnŽes du lecteur selon la musique passŽ en paramtre.
     * @param current musique dont les donnŽes sont ˆ afficher dans le lecteur
     */
    public void updateData(Music current)
    {
        if( current != null ) {
            setDisplay("artist", current.getAuthor());
            setDisplay("title", current.getTitle());
            setDisplay("album", current.getAlbum());
            int timeInSec = current.getDurationInSec();
            setDisplay("left", (int)Math.floor(timeInSec/60) + ":" + String.format("%02d", timeInSec%60));
            sliderPlayer.setMaximum(current.getDuration());    
        }
        else {
            setDisplay("title", "Ajouter une musique dans la playlist");
            setDisplay("artist", "---");
            setDisplay("album", "---");
            setDisplay("left", "0:00");
        }
    }
    
    /**
     * Specifie / Met a jour la playlist.
     * @param list
     */
    public void setPlaylist(DefaultListModel list)
    {
        this.playlist.setModel(list);
    }

    /**
     * Affiche, cache la playlist.
     */
    public void togglePlaylist()
    {
        playlistPanel.setVisible( !playlistPanel.isVisible() );
        this.pack();
    }

    /**
     * Allume, eteind le bouton loop
     * Methode reservee.
     */
    public void toggleLoopIcon(boolean activate)
    {
        if( activate )
            buttonPlayer.get("loop").setIcon(iconLoopOn);
        else
            buttonPlayer.get("loop").setIcon(iconLoopOff);
    }

}
