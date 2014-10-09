package com.el1t.blanks;

import android.view.animation.Interpolator;
/**
 * Created by El1t on 10/9/14.
 */
public class EaseOutInterpolator implements Interpolator {
	public EaseOutInterpolator() {
	}

	/**
	 * Since Android < L does not support bezier-curve interpolators,
	 * this uses a piecewise function to approximate the y-value of
	 * the cubic bezier (.55,0,.1,1), through two 6th-degree
	 * polynomials calculated through linear regression.
	 * @param t
	 * @return approximated value
	 */
	public float getInterpolation(float t) {
		if (t < .4) {
			return (float)(-2500.9*Math.pow(t, 6) + 2554.6*Math.pow(t, 5) - 937.78*Math.pow(t, 4) +
							161.36*Math.pow(t, 3) - 11.039*Math.pow(t, 2) + 0.3347*t - 0.0004);
		}
		return (float)(-47.035*Math.pow(t, 6) + 211.76*Math.pow(t, 5) - 395.74*Math.pow(t, 4) +
						394.54*Math.pow(t, 3) - 223.24*Math.pow(t, 2) + 69.151*t - 8.4347);
	}
}