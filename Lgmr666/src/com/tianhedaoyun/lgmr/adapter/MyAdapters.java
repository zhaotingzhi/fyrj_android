package com.tianhedaoyun.lgmr.adapter;

import java.util.List;
import java.util.Map;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.adapter.ListViewAdpter.ViewHolder;
import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.SqliteUtil;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 */
@SuppressLint("ResourceAsColor")
public class MyAdapters extends BaseAdapter {
	private SQLiteDatabase mydata = null;
	private String tablename;
	private Context context;
	private ImageButton btn;
	private RelativeLayout r_collect;
	private AlertDialog dialog ;
	private String oid ;
	private String PointRole ;
	private String type ;
	private String PointNumber ;
	private String x ;
	private String y ;
	private String z ;
	private String state ;
	private String comment;
	private int item_id;
	private View view;
	private boolean dian;
	private float downX; // 点下时候获取的x坐标
	private float upX; // 手指离开时候的x坐标
	private ImageButton btn_del; // 用于执行删除的button
	private Animation animation; // 删除时候的动画
	public MyAdapters(Context context) {
		animation = AnimationUtils.loadAnimation(context, R.anim.push_out); // 用xml获取一个动画
		this.context = context;
	}

	@Override
	public int getCount() {
		return Const.CONST_findlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return Const.CONST_findlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@SuppressWarnings("unused")
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item1,null);
			holder = new ViewHolder();
			holder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
			holder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
			holder.txt3 = (TextView) convertView.findViewById(R.id.tet3);
			holder.txt4 = (TextView) convertView.findViewById(R.id.tet4);
			holder.txt5 = (TextView) convertView.findViewById(R.id.tet5);
			holder.txtx = (TextView) convertView.findViewById(R.id.txtx);
			holder.txty = (TextView) convertView.findViewById(R.id.txty);
			holder.txtz = (TextView) convertView.findViewById(R.id.txtz);
			holder.txt6 = (TextView) convertView.findViewById(R.id.tet6);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnTouchListener(new OnTouchListener() { // 为每个item设置setOnTouchListener事件

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag(); // 获取滑动时候相应的ViewHolder，以便获取button按钮
				
				view = v; // 得到itemview，在上面加动画
				
				return false;
			}

		});
		
		
		
		
		// 设置holder
		Point point = (Point) Const.CONST_findlist.get(position);

		holder.txt1.setText(point.getOid() + "");
		holder.txt2.setText(point.getPointNumber());
		holder.txt3.setText(point.getPointRole());
		holder.txt4.setText(point.getType());
		holder.txtx.setText(point.getX() + "");
		holder.txty.setText(point.getY() + "");
		holder.txtz.setText(point.getZ() + "");
		holder.txt5.setText(point.getState());
		holder.txt6.setText(point.getComment());
		
		btn=(ImageButton) convertView.findViewById(R.id.btn_comment);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View allview = layoutInflater.inflate(R.layout.allview, null); 
				TextView o_id=(TextView) allview.findViewById(R.id.o_id);
				TextView number_id=(TextView) allview.findViewById(R.id.number_id);
				TextView fy_pointRole=(TextView) allview.findViewById(R.id.fy_pointRole);
				TextView type1=(TextView) allview.findViewById(R.id.type1);
				EditText et_save=(EditText) allview.findViewById(R.id.et_save);
				TextView fy_x=(TextView) allview.findViewById(R.id.fy_x);
				TextView fy_y=(TextView) allview.findViewById(R.id.fy_y);
				TextView fy_z=(TextView) allview.findViewById(R.id.fy_z);
				o_id.setText((Const.CONST_findlist.get(position)).getOid()+"");
				number_id.setText((Const.CONST_findlist.get(position)).getPointNumber());
				fy_pointRole.setText((Const.CONST_findlist.get(position)).getPointRole());
				type1.setText((Const.CONST_findlist.get(position)).getType());
				fy_x.setText((Const.CONST_findlist.get(position)).getX()+"");
				fy_y.setText((Const.CONST_findlist.get(position)).getY()+"");
				fy_z.setText((Const.CONST_findlist.get(position)).getZ()+"");
				if((Const.CONST_findlist.get(position)).getComment()==null){
					et_save.setText("");
				}else{
					et_save.setText((Const.CONST_findlist.get(position)).getComment());
				}
				
				dialog=new AlertDialog.Builder(context)
		        .setTitle("详细信息")
		        .setView(allview)               
		        .setPositiveButton("确定", null)
		        .create();
				 dialog.show();
				WindowManager.LayoutParams params = 
						dialog.getWindow().getAttributes();
				        params.width = 900;
				        params.height = 1200 ;
						dialog.getWindow().setAttributes(params);
			}
			});
		
		btn_del=(ImageButton) convertView.findViewById(R.id.del_comment);
		
	if (position == selectItem-1) {
			convertView.setBackgroundColor(R.color.my);
			btn.setVisibility(View.VISIBLE);
			if(dian){
				btn_del.setVisibility(View.VISIBLE);
				}
				btn_del.setOnClickListener(new OnClickListener() { // 为button绑定事件

					@Override
					public void onClick(View v) {
					deleteItem(view, position); // 删除数据，加动画
					SqliteUtil.delete_dian(mydata, tablename, Const.CONST_findlist.get(position).getPointNumber());
					System.out.println("mydata--"+mydata+"tablename--"+tablename+"----"+Const.CONST_findlist.get(position).getPointNumber());
					}
				});
		} else {
			convertView.setBackgroundColor(Color.TRANSPARENT);
			btn.setVisibility(View.GONE);
			btn_del.setVisibility(View.GONE);
		}

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
				Const.CONST_findlist.remove(position); // 把数据源里面相应数据删除
				notifyDataSetChanged();
			}
		});

	}
	
	class ViewHolder {
		TextView txt1, txt2, txt3, txt4, txt5, txtx, txty, txtz,txt6;
		ImageButton btn_del;
		ImageButton btn;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
		item_id=selectItem-1;
	}
	public void setdian(Boolean a,String tablename) {
		this.dian = a;
		this.tablename=tablename;
		System.out.println("dian--"+dian+"---tablename--"+tablename);
	}

	private int selectItem = -1;

}