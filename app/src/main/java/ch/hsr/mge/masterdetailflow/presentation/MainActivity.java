package ch.hsr.mge.masterdetailflow.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.Observable;
import java.util.Observer;

import ch.hsr.mge.masterdetailflow.Application;
import ch.hsr.mge.masterdetailflow.R;
import ch.hsr.mge.masterdetailflow.domain.Note;

public class MainActivity extends AppCompatActivity implements ItemSelectionListener, Observer {

    private boolean isTwoPaneMode;
    private int currentlyObserving = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.notesDetailContainer) != null) {
            // Wir befinden uns im Tablet-Modus!
            isTwoPaneMode = true;
        }
    }

    @Override
    public void onItemSelected(int position) {

        if (isTwoPaneMode) {

            Application app = (Application) getApplication();

            // Wir merken uns, bei welcher Notiz wir im Moment als Observer
            // angemeldet sind. Wenn dann ein neuer Eintrag in der Liste
            // selektiert wird, melden wir uns zuerst bei diesem Eintrag ab
            if (currentlyObserving >= 0) {
                app.getNotes().get(currentlyObserving).deleteObserver(this);
            }
            currentlyObserving = position;
            Note note = app.getNotes().get(currentlyObserving);

            // Um den Observer-Effekt nochmals zu verdeutlichen
            // setzen wir den Titel der Notiz als Untertitel der
            // Activity
            getSupportActionBar().setSubtitle(note.getTitle());
            note.addObserver(this);

            Bundle arguments = new Bundle();
            arguments.putInt(NoteDetailFragment.ARG_ITEM, position);
            NoteDetailFragment fragment = new NoteDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.notesDetailContainer, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, DetailActivity.class);
            detailIntent.putExtra(NoteDetailFragment.ARG_ITEM, position);
            startActivity(detailIntent);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        Note note = (Note) observable;
        getSupportActionBar().setSubtitle(note.getTitle());
    }
}
