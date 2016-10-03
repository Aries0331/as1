package com.example.jinzhu_habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    public static HabitList habitList = new HabitList();
    private ListView oldHabitsList;
    private ArrayList<Habit> todayHabit = new ArrayList<>();
    private NewAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // call loadFromFile() to make sure when app open naxt time, former data was still there
        loadFromFile();
        todayHabit.clear();
        oldHabitsList = (ListView) findViewById(R.id.listView);

        // set click listener on listview to check out completion
        oldHabitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                todayHabit.get(position).addCompletions();
                myAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
                saveInFile();
            }
        });

    }

    @Override
    protected void onStart () {
        super.onStart();
        myAdapter = new NewAdapter(this, todayHabit);
        oldHabitsList.setAdapter(myAdapter);
    }

    @Override
    protected  void onResume () {

        super.onResume();
        todayHabit.clear();
        // call method on the new user added habit to check whether it is a today's habit
        todayHabit = convertDays(habitList);
        myAdapter.clear();
        myAdapter.addAll(todayHabit);
        myAdapter.notifyDataSetChanged();

    }

    /** Called when the user clicks the New Habit button */
    public void Add(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    /** Called when the user clicks the Review button */
    public void Review(View view) {
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    // use to convert the integer day stored in dayList to "real" day
    // Code taken from http://stackoverflow.com/questions/5270272/how-to-determine-day-of-week-by-passing-specific-date
    private ArrayList<Habit> convertDays (HabitList habits ) {
        ArrayList<Habit> HabitList1 = habits.getHabit();
        ArrayList<Habit> HabitList2 = new ArrayList<>();

        for (Habit ahabit: HabitList1) {
            Calendar c = Calendar.getInstance();
            c.setTime (new Date());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if(ahabit.getDayList().contains(dayOfWeek)) {
                if (!(HabitList2.contains(ahabit))) {
                    HabitList2.add(ahabit);
                }
            }
        }
        return HabitList2;
    }


    private void loadFromFile() {
        ArrayList<Habit> habits = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Habit>>() {}.getType();
            habits = gson.fromJson(in, listType);
            if(habits == null) {
                habits = new ArrayList<>();
            }
            habitList.update(habits);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException();
        }

    }

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList.getHabit(), writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
