package com.sg.superherofinder.dto;

import java.util.Date;
import java.util.Objects;

public class Sight {

    //No annotation checks here since we're dealing with automatic values or objects that are already checking fields.
    private int id, superId;
    private Location location;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sight sight = (Sight) o;
        return id == sight.id && superId == sight.superId && location.equals(sight.location) && date.equals(sight.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, superId, location, date);
    }
}
