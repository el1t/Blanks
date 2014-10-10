package com.el1t.blanks;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by El1t on 10/9/14.
 */
public class LogoAnimationFragment extends Fragment
{
	private final int DELAY = 500;
	private final int OFFSET = 100;
	private ArrayList<ImageView> logo;
	private ImageView a;
	private TranslateAnimation mTranslateAnimation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_logo, container, false);

		logo = new ArrayList<ImageView>();
		logo.add((ImageView) rootView.findViewById(R.id.logoB));
		logo.add((ImageView) rootView.findViewById(R.id.logoL));
		logo.add((ImageView) rootView.findViewById(R.id.logoA));
		logo.add((ImageView) rootView.findViewById(R.id.logoN));
		logo.add((ImageView) rootView.findViewById(R.id.logoK));
		logo.add((ImageView) rootView.findViewById(R.id.logoS));
		a = (ImageView) rootView.findViewById(R.id.logo);

		// Set animation for logo
		Animation fadeInAnimation;
		for(int i = 0; i < logo.size(); i++) {
			fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
			fadeInAnimation.setFillAfter(true);
			fadeInAnimation.setStartOffset(i * OFFSET);
			logo.get(i).startAnimation(fadeInAnimation);
		}
		// Animate Description (a)
		// Fade
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(OFFSET * 6 + 2 * OFFSET);
		fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// Delete used logos
				for(ImageView iv : logo) {
					((ViewManager)iv.getParent()).removeView(iv);
				}
				a.startAnimation(mTranslateAnimation);
			}
		});
		// Translate
		mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_PARENT, -0.35f);
		mTranslateAnimation.setFillAfter(true);
		mTranslateAnimation.setStartOffset(DELAY);
		mTranslateAnimation.setDuration(750);
		mTranslateAnimation.setInterpolator(new EaseOutInterpolator());
		mTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				FragmentManager fm = getFragmentManager();
				FragmentTransaction transaction = fm.beginTransaction();
				transaction.add(R.id.container, new HomeFragment());
				transaction.commit();
			}
		});
		a.startAnimation(fadeInAnimation);

		return rootView;
	}
}
