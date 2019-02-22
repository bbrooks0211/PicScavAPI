package brooks.api.data;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.CategoryModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class CategoryDAO implements DataAccessInterface<CategoryModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	@Override
	public boolean create(CategoryModel model) {
		String sql = "INSERT INTO categories(name, paid) VALUES(?, ?)";
		
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getName(), model.getPaid());
			
			return rows == 1 ? true : false;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(CategoryModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CategoryModel find(CategoryModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryModel findByID(int id) {
		String sql = "SELECT * FROM categories WHERE id=?";
		CategoryModel category = new CategoryModel();
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				category = new CategoryModel(srs.getInt("id"), srs.getString("name"), srs.getInt("paid"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return category;
	}

	@Override
	public List<CategoryModel> findAllForID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

}
