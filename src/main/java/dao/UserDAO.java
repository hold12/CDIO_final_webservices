package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import dto.Role;
import dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "user.select.where.id", Integer.toString(userId)
        ));

        try {
            if (!rs.first()) return null;
            else return new User(
                    rs.getInt("user_id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("initials"),
                    rs.getString("password"),
                    rs.getBoolean("is_active")
                );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public User getFullUser(int userId) throws DALException {
        if (this.db == null)
            throw new DALException("No database specified.");

        try {
            db.connectToDatabase();
        } catch(ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        try {
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
            return new User(
                    rsUser.getInt("user_id"),
                    rsUser.getString("firstname"),
                    rsUser.getString("lastname"),
                    rsUser.getString("initials"),
                    rsUser.getString("password"),
                    rsUser.getBoolean("is_active"),
                    userRoles
            );
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public User getUser(String token) { // TODO: Refactor UserDAO to UserDAODB and make a UserDAOToken and put this in here
        final Claims claims = Jwts.parser().setSigningKey(Config.AUTH_KEY).parseClaimsJws(token).getBody();
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(claims.get("user"), User.class);
    }

    @Override
    public List<User> getUserList() throws DALException {
        if (this.db == null)
            throw new DALException("No database specified.");

        List<User> usersList = new ArrayList<>();

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
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return usersList;
    }

    @Override
    public int createUser(User user) throws DALException, DataValidationException {
        if (this.db == null)
            throw new DALException("No database specified.");

        userValidation(user);

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rs = db.query(Queries.getFormatted(
                "user.insert",
                Integer.toString(user.getUserId()),
                user.getFirstname(),
                user.getLastname(),
                user.getInitials(),
                generatePassword(user)
        ));

        try {
            if (!rs.first()) return -1;
            else return rs.getInt(0);
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }
    }

    @Override
    public void updateUser(User user) throws DALException, DataValidationException {
        if (this.db == null)
            throw new DALException("No database specified.");

        userValidation(user);

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
    public String generatePassword(User user) throws DALException, DataValidationException {
        if (this.db == null)
            throw new DALException("No database specified.");

        userValidation(user);
        String password = user.generatePassword();
        user.setPassword(password);

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

        return password;
    }

    private void userValidation(User user) throws DataValidationException{
        int userNameLength = user.getFirstname().length() + user.getLastname().length();
        if (userNameLength < 2 || userNameLength > 20)
            throw new DataValidationException("User first name + last name should be between 2 and 20 characters");

        int initialsLength = user.getInitials().length();
        if (initialsLength < 2 || initialsLength > 4)
            throw new DataValidationException("User initials should be between 2 and 4 characters");

        int passwordLength = user.getPassword().length();
        if (passwordLength < 6)
            throw new DataValidationException("User password should be at least 6 characters long");
    }
}
