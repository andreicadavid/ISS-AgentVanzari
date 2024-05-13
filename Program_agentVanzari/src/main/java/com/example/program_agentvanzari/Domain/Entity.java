package com.example.program_agentvanzari.Domain;

public abstract class Entity {
    protected int id;
    public Entity(int id) {
        this.id = id;
    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
