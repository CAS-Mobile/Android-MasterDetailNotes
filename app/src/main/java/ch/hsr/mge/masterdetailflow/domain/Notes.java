package ch.hsr.mge.masterdetailflow.domain;

import java.util.ArrayList;
import java.util.List;

public class Notes {

    private List<Note> notes = new ArrayList<>();

    public Notes() {
        notes.add(new Note("Einkaufen", "Milch"));
        notes.add(new Note("TODO", "CAS Mobile"));
        notes.add(new Note("Selbststudium", "Android"));
    }

    public Note get(int position) {
        return notes.get(position);
    }

    public int getPosition(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (note.equals(notes.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int getSize() {
        return notes.size();
    }

}
