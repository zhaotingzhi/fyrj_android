package com.tianhedaoyun.lgmr.fragment;

import java.lang.reflect.Method;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.bean.StationData;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.KeyboardUtil;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;

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

public class PrismHeight extends Fragment implements View.OnClickListener {
	private TextView table;
	private Button back, next;
	private EditText prism_height;
	String height, messzge;
	private Context ctx;
	private Activity act;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.prism_height, container, false);
		prism_height = (EditText) view.findViewById(R.id.prism_height);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);
		next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		table = (TextView) view.findViewById(R.id.table);

		ctx = this.getActivity();
		act = this.getActivity();
		if (android.os.Build.VERSION.SDK_INT <= 10) {// 4.0以下 danielinbiti
			prism_height.setInputType(InputType.TYPE_NULL);
		} else {
			this.act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(prism_height, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		prism_height.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(act, ctx, prism_height).showKeyboard();
				return false;
			}
		});
		messzge = getFragmentManager().findFragmentByTag("12345").getArguments().getString("table");
		table.setText(messzge);
		if(Const.prism_height!=0.0){
			prism_height.setText(Const.prism_height+"");
			prism_height.setSelection((Const.prism_height+"").length());
		}
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			getFragmentManager().popBackStack();
			break;

		case R.id.next:

			new KeyboardUtil(act, ctx, prism_height).hideKeyboard();
			height = prism_height.getText().toString();
			if (height.length() == 0) {
				Toast.makeText(getActivity(), "请输入棱镜高", Toast.LENGTH_SHORT).show();
				return;
			}
			Const.prism_height = Double.parseDouble(height);
			if (messzge.equals("后视设置")) {
				getFragmentManager().beginTransaction().replace(R.id.connect, new ControlPointSet())
						.addToBackStack(null).commit();
			} else {
				getFragmentManager().beginTransaction().replace(R.id.connect, new ResectionPoint1())
						.addToBackStack(null).commit();
			}
			break;

		case R.id.rear_view:

			break;

		}

	}

}
