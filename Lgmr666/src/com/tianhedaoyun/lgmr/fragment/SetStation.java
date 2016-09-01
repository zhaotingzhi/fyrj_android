package com.tianhedaoyun.lgmr.fragment;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SetStation extends Fragment implements View.OnClickListener {
	private RelativeLayout robot_set;
	private Button r_connect_close;
	private Button rear_rendezvous, rear_view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.station_set, container, false);
		r_connect_close = (Button) view.findViewById(R.id.r_connect_close);
		r_connect_close.setOnClickListener(this);
		rear_rendezvous = (Button) view.findViewById(R.id.rear_rendezvous);
		rear_rendezvous.setOnClickListener(this);
		rear_view = (Button) view.findViewById(R.id.rear_view);
		rear_view.setOnClickListener(this);
		robot_set = (RelativeLayout) view.findViewById(R.id.robot_set);
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.r_connect_close:
			robot_set.setVisibility(View.GONE);
			break;

		case R.id.rear_view:
			SharedObjectUtil.delStationData(getActivity(), "station_info");
			Bundle bundle = new Bundle();
			bundle.putString("table", (String) rear_view.getText() + "设置");
			RobotHeight connect = new RobotHeight();
			connect.setArguments(bundle);
			getFragmentManager().beginTransaction().add(connect, "12345").replace(R.id.robot_set, new RobotHeight())
					.addToBackStack(null).commit();
			break;

		case R.id.rear_rendezvous:
			SharedObjectUtil.delStationData(getActivity(), "station_info");
			Bundle bundle1 = new Bundle();
			bundle1.putString("table", (String) rear_rendezvous.getText() + "设置");
			RobotHeight connect1 = new RobotHeight();
			connect1.setArguments(bundle1);
			getFragmentManager().beginTransaction().add(connect1, "12345").replace(R.id.robot_set, new RobotHeight())
					.addToBackStack(null).commit();
			break;

		}

	}

}
