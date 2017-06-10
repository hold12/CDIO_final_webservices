package dao;

import dto.User;
import jdbclib.DALException;

public interface IUserDAO {
	User getUser(int userId) throws DALException;
}
