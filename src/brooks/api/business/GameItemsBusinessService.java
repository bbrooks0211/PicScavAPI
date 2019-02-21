package brooks.api.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameItemModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class GameItemsBusinessService implements GameItemsServiceInterface {
	
	private DataAccessInterface<GameItemModel> gameItemDAO;

	@Override
	public boolean create(GameItemModel item) {
		return gameItemDAO.create(item);
	}

	@Override
	public boolean create(List<GameItemModel> list) {
		boolean status = true;
		
		for(GameItemModel item : list)
		{
			if(!create(item))
				status = false;
		}
		
		return status;
	}
	
	@Autowired
	private void setGameItemDAO(DataAccessInterface<GameItemModel> dao) {
		this.gameItemDAO = dao;
	}
	
}
