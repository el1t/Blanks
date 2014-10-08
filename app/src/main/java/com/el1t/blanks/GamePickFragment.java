package com.el1t.blanks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by El1t on 10/7/14.
 */
public class GamePickFragment extends Fragment {
	private OnFragmentInteractionListener mListener;
	private TextView cardTitle;
	private TextView pack;
	private EditText answer;
	private Button submit;

	public interface OnFragmentInteractionListener {
		public void submit(String answer);
		public void finish();
	}

	public GamePickFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game_picker, container, false);

		cardTitle	= (TextView)rootView.findViewById(R.id.card_title);
		pack		= (TextView)rootView.findViewById(R.id.pack);
		answer		= (EditText)rootView.findViewById(R.id.answer);
		submit		= (Button)	rootView.findViewById(R.id.submit);

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.submit(answer.getText().toString());
			}
		});

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	public void update(String title, String pack) {
		cardTitle.setText(title);
		this.pack.setText(pack);
	}

	public void clear() {
		answer.setText("");
	}

}
