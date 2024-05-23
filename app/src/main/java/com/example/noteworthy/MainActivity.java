
package com.example.noteworthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteworthy.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{
    ArrayList<NoteModel> noteModels = new ArrayList<>();
    private ActivityMainBinding binding;
    String vv = "Vishal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        RVAdapter noteListAdapter = new RVAdapter(noteModel -> onItemClicked(noteModel));
        binding.recycleview.setAdapter(noteListAdapter);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        List<NoteModel> noteList = dataBaseHelper.getlistnote();
        noteListAdapter.setNoteList(noteList);

        binding.recycleview.addOnItemTouchListener();

        binding.addnewnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnoteintent = new Intent(MainActivity.this, MainActivity2.class);
                addnoteintent.putExtra("idid",vv);
                startActivity(addnoteintent);
                Toast.makeText(MainActivity.this, "Write your heart out :)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        Intent editnote = new Intent(this,MainActivity2.class);
        editnote.putExtra("noteid",noteModels.get(position).getId());

    }
}