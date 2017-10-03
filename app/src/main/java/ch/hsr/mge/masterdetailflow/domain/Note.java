package ch.hsr.mge.masterdetailflow.domain;

import java.util.Observable;

public class Note extends Observable /* fuer Aufgabe 4 */ {

    private String title;
    private String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setChanged();
        notifyObservers();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        // Fuer unsere App brauchen wir keinen Observer fuer den Content-String,
        // da sich niemand dafuer interessiert wenn dieser aendert.
        this.content = content;
    }
}
