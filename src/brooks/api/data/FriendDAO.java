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
		String sql = "INSERT INTO friendRelations(user_1, user_2) VALUES(?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getUsername(), model.getFriendUsername());
			
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
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Finds all friends for a user by their username
	 * @param id
	 */
	@Override
	public List<FriendModel> findAllByString(String string) {
		String sql = "SELECT * FROM friendRelations WHERE user_1 = ? OR user_2 = ?";
		
		List<FriendModel> list = new ArrayList<FriendModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, string, string);
			while(srs.next())
			{
				list.add(new FriendModel(srs.getInt("id"), srs.getString("user_1"), srs.getString("user_2")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return list;
	}

	@Override
	public boolean update(FriendModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FriendModel find(FriendModel model) {
		// TODO Auto-generated method stub
		return null;
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
