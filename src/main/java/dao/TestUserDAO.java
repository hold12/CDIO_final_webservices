package dao;

import dto.User;
import jdbclib.DALException;

/**
 * Created by awo on 09/06/17.
 */
public class TestUserDAO implements IUserDAO {
    @Override
    public User getUser(int userId) throws DALException {
        User activeUser   = new User(1, "John", "Doe", "JD", "password", true);
        User inactiveUser = new User(2, "Jane", "Smith", "JS", "jsLover", false);

        return (userId == 1) ? activeUser : inactiveUser;
    }
}
