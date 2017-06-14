package dao;

import dto.Role;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndersWOlsen on 14-06-2017.
 */
public class RoleDAO implements IRoleDAO {
    private IConnector db;

    @Override
    public List<Role> getRoleList() throws DALException {
        List<Role> rolesList = new ArrayList<Role>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rsRoles = db.query(
                "SELECT * FROM role;" //TODO: use queries instead
        );

        try {
            while (rsRoles.next())
                rolesList.add(new Role(
                        rsRoles.getString("role_name"),
                        rsRoles.getString("permission_names").split(",")
                ));

            db.close();
        } catch (SQLException e) {
            throw new DALException(e);
        }

        return rolesList;
    }
}
