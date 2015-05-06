package cs213.photoAlbum.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.model.iBackend;

/**
 * @author Edward Mamedov
 * @author Hongjie Lin
 *
 */
public class Control implements Controller, Serializable{

	public iBackend backend = new cs213.photoAlbum.model.iBackend();
	static boolean result;
	private static User thisUser;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
	private DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	
	public Control() {
		df.setLenient(false);
		User tempUser = new User("1", "edward");
		thisUser = tempUser;
	}

	@Override
	public void listUsers(){
		List<User> users = backend.getUsers();
		for (int i=0; i<users.size(); i++){
			System.out.println(users.get(i).name);
		}

	}

	@Override
	public boolean addUser(String userId, String userName){
		result = false;
		User user = new cs213.photoAlbum.model.User(userId, userName);
		result = backend.addUser(user);
		return result;
	}

	@Override
	public boolean deleteUser(String userId){
		result = false;
		result = backend.deleteUser(userId);
		return result;
	}

	@Override
	public boolean login(String userId){
		result = false;
		List<User> users = backend.getUsers();
		if (users == null){
			return result; //user does not exist(fail)
		}
		else{
			for(User user: users){
				if(user.id.equals(userId)){
					thisUser = user;
					result = true; //user exists(success)
				}
			}
		}
		return result;
	}
	
	//TODO this has to be implemented because we can't use userID anymore, has to be userName
	
	/*public boolean loginName(String userName){
		result = false;
		List<User> users = backend.getUsers();
		if (users == null){
			return result; //user does not exist(fail)
		}
		else{
			for(User user: users){
				if(user.name.equalsIgnoreCase(userName)){
					thisUser = user;
					result = true; //user exists(success)
				}
			}
		}
		return result;
	}*/

	@Override
	public String whoami(){
		return thisUser.id;
	}

	public boolean rename(int index, String newName){
		Album target = null;
		for(Album album: thisUser.albums){
			if(album.albumName.equalsIgnoreCase(newName))
				return false;
		}
		target = thisUser.getAlbums().get(index);
		thisUser.rename(target, newName);
		return true;
	}
	public static boolean makeAlbum(String albumName){
		result = false;
		Album album = new cs213.photoAlbum.model.Album(albumName);
		result = thisUser.add(album);
		return result;
	}
	public boolean createAlbum(String albumName){
		result = false;
		Album album = new cs213.photoAlbum.model.Album(albumName);
		result = thisUser.add(album);
		return result;
	}

	@Override
	public boolean deleteAlbum(String albumName){
		result = false;
		result = thisUser.delete(albumName);
		return result;
	}
	
	public void addAlbum(Album album){
		thisUser.albums.add(album);
	}

	@Override
	public boolean addPhoto(String fileName, String caption, String albumName) throws FileNotFoundException{
		File f = new File(fileName);
		result = false;
		Photo photo = null;
		if (f.exists()){
			for (int i=0; i<thisUser.albums.size(); i++){
				if (thisUser.albums.get(i).albumName.equals(albumName)){ 
					Date date = new Date(f.lastModified());
					Calendar creationDate = new GregorianCalendar();
					creationDate.setTime(date);
					creationDate.set(Calendar.MILLISECOND,0);
					//found album adding photo
					if(!thisUser.contains(fileName))
						try {
							photo= new Photo(fileName, caption, creationDate, f.getCanonicalPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					else
						photo = thisUser.getPhoto(fileName);
					result = thisUser.albums.get(i).add(photo);
					return result;
				}	
			}
			System.out.println("Album "+albumName+" does not exist");
		}
		else if(!f.exists()) {
			result = false;
			throw new FileNotFoundException();
		}
		return result;
	}

	public void recaption (String fileName, String albumName, String newCaption){
		for(Album album: thisUser.albums){
			if (album.contains(fileName)){
				album.getPhoto(fileName).setCaption(newCaption);
			}
		}
	}
	
	@Override
	public boolean movePhoto(String fileName, String oldAlbumName, String newAlbumName){
		result = false;
		int oldIndex = 0;
		int newIndex = -1;

		for (int i=0; i<thisUser.albums.size(); i++){
			if (thisUser.albums.get(i).albumName.equals(oldAlbumName)){
				oldIndex = i;
			}
			if (thisUser.albums.get(i).albumName.equals(newAlbumName)){
				newIndex = i;
			}
		}

		if(newIndex == -1)
			return false;
		for(int i = 0; i < thisUser.albums.get(oldIndex).photos.size(); i++){
			if(thisUser.albums.get(oldIndex).photos.get(i).fileName.equals(fileName)){
				Photo photo = thisUser.albums.get(oldIndex).photos.get(i);
				thisUser.albums.get(oldIndex).delete(fileName);
				thisUser.albums.get(newIndex).photos.add(photo);
				result = true;
			}
		}

		return result;

	}

	@Override
	public boolean removePhoto(String fileName, String albumName){
		int flag = 0;
		int index = 0;
		result = false;

		for (int i=0; i<thisUser.albums.size(); i++){
			if (thisUser.albums.get(i).albumName.equals(albumName)){
				index = i;
				flag =+1;
			}
		}

		if (flag == 0){
			System.out.println("Album "+albumName+" does not exist");
			return result;
		}

		for (int i=0; i<thisUser.albums.get(index).photos.size(); i++){
			if (thisUser.albums.get(index).photos.get(i).fileName.equals(fileName)){
				thisUser.albums.get(index).delete(fileName);
				result = true;
			}
		}
		if (result == false){
			System.out.println("Photo "+fileName+" is not in album "+albumName);
		}

		return result;
	}

	@Override
	public boolean addTag(String fileName, String tagType, String tagValue){
		List<Album> albums = thisUser.getAlbums();
		int flag = 0;
		for(Album album: albums){
			if(album.contains(fileName)){
				Photo photo = album.getPhoto(fileName);
				if(photo.addTag(tagType, tagValue)){
					flag = 1;
					return true;
				}else{
					return false;
				}
			}
		}

		if(flag == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean deleteTag(String fileName, String tagType, String tagValue){
		List<Album> albums = thisUser.getAlbums();
		int flag = 0;
		for(Album album: albums){
			if(album.contains(fileName)){
				Photo photo = album.getPhoto(fileName);
				if(photo.deleteTag(tagType, tagValue)){
					flag = 1;
				}else{
					return false;
				}
			}
		}

		if(flag == 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public ArrayList<String> listPhotoInfo(String fileName){
		ArrayList<String> list = new ArrayList<String>();
		List<Album> albums = thisUser.getAlbums();
		Photo photo = null;
		for(Album album: albums){
			if(album.contains(fileName)){
				photo = album.getPhoto(fileName);
				break;
			}
		}
		list.add("Caption: " + photo.caption);
		String tmpDate = df.format(photo.getCreationDateTime().getTime());
		list.add("Date: " + tmpDate);
		HashMap<String, List<String>> tags = photo.tags;
		//location
		List<String> loc = tags.get("location");
		if(loc != null && loc.size() != 0)
			list.add("location:" + loc.get(0));
		//people
		ArrayList<String> ppl = (ArrayList) tags.get("people");
		Collections.sort(ppl);
		for(String person: ppl){
			list.add("people:" + person);
		}
		//others
		for(String key: tags.keySet()){
			if(!key.equals("location") && !key.equals("people")){
				ArrayList<String> others = (ArrayList) tags.get(key);
				Collections.sort(others);
				for(String value: others)
					list.add(key + ":" + value);
			}
		}
		
		return list;
		/*
		List<Album> albums = thisUser.getAlbums();
		int flag = 0;
		ArrayList<String> albumNames = new ArrayList<String>();
		Photo photo = null;
		for(Album album: albums){
			if(album.contains(fileName)){
				flag = 1;
				albumNames.add(album.albumName);
				photo = album.getPhoto(fileName);
			}
		}
		if(flag == 0){
			System.out.println("Photo " + fileName + " does not exist");
		}else{
			System.out.println("Photo file name:" + fileName);
			System.out.print("Album: " + albumNames.get(0));
			for(int i = 1; i < albumNames.size(); i++)
				System.out.print("," + albumNames.get(i));
			System.out.println();
			String tmpDate = df.format(photo.getCreationDateTime().getTime());
			System.out.println("Date: " +tmpDate);
			System.out.println("Caption: " + photo.caption);

			//printing tags by location, people, others
			System.out.println("Tags:");
			HashMap<String, List<String>> tags = photo.tags;
			//location
			List<String> loc = tags.get("location");
			if(loc != null && loc.size() != 0)
				System.out.println("location:" + loc.get(0));
			//people
			ArrayList<String> ppl = (ArrayList) tags.get("people");
			Collections.sort(ppl);
			for(String person: ppl){
				System.out.println("people:" + person);
			}
			//others
			for(String key: tags.keySet()){
				if(!key.equals("location") && !key.equals("people")){
					ArrayList<String> others = (ArrayList) tags.get(key);
					Collections.sort(others);
					for(String value: others)
						System.out.println(key + ":" + value);
				}
			}
		}
		*/
	}

	public Album getAlbum(String albumName){
		for(Album album: thisUser.albums){
			if(album.albumName.equals(albumName))
				return album;
		}
		return null;
	}
	
	@Override
	public ArrayList<Album> listAlbums() {
		List<Album> album = thisUser.getAlbums();
		return (ArrayList<Album>) album;
	}
	
	public ArrayList<String> listAlbumNames(){
		ArrayList<Album> albums = listAlbums();
		ArrayList<String> albumNames = new ArrayList<String>();
		for (Album album: albums){
			albumNames.add(album.albumName);
		}
		return albumNames;
		
	}

	@Override
	public void listPhotos(String albumName){
		List<Album> albums = thisUser.getAlbums();
		int flag = 0;
		List<Photo> photos = null;
		for(Album album: albums){
			if(album.albumName.equals(albumName)){
				flag = 1;
				photos = album.getPhotos();
				break;
			}
		}

		if(flag == 0){
			System.out.println("Album " + albumName + " does not exist");
		}else{
			System.out.println("Photos for album " + albumName);
			for(Photo photo: photos){
				String tmpDate = df.format(photo.getCreationDateTime().getTime());
				System.out.println(photo.fileName + " - " + tmpDate);
			}
		}
	}
	
	@Override
	public List<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException{
		List<Photo> output = new ArrayList<Photo>();
		Date sDate = null;
		Date eDate = null;
		
		sDate = df.parse(startDate);
		eDate = df.parse(endDate);
		Calendar start = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		List<Photo> photos = null;
		start.setTime(sDate);
		end.setTime(eDate);
		
		if (end.before(start)){
			throw new ParseException("", 2);
		}
		List<Album> album = thisUser.getAlbums();
		for (int i=0; i<album.size(); i++){
			photos = thisUser.albums.get(i).getPhotos();
			for (int j=0; j<photos.size(); j++){
				Calendar temp = photos.get(j).time;
				if (temp.before(end)&&temp.after(start)){
					if(!output.contains(photos.get(j)))
						output.add(photos.get(j));
					//String tmpDate = df.format(photos.get(j).getCreationDateTime().getTime());
					//output.add(photos.get(j).caption+" - Album: "+album.get(i).albumName+
					//		" - Date: "+tmpDate);
				}
			}
		}
		return output;

	}

	@Override
	public List<Photo> getPhotosByTag(List<Photo> result, String tagType, String tagValue){
		if(result == null){
			result = new ArrayList<Photo>();
		}
		List<Album> albums = thisUser.getAlbums();
		if(tagType == null || tagType.equals("")){
			for(Album album: albums){
				List<Photo> photos = album.getPhotos();
				for(Photo photo: photos){
					for(String key:photo.tags.keySet()){
						if(photo.tags.get(key).contains(tagValue)){
							if(!result.contains(photo))
								result.add(photo);
						}
					}
				}
			}
		}else{
			for(Album album: albums){
				List<Photo> photos = album.getPhotos();
				for(Photo photo: photos){
					List<String> values = photo.tags.get(tagType);
					if(values == null){
						continue;
					}else{
						if(values.contains(tagValue)){
							if(!result.contains(photo))
								result.add(photo);
						}
					}
				}
			}
		}
		return result;
	}
}