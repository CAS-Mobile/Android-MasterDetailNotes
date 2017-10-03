package ch.hsr.mge.masterdetailflow.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ch.hsr.mge.masterdetailflow.R;
import ch.hsr.mge.masterdetailflow.domain.Note;
import ch.hsr.mge.masterdetailflow.domain.Notes;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements Observer {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemRoot;
        public TextView textView;

        public ViewHolder(View itemRoot, TextView textView) {
            super(itemRoot);
            this.itemRoot = itemRoot;
            this.textView = textView;
        }
    }

    private Notes notes;
    private ItemSelectionListener selectionListener;

    public NotesAdapter(Notes notes, ItemSelectionListener selectionListener) {
        this.notes = notes;
        this.selectionListener = selectionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ViewHolder viewHolder = new ViewHolder(v, textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Note note = notes.get(position);
        holder.textView.setText(note.getTitle());
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.getSize();
    }

    public void startObservingNotes() {
        for (int i = 0; i < notes.getSize(); i++) {
            notes.get(i).addObserver(this);
        }
    }

    public void stopObservingNotes() {
        for (int i = 0; i < notes.getSize(); i++) {
            notes.get(i).deleteObserver(this);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        Note changedNote = (Note) observable;
        int positionToUpdate = notes.getPosition(changedNote);
        notifyItemChanged(positionToUpdate);
    }
}