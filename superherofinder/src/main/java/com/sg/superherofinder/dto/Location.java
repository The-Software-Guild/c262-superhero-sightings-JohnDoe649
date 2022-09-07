package com.sg.superherofinder.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {

    private int id;

    @NotNull(message = "The Name field cannot be left blank.")
    @Size(max = 100, message = "Field must be less than 100 characters.")
    private String name;

    @NotNull(message = "This field cannot be left blank.")
    @Size(max = 255, message = "Field must be less than 255 characters.")
    private String address, coordinates;

    @Size(max = 255, message = "Field must be less than 255 characters.")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && name.equals(location.name) && address.equals(location.address) && coordinates.equals(location.coordinates) && Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, coordinates, description);
    }
}
