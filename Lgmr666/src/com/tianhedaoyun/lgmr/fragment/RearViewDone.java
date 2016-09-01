package com.tianhedaoyun.lgmr.fragment;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.math.Arithmetic;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.FileUtil;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;
import com.tianhedaoyun.lgmr.util.StringUtils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RearViewDone extends Fragment implements View.OnClickListener {
	private Button back2, done2;
	private TextView tv_model_distance, tv_shot_distance, tv_abs;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.rear_view_done, container, false);
		back2 = (Button) view.findViewById(R.id.back2);
		back2.setOnClickListener(this);
		done2 = (Button) view.findViewById(R.id.next2);
		done2.setOnClickListener(this);

		tv_model_distance = (TextView) view.findViewById(R.id.model_distance);
		tv_shot_distance = (TextView) view.findViewById(R.id.shot_distance);
		tv_abs = (TextView) view.findViewById(R.id.abs);
		// 测试用
		String a = "后视点坐标：\n" + "X:" + Const.backsightPoint.getX() + "\n" + "Y:" + Const.backsightPoint.getY() + "\n"
				+ "Z:" + Const.backsightPoint.getZ() + "\n" + "后视点斜距：" + Const.backsight_sl + "\n" + "后视点水平角："
				+ Const.backsight_ha + "\n" + "后视点垂直角：" + Const.backsight_va;

		FileUtil.saveTxt(a, getActivity());
		// 模型中两点的距离
		double model_distance = StringUtils.getDouble(Arithmetic.point_distance(Const.controlPoint.getX(),
				Const.controlPoint.getY(), Const.controlPoint.getZ(), Const.backsightPoint.getX(),
				Const.backsightPoint.getY(), Const.backsightPoint.getZ()), "#.000");
		// 两点的实际距离
		double shot_distance = StringUtils.getDouble(
				Const.backsight_sl * Math.cos(Math.toRadians(Math.abs(90.0 - Const.backsight_va * 360))), "#.000");

		// 距离差
		double ads = StringUtils.getDouble(Math.abs(model_distance - shot_distance), "#.000");

		tv_model_distance.setText(model_distance + "");
		tv_shot_distance.setText(shot_distance + "");
		tv_abs.setText(ads + "");

		return view;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.back2:
			getFragmentManager().popBackStack();
			break;

		case R.id.next2:
			Const.stationData.setRobot_height(Const.robot_height);
			Const.stationData.setPrism_height(Const.prism_height);
			Const.stationData.setControl_point(Const.controlPoint);
			Const.stationData.setBacksight_point(Const.backsightPoint);
			Const.stationData.setHoushi_ha(Const.backsight_ha);
			Const.stationData.setHoushi_va(Const.backsight_va);
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
