package ch.hsr.mge.masterdetailflow.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hsr.mge.masterdetailflow.Application;
import ch.hsr.mge.masterdetailflow.R;

public class NotesListFragment extends Fragment {

    private ItemSelectionListener itemSelectionCallback = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Eine Optimierung, wenn sich die Displaygroesse der Liste nicht aendern wird.
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Application app = (Application) getActivity().getApplication();

        adapter = new NotesAdapter(app.getNotes(), itemSelectionCallback);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (!(activity instanceof ItemSelectionListener)) {
            throw new IllegalStateException("Activity must implement OnItemSelection");
        }

        itemSelectionCallback = (ItemSelectionListener) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Nur wenn die App im Vordergrund aktiv ist wollen wir auch ueber die Updates
        // informiert werden
        adapter.startObservingNotes();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopObservingNotes();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemSelectionCallback = null;
    }
}
