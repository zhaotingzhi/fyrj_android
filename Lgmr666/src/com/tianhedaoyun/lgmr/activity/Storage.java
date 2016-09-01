package com.tianhedaoyun.lgmr.activity;

import com.tianhedaoyun.lgmr.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Storage extends BaseActivity implements OnClickListener  {
	
	    private TextView back,setting;
	    private Spinner spinner;
	    private ArrayAdapter adapter;
	    private ImageView imageViewembedded;
	    protected boolean isBrewing = false; // 按钮置换 
	    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.storage);
        init();
	}
	
	 private void init() {
		    back=(TextView) findViewById(R.id.back);
		    back.setOnClickListener(this);
		    setting=(TextView) findViewById(R.id.setting);
		    setting.setOnClickListener(this);
		    spinner = (Spinner) findViewById(R.id.spinnerstorage);
	        adapter = ArrayAdapter.createFromResource(this, R.array.storage, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);
	        spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
	        spinner.setVisibility(View.VISIBLE);  
	        imageViewembedded=(ImageView) findViewById(R.id.imageViewembedded);
	        imageViewembedded.setOnClickListener(this);
			
		}
	 
	 
	 class SpinnerXMLSelectedListener implements OnItemSelectedListener{
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	        	//spinner 事件  获取
	           
	        }
	 
	        public void onNothingSelected(AdapterView<?> arg0) {
	             
	        }
	         
	    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		case R.id.setting:
			Toast.makeText(Storage.this, "功能正在完善中！", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.imageViewembedded:
			 if(isBrewing)  
				 stopView(imageViewembedded);  
	            else  
	             startView(imageViewembedded);  
			
			break;

		default:
			break;
		}
		
	}
	
	public void startView(ImageView view){  
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.font_color_close);//打开资源图片     
		view.setImageBitmap(bmp);   
		isBrewing = true;  
		}  
	
	public void stopView(ImageView view){  
		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.font_color_open);     
		view.setImageBitmap(bmp);   
		isBrewing = false;  
		}

}
