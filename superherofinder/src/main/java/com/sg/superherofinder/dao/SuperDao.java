package com.sg.superherofinder.dao;

import com.sg.superherofinder.dto.Super;

import java.util.List;

public interface SuperDao {

    Super getSuperById(int id);

    List<Super> getAllSupers();

    Super addSuper(Super superhero);

    void updateSuper(Super superhero);

    void deleteSuper(int id);

}
