package cs213.photoAlbum.model;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
/**
 * Photo Class
 * @author Hongjie Lin
 */

public class Photo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * unique(per user) file name
	 */
	public String fileName;
	/**
	 * caption of the photo
	 */
	public String caption;
	/**
	 * dates and time of latest modification
	 */
	public Calendar time;
	/**
	 * tags - include location, people, and other tags
	 */
	public HashMap<String, List<String>> tags;

	public String exactPath;
	
	/**
	 * Constructor of Photo
	 * @param fileName
	 * @param caption
	 * @param time - dates and time of latest modification
	 */
	public Photo(String fileName, String caption, Calendar time, String exactPath){
		this.fileName = fileName;
		this.caption = caption;
		this.time = time;
		this.exactPath = exactPath;
		tags = new HashMap<String, List<String>>();
		tags.put("location", new ArrayList<String>());
		tags.put("people", new ArrayList<String>());
	}

	/**
	 * Edit fileName
	 * @param newFileName
	 */
	public void setFileName(String newFileName){
		this.fileName = fileName;
	}

	/**
	 * Edit caption
	 * @param newCaption
	 */
	public void setCaption(String newCaption){
		this.caption = newCaption;
	}

	/**
	 * Edit time
	 * @param newTime
	 */
	public void setTime(Calendar newTime){
		this.time = newTime;
	}

	/**
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return photo time(MM/DD/YYYY-HH:MM:SS, in 24-hour-clock)
	 */
	public Calendar getCreationDateTime() {
		return time;
	}

	/**
	 * add a new tag
	 * @param type - tag type
	 * @param value - tag value
	 * @return true on success, false otherwise
	 */
	public boolean addTag(String type, String value){
		List<String> tagValues = tags.get(type);
		
		if(tagValues != null){
			if(type.equalsIgnoreCase("location")){
				tagValues = new ArrayList<String>();
				tagValues.add(value);
				tags.put(type, tagValues);
				return true;
			}
			if(tagValues.contains(value)){
				return false;
			}else{
				tagValues.add(value);
				return true;
			}
		}else{//new tagType
			tagValues = new ArrayList<String>();
			tagValues.add(value);
			tags.put(type, tagValues);
			return true;
		}
	}


	/**
	 * @param type - tag type
	 * @param value - tag value
	 * @return true on success, false otherwise
	 */
	public boolean deleteTag(String type, String value){
		List<String> tagValues = tags.get(type);
		if(tagValues == null || tagValues.size()==0 || !tagValues.contains(value)){
			return false;
		}else{
			tagValues.remove(value);
			return true;
		}
	}
	/**
	 * @return the tags
	 */
	public HashMap<String, List<String>> listTags(){
		return tags;
	}

}

