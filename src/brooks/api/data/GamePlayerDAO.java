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
import brooks.api.models.PlayerModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class GamePlayerDAO implements DataAccessInterface<PlayerModel>{
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(PlayerModel model) {
		String sql = "INSERT INTO gamePlayers(gameID, userID) VALUES(?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getGameID(), model.getUserID());
			
			return rows == 1 ? true : false;
			
		} catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(PlayerModel model) {
		String sql = "UPDATE gamePlayers SET points=? WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getScore(), model.getId());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM gamePlayers WHERE id=?";
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
	public PlayerModel find(PlayerModel model) {
		String sql = "SELECT * FROM gamePlayers WHERE userID=? AND gameID=?";
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getUserID(), model.getGameID());
			if(srs.next())
			{
				return new PlayerModel(srs.getInt("id"), srs.getInt("userID"), srs.getInt("points"), srs.getInt("gameID"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		return new PlayerModel();
	}

	@Override
	public PlayerModel findByID(int id) {
		String sql = "SELECT * FROM gamePlayers WHERE id=?";
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				return new PlayerModel(srs.getInt("id"), srs.getInt("userID"), srs.getInt("points"), srs.getInt("gameID"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		return new PlayerModel();
	}

	@Override
	public List<PlayerModel> findAllForID(int id) {
		List<PlayerModel> list = new ArrayList<PlayerModel>();
		String sql = "SELECT * FROM gamePlayers WHERE gameID=?";
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			while(srs.next())
			{
				PlayerModel model = new PlayerModel(srs.getInt("id"), srs.getInt("userID"), "name", srs.getInt("points"), srs.getInt("gameID"));
				list.add(model);
			}
		} catch (Exception e) {
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}

	@Override
	public List<PlayerModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<PlayerModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
