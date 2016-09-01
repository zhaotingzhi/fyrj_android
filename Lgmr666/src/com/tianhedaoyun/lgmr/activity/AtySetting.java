package com.tianhedaoyun.lgmr.activity;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.bean.SettingData;
import com.tianhedaoyun.lgmr.util.SharedSettingUtil;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AtySetting extends BaseActivity implements OnClickListener {

	private TextView back, setting;
	private RelativeLayout weather, view, storage;
	private ImageView imageViewfont_color = null;
	private ImageView imageViewplay_sound = null;
	private ImageView imageViewrst_packet = null;
	protected boolean isBrewing = false; // 按钮置换

	private int temperature, pressure;

	private boolean changeTextColor, playSounds;
	private EditText temp, pre;
	private View weatherview;
	private ToggleButton changeColor;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		init();
	}

	private void init() {
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(this);
		setting = (TextView) findViewById(R.id.setting);
		setting.setOnClickListener(this);
		weather = (RelativeLayout) findViewById(R.id.weather);
		weather.setOnClickListener(this);

		LayoutInflater layoutInflater = LayoutInflater.from(this);
		weatherview = layoutInflater.inflate(R.layout.weather, null);
		temp = (EditText) weatherview.findViewById(R.id.temperature);
		pre = (EditText) weatherview.findViewById(R.id.pressure);
		changeColor = (ToggleButton) findViewById(R.id.changeTextColor);

		SettingData data = SharedSettingUtil.readObject(getApplicationContext(), "setting_data");
		if (data != null) {
			changeColor.setChecked(data.isChangeTextColor());
		}

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onClick(View v) {

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.setting:
			// 设置完成，保存数据到缓存
			SharedSettingUtil.delSettingData(getApplicationContext(), "setting_data");
			temperature = Integer.parseInt(temp.getText().toString());
			pressure = Integer.parseInt(pre.getText().toString());
			changeTextColor = changeColor.isChecked();

			SettingData data = new SettingData(temperature, pressure, changeTextColor, playSounds);
			SharedSettingUtil.saveObject(getApplicationContext(), "setting_data", data);
			finish();
			break;

		case R.id.weather:

			SettingData Settingdata = SharedSettingUtil.readObject(getApplicationContext(), "setting_data");
			if (Settingdata.getTemperature() != 0) {
				temp.setText(Settingdata.getTemperature() + "");
				pre.setText(Settingdata.getPressure() + "");
			}
			
			weatherview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.weather, null);
			
			// 创建一个AlertDialog对话框
			AlertDialog a = new AlertDialog.Builder(this).setTitle("天气设置").setView(weatherview) // 加载自定义的对话框式样
					.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							temperature = Integer.parseInt(temp.getText().toString());
							pressure = Integer.parseInt(temp.getText().toString());
							
							InputMethodManager imm = (InputMethodManager) getSystemService(
									Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(weatherview.getWindowToken(), 0);


						}
					}).setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							InputMethodManager imm = (InputMethodManager) getSystemService(
									Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(weatherview.getWindowToken(), 0);


						}
					}).create();
			a.setCancelable(false);
			a.show();
			break;


		}

	}

}
