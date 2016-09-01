package com.tianhedaoyun.lgmr.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gvitech.android.IFeatureLayer;
import com.gvitech.android.RenderControl;
import com.tianhedaoyun.lgmr.adapter.MyAdapters;
import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.math.PrecisionUtil;
import com.tianhedaoyun.lgmr.model.util.ModelUtil;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;
import com.tianhedaoyun.lgmr.util.SqliteUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AnotherTask extends AsyncTask<String, Void, List<Point>> {
	private AlertDialog dialog ;
	private Context context;
	private String path;
	private String cachePath;
	private String tablename;
	private ArrayList<Point> list;
	private SQLiteDatabase mydata = null;
	private ListView lv;
	private TextView point_x, point_y, point_z, points;
	private ProgressDialog progressDialog;

	public AnotherTask(Context context, String path, String cachePath, String tablename, ListView lv, TextView point_x,
			TextView point_y, TextView point_z, TextView points, ProgressDialog progressDialog) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.path = path;
		this.cachePath = cachePath;
		this.lv = lv;
		this.point_x = point_x;
		this.point_y = point_y;
		this.point_z = point_z;
		this.tablename = tablename;
		this.points = points;
		this.progressDialog=progressDialog;
	}

	public static List<Point> findlist() {

		return Const.CONST_findlist;

	}

	@Override
	protected void onPostExecute(final List<Point> result) {

		if (SqliteUtil.find(mydata, tablename).size() > 0) {
			List<Map<String, Object>> list_dian = SqliteUtil.find_dian(mydata, tablename, "'已放样点'");
			if (list_dian.size() != 0) {
				for (int f = 0; f < list_dian.size(); f++) {
					if (Const.CONST_modelLayer != null) {
						Const.CONST_rpoi3 = Const.CONST_modelLayer.createRenderModelPoint(3);
						String a = Const.CONST_rpoi3.getFdeGeometry();
						Const.CONST_rpoi3.setX(Double.parseDouble(list_dian.get(f).get("x").toString()));
						Const.CONST_rpoi3.setY(Double.parseDouble(list_dian.get(f).get("y").toString()));
						Const.CONST_rpoi3.setZ(Double.parseDouble(list_dian.get(f).get("z").toString()));
						System.out.println(Double.parseDouble(list_dian.get(f).get("x").toString()) + "加载成功！");
					}
				}
			}

		}
		
		ModelUtil.setRed();
		progressDialog.dismiss();
		
		

		// 对UI组件的更新操作
		Const.CONST_madapter = new MyAdapters(context);
		points.setText("共" + Const.CONST_findlist.size() + "" + "条信息");
		lv.setAdapter(Const.CONST_madapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
				System.out.println("POSITIPN:" + position);
				Const.CONST_madapter.setSelectItem(position);
				Const.CONST_madapter.notifyDataSetInvalidated();
				lv.smoothScrollToPositionFromTop(position, 0);
				Const.point = result.get(position - 1);
				
				point_x.setText(PrecisionUtil.decimal3(Const.point.getX()));
				point_y.setText(PrecisionUtil.decimal3(Const.point.getY()));
				point_z.setText(PrecisionUtil.decimal3(Const.point.getZ()));
				
				if (Const.CONST_PD) {
					if (Const.CONST_modelLayer != null) {
						if (Const.CONST_rpoi1 != null) {
							RenderControl.get().objectManager.deleteObject(Const.CONST_rpoi1.objectId());
						}
						Const.CONST_rpoi1 = Const.CONST_modelLayer.createRenderModelPoint(2);
						Const.CONST_rpoi1.setX(Const.point.getX());
						Const.CONST_rpoi1.setY(Const.point.getY());
						Const.CONST_rpoi1.setZ(Const.point.getZ());
					}
				}

				ModelUtil.btnCreateLabel(Const.point.getX(), Const.point.getY(), Const.point.getZ(),
						Const.point.getPointNumber());
				ModelUtil.flyTo(Const.point.getX(), Const.point.getY(), Const.point.getZ(), 3.0, 0.0, -45.0, 0.0);

				ModelUtil.setItemRed();
				
              //判断是否是放样点
				if (Const.point.getState() == null) {
					Const.CONST_setdian = false;
				} else {
					Const.CONST_setdian = true;
				}
				Const.CONST_madapter.setdian(Const.CONST_setdian, tablename);
			

				if (SharedObjectUtil.readObject(context, "station_info") != null) {
					Const.lofting_point = Const.point;
				}
				

			}
		});
	}

	@Override
	protected List<Point> doInBackground(String... params) {
		
		ModelUtil.lvModel(path, cachePath);
		// 耗时的操作
		if (SqliteUtil.exits(tablename) == false) {
			SqliteUtil.createTable(tablename);
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (SqliteUtil.find(mydata, tablename).size() == 0) {
			ModelUtil.point(path, tablename, mydata);
		}
		

		Const.CONST_findlist = SqliteUtil.find(mydata, tablename);
		return Const.CONST_findlist;
	}

}
