package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Location;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LocationDaoImplTest {

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
        List<Location> locations = locationDao.getAll();
        for(Location location : locations) {
            locationDao.deleteLocation(location.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void getAndAddLocationById() {

        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());

        assertEquals(location, fromDao);
    }

    @Test
    void getAll() {
        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Another test");
        location2.setDescription("Delaware");
        location2.setAddress("11111 Another Place Maybe?");
        location2.setCoordinates("12:14?");
        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAll();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @Test
    void updateLocation() {
        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("The Actual Ohio Name");
        locationDao.updateLocation(location);
        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    @Test
    void deleteLocation() {
        Location location = new Location();
        location.setName("test");
        location.setDescription("Ohio");
        location.setAddress("11111 A Place");
        location.setCoordinates("bad cords");
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        locationDao.deleteLocation(location.getId());
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }
}