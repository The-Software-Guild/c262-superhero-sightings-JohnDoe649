package com.sg.superherofinder.controller;

import com.sg.superherofinder.dao.LocationDao;
import com.sg.superherofinder.dao.SightDao;
import com.sg.superherofinder.dto.Location;
import com.sg.superherofinder.dto.Sight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SightController {

    @Autowired
    SightDao sightDao;

    @Autowired
    LocationDao locationDao;

    @GetMapping("sights")
    public String displaySight(Model model) {
        List<Sight> sights = sightDao.getAllSights();
        List<Location> locations = locationDao.getAll();
        model.addAttribute("sights", sights);
        model.addAttribute("locations", locations);
        return "sights";
    }

    @PostMapping("/sights/addSight")
    public String addSight(Sight sight, HttpServletRequest request) {
        String locId = request.getParameter("locId");

        sight.setLocation(locationDao.getLocationById(Integer.parseInt(locId)));

        sightDao.addSight(sight);

        return "redirect:/sights";
    }

    @GetMapping("sightDetail")
    public String sightDetail(Integer id, Model model) {
        Sight sight = sightDao.getSightById(id);
        model.addAttribute("sight", sight);
        return "sightDetail";
    }

    @GetMapping("/sights/deleteSight")
    public String deleteSight(Integer id) {
        sightDao.deleteSight(id);
        return "redirect:/sights";
    }

    @GetMapping("/sights/editSight")
    public String editSight(Integer id, Model model) {
        Sight sight = sightDao.getSightById(id);
        List<Location> locations = locationDao.getAll();
        model.addAttribute("sight", sight);
        model.addAttribute("locations", locations);
        return "editSight";
    }

    @PostMapping("/sights/editSight")
    public String performEditSight(Sight sight, HttpServletRequest request) {

        String locId = request.getParameter("locId");

        sight.setLocation(locationDao.getLocationById(Integer.parseInt(locId)));

        sightDao.updateSight(sight);

        return "redirect:/sights";
    }

}
