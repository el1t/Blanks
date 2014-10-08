package com.el1t.blanks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by El1t on 10/8/14.
 */
public class HomeFragment extends Fragment {
	private Home mListener;
	private Button start;
	private ImageView logo;

	public interface Home {
		public void start();
	}

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		logo	= (ImageView)rootView.findViewById(R.id.logo);
		start	= (Button)	 rootView.findViewById(R.id.start);

		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.start();
			}
		});

		// Fading animations
		// Set animation for logo
		Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(500);
		logo.startAnimation(fadeInAnimation);

		// Set animation for start button
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(1000);
		start.startAnimation(fadeInAnimation);

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mListener = (Home) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
}
