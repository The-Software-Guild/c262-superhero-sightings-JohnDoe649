package com.sg.superherofinder.controller;

import com.sg.superherofinder.dao.OrganizationDao;
import com.sg.superherofinder.dao.SuperDaoImpl;
import com.sg.superherofinder.dto.Organization;
import com.sg.superherofinder.dto.Super;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SuperDaoImpl superDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("organizations", organizations);
        model.addAttribute("supers", supers);
        return "organizations";
    }

    @PostMapping("/organizations/addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Super> supers = new ArrayList<>();
        for(String superId : superIds) {
            supers.add(superDao.getSuperById(Integer.parseInt(superId)));
        }
        organization.setSupers(supers);
        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("/organizations/organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizations/organizationDetail";
    }

    @GetMapping("/organizations/deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.getOrganizationById(id);
        return "redirect:/organizations";
    }

    @GetMapping("/organizations/editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Super> supers = superDao.getAllSupers();
        model.addAttribute("organization", organization);
        model.addAttribute("supers", supers);
        return "editOrganization";
    }

    @PostMapping("/organizations/editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request) {
        String[] superIds = request.getParameterValues("superId");

        List<Super> supers = new ArrayList<>();
        for(String superId : superIds) {
            supers.add(superDao.getSuperById(Integer.parseInt(superId)));
        }
        organization.setSupers(supers);
        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }

}
