package com.example.jinzhu_habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    private EditText bodyText;
    public static HabitList habitList = new HabitList();
    private ArrayList<Integer> dayList = new ArrayList<Integer>();

    private CheckBox mon;
    private CheckBox tue;
    private CheckBox wed;
    private CheckBox thu;
    private CheckBox fri;
    private CheckBox sat;
    private CheckBox sun;

    /**
     * Called when the activity is first created
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bodyText = (EditText) findViewById(R.id.body);
        Button saveButton = (Button) findViewById(R.id.save);

        // CheckBox
        mon = (CheckBox) findViewById(R.id.Mon);
        tue = (CheckBox) findViewById(R.id.Tue);
        wed = (CheckBox) findViewById(R.id.Wed);
        thu = (CheckBox) findViewById(R.id.Thu);
        fri = (CheckBox) findViewById(R.id.Fri);
        sat = (CheckBox) findViewById(R.id.Sat);
        sun = (CheckBox) findViewById(R.id.Sun);


        /** Called when the user clicks the Send button */
        if (saveButton != null) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setResult(RESULT_OK);
                    dayList.clear();

                    // Check if the checkbox is clicked
                    if(mon.isChecked())
                        dayList.add(2);
                    if(tue.isChecked())
                        dayList.add(3);
                    if(wed.isChecked())
                        dayList.add(4);
                    if(thu.isChecked())
                        dayList.add(5);
                    if(fri.isChecked())
                        dayList.add(6);
                    if(sat.isChecked())
                        dayList.add(7);
                    if(sun.isChecked())
                        dayList.add(1);

                    // text field
                    String text = bodyText.getText().toString();
                    // Add new Habit to Habit List with sepecific day
                    Habit MyNewHabit = new Habit(text, dayList);
                    MainActivity.habitList.add(MyNewHabit);
                    saveInFile();
                    finish();
                }
            });
        }


    }

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME, 0);
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
