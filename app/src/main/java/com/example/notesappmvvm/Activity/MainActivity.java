package com.example.notesappmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.notesappmvvm.Adapter.AllNotesRecyclerAdapter;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //In the main activity, binding does not apply
    //So proceed normally
    private FloatingActionButton addNoteButton;
    private NotesViewModel notesViewModel;
    private RecyclerView allNotesRecycler;
    private AllNotesRecyclerAdapter adapter;
    private TextView noFilterTv, highToLowTv, lowToHighTv;
    private List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.test);

        //this view model object gives us access to the getAllNotes method
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        allNotesRecycler = findViewById(R.id.allNotesRecycler);

        filterNotesAllList = new ArrayList<>();

        //the main activity should always contain all existing notes
        //observe() adds the main activity to the list of observers
        //we want to get all notes and also observe the database for changes in content of the returned list
        notesViewModel.getHighToLowPriorityNotes.observe(this, notes -> {
            setAdapter(notes);
        });

        setUpTvs();

        addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });
    }

    public void setAdapter(List<Notes> notes) {
        adapter = new AllNotesRecyclerAdapter(this, notes);
        //allNotesRecycler.setLayoutManager(new StaggeredGridLayoutManager());
        allNotesRecycler.setAdapter(adapter);
    }

    private void loadData(int i){
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }else if(i==1){
            notesViewModel.getHighToLowPriorityNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }else if(i==2){
            notesViewModel.getLowToHighPriorityNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    private void setUpTvs(){
        noFilterTv = findViewById(R.id.noFilterTv);
        highToLowTv = findViewById(R.id.highToLowFilterTv);
        lowToHighTv = findViewById(R.id.lowToHighFilterTv);

        noFilterTv.setOnClickListener(v -> {
            noFilterTv.setBackgroundResource(R.drawable.filter_back_with_border);
            highToLowTv.setBackgroundResource(R.drawable.filter_back);
            lowToHighTv.setBackgroundResource(R.drawable.filter_back);
            loadData(0);
        });

        highToLowTv.setBackgroundResource(R.drawable.filter_back_with_border);
        highToLowTv.setOnClickListener(v -> {
            noFilterTv.setBackgroundResource(R.drawable.filter_back);
            highToLowTv.setBackgroundResource(R.drawable.filter_back_with_border);
            lowToHighTv.setBackgroundResource(R.drawable.filter_back);
            loadData(1);
        });

        lowToHighTv.setOnClickListener(v -> {
            noFilterTv.setBackgroundResource(R.drawable.filter_back);
            highToLowTv.setBackgroundResource(R.drawable.filter_back);
            lowToHighTv.setBackgroundResource(R.drawable.filter_back_with_border);
            loadData(2);
        });
    }

    //This is search procedure. follow it always
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });
        return true;
    }

    private void NotesFilter(String newText) {
        ArrayList<Notes> filterNames = new ArrayList<>();
        for(Notes notes: this.filterNotesAllList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText) ||
                    notes.notesTitle.contains(newText.toUpperCase()) || notes.notesSubtitle.contains(newText.toUpperCase())){
                filterNames.add(notes);
            }
        }
        this.adapter.setAllNotes(filterNames);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.searchBar){

        }
        return true;
    }
}