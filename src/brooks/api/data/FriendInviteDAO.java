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
import brooks.api.models.FriendInviteModel;
import brooks.api.utility.interceptors.LoggingInterceptor;


/**
 * Data Access class for all Friend Invite operations
 * @author Brendan Brooks
 *
 */
public class FriendInviteDAO implements DataAccessInterface<FriendInviteModel> {
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	/**
	 * Creates an invite in the database
	 * @param model
	 */
	@Override
	public boolean create(FriendInviteModel model) {
		String sql = "INSERT INTO friendInvites(senderID, receiverID, accepted) VALUES(?, ?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getSenderID(), model.getReceiverID(), 0);
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	/**
	 * Accepts the invite
	 * @param FriendInviteModel
	 */
	@Override
	public boolean update(FriendInviteModel model) {
		String sql = "UPDATE friendInvites SET accepted=1 WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getId());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	/**
	 * Declines the invite by deleting it
	 * @param FriendInviteModel
	 */
	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM friendInvites WHERE id=?";
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

	/**
	 * Finds an invite based on the sender and receiver
	 * @param model
	 * @return FriendInviteModel
	 */
	@Override
	public FriendInviteModel find(FriendInviteModel model) {
		String sql = "SELECT * FROM friendInvites WHERE senderID = ? AND receiverID = ? AND accepted = ?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getSenderID(), model.getReceiverID(), 0);
			if(srs.next())
			{
				FriendInviteModel invite = new FriendInviteModel(srs.getInt("id"), srs.getInt("senderID"), srs.getInt("receiverID"), srs.getInt("accepted"));
				return invite;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return new FriendInviteModel();
	}

	/**
	 * Gets the friend invite by the id
	 * @param id
	 */
	@Override
	public FriendInviteModel findByID(int id) {
		String sql = "SELECT * FROM friendInvites WHERE id=?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id);
			if(srs.next())
			{
				FriendInviteModel invite = new FriendInviteModel(srs.getInt("id"), srs.getInt("senderID"), srs.getInt("receiverID"), srs.getInt("accepted"));
				return invite;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return new FriendInviteModel();
	}

	/**
	 * finds all invites for a user based on their ID
	 * @param id
	 * @return List<FriendInviteModel>
	 */
	@Override
	public List<FriendInviteModel> findAllForID(int id) {
		String sql = "SELECT * FROM friendInvites WHERE receiverID=? AND accepted=?";
		List<FriendInviteModel> list = new ArrayList<FriendInviteModel>();
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, id, 0);
			while(srs.next())
			{
				FriendInviteModel invite = new FriendInviteModel(srs.getInt("id"), srs.getInt("senderID"), srs.getInt("receiverID"), srs.getInt("accepted"));
				list.add(invite);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
		return list;
	}

	/**
	 * Gets all friend invites for a user based on their username.
	 * NO LONGER IN USE
	 * @param string
	 */
	@Override
	public List<FriendInviteModel> findAllByString(String string) {
		return null;
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<FriendInviteModel> findAll() {
		// TODO Auto-generated method stub
		return null;
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
