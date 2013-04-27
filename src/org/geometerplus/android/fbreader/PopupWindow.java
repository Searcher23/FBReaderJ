/*
 * Copyright (C) 2009-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.android.fbreader;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.*;
import android.widget.*;

import org.geometerplus.zlibrary.ui.android.R;

public class PopupWindow extends LinearLayout {
	public static enum Location {
		BottomFlat,
		Bottom,
		Floating
	}

	private final Activity myActivity;
	private final boolean myAnimated;

	public PopupWindow(Activity activity, RelativeLayout root, Location location) {
		super(activity);
		myActivity = activity;

		setFocusable(false);

		final LayoutInflater inflater =
			(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final RelativeLayout.LayoutParams p;
		switch (location) {
			default:
			case BottomFlat:
				inflater.inflate(R.layout.control_panel_bottom_flat, this, true);
				setBackgroundColor(FBReader.ACTION_BAR_COLOR);
				p = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
				);
				myAnimated = true;
				break;
			case Bottom:
				inflater.inflate(R.layout.control_panel_bottom, this, true);
				p = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
				);
				myAnimated = false;
				break;
			case Floating:
				inflater.inflate(R.layout.control_panel_floating, this, true);
				p = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
				);
				myAnimated = false;
				break;
		}

		p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		p.addRule(RelativeLayout.CENTER_HORIZONTAL);
		root.addView(this, p);

		setVisibility(View.GONE);
	}

	Activity getActivity() {
		return myActivity;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	public void show() {
		myActivity.runOnUiThread(new Runnable() {
			public void run() {
				showInternal();
			}
		});
	}

	private void showInternal() {
		setVisibility(View.VISIBLE);
	}

	public void hide() {
		myActivity.runOnUiThread(new Runnable() {
			public void run() {
				hideInternal();
			}
		});
	}

	private void hideInternal() {
		setVisibility(View.GONE);
	}

	public void addView(View view) {
		((LinearLayout)findViewById(R.id.tools_plate)).addView(view);
	}
}
