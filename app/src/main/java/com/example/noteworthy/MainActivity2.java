package com.example.noteworthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteworthy.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Intent addnewnote = getIntent();
        String title = addnewnote.getStringExtra("idid");
        binding.titlenote.setText(title);



        binding.savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check1 = binding.titlenote.getText().toString();
                String check2 = binding.contentnote.getText().toString();
                if (check1.length()==0 && check2.length()==0){
                    Intent send = new Intent(MainActivity2.this, MainActivity.class);
                    send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(send);
                    Toast.makeText(MainActivity2.this, "Nothing to Save", Toast.LENGTH_SHORT).show();
                }else{
                    NoteModel noteModel = new NoteModel(1,binding.titlenote.getText().toString(),binding.contentnote.getText().toString());
                    Intent send = new Intent(MainActivity2.this,MainActivity.class);
                    send.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(send);
                    Toast.makeText(MainActivity2.this, "note added", Toast.LENGTH_SHORT).show();
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity2.this);
                    boolean success = dataBaseHelper.addNote(noteModel);
                    finish();

                    Toast.makeText(MainActivity2.this, "Success= "+success, Toast.LENGTH_SHORT).show();

                }}



        });

    }
}
