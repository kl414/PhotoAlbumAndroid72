package cs213.photoAlbum.control;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
/**
 * @author Edward Mamedov
 *
 */
public interface Controller {
	/**
	 * Used to add a user to the system
	 * @param userId - Unique ID for user to be created
	 * @param userName - User name for the UserID
	 * @return True upon success, False upon failure
	 */
	public boolean addUser(String userId, String userName);

	/**
	 * Delete a user from the system
	 * @param userId - User ID of the user that is being deleted
	 * @return True upon success, False if userID already exists with userName
	 */
	public boolean deleteUser(String userId);

	/**
	 * Boolean for successful login of a user
	 * @param userId
	 * @return True if successfully logged in, false if user id does not exist
	 */
	public boolean login(String userId);

	/**
	 * Checks the user name of the current person logged in
	 * @return userName
	 */
	public String whoami();
	/**
	 * Create an Album
	 * @param albumName - Name of the album you want to create
	 * @return True if created, False if album already exists for user
	 */
	public boolean createAlbum(String albumName);

	/**
	 * Delete an Album
	 * @param albumName - Name of the album you want to delete
	 * @return True if deleted album, False if album doesn't exist for user
	 */
	public boolean deleteAlbum(String albumName);

	/**
	 * Add a photo to an album
	 * @param fileName
	 * @param caption
	 * @param albumName
	 * @return True if added photo to album, False if photo already exists in album or if filename does not exist
	 * @throws FileNotFoundException
	 */
	public boolean addPhoto(String fileName, String caption, String albumName) throws FileNotFoundException;

	/**
	 * Move a photo from one album to another
	 * @param fileName
	 * @param oldAlbumName
	 * @param newAlbumName
	 * @return True if successfully moved photo, False if filename does not exist in oldAlbumName
	 */
	public boolean movePhoto(String fileName, String oldAlbumName, String newAlbumName);

	/**
	 * Remove a photo from an album
	 * @param fileName
	 * @param albumName
	 * @return True if successfully removed photo, False if photo is not in albumName
	 */
	public boolean removePhoto(String fileName, String albumName);

	/**
	 * Add a tag to a photo
	 * @param fileName
	 * @param tagType
	 * @param tagValue
	 * @return True if added tag, False if Tag already exists for fileName tagType:tagValue
	 */
	public boolean addTag(String fileName, String tagType, String tagValue);

	/**
	 * Delete a tag from a photo
	 * @param fileName
	 * @param tagType
	 * @param tagValue
	 * @return True if deleted tag, False if tag does not exist for fileName tagType:tagValue
	 */
	public boolean deleteTag(String fileName, String tagType, String tagValue);

	/**
	 * List a photo's information
	 * @param fileName
	 * @return Null if failed to do so, A string containing all of the info if successful.
	 */
	public ArrayList<String> listPhotoInfo(String fileName);

	/**
	 * Return all of the users
	 * @return A List<String> containing all of the users
	 */
	public void listUsers();

	/**
	 * Prints all of the users
	 * @return All of the users
	 */
	public ArrayList<Album> listAlbums();

	/**
	 * Return all photos within range of dates
	 * @param startDate
	 * @param endDate
	 * @return A List<String> containing all the photos between startDate and endDate
	 * @throws ParseException 
	 */
	public List<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException;

	/**
	 * Return all photos sharing a tag
	 * @param List<Photo> result
	 * @param String tagType
	 * @param String tagValue
	 * @return A List<String> containing all the photos with a specific tag
	 */
	public List<Photo> getPhotosByTag(List<Photo> result, String tagType, String tagValue);

	/**
	 * Print the list of photos under this album
	 * @param String albumName
	 */
	public void listPhotos(String albumName);


}
