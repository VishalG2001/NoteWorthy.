package com.example.noteworthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String NOTEWORTHY_TABLE = "Noteworthy";
    public static final String NOTE_TITLE = "NOTE_TITLE";
    public static final String NOTE_CONTENT = "NOTE_CONTENT";
    public static final String NOTE_ID = "ID";

    public DataBaseHelper(@Nullable Context context){
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String newnote = "CREATE TABLE IF NOT EXISTS " + NOTEWORTHY_TABLE + "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE_TITLE + " TEXT," + NOTE_CONTENT + " TEXT)";
        db.execSQL(newnote);

    }
    public boolean deleteNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String delnote ="DELETE  FROM " + NOTEWORTHY_TABLE + " WHERE " + NOTE_ID + " = " + noteModel.getId();
        Cursor cursor = db.rawQuery(delnote, null);
        if (cursor.moveToFirst()){
            return true;
        }

        else {
            return false;
        }}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void updateNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", noteModel.getTitle());
        values.put("content", noteModel.getContent());

        db.update(NOTEWORTHY_TABLE, values, "id = ?", new String[]{String.valueOf(noteModel.getId())});
        db.close();
    }
    public boolean addNote(NoteModel noteModel) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_TITLE, noteModel.getTitle());
        contentValues.put(NOTE_CONTENT, noteModel.getContent());
        long insert = db.insert(NOTEWORTHY_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public List<NoteModel> getlistnote(){
        List<NoteModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ NOTEWORTHY_TABLE;
        SQLiteDatabase db= this.getReadableDatabase();  //change it to this.getWritableDatabase() if necessary
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            do {
                int noteID=cursor.getInt(0);
                String titleName = cursor.getString(1);
                String noteName = cursor.getString(2);
                NoteModel newNote = new NoteModel(noteID,titleName,noteName);

                returnList.add(newNote);

            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
//        db.close();
        return returnList;
    }
}
