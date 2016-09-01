package com.tianhedaoyun.lgmr.adapter;

import java.util.List;
import java.util.Map;

import com.gvitech.android.IEulerAngle;
import com.gvitech.android.IVector3;
import com.gvitech.android.RenderControl;
import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.util.SqliteUtil;
import com.gvitech.android.EnumValue.gviSetCameraFlags;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdpter extends BaseAdapter {
	private List<Map<String, Object>> data;
	private Context context;
	private float downX; // 点下时候获取的x坐标
	private float upX; // 手指离开时候的x坐标
	private Button button; // 用于执行删除的button
	private Button butgo; // 用于执行视角的button
	private Animation animation; // 删除时候的动画
	private View view;
	int item_id;
	String name ;
	SQLiteDatabase mydata = null;
	String table;

	public ListViewAdpter(List<Map<String, Object>> data,String table ,Context context) {
		this.data = data;
		this.context = context;
		this.table=table;
		animation = AnimationUtils.loadAnimation(context, R.anim.push_out); // 用xml获取一个动画
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item,
					null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.text);
			holder.button = (Button) convertView.findViewById(R.id.bt);
			holder.butgo = (Button) convertView.findViewById(R.id.btgo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnTouchListener(new OnTouchListener() { // 为每个item设置setOnTouchListener事件

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						final ViewHolder holder = (ViewHolder) v.getTag(); // 获取滑动时候相应的ViewHolder，以便获取button按钮
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN: // 手指按下
							downX = event.getX(); // 获取手指x坐标
							if (button != null) {
								button.setVisibility(View.GONE); // 影藏显示出来的button
								butgo.setVisibility(View.GONE); 
							}
							break;
						case MotionEvent.ACTION_UP: // 手指离开
							upX = event.getX(); // 获取x坐标值
							break;
						}

						if (holder.button != null) {
							if (Math.abs(downX - upX) > 35) { // 2次坐标的绝对值如果大于35，就认为是左右滑动
								holder.button.setVisibility(View.VISIBLE); // 显示删除button
								holder.butgo.setVisibility(View.VISIBLE); // 显示删除button
								button = holder.button; // 赋值给全局button，一会儿用
								butgo = holder.butgo;
								view = v; // 得到itemview，在上面加动画
								return true; // 终止事件
							}
							return false; // 释放事件，使onitemClick可以执行
						}
						return false;
					}

				});

		holder.button.setOnClickListener(new OnClickListener() { // 为button绑定事件

					@Override
					public void onClick(View v) {

						if (button != null) {
							button.setVisibility(View.GONE); // 点击删除按钮后，影藏按钮
							butgo.setVisibility(View.GONE);
							deleteItem(view, position); // 删除数据，加动画
							SqliteUtil.delete(mydata, table, data.get(position).get("name").toString());
						}

					}
				});
		holder.butgo.setOnClickListener(new OnClickListener() { // 为button绑定事件

			@Override
			public void onClick(View v) {

				if (butgo != null) {
					butgo.setVisibility(View.GONE); // 点击视图按钮后，影藏按钮
					IVector3 v1 = new IVector3();
					v1.set(Double.parseDouble(data.get(position).get("x").toString()), 
							Double.parseDouble(data.get(position).get("y").toString()), 
							Double.parseDouble(data.get(position).get("z").toString()));
					IEulerAngle e = new IEulerAngle();
					  
					e.set(Double.parseDouble(data.get(position).get("heading").toString()), 
							Double.parseDouble(data.get(position).get("tilt").toString()), 
							Double.parseDouble(data.get(position).get("roll").toString()));
					RenderControl.get().camera.setCamera(v1, e, gviSetCameraFlags.gviSetCameraNoFlags);
				}
			}
		});
		
		
		Map map = (Map) data.get(position);
		name = (String) map.get("name");
		holder.textView.setText(name); // 显示数据
		return convertView;
	}

	public void deleteItem(View view, final int position) {
		view.startAnimation(animation); // 给view设置动画
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) { // 动画执行完毕
				data.remove(position); // 把数据源里面相应数据删除
				notifyDataSetChanged();

			}
		});

	}

	static class ViewHolder {
		TextView textView; // 显示数据的view
		Button button;// 删除按钮
		Button butgo;//视角
	}
	
	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
		item_id=selectItem-1;
	}
	private int selectItem = -1;

}
