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

    public UserDAO() {}

    public User getUser(int userId) throws DALException {
        if (this.db == null)
            throw new DALException("No database specified.");

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
        if (this.db == null)
            throw new DALException("No database specified.");

        try {
            db.connectToDatabase();

            ResultSet rsUser = db.query(Queries.getFormatted(
               "user.select.where.id", Integer.toString(userId)
            ));

            if (!rsUser.first()) return null;
            ResultSet rsRoles = db.query(Queries.getFormatted("user.select.roles", Integer.toString(userId)));

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
    public User getUser(String token) { // TODO: Refactor UserDAO to UserDAODB and make a UserDAOToken and put this in here
        final Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(claims.get("user"), User.class);
        return user;
    }

    @Override
    public List<User> getUserList() throws DALException {
        if (this.db == null)
            throw new DALException("No database specified.");

        List<User> usersList = new ArrayList<User>();

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rsUser = db.query(
                Queries.getSQL("user.select.all")
        );

        try {
            while (rsUser.next()) { // TODO: Use the RoleDAO here instead of the following crap
                ResultSet rsRoles = db.query(Queries.getFormatted("user.select.roles", Integer.toString(rsUser.getInt("user_id"))));
                List<Role> userRoles = new ArrayList<>();
                while(rsRoles.next()) {
                    userRoles.add(new Role(
                            rsRoles.getString("role_name"),
                            rsRoles.getString("permission_names").split(",")
                    ));
                }

                usersList.add(new User(
                        rsUser.getInt("user_id"),
                        rsUser.getString("firstname"),
                        rsUser.getString("lastname"),
                        rsUser.getString("initials"),
                        rsUser.getString("password"),
                        rsUser.getBoolean("is_active"),
                        userRoles
                ));
            }
            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }
        return usersList;
    }

    @Override
    public void updateUser(User user) throws DALException {
        if (this.db == null)
            throw new DALException("No database specified.");

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
        if (this.db == null)
            throw new DALException("No database specified.");

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
