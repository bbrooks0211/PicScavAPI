package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.GameItemModel;

public interface GameItemsServiceInterface {
	public boolean create(GameItemModel item);
	public boolean create(List<GameItemModel> list);
}
