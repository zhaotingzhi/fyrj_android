package com.tianhedaoyun.lgmr.receiver;

import com.tianhedaoyun.lgmr.util.Const;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

/**
 * 广播接收器
 * 
 * @author len
 *
 */
public class MsgReceiver extends BroadcastReceiver {

	public MsgReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// 拿到wifi监听信息
		int wifi_status = intent.getIntExtra("wifi_status", Toast.LENGTH_SHORT);

		switch (wifi_status) {
		case 2:
			Const.app.getNhandler().sendEmptyMessage(1);
			break;
		case 3:
			Const.app.getNhandler().sendEmptyMessage(2);
			break;
		default:
			break;
		}

	}

}