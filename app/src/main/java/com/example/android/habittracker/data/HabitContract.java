package com.example.android.habittracker.data;
import android.provider.BaseColumns;

/**
 * Created by takahashiteruyuki on 2017/08/27.
 */

public class HabitContract {
    private HabitContract() {}
    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habits1";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME ="name";
        public final static String COLUMN_HABIT_DETAIL = "detail";
        public final static String COLUMN_HABIT_DAY = "day";
        public final static String COLUMN_HABIT_TIME = "time";
        public static final int DAY_UNKNOWN = 0;
        public static final int DAY_SUN = 1;
        public static final int DAY_MON = 2;
        public static final int DAY_TUE = 3;
        public static final int DAY_WED = 4;
        public static final int DAY_THU = 5;
        public static final int DAY_FRI = 6;
        public static final int DAY_SAT = 7;
    }
}
