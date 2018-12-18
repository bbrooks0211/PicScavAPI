package brooks.api.data;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class FriendInviteDAO implements DataAccessInterface<FriendInviteModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

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
			logger.error("[ERROR] DATABASE EXCEPTION ERRORED");
		}
		
		return false;
	}

	/**
	 * Accepts the invite
	 * @param FriendInviteModel
	 */
	@Override
	public boolean update(FriendInviteModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Declines the invite by deleting it
	 * @param FriendInviteModel
	 */
	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FriendInviteModel find(FriendInviteModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FriendInviteModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
}
