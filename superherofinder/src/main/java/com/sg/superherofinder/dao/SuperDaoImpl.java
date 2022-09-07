package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Super;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//All dao classes will be annotated with Repository and have interfaces to implement.
@Repository
public class SuperDaoImpl implements SuperDao {

    //must include jdbc for all DAO connected with the autowired annotation
    @Autowired
    JdbcTemplate jdbc;

    //try and catch when finding a specific entry by id so that we can send an error if no entries were found and continue the code.
    @Override
    public Super getSuperById(int id) {
        try {
            final String SELECT_SUPERS_BY_ID = "SELECT * FROM supers WHERE id = ?";
            return jdbc.queryForObject(SELECT_SUPERS_BY_ID, new SuperMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Super> getAllSupers() {
        final String SELECT_ALL_SUPERS = "SELECT * FROM supers";
        return jdbc.query(SELECT_ALL_SUPERS, new SuperMapper());
    }

    //Adds and Updates will check every column in the SQL database.
    //We use Transactional for add and delete, to revert changes in case anything goes wrong with a value.
    @Override
    @Transactional
    public Super addSuper(Super superhero) {
        final String INSERT_SUPER = "INSERT INTO supers(superName, superDescription, superpower) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SUPER,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
        return superhero;
    }

    @Override
    public void updateSuper(Super superhero) {
        final String UPDATE_SUPER = "UPDATE supers SET superName = ?, superDescription = ?, superpower = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPER,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getPower(),
                superhero.getId());
    }

    @Override
    @Transactional
    public void deleteSuper(int id) {

        //Supers are references in 3 tables, so we're checking all entries that list them and will delete them.
        final String DELETE_SIGHTS = "DELETE FROM sights WHERE superId = ? ";
        jdbc.update(DELETE_SIGHTS, id);

        final String DELETE_SUPERORGANIZATION = "DELETE FROM superOrganizations WHERE superId = ? ";
        jdbc.update(DELETE_SUPERORGANIZATION, id);

        final String DELETE_SUPER = "DELETE FROM supers WHERE id = ? ";
        jdbc.update(DELETE_SUPER, id);
    }

    //Our mapper is used to "unmarshall" data from our database to a local object.
    public static final class SuperMapper implements RowMapper<Super>{

        @Override
        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super superhero = new Super();
            superhero.setId(rs.getInt("id"));
            superhero.setName(rs.getString("superName"));
            superhero.setDescription(rs.getString("superDescription"));
            superhero.setPower(rs.getString("superpower"));

            return superhero;
        }

    }
}
