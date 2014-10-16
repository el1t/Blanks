package com.el1t.blanks;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GameActivity extends Activity implements GameBlankFragment.OnFragmentInteractionListener
{
	private static final String TAG = "GAME";
	private URL SERVER;
	private GameBlankFragment mBlankFragment;
	private GamePickFragment mPickFragment;
	private InputMethodManager mInputMethodManager;
	private String answer;
	private String title;
	private String pack;
	private int view;
	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mFragmentManager = getFragmentManager();
		mBlankFragment = new GameBlankFragment();

		if (savedInstanceState == null) {
			mFragmentManager.beginTransaction()
					.add(R.id.layout, mBlankFragment)
					.commit();
		}

		try {
			SERVER = new URL("http://blanks.herokuapp.com");
		} catch (MalformedURLException e) {
			Log.e(TAG, "URL problem!", e);
		}

		mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		view = 0;
		requestCard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.regular, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
	public void onPause() {
		super.onPause();

		mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	// Handles answer submission
	public void submit(String answer) {
		this.answer = answer;
		mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		mBlankFragment.clear();
		requestCard();
	}

	// Switches to next view
	protected void nextView() {
		switch (view) {
			case 0:
				mPickFragment = new GamePickFragment();
				Bundle content = new Bundle();
				content.putString("title", title);
				content.putString("pack", pack);
				mPickFragment.setArguments(content);
				mFragmentManager.beginTransaction()
						.replace(R.id.layout, mPickFragment)
						.commit();
				view++;
				break;

			case 1:
				mBlankFragment = new GameBlankFragment();
				mFragmentManager.beginTransaction()
						.replace(R.id.layout, mBlankFragment)
						.commit();
				view = 0;
				break;
		}
	}

	// Updates the card text with a new question
	protected void updateCard(String response) {
		title = response;
		pack = "First Version";
		mBlankFragment.update(response, "First Version");
	}

	// Queries server for a random card
	protected void requestCard() {
		new WebConnection().execute(SERVER);
	}

	// AsyncTask to handle contacting the server
	private class WebConnection extends AsyncTask<URL, Void, String> {
		private static final String TAG = "CONNECTION";

		@Override
		protected String doInBackground(URL... urls) {
			assert(urls.length == 1);
			URL url = urls[0];
			HttpURLConnection urlConnection;
			String response = "";
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				response = readStream(in);
				urlConnection.disconnect();
			} catch (Exception e) {
				Log.e(TAG, "Connection error.", e);
			}
			return response.replace("\"", "");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateCard(result);
		}

		private String readStream(InputStream is) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}
	}
}