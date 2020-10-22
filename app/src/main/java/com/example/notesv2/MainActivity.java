package com.example.notesv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListPopupWindow;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    RecyclerView notesList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter mAdapter;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesList = findViewById(R.id.notesList);
        layoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(layoutManager);
        String[] programmingList = {"C++", "C", "PYTHON", "C#", "JAVA", "JAVA SCRIPT", "DART", "SWIFT", "C++", "C", "PYTHON", "C#", "JAVA", "JAVA SCRIPT", "DART", "SWIFT"};
        mAdapter = new MyAdapter(programmingList);
        notesList.setAdapter(mAdapter);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this, CreateNotesActivity.class);
            }
        });





    }


}