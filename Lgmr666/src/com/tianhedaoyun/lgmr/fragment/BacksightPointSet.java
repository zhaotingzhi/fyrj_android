package com.tianhedaoyun.lgmr.fragment;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.LN100.LN100FSM;
import com.tianhedaoyun.lgmr.LN100.LN100Util;
import com.tianhedaoyun.lgmr.util.Const;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class BacksightPointSet extends Fragment implements View.OnClickListener {
	private Button back1, next1;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.backsight_point_set, container, false);
		back1 = (Button) view.findViewById(R.id.back1);
		back1.setOnClickListener(this);
		next1 = (Button) view.findViewById(R.id.next1);
		next1.setOnClickListener(this);
		Const.CONST_PD = false;
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back1:
			getFragmentManager().popBackStack();
			break;
		case R.id.next1:
			if (!LN100Util.getInstance().isConnected()) {
				Toast.makeText(getActivity(), "请正确设置WIFI", Toast.LENGTH_LONG).show();
				return;
			}
			if (!LN100FSM.s_Lock) {
				Toast.makeText(getActivity(), "请先设置仪器和棱镜锁定", Toast.LENGTH_LONG).show();
				return;
			}
			if (!LN100FSM.s_TrackMeasure) {
				Toast.makeText(getActivity(), "请先打开自动测距功能", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Const.point == Const.controlPoint) {
				Toast.makeText(getActivity(), "控制点和后视点不能为同一个点，请重新选择", Toast.LENGTH_SHORT).show();
				return;
			}

			Const.backsightPoint = Const.point;

			Const.isbacksightdone = true;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getFragmentManager().beginTransaction().replace(R.id.connect_done, new RearViewDone()).addToBackStack(null)
					.commit();

			break;

		}

	}

}
