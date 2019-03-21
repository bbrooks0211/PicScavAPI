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
