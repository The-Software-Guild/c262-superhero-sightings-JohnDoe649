package com.sg.superherofinder.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Organization {

    private int id;

    @NotNull(message = "The Name field cannot be left blank.")
    @Size(max = 100, message = "Field must be under 100 characters.")
    private String name;

    @NotNull(message = "The address field cannot be left blank.")
    @Size(max = 255, message = "Field must be under 255 characters.")
    private String address;

    @Size(max = 255, message = "Field must be under 255 characters.")
    private String description;

    private List<Super> supers;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Super> getSupers() {
        return supers;
    }

    public void setSupers(List<Super> supers) {
        this.supers = supers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id == that.id && name.equals(that.name) && address.equals(that.address) && Objects.equals(description, that.description) && supers.equals(that.supers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, description, supers);
    }
}
