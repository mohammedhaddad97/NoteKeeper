package com.jwhh.notekeeper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    static DataManager dm;

    @BeforeClass
    public static void initializeDataManager() {
        dm = DataManager.getInstance();
    }

    @Before
    public void setUp() {
        dm.getNotes().clear();
        dm.initializeExampleNotes();
    }

    @Test
    public void createNewNoe() {
        CourseInfo course = dm.getCourse("android_intents");
        String title = "I'm just a dummy title to a test note";
        String text = "Yup!! that's right, I'm the dummy text under the dummy" +
                "title, for the dummy note";

        int noteIndex = dm.createNewNote();

        NoteInfo createNote = dm.getNotes().get(noteIndex);
        createNote.setCourse(course);
        createNote.setTitle(title);
        createNote.setText(text);

        NoteInfo newNote = dm.getNotes().get(noteIndex);

        assertEquals(course, newNote.getCourse());
        assertEquals(title, newNote.getTitle());
        assertEquals(text, newNote.getText());
    }

    @Test
    public void findSimilarNotes() {

        CourseInfo course = dm.getCourse("android_intents");
        String title = "I'm just a dummy title to a test note";
        String text1 = "Yup!! that's right, I'm the dummy text under the dummy" +
                "title, for the dummy note";

        String text2 = "That's right, I'm the dummy text under the dummy" +
                "title, for the dummy note";

        int noteIndex1 = dm.createNewNote();

        NoteInfo noteInfo1 = dm.getNotes().get(noteIndex1);
        noteInfo1.setCourse(course);
        noteInfo1.setTitle(title);
        noteInfo1.setText(text1);


        int noteIndex2 = dm.createNewNote();

        NoteInfo noteInfo2 = dm.getNotes().get(noteIndex2);
        noteInfo2.setCourse(course);
        noteInfo2.setTitle(title);
        noteInfo2.setText(text2);

        int foundIndex1 = dm.findNote(noteInfo1);

        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = dm.findNote(noteInfo2);

        assertEquals(noteIndex2, foundIndex2);

    }

    @Test
    public void createNewNoteOneStepCreation() {
        CourseInfo course = dm.getCourse("android_intents");
        String title = "I'm just a dummy title to a test note";
        String text1 = "Yup!! that's right, I'm the dummy text under the dummy" +
                "title, for the dummy note";

        int noteIndex = dm.createNewNote(course, title, text1);

        NoteInfo newNote = dm.getNotes().get(noteIndex);

        assertEquals(course, newNote.getCourse());
        assertEquals(title, newNote.getTitle());
        assertEquals(text1, newNote.getText());

    }

}