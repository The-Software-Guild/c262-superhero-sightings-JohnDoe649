package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Organization;
import com.sg.superherofinder.dto.Super;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrganizationDaoImplTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperDao superDao;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
        List<Organization> organizations  = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganization(organization.getId());
        }

        List<Super> supers = superDao.getAllSupers();
        for(Super superhero : supers) {
            superDao.deleteSuper(superhero.getId());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAndAddOrganizationById() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        List<Super> supers = new ArrayList<>();
        supers.add(superhero);

        Organization organization = new Organization();
        organization.setName("test... wait but I named the super test so...");
        organization.setDescription("Lonely");
        organization.setAddress("Depressionvile");
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());

        assertEquals(organization, fromDao);
    }

    @Test
    void getAllOrganizations() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        List<Super> supers = new ArrayList<>();
        supers.add(superhero);

        Organization organization = new Organization();
        organization.setName("test... wait but I named the super test so...");
        organization.setDescription("Lonely");
        organization.setAddress("Depressionvile");
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("test2 oh cool a unique name");
        //organization2.setDescription(""); testing appropriate null for description values here
        organization2.setAddress("All by myself...");
        organization2.setSupers(supers);
        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    @Test
    void updateOrganization() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        List<Super> supers = new ArrayList<>();
        supers.add(superhero);

        Organization organization = new Organization();
        organization.setName("test... wait but I named the super test so...");
        organization.setDescription("Lonely");
        organization.setAddress("Depressionvile");
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        Super supervillain = new Super();
        supervillain.setName("second");
        supervillain.setDescription("Generic villain");
        supervillain.setPower("Beansprouts");
        supervillain = superDao.addSuper(supervillain);
        supers.add(supervillain);
        organization.setSupers(supers);

        organizationDao.updateOrganization(organization);
        assertNotEquals(organization, fromDao);

        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }

    @Test
    void deleteOrganization() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        List<Super> supers = new ArrayList<>();
        supers.add(superhero);

        Organization organization = new Organization();
        organization.setName("test... wait but I named the super test so...");
        organization.setDescription("Lonely");
        organization.setAddress("Depressionvile");
        organization.setSupers(supers);
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organizationDao.deleteOrganization(organization.getId());
        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(fromDao);
    }
}