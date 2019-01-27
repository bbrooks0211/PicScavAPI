package brooks.api.data;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * Data access class for game-related operations
 * @author Brendan Brooks
 *
 */
public class GameDAO implements DataAccessInterface<GameModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameModel> findAllForID(int id) {
		// TODO Auto-generated method stub
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
	
}
