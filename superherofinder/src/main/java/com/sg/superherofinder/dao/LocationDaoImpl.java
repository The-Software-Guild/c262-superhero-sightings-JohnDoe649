package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoImpl implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM locations WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationDaoImpl.LocMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAll() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM locations";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationDaoImpl.LocMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO locations(locName, locDescription, locAddress, coordinates) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCoordinates());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE locations SET locName = ?, locDescription = ?, locAddress = ?, coordinates = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCoordinates(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocation(int id) {
        final String DELETE_SIGHTS = "DELETE FROM sights WHERE locId = ? ";
        jdbc.update(DELETE_SIGHTS, id);

        final String DELETE_LOCATION = "DELETE FROM locations WHERE id = ? ";
        jdbc.update(DELETE_LOCATION, id);
    }

    public static final class LocMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("locName"));
            location.setDescription(rs.getString("locDescription"));
            location.setAddress(rs.getString("locAddress"));
            location.setCoordinates(rs.getString("coordinates"));

            return location;
        }

    }
}
