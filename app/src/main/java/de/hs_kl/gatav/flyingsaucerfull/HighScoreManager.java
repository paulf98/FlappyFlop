package de.hs_kl.gatav.flyingsaucerfull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoreManager extends SQLiteOpenHelper {

    public static final int DATENBANK_VERSION = 1;
    public static final String DATENBANK_NAME = "Highscores.db";

    //Struktur der Datenbank
    public static final String TABELLE_SCORE = "scores";
    public static final String SPALTE_SCORE_ID = "score_id";
    public static final String SPALTE_SCORE = "score";


    //Konstruktor für Datenbank
    public HighScoreManager (Context ctx){
        super(ctx, DATENBANK_NAME, null, DATENBANK_VERSION);
    }


    //Create Methode, um Datenbank Tabell zu erstellen
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                "CREATE TABLE " + TABELLE_SCORE + "(" +
                        SPALTE_SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        SPALTE_SCORE + " INTEGER" + ")"
        );
    }

    // Wird aufgerufen, wenn Datenbank Version erhöht wird
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABELLE_SCORE);
        onCreate(db);
    }


    //Füge neuen Highscore in die Datenbank ein
    public void insertScore(int score){
        ContentValues neueZeile = new ContentValues();
        neueZeile.put(SPALTE_SCORE, score);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABELLE_SCORE, null, neueZeile);
    }

    //Gibt die 5 höchsten Scores aus der Datenbank als Zeiger zurück
    public Cursor selectAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor meinZeiger = db.rawQuery("SELECT " + SPALTE_SCORE + " FROM " + TABELLE_SCORE + " order by 1 DESC LIMIT 5", null);
        meinZeiger.moveToFirst();
        return meinZeiger;
    }
}
