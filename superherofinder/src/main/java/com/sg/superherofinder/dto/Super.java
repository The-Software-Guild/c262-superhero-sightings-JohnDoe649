package com.sg.superherofinder.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Super {

    //Here I separate the variables by their types and their annotations (while description is a string, it CAN be left NULL)
    //id doesn't need any checks since it is being handled already in SQL
    private int id;

    @NotBlank(message = "This field cannot be blank.")
    @Size(max = 50, message = "Field must be less than 50 characters.")
    private String name, power;

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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Remember that our description value can be null
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Super aSuper = (Super) o;
        return id == aSuper.id && name.equals(aSuper.name) && power.equals(aSuper.power) && Objects.equals(description, aSuper.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, power, description);
    }
}
