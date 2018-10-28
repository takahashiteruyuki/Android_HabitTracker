package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;
public class EditorActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mDetailEditText;
    private EditText mTimeEditText;
    private Spinner mDaySpinner;
    private int mDay = HabitEntry.DAY_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mDetailEditText = (EditText) findViewById(R.id.edit_habit_detail);
        mTimeEditText = (EditText) findViewById(R.id.edit_habit_time);
        mDaySpinner = (Spinner) findViewById(R.id.spinner_day);
        setupSpinner();
    }
    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_day_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mDaySpinner.setAdapter(genderSpinnerAdapter);
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.day_sun))) {
                        mDay = HabitEntry.DAY_SUN;
                    } else if (selection.equals(getString(R.string.day_mon))) {
                        mDay = HabitEntry.DAY_MON;
                    } else if (selection.equals(getString(R.string.day_tue))) {
                        mDay = HabitEntry.DAY_TUE;
                    } else if (selection.equals(getString(R.string.day_wed))) {
                        mDay = HabitEntry.DAY_WED;
                    } else if (selection.equals(getString(R.string.day_thu))) {
                        mDay = HabitEntry.DAY_THU;
                    } else if (selection.equals(getString(R.string.day_fri))) {
                        mDay = HabitEntry.DAY_FRI;
                    } else if (selection.equals(getString(R.string.day_sat))) {
                        mDay = HabitEntry.DAY_SAT;
                    } else {
                        mDay = HabitEntry.DAY_UNKNOWN;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDay = HabitEntry.DAY_UNKNOWN;
            }
        });
    }
    private void insertHabit() {
        if(mNameEditText.getText().toString().trim().equals("")
                ||mDetailEditText.getText().toString().trim().equals("")
                ||mTimeEditText.getText().toString().trim().equals("")) {
            return;
        }
        String nameString =mNameEditText.getText().toString().trim();
        String detailString =mDetailEditText.getText().toString().trim();
        String timeString =mTimeEditText.getText().toString().trim();

        int time = Integer.parseInt(timeString);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(HabitEntry.COLUMN_HABIT_DETAIL, detailString);
        values.put(HabitEntry.COLUMN_HABIT_DAY, mDay);
        values.put(HabitEntry.COLUMN_HABIT_TIME, time);
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                insertHabit();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
