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
		String sql = "SELECT * FROM categories WHERE name=?";
		CategoryModel category = new CategoryModel();

		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getName());
			if(srs.next())
			{
				category = new CategoryModel(srs.getInt("id"), srs.getString("name"), srs.getInt("paid"));
				return category;
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		return category;
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
		String sql = "SELECT * FROM categories WHERE id=?";
		List<CategoryModel> list = new ArrayList<CategoryModel>();
		CategoryModel category = new CategoryModel();
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			while(srs.next())
			{
				list.add(new CategoryModel(srs.getInt("id"), srs.getString("name"), srs.getInt("paid")));
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
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

/*
This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/
