package com.example.noteworthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private Context context;

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
    public NoteModel getNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOTEWORTHY_TABLE, new String[]{NOTE_ID, NOTE_TITLE, NOTE_TITLE}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            NoteModel note = new NoteModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return note;
        } else {
            return null;
        }
    }

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
    void updateNote1(String title, String content, String id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE, title);
        cv.put(NOTE_CONTENT, content);

        int updatedCount = db.update(NOTEWORTHY_TABLE, cv, NOTE_ID + " = ?", new String[]{id});
//        if (updatedCount > 0) {
//            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
//
//        }
        db.close();}

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
    public NoteModel get(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String getNote = "SELECT * FROM " + NOTEWORTHY_TABLE + " WHERE " + NOTE_ID + " = " + id;
        Cursor cursor = db.rawQuery(getNote, null);
        if (cursor.moveToFirst()) {
            int noteID = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            cursor.close();
            db.close();
            return new NoteModel(noteID, title, content);
        } else {
            db.close();
            return null;
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
