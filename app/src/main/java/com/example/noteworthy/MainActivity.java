package com.example.noteworthy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.noteworthy.databinding.ActivityMainBinding;
import com.example.noteworthy.databinding.LayyBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {
    private ArrayList<NoteModel> noteModels = new ArrayList<>();
    private ActivityMainBinding binding;
    private RVAdapter noteListAdapter;
    private DataBaseHelper dataBaseHelper;
    RVAdapter.MyViewHolder myViewHolder;


    ActivityResultLauncher<Intent> mainActivity2Result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    updateNoteList();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dataBaseHelper = new DataBaseHelper(this);


        noteListAdapter = new RVAdapter(this);
        binding.recycleview.setAdapter(noteListAdapter);
        binding.recycleview.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(binding.toolbarV);
        getSupportActionBar().setTitle("NoteWorthy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<NoteModel> noteList = dataBaseHelper.getlistnote();
        noteModels.addAll(noteList);
        noteListAdapter.setNoteList(noteList);
        noteListAdapter.setOnClickListener(new RVAdapter.NoteClickListener() {
            @Override
            public void onClick(NoteModel noteModel) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("noteId", noteModel.getId());
                mainActivity2Result.launch(intent);
            }
        });
        noteListAdapter.setThreeDotClick(new RVAdapter.ThreeDotClick() {
            @Override
            public void onClick(NoteModel noteModel) {


                PopupMenu popupMenu = new PopupMenu(MainActivity.this,binding.recycleview);
                popupMenu.setGravity(5);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete_option){
                            boolean isdeleted = dataBaseHelper.deleteNoteFromRV(noteModel.getId());
                            if(isdeleted){
                                Toast.makeText(MainActivity.this, "myViewHolder.getAdapterPosition()", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        noteListAdapter.setOnLongClickListener(new RVAdapter.NoteLongClickListener() {
            @Override
            public void onLongClick(NoteModel noteModel) {
                dataBaseHelper.deleteNoteFromRV(noteModel.getId());
                Toast.makeText(MainActivity.this, "Note Deleted :)", Toast.LENGTH_SHORT).show();
            }
        });


        binding.addnewnote.setOnClickListener(v -> {
            Intent addNoteIntent = new Intent(MainActivity.this, MainActivity2.class);
            mainActivity2Result.launch(addNoteIntent);
            Toast.makeText(MainActivity.this, "Write your heart out :)", Toast.LENGTH_SHORT).show();
        });
    }

    public void updateNoteList() {
        List<NoteModel> noteModelList = dataBaseHelper.getlistnote();
        noteModels.clear();
        noteModels.addAll(noteModelList);
        noteListAdapter.setNoteList(noteModelList);
    }

    @Override
    public void onItemClicked(int position) {
        Intent editNoteIntent = new Intent(this, MainActivity2.class);
        editNoteIntent.putExtra("noteId", noteModels.get(position).getId());
        startActivity(editNoteIntent);
    }

}
