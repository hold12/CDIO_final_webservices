package dao;

import config.Config;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private IConnector connector;

    public UserDAO(IConnector connector) {
        this.connector = connector;
    }

    public User getUser(int userId) throws DALException {
        User returnedUser;


        try {
            connector.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

//        ResultSet rs = connector.query("SELECT * FROM web_user WHERE user_id = " + Integer.toString(userId));
        ResultSet rs = connector.query(Queries.getFormatted(
                "user.select.where.id", Integer.toString(userId)
        ));

        try {
            if (!rs.first()) return null;

            returnedUser = new User(
                    rs.getInt("user_id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("initials"),
                    rs.getString("password"),
                    rs.getBoolean("is_active")
                );

            connector.close();

            return returnedUser;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public User getUser(String token) throws DALException {
        final Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
        return getUser(Integer.parseInt(claims.getSubject()));
    }

    @Override
    public List<User> getUserList() throws DALException {
        List<User> list = new ArrayList<User>();

        try {
            connector.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = connector.query(
                Queries.getSQL("user.select.all")
        );

        try {
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("initials"),
                        rs.getString("password"),
                        rs.getBoolean("is_active"))
                );
            }
            connector.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }
        return list;
    }
}