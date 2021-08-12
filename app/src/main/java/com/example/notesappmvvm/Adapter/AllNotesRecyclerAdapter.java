package com.example.notesappmvvm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappmvvm.Activity.UpdateNotesActivity;
import com.example.notesappmvvm.Model.Notes;
import com.example.notesappmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class AllNotesRecyclerAdapter extends RecyclerView.Adapter<AllNotesRecyclerAdapter.AllNotesViewHolder> {
    private Context context;
    private List<Notes> allNotes;

    public AllNotesRecyclerAdapter(Context context, List<Notes> allNotes) {
        this.context = context;
        this.allNotes = allNotes;
    }

    @NonNull
    @Override
    public AllNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_notes_items, parent, false);
        return new AllNotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNotesRecyclerAdapter.AllNotesViewHolder holder, int position) {
        holder.bind(allNotes.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void setAllNotes(List<Notes> filterredName){
        this.allNotes = filterredName;
        notifyDataSetChanged();
    }

    class AllNotesViewHolder extends RecyclerView.ViewHolder{
        private TextView notesTitle, notesSubtitle, notesDate;
        private View notesPriority;
        private View itemView;
        public AllNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitle = itemView.findViewById(R.id.notesTitle);
            notesSubtitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
            this.itemView = itemView;
        }

        public void bind(Notes notes){
            notesTitle.setText(notes.notesTitle);
            notesSubtitle.setText(notes.notesSubtitle);
            notesDate.setText(notes.notesDate);
            if(notes.notesPriority.equals("1")){
                notesPriority.setBackgroundResource(R.drawable.green_circle);
            }
            else if(notes.notesPriority.equals("2")){
                notesPriority.setBackgroundResource(R.drawable.yellow_circle);
            }
            else if(notes.notesPriority.equals("3")){
                notesPriority.setBackgroundResource(R.drawable.red_circle);
            }
            //When an item is clicked, you want to transfer the id, title, subtitle,
            // notes and priority to the update activity intent
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, UpdateNotesActivity.class);
                intent.putExtra("id", notes.id);
                intent.putExtra("title", notes.notesTitle);
                intent.putExtra("subtitle", notes.notesSubtitle);
                intent.putExtra("notes", notes.notes);
                intent.putExtra("priority", notes.notesPriority);
                context.startActivity(intent);
            });

        }
    }
}
