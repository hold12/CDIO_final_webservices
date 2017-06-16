package dao;

import dto.User;
import jdbclib.DALException;

import java.util.List;

public interface IUserDAO {
	User getUser(int userId) throws DALException;
	User getUser(String token);
	User getFullUser(int userId) throws DALException;
	int createUser(User user) throws DALException, DataValidationException;
	void updateUser(User user) throws DALException, DataValidationException;
	List<User> getUserList() throws DALException;
	String generatePassword(User user) throws DALException, DataValidationException;
}
