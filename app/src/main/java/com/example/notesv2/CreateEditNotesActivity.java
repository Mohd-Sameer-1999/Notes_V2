package com.example.notesv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEditNotesActivity extends AppCompatActivity {

    public static final String Extra_ID = "com.example.notesv2.Extra_ID";
    public static final String Extra_Title = "com.example.notesv2.Extra_Title";
    public static final String Extra_Description = "com.example.notesv2.Extra_Description";

    private EditText titleEditText;
    private EditText descriptionEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(Extra_ID)){
            setTitle("Edit Note");
            titleEditText.setText(intent.getStringExtra(Extra_Title));
            descriptionEditText.setText(intent.getStringExtra(Extra_Description));
        }else{
            setTitle("Add Note");
        }

    }

    private void saveNote(){

        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(Extra_Title, title);
        data.putExtra(Extra_Description, description);

        long id = getIntent().getLongExtra(Extra_ID, -1);
        if(id != -1){
            data.putExtra(Extra_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}