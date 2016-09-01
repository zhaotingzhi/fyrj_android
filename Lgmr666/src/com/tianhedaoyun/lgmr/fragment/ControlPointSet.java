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

public class ControlPointSet extends Fragment implements View.OnClickListener {
	private Button back, next;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.control_point_set, container, false);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);
		next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(this);
		Const.isSetControlPoint = true;
		
		Const.CONST_PD = true;
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			Const.isSetControlPoint = false;
			getFragmentManager().popBackStack();
			break;

		case R.id.next:
			if (!LN100Util.getInstance().isConnected()) {
				Toast.makeText(getActivity(), "请正确设置WIFI", Toast.LENGTH_LONG).show();
				return;
			}
			if (!LN100FSM.s_Lock) {
				Toast.makeText(getActivity(), "请先设置仪器和棱镜锁定", Toast.LENGTH_LONG).show();
				return;
			}
			if (Const.point == null) {
				Toast.makeText(getActivity(), "请选择一个控制点", Toast.LENGTH_SHORT).show();
				return;
			}
			Const.controlPoint = Const.point;

			getFragmentManager().beginTransaction().replace(R.id.connect1, new BacksightPointSet()).addToBackStack(null).commit();
			
			break;

		case R.id.rear_view:

			break;

		}

	}

}
