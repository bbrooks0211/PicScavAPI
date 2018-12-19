package brooks.api.data.interfaces;

import java.util.List;

public interface DataAccessInterface<T> {
	public boolean create(T model);
	public boolean update(T model);
	public boolean delete(int id);
	public T find(T model);
	public T findByID(int id);
	public List<T> findAllForID(int id);
	public List<T> findAllByString(String string);
}
