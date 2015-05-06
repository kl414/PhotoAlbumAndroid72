package com.kevinlin.photoalbum.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Album Class
 * @author Hongjie Lin
 *
 */
public class Album implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * Album name, no duplicate under same user
	 */
	public String albumName;
	/**
	 * Photos under this album
	 */
	public List<Photo> photos;
	
	/**
	 * Constructor of Album
	 * @param albumName
	 */
	public Album(String albumName){
		this.albumName = albumName;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * Add a photo to this album
	 * @param photo - The new photo to be added
	 * @return true on success, false otherwise
	 */
	public boolean add(Photo newPhoto){
		for(Photo photo: photos){
			if(photo.fileName.equals(newPhoto.fileName)){
				new Error("Photo "+newPhoto.fileName+" already exists in "+albumName);
				return false;
			}
		}
		photos.add(newPhoto);
		return true;
	}
	
	/**
	 * Delete a photo from this album
	 * @param photoName - The name of photo to be deleted
	 * @return true on success, false otherwise
	 */
	public boolean delete(String photoName){
		for(Photo photo: photos){
			if(photo.fileName.equals(photoName))
				return photos.remove(photo);
		}
		return false;
	}
	
	/*
	public void write(File root){
		File albumDir = new File(root, albumName);
		
		if(!albumDir.exists()){
			albumDir.mkdir();
		}
		
		for(Photo photo: photos){
			photo.write(albumDir);
		}
	}
	*/
	
	/**
	 * Change the photo's caption
	 * @param photoName - The name of photo to be changed
	 * @param newCaption - The new caption of the photo
	 */
	public void recaption(String photoName, String newCaption){
		for(Photo photo: photos){
			if(photo.fileName.equals(photoName))
				photo.setCaption(newCaption);
		}
	}
	
	/**
	 * @param fileName
	 * @return The photo specified by the name
	 */
	public Photo getPhoto(String fileName){
		for(Photo photo: photos){
			if(photo.fileName.equals(fileName))
				return photo;
		}
		return null;
	}
	/**
	 * get photos
	 * @return the photos under the album instance
	 */
	public List<Photo> getPhotos(){
		return photos;
	}
	
	/**
	 * @param fileName
	 * @return true if the album contains the photo named by fileName
	 */
	public boolean contains(String fileName){
		for(Photo photo: photos){
			if(photo.fileName.equals(fileName))
				return true;
		}
		return false;
	}
	
	/**
	 * @param name - the new name of album
	 */
	public void setName(String name){
		this.albumName = name;
	}
	
}
