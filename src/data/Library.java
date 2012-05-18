package data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class Library extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    // Les entetes du tableau
	private final String[] entetes = {"Titre", "Artiste", "Album", "Annee", "Genre", "Duree"};
	private final List<Music> musiques = new ArrayList<Music>();
    
    public Library() {
        super();
	}
    
    public void addSong(Music music){
    	this.musiques.add(music);
    }
    
    public void ClearAll()
    {
    	this.musiques.clear();
    }
    
    /**
     * @return le nombre de ligne du tableau
     */
    public int getRowCount() {
        return musiques.size();
    }

    /**
     * @return le nombre de colonnes
     */
    public int getColumnCount() {
        return entetes.length;
    }

    /**
     * @return le nom de la colonne en fonction de son index
     * @param columnIndex le numero de colonne
     */
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    /**
     * @return l'élément du tableau à l'intersection ligne colonne spécifiee
     * @param rowIndex l'index de ligne
     * @param columnIndex l'index de colonne
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return musiques.get(rowIndex).getTitle();
            case 1:
                return musiques.get(rowIndex).getAuthor();
            case 2:
                return musiques.get(rowIndex).getAlbum();
            case 3:
                return musiques.get(rowIndex).getYear();
            case 4:
                return musiques.get(rowIndex).getGenre();
            case 5:
                return musiques.get(rowIndex).getDuration();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
    
}
