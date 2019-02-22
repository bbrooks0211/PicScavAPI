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
import brooks.api.models.ItemModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class ItemReferenceDAO implements DataAccessInterface<ItemModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(ItemModel model) {
		String sql = "INSERT INTO items(category, item, points, creatorID) VALUES(?, ?, ?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getItem(), model.getPoints(), model.getCreatorID());
			
			return rows == 1 ? true : false;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(ItemModel model) {
		String sql = "UPDATE items SET category=?, item=?, points=?, creatorID=? WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getItem(), model.getPoints(), model.getCreatorID());
			
			return rows == 1 ? true : false;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM items WHERE id=?";
		
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
	public ItemModel find(ItemModel model) {
		String sql = "SELECT * FROM items WHERE item=? AND category=?";
		ItemModel item = new ItemModel();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getItem(), model.getCategory());
			if(srs.next())
			{
				item = new ItemModel(srs.getInt("id"), srs.getString("item"), srs.getInt("points"), srs.getInt("creatorID"), "", srs.getInt("categoryID"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return item;
	}

	@Override
	public ItemModel findByID(int id) {
		String sql = "SELECT * FROM items WHERE id=?";
		ItemModel item = new ItemModel();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				item = new ItemModel(srs.getInt("id"), srs.getString("item"), srs.getInt("points"), srs.getInt("creatorID"), "", srs.getInt("categoryID"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return item;
	}

	@Override
	public List<ItemModel> findAllForID(int id) {
		String sql = "SELECT * FROM items WHERE categoryID=?";
		List<ItemModel> list = new ArrayList<ItemModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			while(srs.next())
			{
				ItemModel item = new ItemModel(srs.getInt("id"), srs.getString("item"), srs.getInt("points"), srs.getInt("creatorID"), "", srs.getInt("categoryID"));
				list.add(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}

	@Override
	public List<ItemModel> findAllByString(String string) {
		
		String sql = "SELECT * FROM items WHERE category=?";
		List<ItemModel> list = new ArrayList<ItemModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, string);
			while(srs.next())
			{
				ItemModel item = new ItemModel(srs.getInt("id"), srs.getString("item"), srs.getInt("points"), srs.getInt("creatorID"), "", srs.getInt("categoryID"));
				list.add(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}
	
	@Override
	public List<ItemModel> findAll() {
		
		String sql = "SELECT * FROM items";
		List<ItemModel> list = new ArrayList<ItemModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while(srs.next())
			{
				ItemModel item = new ItemModel(srs.getInt("id"), srs.getString("item"), srs.getInt("points"), srs.getInt("creatorID"), "", srs.getInt("categoryID"));
				list.add(item);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

}
