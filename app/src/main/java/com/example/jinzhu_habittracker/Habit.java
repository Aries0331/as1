package com.example.jinzhu_habittracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aries on 9/30/16.
 */
public class Habit {

    // attributes
    private String habit;
    private Date date;
    private ArrayList<Integer> day;
    private ArrayList<Completion> complete = new ArrayList<>();

    public Habit (String habit, ArrayList<Integer> days) {
        this.habit = habit;
        this.date = new Date();
        this.day = days;
    }

    public String getHabit () {
        return habit;
    }

    public Date getDate () {
        return date;
    }

    public ArrayList<Integer> getDayList(){
        return day;
    }

    public Integer getCount() {
        return complete.size();
    }

    public void addCompletions() {
        Completion completion = new Completion();
        complete.add(completion);
    }

    public void deleteCompletions() {
        complete.clear();
    }

    public ArrayList<Completion> getCompletion_records() {
        return this.complete;
    }

    @Override
    public String toString(){
        return this.habit;
    }

}
