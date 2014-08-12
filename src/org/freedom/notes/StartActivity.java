package org.freedom.notes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.org.freedom.notes.R;

public class StartActivity extends NotesBasicActivity {

	private Button createButton;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createButton = (Button) findViewById(R.id.action_create_new);
		createButton.setOnClickListener(new CreateHandler());
	}

	@Override
	protected void onResume() {
		super.onResume();
		createButton.setEnabled(true);
	}

	private class CreateHandler implements OnClickListener {

		@Override
		public void onClick(final View v) {
			createButton.setEnabled(false);
			startActivity(NoteActivity.INTENT_CREATE(StartActivity.this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected int getHeaderLayoutId() {
		return R.layout.activity_header;
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.activity_start_content;
	}

	@Override
	protected int getFooterLayoutId() {
		return R.layout.activity_start_footer;
	}
}
