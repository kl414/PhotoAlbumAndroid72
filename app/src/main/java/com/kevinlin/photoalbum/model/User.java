package cs213.photoAlbum.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User Class
 * @author Hongjie Lin
 *
 */
public class User implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the user's ID, used to log in
	 */
	public String id;
	/**
	 * user's full name
	 */
	public String name;
	/**
	 * user's albums
	 */
	public List<Album> albums;
	
	/**
	 * Constructor of User
	 * @param id - the user's unique id
	 * @param name - the user's full name
	 */
	public User(String id, String name){
		this.id = id;
		this.name = name;
		albums = new ArrayList<Album>();
	}
	
	/**
	 * add an album to the user
	 * @param newAlbum - the album to be added
	 * @return true on success, false otherwise
	 */
	public boolean add(Album newAlbum){
		for(Album album: albums){
			if(album.albumName.equals(newAlbum.albumName))
				return false;
		}
		return albums.add(newAlbum);
	}
	
	
	/**
	 * delete an album from user
	 * @param albumName - the album to be deleted
	 * @return true on success, false otherwise
	 */
	public boolean delete(String albumName){
		for(Album album: albums){
			if(album.albumName.equals(albumName))
				return albums.remove(album);
		}
		return false;
	}
	
	/**
	 * rename an album
	 * @param album - the album to be renamed
	 * @param newName - the new name for the album
	 */
	public void rename(Album album, String newName){
		int index = albums.indexOf(album);
		if(index == -1){
			System.err.println("The album to be renamed does not exist!");
		}else{
			albums.get(index).setName(newName);
		}
			
	}
	
	public boolean contains(String fileName){
		for(Album album: albums)
			if(album.contains(fileName))
				return true;
		return false;
	}
	
	public Photo getPhoto(String fileName){
		for(Album album: albums)
			if(album.contains(fileName))
				return album.getPhoto(fileName);
		return null;
	}
	/**
	 * get albums
	 * @return the albums of the user instance
	 */
	public List<Album> getAlbums(){
		return albums;
	}
}
