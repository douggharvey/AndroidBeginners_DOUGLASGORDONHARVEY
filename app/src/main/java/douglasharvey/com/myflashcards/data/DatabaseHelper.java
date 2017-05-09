package douglasharvey.com.myflashcards.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import douglasharvey.com.myflashcards.data.CardContract.CardEntry;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="flashcards.db";
    private static final int SCHEMA=1;

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + CardEntry.TABLE_NAME +
                              "(" + CardEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    CardEntry.COLUMN_QUESTION + " TEXT, " +
                                    CardEntry.COLUMN_ANSWER + " TEXT);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME);
        onCreate(db);
    }
}
