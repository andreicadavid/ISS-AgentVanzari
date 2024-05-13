package com.example.program_agentvanzari.Repository;

import com.example.program_agentvanzari.Domain.Entity;

import java.util.List;

public interface IDatabaseRepository {
    void createTablesIfNotExists();
    void add(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
    List getData();
    Entity getById(int id);
}
