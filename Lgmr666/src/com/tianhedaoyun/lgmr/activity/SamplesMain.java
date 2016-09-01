package com.tianhedaoyun.lgmr.activity;

import java.util.ArrayList;
import java.util.List;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.adapter.FragmentAdapter;
import com.tianhedaoyun.lgmr.fragment.CompanyDesc;
import com.tianhedaoyun.lgmr.fragment.SampleModels;
import com.tianhedaoyun.lgmr.util.PopMenu;
import com.tianhedaoyun.lgmr.util.PopMenu.OnItemClickListener;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SamplesMain extends FragmentActivity{

	private TextView company_desc, model_samples;
	private Resources res;

	private ViewPager mViewPager;
	private FragmentAdapter mAdapter;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private ImageView home;
	private PopMenu popMenu;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_samples);
		res = getResources();
		initView();

		mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());

	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.body);
		company_desc = (TextView) findViewById(R.id.company_desc);
		model_samples = (TextView) findViewById(R.id.model_samples);

		// 初始化弹出菜单
		home = (ImageView) findViewById(R.id.home);
		home.setOnClickListener(new MyonClickListener());
		popMenu = new PopMenu(this);
		popMenu.addItems(new String[] { "设置", "关于", "登录" });
		popMenu.setOnItemClickListener(new myOnItemClickListener());

		company_desc.setOnClickListener(new TabOnClickListener(0));
		model_samples.setOnClickListener(new TabOnClickListener(1));

		fragments.add(new CompanyDesc());
		fragments.add(new SampleModels());
	}

	private class MyonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			popMenu.showAsDropDown(v);
		}

	}

	private class myOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(int index) {
			if (index == 2) {
				Intent intent = new Intent(SamplesMain.this, AtyLogin.class);
				startActivity(intent);
			}
			
			if (index == 1) {
				Intent intent = new Intent(SamplesMain.this, About.class);
				startActivity(intent);
			}
			
			if (index == 0) {
				Intent intent1 = new Intent(SamplesMain.this, AtySetting.class);
				startActivity(intent1);

			}

		}

	}

	public class TabOnClickListener implements OnClickListener {
		private int index = 0;

		public TabOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mViewPager.setCurrentItem(index);
		}

	}

	public class TabOnPageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int state) {

		}

		public void onPageSelected(int position) {
			resetTextView();
			switch (position) {
			case 0:
				company_desc.setTextColor(res.getColor(R.color.blue));
				break;
			case 1:
				model_samples.setTextColor(res.getColor(R.color.blue));
				break;
			}
		}

		private void resetTextView() {
			company_desc.setTextColor(res.getColor(R.color.black));
			model_samples.setTextColor(res.getColor(R.color.black));

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

}
