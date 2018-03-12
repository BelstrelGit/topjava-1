package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        // System.out.println( getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));


        List<UserMealWithExceed> exceedList = getFilteredWithCycle(mealList, LocalTime.of(7, 0), LocalTime.of(22, 0), 2000);
        exceedList.forEach(System.out::println);//m -> System.out.println(m)
    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> list = new ArrayList<>();
        Map<LocalDate, Integer> mapUserMealPerDay = init(mealList);
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        mapUserMealPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return list;
    }

    public static Map<LocalDate, Integer> init(List<UserMeal> mealList) {
        Map<LocalDate, Integer> mapMealPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            mapMealPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (value, newValue) -> (value + newValue));
        }
        return mapMealPerDay;
    }

    public static List<UserMealWithExceed> getFilteredWithStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));//um->um.getCalories()
        return mealList.stream().filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)).
                map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                        sumCaloriesByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay)).
                collect(Collectors.toList());

    }


    public static List<UserMealWithExceed> getFilteredWithCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesByDate = new HashMap<>();
        for (UserMeal m : mealList) {
            LocalDate date = m.getDateTime().toLocalDate();
            sumCaloriesByDate.put(date, sumCaloriesByDate.getOrDefault(date, 0) + m.getCalories());

        }
        List<UserMealWithExceed> exList = new ArrayList<>();
        for (UserMeal m : mealList) {
            if (TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime)) {
                exList.add(new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(),
                        sumCaloriesByDate.get(m.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }return exList;
    }
}

