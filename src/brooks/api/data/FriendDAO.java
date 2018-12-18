package brooks.api.data;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

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
			int rows = jdbcTemplateObject.update(sql, model.getUsername(), model.getFriendUsername(), 0);
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION ERRORED");
		}
		
		return false;
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
