package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Organization;
import com.sg.superherofinder.dto.Super;
import java.util.List;

public interface OrganizationDao {

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(int id);

    List<Organization> getOrganizationsForSuper(Super superhero);

}
