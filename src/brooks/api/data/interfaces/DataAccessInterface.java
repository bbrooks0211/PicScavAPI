package brooks.api.data.interfaces;

public interface DataAccessInterface<T> {
	public boolean create(T model);
	public boolean update(T model);
	public boolean delete(int id);
	public T find(T model);
	public T findByID(int id);
}
