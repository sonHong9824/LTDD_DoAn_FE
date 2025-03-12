package com.example.template_project.model;

import java.io.Serializable;

public class Genre implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
