package dao;

import dto.Role;
import jdbclib.DALException;
import jdbclib.IConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements IRoleDAO {
    private final IConnector db;

    public RoleDAO(IConnector db) {
        this.db = db;
    }

    @Override
    public List<Role> getRoleList() throws DALException {
        List<Role> rolesList = new ArrayList<>();

        try {
            db.connectToDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            throw new DALException(e);
        }

        ResultSet rsRoles = db.query(
                Queries.getSQL("role.get.all")
        );

        try {
            String role;
            while (rsRoles.next()) {
                role = rsRoles.getString("role_name");
                List<String> permissions = new ArrayList<>();

                ResultSet rsPermissions = db.query(
                        Queries.getFormatted("role.get.where.role", role));

                while (rsPermissions.next()) {
                    String perm = rsPermissions.getString("permission_name");
//                    System.out.println(role + " : " + perm);
                    permissions.add(perm);
                }

                rolesList.add(new Role(
                        rsRoles.getString("role_name"),
                        permissions
                ));
            }
        } catch (SQLException e) {
            throw new DALException(e);
        } finally {
            db.close();
        }

        return rolesList;
    }

    @Override
    public List<String> getRoleNames() throws DALException {
        List<Role> roles = getRoleList();
        List<String> roleNames = new ArrayList<>();

        for (Role r : roles) {
            roleNames.add(r.getRole_name());
        }

        return  roleNames;
    }
}
