package com.example.jinzhu_habittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class EditActivity extends AppCompatActivity {

    // attributed
    int flag;
    Habit habit;
    TextView HabitName;
    TextView Count;
    Button DeleteAll;
    Button Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // code from http://stackoverflow.com/questions/10674388/nullpointerexception-from-getextras
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        HabitName = (TextView) findViewById(R.id.habitname);
        Count = (TextView) findViewById(R.id.count);
        Delete = (Button) findViewById(R.id.delete);
        DeleteAll = (Button) findViewById(R.id.deleteall);

        // code taken from StackOverflow http://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
        if(bundle != null){
            flag = bundle.getInt("sendAPosition");
            habit = MainActivity.habitList.getHabit().get(flag);
        }

        HabitName.setText(habit.getHabit());
        Count.setText(String.valueOf(habit.getCount()));

        // If the delete Delete Record Button click
        if (DeleteAll != null) {
            DeleteAll.setOnClickListener(new View.OnClickListener(){

                public void  onClick(View view){
                    setResult(RESULT_OK);
                    Toast.makeText(EditActivity.this, "Delete Habit", Toast.LENGTH_SHORT).show();
                    MainActivity.habitList.delete(habit);
                    saveInFile();
                    finish();
                }
            });
        }

        // If delete Habit Button click
        if (Delete != null) {
            Delete.setOnClickListener(new View.OnClickListener(){

                public void  onClick(View view){
                    setResult(RESULT_OK);
                    habit.deleteCompletions();
                    Toast.makeText(EditActivity.this, "Delete Completion Count", Toast.LENGTH_SHORT).show();
                    Count.setText(String.valueOf(habit.getCount()));
                    saveInFile();
                }
            });
        }
    }


    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(MainActivity.FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(MainActivity.habitList.getHabit(), writer);
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
