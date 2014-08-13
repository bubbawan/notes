package org.freedom.notes;

import org.freedom.androbasics.inject.InjectView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends NotesBasicActivity {

	@InjectView(id = R.id.action_create_new)
	private Button createButton;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
