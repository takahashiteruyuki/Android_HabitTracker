package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class CatalogActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDbHelper(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
    public Cursor queryAllHabits() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DETAIL,
                HabitEntry.COLUMN_HABIT_DAY,
                HabitEntry.COLUMN_HABIT_TIME};
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
    private void displayDatabaseInfo() {
        Cursor cursor =queryAllHabits();
        TextView displayView = (TextView) findViewById(R.id.text_view_habit);
        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_DETAIL + " - " +
                    HabitEntry.COLUMN_HABIT_DAY+ " - " +
                    HabitEntry.COLUMN_HABIT_TIME + "\n");
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int detailColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DETAIL);
            int dayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DAY);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDetail = cursor.getString(detailColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDetail + " - " +
                        getDay(currentDay) + " - " +
                        currentTime+"min"));
            }
        } finally {
            cursor.close();
        }
    }
    private String getDay(int dayid){
        String dayString;
        if(dayid==0){
            dayString="UnKown";
        }else if(dayid==1) {
            dayString = "Sun";
        }else if(dayid==2){
            dayString="Mon";
        }else if(dayid==3){
            dayString="Tue";
        }else if(dayid==4){
            dayString="Wed";
        }else if(dayid==5){
            dayString="Thu";
        }else if(dayid==6){
            dayString="Fri";
        }else {
            dayString="Sat";
        }
        return dayString;
    }
    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Jogging");
        values.put(HabitEntry.COLUMN_HABIT_DETAIL, "Run around");
        values.put(HabitEntry.COLUMN_HABIT_DAY, HabitEntry.DAY_SUN);
        values.put(HabitEntry.COLUMN_HABIT_TIME, 10);
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
