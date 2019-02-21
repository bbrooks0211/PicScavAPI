package brooks.api.data;

import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.GameDAOInterface;
import brooks.api.models.GameModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * Data access class for game-related operations
 * @author Brendan Brooks
 *
 */
public class GameDAO implements GameDAOInterface {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	@Override
	public int createAndReturnID(GameModel model) {
		String sql = "INSERT INTO games(hostID, lobbyName, hostUsername, category, timeLimit, endTime, startTime) VALUES(?, ?, ?, ?, ?, ?, ?)";
		int id = -1;
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getHostID(), model.getLobbyName(), model.getHostUsername(), model.getCategory(), model.getTimeLimit(), model.getEndTime(), model.getStartTime());
			if(rows == 1) {
				String queryID = "SELECT LAST_INSERT_ID() FROM games";
				SqlRowSet srs = jdbcTemplateObject.queryForRowSet(queryID);
				if(srs.next())
					id = srs.getInt("LAST_INSERT_ID()");
			}
			return id;
		} catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return id;
	}

	@Override
	public boolean create(GameModel model) {
		String sql = "INSERT INTO games(hostID, lobbyName, hostUsername, category, timeLimit, endTime, startTime) VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getHostID(), model.getLobbyName(), model.getHostUsername(), model.getCategory(), model.getTimeLimit(), model.getEndTime(), model.getStartTime());
			
			return rows == 1 ? true : false;
		} catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(GameModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameModel find(GameModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel findByID(int id) {
		String sql = "SELECT * FROM games WHERE id=?";
		try {
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				GameModel game = new GameModel(srs.getInt("id"), srs.getInt("hostID"), srs.getString("lobbyName"), srs.getString("category"), srs.getLong("timeLimit"), srs.getTimestamp("endTime"), srs.getTimestamp("startTime"));
				return game;
			}
		} catch(Exception e) {
			logger.error("[ERROR] AN EXCEPTION OCCURRED IN THE DATA ACCESS LAYER \n" + e.getLocalizedMessage() + "\n" + e.getStackTrace());
		}
		
		return new GameModel();
	}

	@Override
	public List<GameModel> findAllForID(int id) {
		return null;
	}

	@Override
	public List<GameModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<GameModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
