package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import dto.Role;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

            ResultSet rsUser = db.query(Queries.getFormatted(
               "user.select.where.id", Integer.toString(userId)
            ));

            if (!rsUser.first()) return null;
            System.out.println(Queries.getFormatted("user.select.roles", "3"));
            ResultSet rsRoles = db.query(Queries.getFormatted("user.select.roles", "3"));

            List<Role> userRoles = new ArrayList<>();
            while(rsRoles.next()) {
                userRoles.add(new Role(
                        rsRoles.getString("role_name"),
                        rsRoles.getString("permission_names").split(",")
                ));
            }

            User dbUser = new User(
                    rsUser.getInt("user_id"),
                    rsUser.getString("firstname"),
                    rsUser.getString("lastname"),
                    rsUser.getString("initials"),
                    rsUser.getString("password"),
                    rsUser.getBoolean("is_active"),
                    userRoles
            );

            return dbUser;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }
    }

    @Override
    public User getUser(String token) throws DALException {
        final Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(claims.get("user"), User.class);
        return user;
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
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        db.update(Queries.getFormatted(
           "user.update",
                Integer.toString(user.getUserId()),
                user.getFirstname(),
                user.getLastname(),
                user.getInitials(),
                user.getPassword(),
                Boolean.toString(user.isActive())
        ));

        db.close();
    }

    @Override
    public int createUser(User user) throws DALException {
        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        final ResultSet rsUserId = db.query(Queries.getFormatted(
                "user.insert",
                Integer.toString(user.getUserId()),
                user.getFirstname(),
                user.getLastname(),
                user.getInitials(),
                user.getPassword()
        ));

        int userId = 0;

        try {
            rsUserId.first();
            userId = rsUserId.getInt(0);
        } catch (SQLException e) {
            System.out.println("Failed to retrieve created user id.");
        }
        return userId;
    }
}
