package dao;

import dto.Role;
import jdbclib.DALException;

import java.util.List;

/**
 * Created by AndersWOlsen on 14-06-2017.
 */
public interface IRoleDAO {
    List<Role> getRoleList() throws DALException;
}
