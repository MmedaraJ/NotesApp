package com.example.notesappmvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> getHighToLowPriorityNotes;
    public LiveData<List<Notes>> getLowToHighPriorityNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        getHighToLowPriorityNotes = repository.getHighToLowPriorityNotes;
        getLowToHighPriorityNotes = repository.getLowToHighPriorityNotes;
    }

    public void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
}
