package com.example.vipul.sports_arena;

/**
 * Created by vipul on 2/12/17.
 */

public class Arena {

    private int id;
    private String name;
    private String location;
    private String timing;

    public Arena(int id, String name, String location, String timing) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.timing = timing;
    }


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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
