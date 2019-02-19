package brooks.api.data.interfaces;

import java.util.List;

/**
 * Generic interface nterface for Data Access Objects
 * @author Brendan Brooks
 *
 * @param <T>
 */
public interface DataAccessInterface<T> {
	
	/**
	 * Creates a database entry for the parameter
	 * @param model
	 * @return boolean
	 */
	public boolean create(T model);
	
	/**
	 * Updates the corresponding database entry for the object
	 * @param model
	 * @return
	 */
	public boolean update(T model);
	
	/**
	 * Deletes an entry from the database
	 * @param id
	 * @return boolean
	 */
	public boolean delete(int id);
	
	/**
	 * Find the database entry by a model
	 * @param model
	 * @return model
	 */
	public T find(T model);
	
	/**
	 * Find an entry by the id
	 * @param id
	 * @return model
	 */
	public T findByID(int id);
	
	/**
	 * Find a list of objects for a given foreign key that's an ID
	 * @param id
	 * @return List<T>
	 */
	public List<T> findAllForID(int id);
	
	/**
	 * Find a list of objects for a given string that they have in common (such as a username, category, etc.)
	 * @param id
	 * @return List<T>
	 */
	public List<T> findAllByString(String string);
	
	public List<T> findAll();
}
