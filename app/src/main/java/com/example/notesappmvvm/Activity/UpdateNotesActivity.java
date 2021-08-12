package com.example.notesappmvvm.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;
import com.example.notesappmvvm.ViewModel.NotesViewModel;
import com.example.notesappmvvm.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {
    private ActivityUpdateNotesBinding binding;
    private NotesViewModel notesViewModel;
    private TextView yes, no;
    private String updatedTitle, updatedSubTitle, updatedNotes, updatedPriority;
    private String sTitle, sSubTitle, sNotes, sPriority;
    private int sId;
    private BottomSheetDialog sheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        //receive the transferred information
        //this info was transferred from the allNotesViewHolder view holder
        sId = getIntent().getIntExtra("id", 0);
        sTitle = getIntent().getStringExtra("title");
        sSubTitle = getIntent().getStringExtra("subtitle");
        sNotes = getIntent().getStringExtra("notes");
        sPriority = getIntent().getStringExtra("priority");

        //Now update the elements in update notes layout with the above information
        binding.updateTitleEditText.setText(sTitle);
        binding.updateSubTitleEditText.setText(sSubTitle);
        binding.updateNotesEditText.setText(sNotes);

        showNotesPriority();
        setUpdatedNotesPriority();

        //when update button is clicked
        binding.updateNotesButton.setOnClickListener(v -> {
            updatedTitle = binding.updateTitleEditText.getText().toString();
            updatedSubTitle = binding.updateSubTitleEditText.getText().toString();
            updatedNotes = binding.updateNotesEditText.getText().toString();
            updateNotes(updatedTitle, updatedSubTitle, updatedNotes, updatedPriority);
        });

    }

    private void showNotesPriority(){
        //Manage priority circles
        if(sPriority.equals("1")){
            binding.updateGreenCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.updateYellowCircle.setImageResource(0);
            binding.updateRedCircle.setImageResource(0);
        }
        else if(sPriority.equals("2")){
            binding.updateGreenCircle.setImageResource(0);
            binding.updateYellowCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.updateRedCircle.setImageResource(0);
        }
        else if(sPriority.equals("3")){
            binding.updateGreenCircle.setImageResource(0);
            binding.updateYellowCircle.setImageResource(0);
            binding.updateRedCircle.setImageResource(R.drawable.ic_baseline_done_24);
        }

    }

    private void setUpdatedNotesPriority(){
        updatedPriority = sPriority;
        binding.updateGreenCircle.setOnClickListener(v -> {
            binding.updateGreenCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.updateYellowCircle.setImageResource(0);
            binding.updateRedCircle.setImageResource(0);
            updatedPriority = "1";
        });
        binding.updateYellowCircle.setOnClickListener(v -> {
            binding.updateGreenCircle.setImageResource(0);
            binding.updateYellowCircle.setImageResource(R.drawable.ic_baseline_done_24);
            binding.updateRedCircle.setImageResource(0);
            updatedPriority = "2";
        });
        binding.updateRedCircle.setOnClickListener(v -> {
            binding.updateGreenCircle.setImageResource(0);
            binding.updateYellowCircle.setImageResource(0);
            binding.updateRedCircle.setImageResource(R.drawable.ic_baseline_done_24);
            updatedPriority = "3";
        });
    }

    private void updateNotes(String updatedTitle, String updatedSubTitle, String updatedNotes, String updatedPriority) {
        //Make a new object with the updated properties
        //But use the same id
        Notes updated = new Notes();
        updated.id = sId;
        updated.notesTitle = updatedTitle;
        updated.notesSubtitle = updatedSubTitle;
        updated.notes = updatedNotes;
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance();
        CharSequence sequence = df.format(date.getTime());
        updated.notesDate = sequence.toString();
        updated.notesPriority = updatedPriority;

        //updating happens based in existing id
        //So make a new object with the updated properties
        //But use the same id
        notesViewModel.updateNote(updated);

        Toast.makeText(this, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();
        //call finish when activity is done being used and should be closed
        //Note that main activity is not finished.
        //So when this activity closes, main activity appears because it has been open on the background
        finish();
    }

    //when a menu item is created, inflate that layout in the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    //whn an options menu is selected, inflate the layout associated with it
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.deleteIcon){
            //make a dialog box for the update notes activity
            sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this, R.style.BottomSheetStyle);
            //inflate the delete dialog view
            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_bottom_screen, (LinearLayout) findViewById(R.id.bottomSheet));
            //make that inflated the content view for the dialog box
            sheetDialog.setContentView(view);
            sheetDialog.show();
            no = view.findViewById(R.id.noDeleteButton);
            yes = view.findViewById(R.id.yesDeleteButton);
            //if yes is clicked, delete the note with the correct id
            //close the update activity and return to main activity
            yes.setOnClickListener(v -> {
                notesViewModel.deleteNote(sId);
                finish();
            });
            //if no is clicked, simply dismiss the dialog box.
            //do not close the activity
            //allow the user to keep editing his note
            no.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });
            sheetDialog.show();
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sheetDialog != null){
            sheetDialog.dismiss();
            sheetDialog = null;
        }
    }
}