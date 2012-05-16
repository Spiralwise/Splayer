package data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.farng.mp3.TagException;

public class Library extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // Les entetes du tableau
//	private final String[] entetes = {"Titre", "Artiste", "Album", "Annee", "Genre", "Duree"};
	private final String[] entetes = {"Titre"};
//  private final List<Music> musiques = new ArrayList<Music>();
    private final List<String> musiques = new ArrayList<String>();

    
    public Library() {
        super();
			musiques.add("Runaway");
	        musiques.add("Wake up");
		try {
			this.connectLibrary("./data/mp3database.sqlite");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		}
    }

    public void connectLibrary(String path) throws ClassNotFoundException, IOException, TagException{
    	// load the sqlite-JDBC driver using the current class loader
    	Class.forName("org.sqlite.JDBC");
    	Connection connection = null;
    	try
    	{
    		// create a database connection
    		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
    		Statement statement = connection.createStatement();
    		statement.setQueryTimeout(30);  // set timeout to 30 sec.

    		ResultSet rs = statement.executeQuery("select * from songs");
    		int i=1;
    		while(rs.next())
    		{
    			// read the result set
//    			System.out.println(i + " " + rs.getString("album") + " " + rs.getString("artist") + " " +
//    					rs.getString("title") + " " + rs.getString("genre") + " " + rs.getString("year") + " " + rs.getString("duration"));
//    			i++;

    			// transfere les donnees dans le tableau
    			this.musiques.add(rs.getString("title"));
//    			rs.getString("title");
//    			rs.getString("artist");
//    			rs.getString("album");
//    			rs.getString("year");
//    			rs.getString("genre");
//    			rs.getString("duration");
    		}
    	}
    	catch(SQLException e)
    	{
    		// if the error message is "out of memory", 
    		// it probably means no database file is found
    		System.err.println(e.getMessage());
    	}
    	finally
    	{
    		try
    		{
    			if(connection != null)
    				connection.close();
    		}
    		catch(SQLException e)
    		{
    			// connection close failed.
    			System.err.println(e);
    		}
    	}
    }
    
//    public void addSong(Music music){
//    	this.musiques.add(music);
//    }
    
    public void addSong(String music){
    	this.musiques.add(music);
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


	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * @return l'élément du tableau à l'intersection ligne colonne spécifiee
     * @param rowIndex l'index de ligne
     * @param columnIndex l'index de colonne
     */
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        switch(columnIndex){
//            case 0:
//                return musiques.get(rowIndex).getTitle();
//            case 1:
//                return musiques.get(rowIndex).getArtist();
//            case 2:
//                return musiques.get(rowIndex).getAlbum();
//            case 3:
//                return musiques.get(rowIndex).GetYear();
//            case 4:
//                return musiques.get(rowIndex).GetGenre();
//            case 5:
//                return musiques.get(rowIndex).getDuration();
//            default:
//                return null; //Ne devrait jamais arriver
//        }
//    }
}
