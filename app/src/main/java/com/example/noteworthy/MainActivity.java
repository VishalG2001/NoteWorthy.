package com.example.noteworthy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteworthy.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {
    private ArrayList<NoteModel> noteModels = new ArrayList<>();
    private ActivityMainBinding binding;
    private RVAdapter noteListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        noteListAdapter = new RVAdapter(this);
        binding.recycleview.setAdapter(noteListAdapter);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        List<NoteModel> noteList = dataBaseHelper.getlistnote();
        noteModels.addAll(noteList);
        noteListAdapter.setNoteList(noteList);


        binding.addnewnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteIntent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(addNoteIntent);
                Toast.makeText(MainActivity.this, "Write your heart out :)", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClicked(int position) {
        Intent editNoteIntent = new Intent(this, MainActivity2.class);
        editNoteIntent.putExtra("noteId", noteModels.get(position).getId());
        startActivity(editNoteIntent);
    }
}
