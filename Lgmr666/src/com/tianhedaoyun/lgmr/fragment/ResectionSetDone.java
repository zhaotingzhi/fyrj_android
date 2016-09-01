package com.tianhedaoyun.lgmr.fragment;

import com.gvitech.android.RenderControl;
import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.math.Arithmetic;
import com.tianhedaoyun.lgmr.math.PrecisionUtil;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.FileUtil;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ResectionSetDone extends Fragment implements OnClickListener {

	private TextView res_pointNum1, res_x1, res_y1, res_z1, res_pointNum2, res_x2, res_y2, res_z2, res_x3, res_y3,
			res_z3;
	private Button back, next;
	private double[] d3;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.resetion_set_done, container, false);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);
		next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		res_pointNum1 = (TextView) view.findViewById(R.id.res_pointNum1);
		res_x1 = (TextView) view.findViewById(R.id.res_x1);
		res_y1 = (TextView) view.findViewById(R.id.res_y1);
		res_z1 = (TextView) view.findViewById(R.id.res_z1);
		res_pointNum2 = (TextView) view.findViewById(R.id.res_pointNum2);
		res_x2 = (TextView) view.findViewById(R.id.res_x2);
		res_y2 = (TextView) view.findViewById(R.id.res_y2);
		res_z2 = (TextView) view.findViewById(R.id.res_z2);
		res_x3 = (TextView) view.findViewById(R.id.res_x3);
		res_y3 = (TextView) view.findViewById(R.id.res_y3);
		res_z3 = (TextView) view.findViewById(R.id.res_z3);

		d3 = Arithmetic.cal_p_coordinate(Const.res_ponit1.getX(), Const.res_ponit1.getY(), Const.res_ponit1.getZ(),
				Const.res_ponit2.getX(), Const.res_ponit2.getY(), Const.res_ponit2.getZ(), Const.resection_ha1,
				Const.resection_ha2, Const.resection_va1, Const.resection_va2, Const.resection_sl1, Const.resection_sl2,
				Const.robot_height, Const.prism_height);

		// 测试用
		String a = "后方交会第二点坐标：\n" + "X:" + Const.res_ponit2.getX() + "\n" + "Y:" + Const.res_ponit2.getY() + "\n" + "Z:"
				+ Const.res_ponit2.getZ() + "\n" + "第二点斜距：" + Const.resection_sl2 + "\n" + "第二点水平角："
				+ Const.resection_ha2 + "\n" + "第二点垂直角：" + Const.resection_va2;

		FileUtil.saveTxt(a, getActivity());

		Const.controlPoint.setX(d3[0]);
		Const.controlPoint.setY(d3[1]);
		Const.controlPoint.setZ(d3[2]);
		res_pointNum1.setText(Const.res_ponit1.getPointNumber());
		res_x1.setText(PrecisionUtil.decimal3(Const.res_ponit1.getX()));
		res_y1.setText(PrecisionUtil.decimal3(Const.res_ponit1.getY()));
		res_z1.setText(PrecisionUtil.decimal3(Const.res_ponit1.getZ()));
		res_pointNum2.setText(Const.res_ponit2.getPointNumber());
		res_x2.setText(PrecisionUtil.decimal3(Const.res_ponit2.getX()));
		res_y2.setText(PrecisionUtil.decimal3(Const.res_ponit2.getY()));
		res_z2.setText(PrecisionUtil.decimal3(Const.res_ponit2.getZ()));
		
		res_x3.setText(PrecisionUtil.decimal3(d3[0]));
		res_y3.setText(PrecisionUtil.decimal3(d3[1]));
		res_z3.setText(PrecisionUtil.decimal3(d3[2]));
		
	
			if (Const.CONST_modelLayer != null) {
				if (Const.CONST_rpoi1 != null) {
					RenderControl.get().objectManager.deleteObject(Const.CONST_rpoi1.objectId());
				}
				Const.CONST_rpoi1 = Const.CONST_modelLayer.createRenderModelPoint(2);
				Const.CONST_rpoi1.setX(d3[0]);
				Const.CONST_rpoi1.setY(d3[1]);
				Const.CONST_rpoi1.setZ(d3[2]);
			}

		return view;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back:
			getFragmentManager().popBackStack();
			break;

		case R.id.next:
			Const.stationData.setRobot_height(Const.robot_height);
			Const.stationData.setPrism_height(Const.prism_height);
			Const.stationData.setControl_point(Const.controlPoint);
			Const.stationData.setBacksight_point(Const.res_ponit2);
			Const.stationData.setHoushi_ha(Const.resection_ha2);
			Const.stationData.setHoushi_va(Const.resection_va2);
			SharedObjectUtil.saveObject(getActivity(), "station_info", Const.stationData);

			FragmentManager fragmentManager = getFragmentManager();
			for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
				fragmentManager.popBackStack();
			}
			fragmentManager.popBackStackImmediate(SetStation.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
			
			break;

		}

	}

}
