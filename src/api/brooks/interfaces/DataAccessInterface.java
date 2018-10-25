package api.brooks.interfaces;

public interface DataAccessInterface<T> {
	public boolean create(T model);
	public boolean update(T model);
	public boolean delete(T model);
	public T find(T model);
	public T findByID(int id);
}
