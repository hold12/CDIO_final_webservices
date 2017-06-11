package dao;

import config.Config;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private IConnector db;

    public UserDAO(IConnector db) {
        this.db = db;
    }

    public User getUser(int userId) throws DALException {
        User returnedUser;


        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

//        ResultSet rs = db.query("SELECT * FROM web_user WHERE user_id = " + Integer.toString(userId));
        ResultSet rs = db.query(Queries.getFormatted(
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

            db.close();

            return returnedUser;
        } catch (SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public User getFullUser(int userId) throws DALException {
        try {
            db.connectToDatabase();

            ResultSet rs = db.query(Queries.getFormatted(
               "user.select.full.where.id", Integer.toString(userId)
            ));

            if (!rs.first()) return null;

            User dbUser = new User(

            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        throw new NotImplementedException();
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
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(
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
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }
        return list;
    }

    @Override
    public void updateUser(User user) throws DALException {

    }
}