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
import brooks.api.models.FoundItemModel;
import brooks.api.models.GameItemModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class FoundItemDAO implements DataAccessInterface<FoundItemModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(FoundItemModel model) {
		String sql = "INSERT INTO foundItems(gameID, itemID, userID, points, image) VALUES(?, ?, ?, ?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getGameID(), model.getItemID(), model.getUserID(), model.getPoints(), model.getImageURL());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(FoundItemModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FoundItemModel find(FoundItemModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoundItemModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoundItemModel> findAllForID(int id) {
		List<FoundItemModel> list = new ArrayList<FoundItemModel>();
		String sql = "SELECT * FROM foundItems WHERE gameID=?";
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			while(srs.next())
			{
				list.add(new FoundItemModel(srs.getInt("id"), srs.getInt("gameID"), srs.getInt("itemID"), srs.getInt("userID"), srs.getInt("points"), srs.getString("image")));
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
	public List<FoundItemModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoundItemModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

}
