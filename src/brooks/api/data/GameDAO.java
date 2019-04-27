package brooks.api.data;

import java.sql.Statement;
import java.util.ArrayList;
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
		String sql = "DELETE FROM games WHERE id=?";
		
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
		String sql1 = "SELECT * FROM gamePlayers WHERE userID=? ORDER BY `id` DESC";
		List<GameModel> list = new ArrayList<GameModel>();
		try {
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql1, id);
			while(srs.next())
			{
				String sql = "SELECT * FROM games WHERE id=?";
				SqlRowSet srs1 = jdbcTemplateObject.queryForRowSet(sql, srs.getInt("gameID"));
				
				if(srs1.next())
				{
					GameModel game = new GameModel(srs1.getInt("id"), srs1.getInt("hostID"), srs1.getString("lobbyName"), srs1.getString("category"), srs1.getLong("timeLimit"), srs1.getTimestamp("endTime"), srs1.getTimestamp("startTime"));
					list.add(game);
				}
			}
		} catch(Exception e) {
			logger.error("[ERROR] AN EXCEPTION OCCURRED IN THE DATA ACCESS LAYER \n" + e.getLocalizedMessage().toString() + "\n" + e.getStackTrace().toString());
		}
		
		return list;
	}
	
	@Override
	public List<GameModel> findAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM games WHERE endTime > sysdate()";

		return null;
	}

	@Override
	public List<GameModel> getCurrentGames(int id) {
		String sql1 = "SELECT * FROM gamePlayers WHERE userID=? ORDER BY `id` DESC";
		List<GameModel> list = new ArrayList<GameModel>();
		try {
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql1, id);
			while(srs.next())
			{
				String sql = "SELECT * FROM games WHERE endTime > sysdate() AND id=?";
				SqlRowSet srs1 = jdbcTemplateObject.queryForRowSet(sql, srs.getInt("gameID"));
				
				if(srs1.next())
				{
					GameModel game = new GameModel(srs1.getInt("id"), srs1.getInt("hostID"), srs1.getString("lobbyName"), srs1.getString("category"), srs1.getLong("timeLimit"), srs1.getTimestamp("endTime"), srs1.getTimestamp("startTime"));
					list.add(game);
				}
			}
		} catch(Exception e) {
			logger.error("[ERROR] AN EXCEPTION OCCURRED IN THE DATA ACCESS LAYER \n" + e.getLocalizedMessage().toString() + "\n" + e.getStackTrace().toString());
		}
		
		return list;
	}
	
	@Override
	public List<GameModel> getPastGames(int id, int num) {
		String sql1 = "SELECT * FROM gamePlayers WHERE userID=? ORDER BY `id` DESC LIMIT " + num;
		List<GameModel> list = new ArrayList<GameModel>();
		try {
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql1, id);
			while(srs.next())
			{
				String sql = "SELECT * FROM games WHERE endTime < sysdate() AND id=?";
				SqlRowSet srs1 = jdbcTemplateObject.queryForRowSet(sql, srs.getInt("gameID"));
				
				if(srs1.next())
				{
					GameModel game = new GameModel(srs1.getInt("id"), srs1.getInt("hostID"), srs1.getString("lobbyName"), srs1.getString("category"), srs1.getLong("timeLimit"), srs1.getTimestamp("endTime"), srs1.getTimestamp("startTime"));
					list.add(game);
				}
			}
		} catch(Exception e) {
			logger.error("[ERROR] AN EXCEPTION OCCURRED IN THE DATA ACCESS LAYER \n" + e.getLocalizedMessage().toString() + "\n" + e.getStackTrace().toString());
		}
		
		return list;
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

/*
Copyright 2019, Brendan Brooks.  

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
