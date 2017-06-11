package dao;

import dto.User;
import jdbclib.DALException;

import java.util.List;

public interface IUserDAO {
	User getUser(int userId) throws DALException;
	User getUser(String token) throws DALException;
	User getFullUser(int userId) throws DALException;
	void updateUser(User user) throws DALException;
	List<User> getUserList() throws DALException;
}
