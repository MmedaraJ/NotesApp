/**
 * This repository class executes the methods in the dao interface
 */
package com.example.notesappmvvm.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesappmvvm.Dao.NotesDao;
import com.example.notesappmvvm.Database.NotesDatabase;
import com.example.notesappmvvm.Model.Notes;

import java.util.List;

public class NotesRepository {
    public NotesDao notesDoa;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<Notes>> getHighToLowPriorityNotes;
    public LiveData<List<Notes>> getLowToHighPriorityNotes;

    //Has access to the existing notes. Can access these notes through the data access objects class
    //Initialize a database object and getAllNotes
    public NotesRepository(Application application){
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDoa = database.notesDao();
        getAllNotes = notesDoa.getAllNotes();
        getHighToLowPriorityNotes = notesDoa.getHighToLowPriorityNotes();
        getLowToHighPriorityNotes = notesDoa.getLowToHighPriorityNotes();
    }

    public void insertNotes(Notes notes){
        notesDoa.insertNotes(notes);
    }

    public void deleteNotes(int id){
        notesDoa.deleteNotes(id);
    }

    public void updateNotes(Notes notes){
        notesDoa.updateNotes(notes);
    }
}
