package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import java.util.List;

public interface LocationDao {

    Location getLocationById(int id);

    List<Location> getAll();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(int id);

}
