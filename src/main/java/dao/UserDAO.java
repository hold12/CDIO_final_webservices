package dao;

import jdbclib.*;
import dto.UserDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private IConnector connector;

    public UserDAO(IConnector connector) {
        this.connector = connector;
    }

    public UserDTO getUser(int userId) throws DALException {
        UserDTO returnedUser;
//        ResultSet rs = connector.query(Queries.getFormatted(
//                "user.select.where.id", Integer.toString(userId)
//        ));

        try {
            connector.connectToDatabase();
        } catch(ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        ResultSet rs = connector.query("SELECT * FROM web_user WHERE user_id = " + Integer.toString(userId));

        try {
            if (!rs.first()) return null;

            returnedUser = new UserDTO(
                    rs.getInt("user_id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("initials"),
                    rs.getString("password"),
                    rs.getBoolean("is_active")
                );

            try {
                connector.close();
            } catch (DALException e) {
                System.err.println(e.getMessage());
            }

            return returnedUser;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

//    public List<UserDTO> getUserList() throws DALException {
//        List<UserDTO> list = new ArrayList<UserDTO>();
//        ResultSet rs = connector.query(
//                Queries.getSQL("user.select.all")
//        );
//
//        try {
//            while (rs.next()) {
//                list.add(new UserDTO(
//                        rs.getInt("user_id"),
//                        rs.getString("user_firstname"),
//                        rs.getString("user_lastname"),
//                        rs.getString("initials"),
//                        rs.getString("password"),
//                        rs.getBoolean("is_active"))
//                );
//            }
//        } catch (SQLException e) {
//            throw new DALException(e);
//        }
//        return list;
//    }
}