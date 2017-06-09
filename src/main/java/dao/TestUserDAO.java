package dao;

import dto.UserDTO;
import jdbclib.DALException;

/**
 * Created by awo on 09/06/17.
 */
public class TestUserDAO implements IUserDAO {
    @Override
    public UserDTO getUser(int userId) throws DALException {
        UserDTO activeUser   = new UserDTO(1, "John", "Doe", "JD", "password", true);
        UserDTO inactiveUser = new UserDTO(2, "Jane", "Smith", "JS", "jsLover", false);

        return (userId == 1) ? activeUser : inactiveUser;
    }
}
