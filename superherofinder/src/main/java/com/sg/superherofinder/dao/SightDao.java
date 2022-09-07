package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import com.sg.superherofinder.dto.Sight;
import com.sg.superherofinder.dto.Super;

import java.util.List;

public interface SightDao {

    Sight getSightById(int id);

    List<Sight> getAllSights();

    Sight addSight(Sight sight);

    void updateSight(Sight sight);

    void deleteSight(int id);

    List<Sight> getSightingsForLocations(Location location);

}
