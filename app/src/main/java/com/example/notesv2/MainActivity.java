package com.example.notesv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity{

    private NoteViewModel noteViewModel;
    public static final int CREATE_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;


    public static RecyclerView notesList;
    RecyclerView.LayoutManager layoutManager;
    NoteAdapter adapter;
    FloatingActionButton addNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotes = findViewById(R.id.addNotes);
        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEditNotesActivity.class);
                startActivityForResult(intent, CREATE_NOTE_REQUEST_CODE);

            }
        });

        notesList = findViewById(R.id.notesList);
        layoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(layoutManager);
        notesList.setHasFixedSize(true);

        adapter = new NoteAdapter();

        notesList.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                if(direction ==ItemTouchHelper.RIGHT){
                    final Note deletedNote = adapter.getNotesAt(position);
                    noteViewModel.delete(adapter.getNotesAt(position));
                    Snackbar.make(notesList, deletedNote.getTitle() + " is deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            noteViewModel.insert(deletedNote);
                        }
                    }).show();
                }


            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        }).attachToRecyclerView(notesList);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, CreateEditNotesActivity.class);
                intent.putExtra(CreateEditNotesActivity.Extra_ID, note.getId());
                intent.putExtra(CreateEditNotesActivity.Extra_Title, note.getTitle());
                intent.putExtra(CreateEditNotesActivity.Extra_Description, note.getDescription());

                startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            String title = data.getStringExtra(CreateEditNotesActivity.Extra_Title);
            String description = data.getStringExtra(CreateEditNotesActivity.Extra_Description);

            Note note = new Note(title, description);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
                long id = data.getLongExtra(CreateEditNotesActivity.Extra_ID, -1);

                if (id == -1) {
                    Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = data.getStringExtra(CreateEditNotesActivity.Extra_Title);
                String description = data.getStringExtra(CreateEditNotesActivity.Extra_Description);

                Note note = new Note(title, description);
                note.setId(id);
                noteViewModel.update(note);
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();


        }

        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

//        MenuItem searchItem = menu.findItem(R.id.search);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchDatabase(newText);
//                return true;
//            }
//        });

        return true;
    }

//    public void searchDatabase(String query){
//        String searchQuery = "%" + query + "%";
//        noteViewModel.searchDatabase(searchQuery).observe(this, new Observer<List<Note>>() {
//            @Override
//            public void onChanged(List<Note> notes) {
//                adapter.submitList(notes);
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAllNotes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}