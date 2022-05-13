package com.project.ezoo.services;

import com.project.ezoo.model.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> findAll();

    Animal findAnimalById(long id);

    void delete(long id);

    Animal save(Animal animal);

    Animal update(Animal animal, long id);

    void deleteAll();
}
