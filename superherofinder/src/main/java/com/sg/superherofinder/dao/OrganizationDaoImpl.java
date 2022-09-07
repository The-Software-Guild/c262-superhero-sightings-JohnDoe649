package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Organization;
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

@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATIONS_BY_ID = "SELECT * FROM organizations WHERE id = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATIONS_BY_ID, new OrganizationDaoImpl.OrganizationMapper(), id);
            organization.setSupers(getSuperForOrganization(id));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //When we have different objects or list of objects, we have to be a little more complex and call for their rowmappers
    private List<Super> getSuperForOrganization(int id) {
        final String SELECT_SUPER_FOR_ORGANIZATION = "SELECT s.* FROM supers s "
                + "JOIN superOrganizations so ON s.id = so.superId WHERE so.id = ?";
        return jdbc.query(SELECT_SUPER_FOR_ORGANIZATION, new SuperDaoImpl.SuperMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateSupers(organizations);
        return organizations;
    }

    //Same for getAll, we will provide associate methods to help with different objects.
    private void associateSupers(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setSupers(getSuperForOrganization(organization.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organizations(orgName, orgDescription, orgAddress) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertCourseStudent(organization);
        return organization;
    }

    //Organizations can have multiple supers aka an object list, hence an associate method so we can form our list.
    private void insertCourseStudent(Organization organization) {
        final String INSERT_ORGANIZATION_SUPER = "INSERT INTO "
                + "superOrganizations(orgId, superId) VALUES(?,?)";
        for(Super superhero : organization.getSupers()) {
            jdbc.update(INSERT_ORGANIZATION_SUPER,
                    organization.getId(),
                    superhero.getId());
        }
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organizations SET orgName = ?, orgDescription = ?, "
                + "orgAddress = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getId());

        final String DELETE_SUPERORGANIZATION = "DELETE FROM superOrganizations WHERE orgId = ?";
        jdbc.update(DELETE_SUPERORGANIZATION, organization.getId());
        insertCourseStudent(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(int id) {
        final String DELETE_SUPERORGANIZATION = "DELETE FROM superOrganizations WHERE orgId = ? ";
        jdbc.update(DELETE_SUPERORGANIZATION, id);

        final String DELETE_ORGANIZATION = "DELETE FROM organizations WHERE id = ? ";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getOrganizationsForSuper(Super superhero) {
        final String SELECT_ORGANIZATIONS_FROM_SUPERS = "SELECT o.* FROM organizations o JOIN "
                + "superOrganizations so ON so.orgId = o.id WHERE so.superId = ?";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FROM_SUPERS,
                new OrganizationMapper(), superhero.getId());
        associateSupers(organizations);
        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("orgName"));
            organization.setAddress(rs.getString("orgDescription"));
            organization.setDescription(rs.getString("orgAddress"));

            return organization;
        }

    }
}
