package com.MIAG.miaggce.models;

public class Subject_test {
    private int id;
    private String name;
    private int time = 3600; //in minute

    public Subject_test() {
    }

    public Subject_test(int id, String name) {
        this.id = id;
        this.name = name;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
