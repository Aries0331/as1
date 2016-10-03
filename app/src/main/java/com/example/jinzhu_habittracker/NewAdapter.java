package com.example.jinzhu_habittracker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Aries on 9/30/16.
 */

// A user defined adapter used to count number of completions
// Code from https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
public class NewAdapter extends ArrayAdapter<Habit> {

    public NewAdapter(Context context, ArrayList<Habit> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        boolean doneToday = Boolean.FALSE;
        int todayCompletionCount = 0;

        // Get the data item for this position
        Habit habit = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView myHabit = (TextView) convertView.findViewById(R.id.habit);
        TextView myHabitCount = (TextView) convertView.findViewById(R.id.habitCount);
        // Populate the data into the template view using the data object
        myHabit.setText(habit.toString());

        // code taken from http://stackoverflow.com/questions/2517709/comparing-two-java-util-dates-to-see-if-they-are-in-the-same-day
        Date todayDate = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(todayDate);
        Calendar cal2 = Calendar.getInstance();
        for(Completion aCompletion: habit.getCompletions()){
            cal2.setTime(aCompletion.getDate());
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)){
                doneToday = Boolean.TRUE;
                todayCompletionCount++;
            }
        }

        if (doneToday) {
            myHabit.setBackgroundResource(R.color.orange);
        }
        else {
            myHabit.setBackgroundResource(R.color.silver);
        }

        myHabitCount.setText(String.valueOf(todayCompletionCount));

        // Return the completed view to render on screen
        return convertView;
    }
}

