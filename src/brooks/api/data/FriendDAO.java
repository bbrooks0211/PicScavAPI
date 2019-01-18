package brooks.api.data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * Data Access class for all Friend operations
 * @author Brendan Brooks
 *
 */
public class FriendDAO implements DataAccessInterface<FriendModel> {
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	/**
	 * Adds a friend relationship to the database
	 * @param FriendModel
	 */
	@Override
	public boolean create(FriendModel model) {
		String sql = "INSERT INTO friendRelations(user_1_id, user_2_id) VALUES(?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getUserID(), model.getFriendID());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}
	
	@Override
	public List<FriendModel> findAllForID(int id) {
		String sql = "SELECT * FROM friendRelations WHERE user_1_id = ? OR user_2_id = ?";
		
		List<FriendModel> list = new ArrayList<FriendModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id, id);
			while(srs.next())
			{
				int user1 = srs.getInt("user_1_id");
				int user2 = srs.getInt("user_2_id");
				
				//If/else statement to ensure that the friendUsername field in the model is filled by the friend info, and the username field is filled by the user
				if(user1 == id)
					list.add(new FriendModel(srs.getInt("id"), user2, user1));
				else
					list.add(new FriendModel(srs.getInt("id"), user1, user2));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return list;
	}
	
	/**
	 * Finds all friends for a user by their username.
	 * THIS FUNCTION IS DEPRECATED AND WILL NO LONGER BE USED
	 * @param id
	 */
	@Override
	public List<FriendModel> findAllByString(String string) {
		return null;
	}

	@Override
	public boolean update(FriendModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM friendRelations WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, id);
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return false;
	}

	@Override
	public FriendModel find(FriendModel model) {
		String sql = "SELECT * FROM friendRelations WHERE (user_1_id = ? AND user_2_id = ?) OR (user_1_id = ? AND user_2_id = ?)";
		
		FriendModel returnModel = new FriendModel();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getUserID(), model.getFriendID(), model.getFriendID(), model.getUserID());
			if(srs.next())
			{
				returnModel = new FriendModel(srs.getInt("id"), srs.getInt("user_1_id"), srs.getInt("user_2_id"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return returnModel;
	}

	@Override
	public FriendModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

}
