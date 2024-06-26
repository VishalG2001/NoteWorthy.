package com.example.noteworthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.noteworthy.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private int noteId;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getIntExtra("noteId", -1);
            if (noteId != -1) {
                loadNoteDetails(noteId);
            }
        }
        setSupportActionBar(binding.toolbarR);
        getSupportActionBar().setTitle("create note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        binding.savenote.setOnClickListener(v -> {
//            saveEditSave();
//        });
    }
   @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_icon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_save){
            saveEditSave();
        }
        if(id==R.id.menu_delete){
            dataBaseHelper.deleteNoteFromRV(noteId);
            navigateToMainActivity();

        }
        return false;
    }

    private void loadNoteDetails(int id) {
        NoteModel note = dataBaseHelper.getNoteById(id);
        if (note != null) {
            binding.titlenote.setText(note.getTitle());
            binding.contentnote.setText(note.getContent());
        }
    }
    public enum state{
        VIEW;



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void navigateToMainActivity() {
        Intent send = new Intent();
        setResult(Activity.RESULT_OK, send);
        finish();
    }
    private void saveEditSave(){

            String title = binding.titlenote.getText().toString();
            String content = binding.contentnote.getText().toString();

            if (title.isEmpty() && content.isEmpty()) {
                Toast.makeText(MainActivity2.this, "Nothing to Save", Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            } else {
                if (noteId == -1) {
                    NoteModel noteModel = new NoteModel(0, title, content);
                    boolean success = dataBaseHelper.addNote(noteModel);
                    if (success) {
                        Toast.makeText(MainActivity2.this, "Note added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity2.this, "Failed to add note", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dataBaseHelper.updateNote1(title, content, String.valueOf(noteId));
                    Toast.makeText(MainActivity2.this, "Note updated", Toast.LENGTH_SHORT).show();
                }
                navigateToMainActivity();
            }
    }
}
