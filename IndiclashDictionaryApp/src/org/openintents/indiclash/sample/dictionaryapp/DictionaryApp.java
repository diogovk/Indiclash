package org.openintents.indiclash.sample.dictionaryapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * A sample dictionary app.
 */
public class DictionaryApp extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Button to launch a card creation Intent.
		Button button = (Button) findViewById(R.id.addWordToFlashcardApp);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(DictionaryApp.this, "Adding word to flashcard app", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setAction("org.openintents.action.CREATE_FLASHCARDS");
				intent.putExtra("VERSION", 1);
				intent.putExtra("DEFAULT_NOTE_TYPE", "Basic");
				
				ArrayList<HashMap<String, Serializable>> notes = new ArrayList<HashMap<String, Serializable>>();
				HashMap<String, Serializable> note1 = new HashMap<String, Serializable>();
				note1.put("SOURCE_TEXT", "夏");
				note1.put("TARGET_TEXT", "summer");
				String[] optional_parameters1 = new String[]{"なつ", "Common word", "Adverbial noun, Temporal noun"};
				
				note1.put("OPTIONAL_PARAMETERS", optional_parameters1);
				
				HashMap<String, Serializable> note2 = new HashMap<String, Serializable>();
				note2.put("SOURCE_TEXT", "サマー");
				note2.put("TARGET_TEXT", "summer");
				String[] optional_parameters2 = new String[]{"サマー", "Common word", "Noun"};
				
				note2.put("OPTIONAL_PARAMETERS", optional_parameters2);
				notes.add(note1);
				notes.add(note2);
				intent.putExtra("NOTES", notes);
				startActivity(intent);
			}
		});
	}
}