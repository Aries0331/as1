package com.example.jinzhu_habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    //public static HabitList AllhabitList = new HabitList();
    private ListView AllHabitsList;
    public ArrayAdapter<Habit> adapter;
    public ArrayList<Habit> habitlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        AllHabitsList = (ListView) findViewById(R.id.review);

        AllHabitsList.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //habitlist.get(position).addCompletions();
                Intent intent = new Intent(ReviewActivity.this, EditActivity.class);

                intent.putExtra("sendAPosition", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ArrayAdapter<Habit>(this, R.layout.list__item, MainActivity.habitList.getHabit());
        AllHabitsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
