package com.tianhedaoyun.lgmr.activity;

import com.tianhedaoyun.lgmr.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class About extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
		init();
	}

	private void init() {

	}

}
