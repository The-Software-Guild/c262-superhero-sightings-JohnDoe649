package com.sg.superherofinder.controller;

import com.sg.superherofinder.dao.LocationDao;
import com.sg.superherofinder.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAll();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("locations/addLocation")
    public String addLocation(String locName, String locDescription, String locAddress, String coordinates) {
        Location location = new Location();
        location.setName(locName);
        location.setDescription(locDescription);
        location.setAddress(locAddress);
        location.setCoordinates(coordinates);
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("locations/deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("locations/editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("locations/editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if(result.hasErrors()) {
            return "editLocation";
        }
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

}
