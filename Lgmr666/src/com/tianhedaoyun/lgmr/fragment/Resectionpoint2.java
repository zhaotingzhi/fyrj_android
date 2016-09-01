package com.tianhedaoyun.lgmr.fragment;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.LN100.LN100FSM;
import com.tianhedaoyun.lgmr.LN100.LN100Util;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.FileUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Resectionpoint2 extends Fragment implements View.OnClickListener {
	private Button back, next;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.resection_point2, container, false);
		back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(this);
		next = (Button) view.findViewById(R.id.next);
		next.setOnClickListener(this);

		// 测试用
		String a = "后方交会第一点坐标：\n" + "X:" + Const.res_ponit1.getX() + "\n" + "Y:" + Const.res_ponit1.getY() + "\n" + "Z:"
				+ Const.res_ponit1.getZ() + "\n" + "第一点斜距：" + Const.resection_sl1 + "\n" + "第一点水平角："
				+ Const.resection_ha1 + "\n" + "第一点垂直角：" + Const.resection_va1;

		FileUtil.saveTxt(a, getActivity());
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
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

			if (!LN100FSM.s_TrackMeasure) {
				Toast.makeText(getActivity(), "请先打开自动测距功能", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Const.point == Const.res_ponit1) {
				Toast.makeText(getActivity(), "请重新选择一个已知点", Toast.LENGTH_SHORT).show();
				return;
			}
			Const.res_ponit2 = Const.point;

			Const.res_isdone2 = true;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			getFragmentManager().beginTransaction().replace(R.id.resection_point2, new ResectionSetDone())
					.addToBackStack(null).commit();
			break;

		}

	}

}
