package api.brooks.interfaces;

import api.brooks.models.LoginModel;
import api.brooks.models.UserModel;

public interface UserDataAccessInterface<T> {
	public boolean create(T model);
	public boolean update(T model);
	public boolean delete(T model);
	public T find(T model);
	public T findByID(int id);
	public UserModel tryLogin(LoginModel model);
}
