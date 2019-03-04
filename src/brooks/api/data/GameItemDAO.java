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
import brooks.api.models.GameItemModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class GameItemDAO implements DataAccessInterface<GameItemModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(GameItemModel model) {
		String sql = "INSERT INTO gameItems(gameID, item, points) VALUES(?, ?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getGameID(), model.getItem(), model.getPoints());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(GameItemModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameItemModel find(GameItemModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameItemModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameItemModel> findAllForID(int id) {
		String sql = "SELECT * FROM gameItems WHERE gameID=?";
		List<GameItemModel> list = new ArrayList<GameItemModel>();
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			while(srs.next())
			{
				list.add(new GameItemModel(srs.getInt("id"), srs.getInt("gameID"), srs.getString("item"), srs.getInt("points"), srs.getInt("found"), null));
			}
		} catch(Exception e) {
			logger.error("[ERROR] EXCEPTION OCCURRED IN THE DATA ACCESS LAYER: " + e.getLocalizedMessage().toString() + " \n" + e.getStackTrace().toString());
		}
		
		return list;
	}

	@Override
	public List<GameItemModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameItemModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

}
