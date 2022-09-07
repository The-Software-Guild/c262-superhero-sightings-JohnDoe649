package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Super;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuperDaoImplTest {

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
        List<Super> supers = superDao.getAllSupers();
        for(Super superhero : supers) {
            superDao.deleteSuper(superhero.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void getAndAddSuperById() {

        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Super fromDao = superDao.getSuperById(superhero.getId());

        assertEquals(superhero, fromDao);

    }

    @Test
    void getAllSupers() {

        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Super supervillain = new Super();
        supervillain.setName("second");
        supervillain.setDescription("Generic villain");
        supervillain.setPower("Beansprouts");
        supervillain = superDao.addSuper(supervillain);

        List<Super> supers = superDao.getAllSupers();

        assertEquals(2, supers.size());
        assertTrue(supers.contains(superhero));
        assertTrue(supers.contains(supervillain));

    }

    @Test
    void updateSuper() {

        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Super fromDao = superDao.getSuperById(superhero.getId());
        assertEquals(superhero, fromDao);

        superhero.setName("Realname Johnson Smith");
        superDao.updateSuper(superhero);
        assertNotEquals(superhero, fromDao);

        fromDao = superDao.getSuperById(superhero.getId());
        assertEquals(superhero, fromDao);
    }

    @Test
    void deleteSuper() {

        Super superhero = new Super();
        superhero.setName("test");
        superhero.setDescription("Generic hero");
        superhero.setPower("Cosmic Fart");
        superhero = superDao.addSuper(superhero);

        Super fromDao = superDao.getSuperById(superhero.getId());
        assertEquals(superhero, fromDao);

        superDao.deleteSuper(superhero.getId());
        fromDao = superDao.getSuperById(superhero.getId());
        assertNull(fromDao);
    }
}