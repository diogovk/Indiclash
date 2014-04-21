package org.openintents.indiclash.sample.flashcardsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.openintents.indiclash.sample.flashcardsapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to create a flashcard.
 * This activity is called via an Intent, for instance by a dictionary app.
 */
public class CreateFlashcard extends Activity {
	final static String[] noteTypeFields = new String[]{"Expression", "Meaning", "Reading"};
	final static String[] basicNoteTypeFields = new String[]{"Front", "Back"};
	final static HashMap<String, String[]> availableNoteTypes ;
	final static String DEFAULT_NOTETYPE = "MyCustomNoteType";
	private Bundle extras;
	private ArrayList<HashMap<String, Serializable>> intentNotes;
	static {
		availableNoteTypes = new HashMap<String, String[]>();
		availableNoteTypes.put("MyCustomNoteType", noteTypeFields);
		availableNoteTypes.put("Basic", basicNoteTypeFields);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_notes_adder);
        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String[] defaultNoteTypeFields = chooseDefaultNoteTypeFields((String) extras.get("DEFAULT_NOTE_TYPE"));
        intentNotes = (ArrayList<HashMap<String, Serializable>>) extras.get("NOTES");
        HashMap<String, Serializable> fistNote = intentNotes.get(0);
		showNotePreview(defaultNoteTypeFields, fistNote);
		TextView textSureAdd = (TextView) findViewById(R.id.text_sure_add);
		textSureAdd.setText(intentNotes.size()+ " new notes will be added. Continue?");
    }

	private void showNotePreview(String[] defaultNoteTypeFields, HashMap<String, Serializable> fistNote) {
		String html = genHtmlFromIntentNote(fistNote, defaultNoteTypeFields);
	    final WebView webView = (WebView) findViewById(R.id.create_flashcard);
        webView.loadDataWithBaseURL("", html, "text/html", "utf-8", null);
	}

	private String[] chooseDefaultNoteTypeFields(String intentNoteType) {
		/*
		 * Fist choice = Note type coming from intent
		 * Second choice = The default note type of this application
		 */
		if (!availableNoteTypes.containsKey(intentNoteType)) {
			Toast.makeText(this, "Could not find Note Type " + intentNoteType + ".\nFalling back to default.",
					Toast.LENGTH_SHORT).show();
			return availableNoteTypes.get(DEFAULT_NOTETYPE);
		}
		return availableNoteTypes.get(intentNoteType);
	}

	public String genHtmlFromIntentNote(HashMap<String, Serializable> intentNote, String[] noteTypeFields) {
		StringBuilder html =  new StringBuilder().append("<html><body>");
		html.append("<p>").append(noteTypeFields[0]).append(": ").append(intentNote.get("SOURCE_TEXT"));
		html.append("<p>").append(noteTypeFields[1]).append(": ").append(intentNote.get("TARGET_TEXT"));
		String[] optionalParameters = (String[]) intentNote.get("OPTIONAL_PARAMETERS");

		if (optionalParameters == null) {
			return html.append("</body></html>").toString();
		}
		for (int i = 2; i < noteTypeFields.length; i++) {
			String fieldContent = optionalParameters[i - 2];
			if (fieldContent != null) {
				html.append("<p>").append(noteTypeFields[i]).append(": ").append(fieldContent);
			}
		}
		return html.append("</body></html>").toString();
	}
	
}