package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by belstrel on 16.03.18.
 */
public interface MealRepository {
    Meal  save(Meal meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();
}
