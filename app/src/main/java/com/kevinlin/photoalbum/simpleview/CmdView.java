package cs213.photoAlbum.simpleview;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.model.iBackend;

public class CmdView {

	/**
	 * @author Edward Mamedov
	 * @param String Array of arguments for view client.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller control = new Control();
		iBackend backend = new cs213.photoAlbum.model.iBackend();
		if(args.length == 0)
		{
			System.out.println("Not enough arguments, type 'help' for usage");
			return;
		}

		if(args[0].equals("help")){
			System.out.println("listusers                       List all existing users");
			System.out.println("adduser [userid] [username]     Add a user");
			System.out.println("deleteuser [userid]             Delete a user");
			System.out.println("login [userid]                  Login as an existing user");
		}

		else if(args[0].equals("listusers")){
			if(args.length != 1)
			{
				System.out.println("Too many arguments, type 'help' for usage");
				return;
			}
			List<User> users = backend.getUsers();

			if (users.isEmpty()){
				System.out.println("No Users Exist");
			}
			else{
				for(User userList : users) {
					System.out.println(userList.id);
				}
			}
		}

		else if(args[0].equals("adduser")){
			if (args.length != 3){
				System.out.println("Invalid number of arguments, type 'help' for usage");
				return;
			}

			boolean temp;
			temp = control.addUser(args[1], args[2]);
			if (temp == false){
				System.out.println("user "+args[1]+" already exists with name "+args[2]);
			}
			else{
				System.out.println("created user "+args[1]+" with name "+args[2]);
			}
			//add user
		}

		else if(args[0].equals("deleteuser")){
			if (args.length != 2){
				System.out.println("Invalid number of arguments, type 'help' for usage");
				return;
			}

			boolean temp;
			temp = control.deleteUser(args[1]);
			if (temp == false){
				System.out.println("user "+args[1]+" does not exist");
			}
			else{
				System.out.println("deleted user "+args[1]);
			}	
		}

		else if (args[0].equals("login")){
			if (args.length != 2){
				System.out.println("Invalid number of arguments, type 'help' for usage");
				return;
			}

			boolean temp;
			temp = control.login(args[1]);
			if (temp==false){
				System.out.println("user "+args[1]+" does not exist");
			}
			else{
				System.out.println("Interactive mode:");
				interactiveMode(control);
			}
		}
		
		try
		{
			Control c = (Control)control;
			iBackend.writeBackend((iBackend)c.backend);
		}
		catch(Exception e)
		{
			System.out.println("ErrorText: Didn't save backend object.");
		}
		
		
	}

	/**
	 * @author Edward Mamedov
	 * Will enable interactive mode upon successfully logging a user in, accepts a sequence of commands
	 */
	private static void interactiveMode(Controller control){
		//String b = control.whoami();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		boolean login = true;
		while(login){
			try{
				input = br.readLine();
			}
			catch(Exception e){
				System.out.println("ErrorText: No input, try again");
			}
			String tokens[] = tokenize(input);

			if (tokens.length<=0){
				System.out.println("ErrorText: Please input valid arguments");
				continue;
			}

			if (tokens[0].equals("createAlbum")){
				if (tokens.length!=2){
					System.out.println("ErrorText: Invalid number of arguments, use 'createAlbum \"<name>\"'");
				}
				else{
					if(control.createAlbum(tokens[1])){
						System.out.println("created album for user "+control.whoami()+": ");
						System.out.println(tokens[1]);
					}
					else{
						System.out.println("album exists for user "+control.whoami()+": ");
						System.out.println(tokens[1]);
					}
				}
			}

			else if (tokens[0].equals("deleteAlbum")){
				if (tokens.length!=2){
					System.out.println("ErrorText: Invalid number of arguments, use 'deleteAlbum \"<name>\"'");
				}
				else{
					if(control.deleteAlbum(tokens[1])){
						System.out.println("deleted album for user "+control.whoami()+": ");
						System.out.println(tokens[1]);
					}
					else{
						System.out.println("album does not exist for user "+control.whoami()+": ");
						System.out.println(tokens[1]);
					}
				}
			}

			else if (tokens[0].equals("listAlbums")){
				if (tokens.length!=1){
					System.out.println("ErrorText: Invalid number of arguments, use 'listAlbums");
				}
				else{
					control.listAlbums();
				}
			}

			else if (tokens[0].equals("listPhotos")){
				if (tokens.length!=2){
					System.out.println("ErrorText: Invalid number of arguments, use 'listPhotos \"<name>\"'");
				}
				else{
					control.listPhotos(tokens[1]);
				}
			}

			else if (tokens[0].equals("addPhoto")){
				if (tokens.length!=4){
					System.out.println("ErrorText: Invalid number of arguments, use 'addPhoto \"<fileName>\" \"<caption>\" \"<albumName>\"'");
				}
				else{
					if(control.addPhoto(tokens[1], tokens[2], tokens[3])){
						System.out.println("Added photo "+tokens[1]+" ");
						System.out.println(tokens[2]+" - Album: "+tokens[3]);
					}
					else{
						//System.out.println(tokens[1]);
						//System.out.println(tokens[2]);
						//System.out.println(tokens[3]);
					}
				}
			}

			else if (tokens[0].equals("movePhoto")){
				if (tokens.length!=4){
					System.out.println("ErrorText: Invalid number of arguments, use 'movePhoto \"<fileName>\" \"<oldAlbumName>\" \"<newAlbumName>\"'");
				}
				else{
					if (control.movePhoto(tokens[1],tokens[2],tokens[3])){
						System.out.println("Moved photo "+tokens[1]+":");
						System.out.println(tokens[1]+" - From album "+tokens[2]+" to album "+tokens[3]);
					}
				}
			}

			else if (tokens[0].equals("removePhoto")){
				if (tokens.length!=3){
					System.out.println("ErrorText: Invalid number of arguments, use 'removePhoto \"<fileName\" \"<albumName>\"'");
				}
				else{
					if (control.removePhoto(tokens[1], tokens[2])){
						System.out.println("removed photo:");
						System.out.println(tokens[1]+" - From album "+tokens[2]);
					}
				}
			}

			else if (tokens[0].equals("addTag")){
				if(tokens.length != 4){
					System.out.println("ErrorText: Invalid number of arguments, use 'addTag \"<fileName>\" <tagType>:\"<tagValue>\"'");
				}else{
					if(control.addTag(tokens[1], tokens[2].replaceAll(":",""), tokens[3])){
						System.out.println("Added tag:");
						System.out.println(tokens[1] + " " + tokens[2]+""+tokens[3]);
					}
				}
			}

			else if (tokens[0].equals("deleteTag")){
				if(tokens.length != 4){
					System.out.println("ErrorText: Invalid number of arguments, use 'deleteTag \"<fileName>\" <tagType>:\"<tagValue>\"'");
				}else{
					if(control.deleteTag(tokens[1], tokens[2].replaceAll(":",""), tokens[3])){
						System.out.println("Deleted tag:");
						System.out.println(tokens[1] + " " + tokens[2]+""+tokens[3]);
					}
				}
			}

			else if (tokens[0].equals("listPhotoInfo")){
				if(tokens.length != 2){
					System.out.println("ErrorText: Invalid number of arguments, use 'listPhotoInfo \"<fileName>\"'");
				}else{
					control.listPhotoInfo(tokens[1]);
				}
			}

			else if (tokens[0].equals("getPhotosByDate")){
				if(tokens.length != 3 )
				{
					System.out.println("ErrorText: Invalid number of arguments, use 'getPhotosByDate \"<startDate>\" \"<endDate>\"'");
				}
				else{
					List<String> photos = new ArrayList<String>();
					try {
						photos = control.getPhotosByDate(tokens[1], tokens[2]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i=0; i<photos.size(); i++){
						System.out.println(photos.get(i));
					}
				}
			}

			else if (tokens[0].equals("getPhotosByTag")){
				List<String> tags = new ArrayList<String>();
				for(int i = 1; i < tokens.length; i++){
					tags.add(tokens[i]);
				}
				
				List<String> output = control.getPhotosByTag(tags);
				Collections.sort(output);
				for(String str: output){
					System.out.println(str);
				}
			}

			else if (tokens[0].equals("logout")){
				login = false;
			}

			else
			{
				System.out.println("ErrorText: No such command");
				continue;
			}
		}
		return;
	}

	private static String[] tokenize(String input){
		List<String> matchList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(input);
		while (regexMatcher.find()) {
		    if (regexMatcher.group(1) != null) {
		        // Add double-quoted string without the quotes
		        matchList.add(regexMatcher.group(1));
		    } else if (regexMatcher.group(2) != null) {
		        // Add single-quoted string without the quotes
		        matchList.add(regexMatcher.group(2));
		    } else {
		        // Add unquoted word
		        matchList.add(regexMatcher.group());
		    }
		} 
		String[] pieces = new String[matchList.size()];
		for(int i = 0; i < matchList.size(); i++)
			pieces[i] = matchList.get(i);
		return pieces;
	}

}
