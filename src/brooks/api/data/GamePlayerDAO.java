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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
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
				PlayerModel model = new PlayerModel(srs.getInt("id"), srs.getInt("userID"), srs.getInt("gameID"));
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
}
