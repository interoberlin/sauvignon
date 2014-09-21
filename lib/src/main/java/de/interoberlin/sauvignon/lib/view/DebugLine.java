package de.interoberlin.sauvignon.lib.view;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DebugLine extends LinearLayout
{
	public DebugLine(Context c)
	{
		super(c);
	}

	public DebugLine(Activity a, String label, String valueOne, String valueTwo)
	{
		super(a);

		TextView tvLabel = new TextView(a);
		TextView tvOne = new TextView(a);
		TextView tvTwo = new TextView(a);

		tvLabel.setText(label);
		tvOne.setText(valueOne);
		tvTwo.setText(valueTwo);

		this.addView(tvLabel, new LayoutParams(200, LayoutParams.WRAP_CONTENT));
		this.addView(tvOne, new LayoutParams(200, LayoutParams.WRAP_CONTENT));
		this.addView(tvTwo, new LayoutParams(200, LayoutParams.WRAP_CONTENT));
	}
}
