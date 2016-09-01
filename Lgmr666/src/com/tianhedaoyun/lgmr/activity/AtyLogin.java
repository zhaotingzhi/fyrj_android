package com.tianhedaoyun.lgmr.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.bean.SettingData;
import com.tianhedaoyun.lgmr.util.AssetsUtil;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.SharedSettingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AtyLogin extends BaseActivity implements OnClickListener {
	
	private Spinner spinner;
	private ArrayAdapter adapter;
	private Button bt_logins;
	private EditText et_name, et_pwordword;
	private ImageView catch_pwd;
	private TextView tv_forget_pwd, tv_register;
	private boolean isHidden = true;
	private long exitTime = 0;// 退出时间
	SharedPreferences sharedPreferences;
	Editor editor;// 获取编辑器
	private TextView sample_models;
	private View weatherview, setttingView;
	private EditText temp, pre;
	private ToggleButton changeColor, sounds;
	private int temperature, pressure;
	private boolean changeTextColor, playSounds;
	private String m_szWLANMAC;
	private SimpleDateFormat df; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		init();
		sharedPreferences = getSharedPreferences("configure", Context.MODE_PRIVATE);

		editor = sharedPreferences.edit();// 获取编辑器
		String name = sharedPreferences.getString("name", "");
		et_name.setText(name);
		
		
		WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

		
		df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		
	}

	private void init() {
		spinner = (Spinner) findViewById(R.id.Spinner01);
		adapter = ArrayAdapter.createFromResource(this, R.array.plantes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		spinner.setVisibility(View.VISIBLE);
		bt_logins = (Button) findViewById(R.id.bt_logins);
		bt_logins.setOnClickListener(this);
		et_name = (EditText) findViewById(R.id.et_uname);
		et_pwordword = (EditText) findViewById(R.id.et_pwd);
		catch_pwd = (ImageView) findViewById(R.id.catch_pwd);
		catch_pwd.setOnClickListener(this);
		tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
		tv_forget_pwd.setOnClickListener(this);
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_register.setOnClickListener(this);
		// 模型样板
		sample_models = (TextView) findViewById(R.id.sample_models);
		sample_models.setOnClickListener(this);

		//提取assets
		AssetsUtil.copy(getApplicationContext(), "datas/NewData.sdb",getApplicationContext().getExternalFilesDir("").getAbsolutePath() , "newData.sdb");
		AssetsUtil.copy(getApplicationContext(), "datas/data1.sdb",getApplicationContext().getExternalFilesDir("").getAbsolutePath() , "data1.sdb");
		// 如果没有保存过settindata那么就先保存
		SettingData data = SharedSettingUtil.readObject(getApplicationContext(), "setting_data");
		if (data == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(this);
			weatherview = layoutInflater.inflate(R.layout.weather, null);
			temp = (EditText) weatherview.findViewById(R.id.temperature);
			pre = (EditText) weatherview.findViewById(R.id.pressure);

			setttingView = layoutInflater.inflate(R.layout.activity_setting, null);
			changeColor = (ToggleButton) setttingView.findViewById(R.id.changeTextColor);

			temperature = Integer.parseInt(temp.getText().toString());
			pressure = Integer.parseInt(pre.getText().toString());
			changeTextColor = changeColor.isChecked();
			data = new SettingData(temperature, pressure, changeTextColor, playSounds);
			SharedSettingUtil.saveObject(getApplicationContext(), "setting_data", data);
		}

	}

	class SpinnerXMLSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// spinner 事件 获取

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_logins:
			editor.putString("name", et_name.getText().toString());
			editor.commit();// 提交修改
			break;

		case R.id.tv_register:
			Intent register = new Intent(AtyLogin.this, AtyRegister.class);
			startActivity(register);
			break;

		case R.id.tv_forget_pwd:
			Intent pwd = new Intent(AtyLogin.this, RegisterPwd.class);
			startActivity(pwd);
			break;

		case R.id.sample_models:
			startActivity(new Intent(AtyLogin.this, SamplesMain.class));
//			try {
//				boolean flag=df.parse(Const.CONST_Time).before(new Date());
//				if(flag){
//					Toast.makeText(this, Const.CONST_Time_g, Toast.LENGTH_LONG).show();
//				}else{
//					
//					if(Const.CONST_iskey.equals(m_szWLANMAC)){
//						startActivity(new Intent(AtyLogin.this, SamplesMain.class));
//					}else{
//						Toast.makeText(this, Const.CONST_iskey_g, Toast.LENGTH_LONG).show();
//					}
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
			break;
		case R.id.catch_pwd:

			if (isHidden) {
				// 设置EditText文本为可见的
				et_pwordword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			} else {
				// 设置EditText文本为隐藏的
				et_pwordword.setTransformationMethod(PasswordTransformationMethod.getInstance());
			}
			isHidden = !isHidden;
			et_pwordword.postInvalidate();
			// 切换后将EditText光标置于末尾
			CharSequence charSequence = et_pwordword.getText();
			if (charSequence instanceof Spannable) {
				Spannable spanText = (Spannable) charSequence;
				Selection.setSelection(spanText, charSequence.length());
			}
			break;
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 按两次返回键退出应用程序
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 判断间隔时间 大于2秒就退出应用
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// 应用名
				String applicationName = getResources().getString(R.string.app_name);
				String msg = "再按一次返回键退出" + applicationName;
				// String msg1 = "再按一次返回键回到桌面";
				Toast.makeText(AtyLogin.this, msg, Toast.LENGTH_SHORT).show();
				// 计算两次返回键按下的时间差
				exitTime = System.currentTimeMillis();
			} else {
				// 关闭应用程序
				// finish();
				// 返回桌面操作
				Intent home = new Intent(Intent.ACTION_MAIN);
				home.addCategory(Intent.CATEGORY_HOME);
				startActivity(home);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String getMyUUID(){
		 
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(this.TELEPHONY_SERVICE);   
 
        final String tmDevice, tmSerial, tmPhone, androidId;   
 
        tmDevice = "" + tm.getDeviceId();  
 
        tmSerial = "" + tm.getSimSerialNumber();   
 
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);   
 
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());   
 
        String uniqueId = deviceUuid.toString();
 
        Log.d("debug","uuid="+uniqueId);
 
        return uniqueId;
 
       }
}
