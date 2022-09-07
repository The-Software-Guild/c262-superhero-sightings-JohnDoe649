package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import com.sg.superherofinder.dto.Sight;
import com.sg.superherofinder.dto.Super;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SightDaoImplTest {

    @Autowired
    SightDao sightDao;

    @Autowired
    SuperDao superDao;

    @Autowired
    LocationDao locationDao;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    void setUp() {
        List<Sight> sights = sightDao.getAllSights();
        for(Sight sight : sights) {
            sightDao.deleteSight(sight.getId());
        }

        List<Super> supers = superDao.getAllSupers();
        for(Super superhero : supers) {
            superDao.deleteSuper(superhero.getId());
        }

        List<Location> locations = locationDao.getAll();
        for(Location location : locations) {
            locationDao.deleteLocation(location.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void getAndAddSightById() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Date date = Date.valueOf("2022-09-01");

        Sight sight = new Sight();
        sight.setSuperId(superhero.getId());
        sight.setLocation(location);
        sight.setDate(date);
        sight = sightDao.addSight(sight);

        Sight fromDao = sightDao.getSightById(sight.getId());

        assertEquals(sight, fromDao);
    }

    @Test
    void getAllSights() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Date date = Date.valueOf("2022-09-01");

        Sight sight = new Sight();
        sight.setSuperId(superhero.getId());
        sight.setLocation(location);
        sight.setDate(date);
        sight = sightDao.addSight(sight);

        Sight sight2 = sight;
        date = Date.valueOf("2022-09-21");
        sight2.setDate(date);
        sight2 = sightDao.addSight(sight2);

        List<Sight> sights = sightDao.getAllSights();

        assertEquals(2, sights.size());
        assertTrue(sights.contains(sight));
        assertTrue(sights.contains(sight2));
    }

    @Test
    void updateSight() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Date date = Date.valueOf("2022-09-01");

        Sight sight = new Sight();
        sight.setSuperId(superhero.getId());
        sight.setLocation(location);
        sight.setDate(date);
        sight = sightDao.addSight(sight);

        Sight fromDao = sightDao.getSightById(sight.getId());
        assertEquals(sight, fromDao);

        date = Date.valueOf("2022-09-21");
        sight.setDate(date);
        sightDao.updateSight(sight);
        assertNotEquals(sight, fromDao);

        fromDao = sightDao.getSightById(sight.getId());
        assertEquals(sight, fromDao);
    }

    @Test
    void deleteSight() {
        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Date date = Date.valueOf("2022-09-01");

        Sight sight = new Sight();
        sight.setSuperId(superhero.getId());
        sight.setLocation(location);
        sight.setDate(date);
        sight = sightDao.addSight(sight);

        Sight fromDao = sightDao.getSightById(sight.getId());
        assertEquals(sight, fromDao);

        sightDao.deleteSight(sight.getId());
        fromDao = sightDao.getSightById(sight.getId());
        assertNull(fromDao);
    }
}