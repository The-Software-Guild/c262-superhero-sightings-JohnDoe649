package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import com.sg.superherofinder.dto.Sight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightDaoImpl implements SightDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sight getSightById(int id) {
        try {
            final String SELECT_SIGHT_BY_ID = "SELECT * FROM sights WHERE id = ?";
            Sight sight = jdbc.queryForObject(SELECT_SIGHT_BY_ID, new SightMapper(), id);
            sight.setLocation(getLocationForSight(id));
            return sight;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSight(int id) {
        final String SELECT_LOCATION_FOR_SIGHT = "SELECT l.* FROM locations l "
                + "JOIN sights s ON s.locId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHT, new LocationDaoImpl.LocMapper(), id);
    }

    @Override
    public List<Sight> getAllSights() {
        final String SELECT_ALL_SIGHTS = "SELECT * FROM sights";
        List<Sight> sights = jdbc.query(SELECT_ALL_SIGHTS, new SightMapper());
        associateLocationsForSights(sights);
        return sights;
    }

    void associateLocationsForSights(List<Sight> sights){
        for (Sight sight : sights) {
            sight.setLocation(getLocationForSight(sight.getId()));
        }
    }

    @Override
    @Transactional
    public Sight addSight(Sight sight) {
        final String INSERT_SIGHT = "INSERT INTO sights(locId, superId, datetime) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHT,
                sight.getLocation().getId(),
                sight.getSuperId(),
                sight.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sight.setId(newId);
        return sight;
    }

    @Override
    public void updateSight(Sight sight) {
        final String UPDATE_SIGHT = "UPDATE sights SET locId = ?, superId = ?, "
                + "date = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHT,
                sight.getLocation().getId(),
                sight.getSuperId(),
                sight.getDate(),
                sight.getId());
    }

    @Override
    @Transactional
    public void deleteSight(int id) {
        final String DELETE_SIGHT = "DELETE FROM sights WHERE id = ? ";
        jdbc.update(DELETE_SIGHT, id);
    }

    @Override
    public List<Sight> getSightingsForLocations(Location location) {
        final String SELECT_SIGHTS_FOR_LOCATION = "SELECT * FROM sights WHERE locId = ?";
        List<Sight> sights = jdbc.query(SELECT_SIGHTS_FOR_LOCATION,
                new SightMapper(), location.getId());
        associateLocationsForSights(sights);
        return sights;
    }

    public static final class SightMapper implements RowMapper<Sight> {

        @Override
        public Sight mapRow(ResultSet rs, int index) throws SQLException {
            Sight sight = new Sight();
            sight.setId(rs.getInt("id"));
            sight.setSuperId(rs.getInt("superId"));
            sight.setDate(Date.valueOf("superDescription"));

            return sight;
        }

    }
}
