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
import brooks.api.models.GameInviteModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * DAO for inviting players to games
 * @author Brendan Brooks
 *
 */
public class GameInviteDAO implements DataAccessInterface<GameInviteModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(GameInviteModel model) {
		String sql = "INSERT INTO gameInvites(senderID, gameID, receiverID, accepted) VALUES(?, ?, ?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getSenderID(), model.getGameID(), model.getReceiverID(), 0);
			
			return rows == 1 ? true : false;
		} catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	/**
	 * Used for accepting invites
	 * @param GameInviteModel
	 * @return boolean
	 */
	@Override
	public boolean update(GameInviteModel model) {
		String sql = "UPDATE gameInvites SET accepted=1 WHERE id=?";
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
	 * Used for declining invites (by deleting them from the database)
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM gameInvites WHERE id=?";
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
	public GameInviteModel find(GameInviteModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameInviteModel findByID(int id) {
		String sql = "SELECT * FROM gameInvites WHERE id=?";
		GameInviteModel invite = new GameInviteModel();
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				invite = new GameInviteModel(srs.getInt("id"), srs.getInt("senderID"), srs.getInt("gameID"), srs.getInt("receiverID"), srs.getInt("accepted"));
			}
		} catch(Exception e) {
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return invite;
	}

	@Override
	public List<GameInviteModel> findAllForID(int id) {
		String sql = "SELECT * FROM gameInvites WHERE receiverID=? AND accepted=?";
		List<GameInviteModel> list = new ArrayList<GameInviteModel>();
		
		try 
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id, 0);
			while(srs.next())
			{
				GameInviteModel invite = new GameInviteModel(srs.getInt("id"), srs.getInt("senderID"), srs.getInt("gameID"), srs.getInt("receiverID"), srs.getInt("accepted"));
				list.add(invite);
			}
			
		} catch(Exception e) {
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}

	@Override
	public List<GameInviteModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<GameInviteModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
