package com.example.jinzhu_habittracker;

import java.util.ArrayList;

/**
 * Created by Aries on 9/30/16.
 */
public class HabitList {

    // attributes
    private ArrayList<Habit> habits = new ArrayList<Habit>();

    // methods
    public HabitList () {}

    public ArrayList<Habit> getHabit () {
        return habits;
    }

    public void add (Habit habit) {
        habits.add(habit);
    }

    public void delete (Habit habit) {
        habits.remove(habit);
    }

    public void update(ArrayList<Habit> habits) {
        this.habits = habits;
    }

}
