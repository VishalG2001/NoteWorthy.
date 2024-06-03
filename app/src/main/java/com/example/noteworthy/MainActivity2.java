package com.example.noteworthy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        binding.savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void loadNoteDetails(int id) {
        NoteModel note = dataBaseHelper.getNoteById(id);
        if (note != null) {
            binding.titlenote.setText(note.getTitle());
            binding.contentnote.setText(note.getContent());
        }
    }

    private void navigateToMainActivity() {
        Intent send = new Intent(MainActivity2.this, MainActivity.class);
        send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(send);
        finish();
    }
}
