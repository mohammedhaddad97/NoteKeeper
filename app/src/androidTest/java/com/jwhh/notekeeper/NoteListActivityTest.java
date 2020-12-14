package com.jwhh.notekeeper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NoteListActivityTest {

    static DataManager dm;

    @BeforeClass
    public static void setUp() {
        dm  = DataManager.getInstance();
    }


    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityActivityRule =
            new ActivityTestRule<>(NoteListActivity.class);

//    @Rule
//    public ActivityScenarioRule<NoteListActivity> mNoteListActivityActivityRule =
//            new ActivityScenarioRule<>(NoteListActivity.class);

    @Test
    public void createNewNote() {

        CourseInfo course = dm.getCourse("java_core");
        String noteTitle = "I'm a new note title";
        String noteText = "I'm a new note Text";

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());
        onView(withId(R.id.spinner_courses))
                .check(matches(withSpinnerText(containsString(course.getTitle()))));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle))
                .check(matches(withText(containsString(noteTitle))));

        onView(withId(R.id.text_note_text)).perform(typeText(noteText), closeSoftKeyboard())
                .check(matches(withText(containsString(noteText))));

        pressBack();

        int noteIndex = dm.getNotes().size() - 1;

        NoteInfo note = dm.getNotes().get(noteIndex);

        assertEquals(course, note.getCourse());
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteText, note.getText());

    }

    // Test of my own to stick in what I learned.
    @Test
    public void editExistingNote() {
        final int NOTE_POS = 3;
        NoteInfo note = dm.getNotes().get(NOTE_POS);

        String title = "I'm an edited title";
        String text = "I'm an edited text";

        // First of all, I have to choose a note from the list.
        onData(allOf(instanceOf(NoteInfo.class), equalTo(note))).perform(click());

        // Second of all, I need to edit the Note Title, Text.

        // For the note Title.
        onView(withId(R.id.text_note_title)).perform(typeText(title))
                .check(matches(withText(containsString(title))));

        // For the note Text.
        onView(withId(R.id.text_note_text)).perform(typeText(text), closeSoftKeyboard())
                .check(matches(withText(containsString(text))));

        pressBack();

        // Get the note again to test whether it's edited correctly or not.
        NoteInfo editedNote = dm.getNotes().get(NOTE_POS);
        assertEquals(title, editedNote.getTitle());
        assertEquals(text, editedNote.getText());

    }
}