package dao;

import dto.User;
import jdbclib.DALException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Created by awo on 09/06/17.
 */
public class TestUserDAO implements IUserDAO {
    @Override
    public User getUser(int userId) throws DALException {
        User[] userArr = new User[2];
        userArr[0] = new User(1, "John", "Doe", "JD", "password", true);
        userArr[1] = new User(2, "Jane", "Smith", "JS", "jsLover", false);

        return (userId >= 1 && userId <= userArr.length) ? userArr[userId - 1] : null;
    }

    @Override
    public User getUser(String token) throws DALException {
        throw new NotImplementedException();
    }

    @Override
    public List<User> getUserList() throws DALException {
        throw new NotImplementedException();
    }
}
