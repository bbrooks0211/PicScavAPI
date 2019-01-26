package brooks.api.business;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameModel;
import brooks.api.utility.TimeUtility;

public class GameBusinessService implements GameBusinessServiceInterface {
	
	private DataAccessInterface<GameModel> gameDAO;

	@Override
	public boolean createNewGame(GameModel game) {
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		game.setStartTime(time);
		game.setEndTime(TimeUtility.addHoursToTimestamp(time, game.getTimeLimit()));
		return gameDAO.create(game);
	}
	
	@Autowired
	private void setGameDAO(DataAccessInterface<GameModel> dao) {
		this.gameDAO = dao;
	}	
}
