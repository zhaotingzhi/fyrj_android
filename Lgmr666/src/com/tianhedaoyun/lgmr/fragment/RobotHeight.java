package com.tianhedaoyun.lgmr.fragment;

import java.lang.reflect.Method;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.bean.StationData;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.KeyboardUtil;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RobotHeight extends Fragment implements View.OnClickListener {
	private Context ctx;
	private Activity act;
	private TextView table;
	private Button next, back;
	private EditText height;
	String instrument_height;
	String message;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.robot_height, container, false);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);
		next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		ctx = this.getActivity();
		act = this.getActivity();
		height = (EditText) view.findViewById(R.id.height);
		if (android.os.Build.VERSION.SDK_INT <= 10) {// 4.0以下 danielinbiti
			height.setInputType(InputType.TYPE_NULL);
		} else {
			this.act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(height, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		height.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(act, ctx, height).showKeyboard();
				return false;
			}
		});
		table = (TextView) view.findViewById(R.id.table);
		message = getFragmentManager().findFragmentByTag("12345").getArguments().getString("table");
		table.setText(message);
		if (Const.robot_height != 0.0) {
			height.setText(Const.robot_height + "");
		}
		return view;

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			getFragmentManager().popBackStack();
			break;

		case R.id.next:
			new KeyboardUtil(act, ctx, height).hideKeyboard();
			instrument_height = height.getText().toString();
			if (instrument_height.length() == 0) {
				Toast.makeText(getActivity(), "请输入仪器高", Toast.LENGTH_SHORT).show();
				return;
			}
			Const.robot_height = Double.parseDouble(instrument_height);
			if (message.equals("后视设置")) {
				getFragmentManager().beginTransaction().replace(R.id.connect2, new PrismHeight()).addToBackStack(null)
						.commit();
				break;

			} else {
				getFragmentManager().beginTransaction().replace(R.id.connect2, new PrismHeight()).addToBackStack(null)
						.commit();

				break;
			}
		}

	}

}
