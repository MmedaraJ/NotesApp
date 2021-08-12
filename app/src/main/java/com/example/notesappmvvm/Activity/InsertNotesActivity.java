package com.example.notesappmvvm.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.example.notesappmvvm.databinding.ActivityInsertNotesBinding;

import java.text.DateFormat;
import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {
    //this can be used because view binding was allowed in the build module
    //view binding replaces findViewById
    private ActivityInsertNotesBinding binding;
    private String title, subTitle, notes;
    private NotesViewModel notesViewModel;
    private String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind this activity with its layout
        //do it like this always
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //We need view model here. Initialize it as follows always except for the class name
        //the view model would use its methods to ask the repository to make changes in the room data base
        //It acts based on the changes made in the app
        //pay attention to how its used in this class
        //this view model object gives us access to the insertNotes method
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        setNotesPriority();

        //when done button is clicked,
        binding.doneNotesButton.setOnClickListener(v -> {
            //!!get the text stored in the title, subtitle and notes Tv
            //this information would be used to create a new entity object. 'Note' in this case
            //remember that binding replaces findViewById
            title = binding.titleEditText.getText().toString();
            subTitle = binding.subTitleEditText.getText().toString();
            notes = binding.notesEditText.getText().toString();

            //create a new note object with the above information
            createNotes(title, subTitle, notes);
        });
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void setNotesPriority(){
        //Manage priority circles
        priority = "1";
        binding.greenCircle.setOnClickListener(v -> {
            binding.greenCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowCircle.setImageResource(0);
            binding.redCircle.setImageResource(0);
            priority= "1";
        });
        binding.yellowCircle.setOnClickListener(v -> {
            binding.greenCircle.setImageResource(0);
            binding.yellowCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redCircle.setImageResource(0);
            priority= "2";
        });
        binding.redCircle.setOnClickListener(v -> {
            binding.greenCircle.setImageResource(0);
            binding.yellowCircle.setImageResource(0);
            binding.redCircle.setImageResource(R.drawable.ic_baseline_done_24);
            priority= "3";
        });
    }

    private void createNotes(String title, String subTitle, String notes) {
        //assuming The user has input the title, subtitle and note, create a new note object
        //remember. This is not a regular object. It is an entity object.
        //Hence, fill its columns with the title, substring and notes
        //do this by note1.title = title etc
        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subTitle;
        notes1.notes = notes;
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance();
        CharSequence sequence = df.format(date.getTime());
        notes1.notesDate = sequence.toString();
        notes1.notesPriority = priority;


        //Then insert this new note into the database
        //It makes sense right?
        //Your user said done, you made a new entity(row in the database)
        //you filled its columns correctly
        //now insert the new row into the database
        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        //call finish when activity is done being used and should be closed
        //Note that main activity is not finished.
        //So when this activity closes, main activity appears because it has been open on the background
        finish();
    }


}