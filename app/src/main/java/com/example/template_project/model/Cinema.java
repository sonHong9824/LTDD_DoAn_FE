package com.example.template_project.model;

import java.util.Objects;

public class Cinema {
    private String id;
    private String name;
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(id, cinema.id); // So sánh theo ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash theo ID để đảm bảo cùng ID thì hashCode giống nhau
    }
}
