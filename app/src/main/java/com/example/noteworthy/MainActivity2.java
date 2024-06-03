package com.example.noteworthy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteworthy.databinding.ActivityMain2Binding;

import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private int noteId;
    private DataBaseHelper dataBaseHelper;
    private NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getIntExtra("noteId", 0);
        }
            if (noteId != 0) {
                loadNoteDetails(noteId);
            }


        if (noteId > 0) {
            noteModel = dataBaseHelper.get(noteId);
            setDataOnViewFromId();}

        Intent addNewNoteIntent = getIntent();
        if (addNewNoteIntent != null) {
            noteId = addNewNoteIntent.getIntExtra("noteId", -1);
            String title = addNewNoteIntent.getStringExtra("title");
            String content = addNewNoteIntent.getStringExtra("content");

            binding.titlenote.setText(title);
            binding.contentnote.setText(content);
        }

        binding.savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check1 = binding.titlenote.getText().toString();
                String check2 = binding.contentnote.getText().toString();
                if (check1.length() == 0 && check2.length() == 0) {
                    Intent send = new Intent(MainActivity2.this, MainActivity.class);
                    send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(send);
                    Toast.makeText(MainActivity2.this, "Nothing to Save", Toast.LENGTH_SHORT).show();
                } else {
                    if (noteId == 0) {
                        NoteModel noteModel = new NoteModel(1, binding.titlenote.getText().toString(), binding.contentnote.getText().toString());
                        Intent send = new Intent(MainActivity2.this, MainActivity.class);
                        send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(send);
                        Toast.makeText(MainActivity2.this, "note added", Toast.LENGTH_SHORT).show();
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity2.this);
                        boolean success = dataBaseHelper.addNote(noteModel);
                        finish();

                        Toast.makeText(MainActivity2.this, "Success= " + success, Toast.LENGTH_SHORT).show();
                    } else {
                        dataBaseHelper.updateNote1(check1,check2,String.valueOf(noteId));
                        Intent send = new Intent(MainActivity2.this, MainActivity.class);
                        send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(send);
                        finish();

                    }
                }
            }
        });
    }

    private void setDataOnViewFromId() {
        binding.titlenote.setText(noteModel.getTitle());
        binding.contentnote.setText(noteModel.getContent());
    }

    private void loadNoteDetails(int id) {
        NoteModel note = dataBaseHelper.getNoteById(id);
        if (note != null) {
            binding.titlenote.setText(note.getTitle());
            binding.contentnote.setText(note.getContent());
        }
    }
}
