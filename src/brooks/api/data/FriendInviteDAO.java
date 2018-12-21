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
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * Data Access class for all Friend Invite operations
 * @author Brendan Brooks
 *
 */
public class FriendInviteDAO implements DataAccessInterface<FriendInviteModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	/**
	 * Creates an invite in the database
	 * @param model
	 */
	@Override
	public boolean create(FriendInviteModel model) {
		String sql = "INSERT INTO friendInvites(sender, receiver, accepted) VALUES(?, ?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getSender(), model.getReceiver(), 0);
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	/**
	 * Accepts the invite
	 * @param FriendInviteModel
	 */
	@Override
	public boolean update(FriendInviteModel model) {
		String sql = "UPDATE friendInvites SET accepted=1 WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getId());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	/**
	 * Declines the invite by deleting it
	 * @param FriendInviteModel
	 */
	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM friendInvites WHERE id=?";
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
	public FriendInviteModel find(FriendInviteModel model) {
		String sql = "SELECT * FROM friendInvites WHERE sender = ? AND receiver = ?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getSender(), model.getReceiver());
			if(srs.next())
			{
				FriendInviteModel invite = new FriendInviteModel(srs.getInt("id"), srs.getString("sender"), srs.getString("receiver"), srs.getInt("accepted"));
				return invite;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return new FriendInviteModel();
	}

	/**
	 * Gets the friend invite by the id
	 * @param id
	 */
	@Override
	public FriendInviteModel findByID(int id) {
		String sql = "SELECT * FROM friendInvites WHERE id=?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				FriendInviteModel invite = new FriendInviteModel(srs.getInt("id"), srs.getString("sender"), srs.getString("receiver"), srs.getInt("accepted"));
				return invite;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return new FriendInviteModel();
	}

	@Override
	public List<FriendInviteModel> findAllForID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets all friend invites for a user based on their username
	 * @param string
	 */
	@Override
	public List<FriendInviteModel> findAllByString(String string) {
		
		String sql = "SELECT * FROM friendInvites WHERE receiver = ? AND accepted = 0";
		
		List<FriendInviteModel> list = new ArrayList<FriendInviteModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, string);
			while(srs.next())
			{
				list.add(new FriendInviteModel(srs.getInt("id"), srs.getString("sender"), srs.getString("receiver"), srs.getInt("accepted")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return list;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
}
