package brooks.api.data;

import java.util.List;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameItemModel;

public class GameItemDAO implements DataAccessInterface<GameItemModel> {

	@Override
	public boolean create(GameItemModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(GameItemModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameItemModel find(GameItemModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameItemModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameItemModel> findAllForID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameItemModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameItemModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
