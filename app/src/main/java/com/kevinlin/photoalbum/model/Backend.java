package com.kevinlin.photoalbum.model;

import java.util.List;

/**
 * functionality that allows for the storage and retrieval from the data directory
 * @author Hongjie Lin
 *
 */
public interface Backend {
	
	/**
	 * read a user from storage into memory
	 * @param id - the user's id to be read from storage
	 * @return 
	 */
	public User read(String id);
	
	/**
	 * write a user to storage
	 * @param id - the user's id to be written to storage
	 */
	public void write(String id);
	
	/**
	 * add an user
	 * @param user - the user to be added
	 * @return true on success, false otherwise
	 */
	public boolean addUser(User user);
	/**
	 * delete an user
	 * @param id - the id of user to be deleted
	 * @return true on success, false otherwise
	 */
	public boolean deleteUser(String id);
	
	
	/**
	 * @return a list of existing users
	 */
	public List<User> getUsers();
}
