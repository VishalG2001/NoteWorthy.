package com.example.noteworthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String NOTEWORTHY_TABLE = "Noteworthy";
    public static final String NOTE_TITLE = "NOTE_TITLE";
    public static final String NOTE_CONTENT = "NOTE_CONTENT";
    public static final String NOTE_ID = "ID";
    private Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String newnote = "CREATE TABLE IF NOT EXISTS " + NOTEWORTHY_TABLE + "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE_TITLE + " TEXT," + NOTE_CONTENT + " TEXT)";
        db.execSQL(newnote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, noteModel.getTitle());
        contentValues.put(NOTE_CONTENT, noteModel.getContent());
        long insert = db.insert(NOTEWORTHY_TABLE, null, contentValues);
        db.close();
        return insert != -1;
    }

    public void updateNote1(String title, String content, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, title);
        cv.put(NOTE_CONTENT, content);
        db.update(NOTEWORTHY_TABLE, cv, NOTE_ID + " = ?", new String[]{id});
        db.close();
    }

    public NoteModel getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOTEWORTHY_TABLE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT}, NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            NoteModel note = new NoteModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return note;
        } else {
            db.close();
            return null;
        }
    }
    public boolean deleteNoteFromRV(int idd) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(NOTEWORTHY_TABLE, NOTE_ID + " = ?", new String[]{String.valueOf(idd)});
        db.close();
        return rowsAffected > 0;
    }


    public List<NoteModel> getlistnote() {
        List<NoteModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTEWORTHY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int noteID = cursor.getInt(0);
                String titleName = cursor.getString(1);
                String noteName = cursor.getString(2);
                NoteModel newNote = new NoteModel(noteID, titleName, noteName);
                returnList.add(newNote);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
