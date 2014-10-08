package com.el1t.blanks;

import android.app.Activity;
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


public class GameActivity extends Activity implements GamePickFragment.OnFragmentInteractionListener{
	private static final String TAG = "GAME";
	private URL SERVER;
	private GamePickFragment mPickFragment;
	private InputMethodManager mInputMethodManager;
	private String answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		mPickFragment = new GamePickFragment();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, mPickFragment)
					.commit();
		}

		try {
			SERVER = new URL("http://blanks.herokuapp.com");
		} catch (MalformedURLException e) {
			Log.e(TAG, "URL problem!", e);
		}

		mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

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
		mPickFragment.clear();
		requestCard();
	}

	// Updates the card text with a new question
	protected void updateCard(String response) {
		mPickFragment.update(response.replace("\"", ""), "First Version");
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
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			//Do anything with response..
			updateCard(result);
		}

		private String readStream(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
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