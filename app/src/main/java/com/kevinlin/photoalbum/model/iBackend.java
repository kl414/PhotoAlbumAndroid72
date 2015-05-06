package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * functionality that allows for the storage and retrieval from the data directory
 * @author Hongjie Lin
 *
 */
public class iBackend implements Backend, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<User> users;
	private static final String savDir = "data";
	private static final String savFile = "users.dat";
	
	public iBackend(){
		Backend b = null;
		try
		{
			b = readBackend();
		}
		catch(Exception e)
		{
			
		}
		if(b == null)
		{
			users = new ArrayList<User>();
		}
		else
		{
			users = b.getUsers();
		}
		//users = new ArrayList<User>();
	}
	
	
	/**
	 * read a user from storage into memory
	 * @param id - the user's id to be read from storage
	 */
	public User read(String id){
		User user = null;
		try {
			FileInputStream fileIn = new FileInputStream("data/" + id);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			user = (User) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		/*
		File[] userDirs = data.listFiles();
		String userName = "";
		List<Album> albums = new ArrayList<Album>();
		
		for(File userDir: userDirs){
			if(userDir.getName().equals(id)){
				File[] albumDirs = userDir.listFiles();
				for(File albumDir: albumDirs){
					if(albumDir.isFile()){
						userName = albumDir.getName();
					}else{
						Album newAlbum = new Album(albumDir.getName());
						newAlbum.photos = readAlbum(albumDir);
						albums.add(newAlbum);
					}
				}
			}
		}
		User user = new User(id, userName);
		user.albums = albums;
		
		return user;
		*/
	}
	
	/*
	private List<Photo> readAlbum(File albumDir){
		File[] photoFiles = albumDir.listFiles();
		List<Photo> photos = new ArrayList<Photo>();
		for(File photoFile: photoFiles){
			try {
				BufferedReader read = new BufferedReader(new FileReader(photoFile.getName()));
				String line = null;
				
				//read fileName & caption
				line = read.readLine();
				StringTokenizer tokens = new StringTokenizer(line);
				String name = tokens.nextToken();
				line = read.readLine();
				tokens = new StringTokenizer(line);
				String caption = tokens.nextToken();
				
				Photo photo = new Photo(name, caption);
				
				while((line = read.readLine()) != null){
					tokens = new StringTokenizer(line);
					String tagType = tokens.nextToken();
					String tagValue = tokens.nextToken();
					photo.addTag(tagType, tagValue);
				}
				
				photos.add(photo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return photos;
	}
	*/
	
	/**
	 * write a user to storage
	 * @param id - the user's id to be written to storage
	 */
	public void write(String id){
		for(User user: users){
			if(user.id.equals(id)){
				try {
					FileOutputStream fileOut = new FileOutputStream("data/" + id);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(user);
					out.close();
					fileOut.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/*
		File data = new File("data");
		for(User user: users){
			if(user.id.equals(id)){
				File root = new File(data, user.id);
				
				if(!root.exists()){
					root.mkdir();
				}
				
				File userName = new File(data, user.name);
				try {
					userName.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(Album album: user.albums){
					album.write(root);
				}
			}
				
		}
		*/
	}

	/**
	 * add an user
	 * @param user - the user to be added
	 * @return true on success, false otherwise
	 */
	public boolean addUser(User newUser){
		//checking if user exist already
		for(User user: users){
			if(user.id.equals(newUser.id))
				return false;
		}

		users.add(newUser);
		return true;
	}

	/**
	 * delete an user
	 * @param id - the id of user to be deleted
	 * @return true on success, false otherwise
	 */
	public boolean deleteUser(String id){
		for(User user: users){
			if(user.id.equals(id)){
				users.remove(user);
				return true;
			}
		}
		return false;
	}


	/**
	 * @return a list of existing users
	 */
	public List<User> getUsers(){
		return users;
	}
	
	public static void writeBackend(iBackend backend) throws FileNotFoundException, IOException {
		File dir = new File(savDir);
		dir.mkdir();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savDir + File.separator + savFile));
		oos.writeObject(backend);
		oos.close();
	}
	
	public static Backend readBackend() throws IOException, ClassNotFoundException
	{
		File dir = new File(savDir);
		dir.mkdir();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savDir + File.separator + savFile));
		Backend backend = (Backend)ois.readObject();
		ois.close();
		return backend;
	}
	
}
