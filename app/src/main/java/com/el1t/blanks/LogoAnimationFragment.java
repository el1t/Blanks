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
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by El1t on 10/9/14.
 */
public class LogoAnimationFragment extends Fragment
{
	private ImageView B;
	private ImageView L;
	private ImageView A;
	private ImageView N;
	private ImageView K;
	private ImageView S;
	private ImageView a;
	private TranslateAnimation mTranslateAnimation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_logo, container, false);

		B = (ImageView) rootView.findViewById(R.id.logoB);
		L = (ImageView) rootView.findViewById(R.id.logoL);
		A = (ImageView) rootView.findViewById(R.id.logoA);
		N = (ImageView) rootView.findViewById(R.id.logoN);
		K = (ImageView) rootView.findViewById(R.id.logoK);
		S = (ImageView) rootView.findViewById(R.id.logoS);
		a = (ImageView) rootView.findViewById(R.id.logo);

		// Set animation for logo
		// Animate B
		Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(500);
		B.startAnimation(fadeInAnimation);
		// Animate L
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(750);
		L.startAnimation(fadeInAnimation);
		// Animate A
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(1000);
		A.startAnimation(fadeInAnimation);
		// Animate N
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(1250);
		N.startAnimation(fadeInAnimation);
		// Animate K
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(1500);
		K.startAnimation(fadeInAnimation);
		// Animate S
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(1750);
		S.startAnimation(fadeInAnimation);
		// Animate Description (a)
		// Fade
		fadeInAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
		fadeInAnimation.setFillAfter(true);
		fadeInAnimation.setStartOffset(2000);
		fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				((ViewManager)B.getParent()).removeView(B);
				((ViewManager)L.getParent()).removeView(L);
				((ViewManager)A.getParent()).removeView(A);
				((ViewManager)N.getParent()).removeView(N);
				((ViewManager)K.getParent()).removeView(K);
				((ViewManager)S.getParent()).removeView(S);
				a.startAnimation(mTranslateAnimation);
			}
		});
		// Translate
		mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_PARENT, -0.25f);
		mTranslateAnimation.setFillAfter(true);
		mTranslateAnimation.setDuration(750);
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
				transaction.replace(R.id.fragment_logo, new HomeFragment());
				transaction.commit();
				((ViewManager)a.getParent()).removeView(a);
			}
		});
		a.startAnimation(fadeInAnimation);

		return rootView;
	}
}
