package com.tianhedaoyun.lgmr.LN100;

import com.tianhedaoyun.lgmr.common.MThread;
import com.tianhedaoyun.lgmr.util.Const;

import android.content.Intent;
import android.os.Message;
import android.util.Log;

public class LN100FSM {
	private static LN100FSM _instance;
	public static int _speed;
	private static MThread mThread;
	public static boolean s_AutoLevel;
	public static boolean s_Connect;
	public static boolean s_GuideLight;
	public static boolean s_LaserPoint;
	public static boolean s_Level;

	public static boolean s_Lock;
	public static boolean s_Track;
	public static boolean s_TrackMeasure;

	static {
		LN100FSM.s_Connect = false;
		LN100FSM.s_Level = false;
		LN100FSM.s_AutoLevel = false;
		LN100FSM.s_Track = false;
		LN100FSM.s_Lock = false;
		LN100FSM.s_GuideLight = false;
		LN100FSM.s_LaserPoint = false;
		LN100FSM.s_TrackMeasure = false;
		LN100FSM.mThread = null;
		LN100FSM._speed = 12;
	}

	public static LN100FSM getInstance() {
		if (LN100FSM._instance == null) {
			LN100FSM._instance = new LN100FSM();
		}
		return LN100FSM._instance;
	}

	// public void Push(final LN100Event ln100Event) {
	// switch (ln100Event.ordinal()) {
	// case 1: {
	// if (!LN100FSM.s_Connect) {
	// LN100FSM.s_Connect = true;
	// this.sendMessage(0);
	// this.arouseMThread();
	// return;
	// }
	// break;
	// }
	// case 2: {
	// if (LN100FSM.s_Connect) {
	// LN100FSM.s_Connect = false;
	// this.sendMessage(1);
	// } else {
	// this.sendMessage(1);
	// }
	// this.initState();
	// }
	// case 5: {
	// if (!LN100FSM.s_Level) {
	// LN100FSM.s_Level = true;
	// this.sendMessage(4);
	// return;
	// }
	// break;
	// }
	// case 6: {
	// if (LN100FSM.s_Level) {
	// LN100FSM.s_Level = false;
	// this.sendMessage(5);
	// return;
	// }
	// break;
	// }
	// case 3: {
	// if (!LN100FSM.s_AutoLevel) {
	// LN100FSM.s_AutoLevel = true;
	// this.sendMessage(4);
	// return;
	// }
	// break;
	// }
	// case 4: {
	// if (LN100FSM.s_AutoLevel) {
	// LN100FSM.s_AutoLevel = false;
	// this.sendMessage(5);
	// this.startAutoTrack();
	// return;
	// }
	// break;
	// }
	// case 7: {
	// if (!LN100FSM.s_Track) {
	// LN100FSM.s_Track = true;
	// this.sendMessage(6);
	// return;
	// }
	// break;
	// }
	// case 8: {
	// if (LN100FSM.s_Track) {
	// LN100FSM.s_Track = false;
	// this.sendMessage(7);
	// return;
	// }
	// break;
	// }
	// case 9: {
	// if (!LN100FSM.s_Lock) {
	// LN100FSM.s_Lock = true;
	// this.sendMessage(8);
	// return;
	// }
	// break;
	// }
	// case 10: {
	// if (LN100FSM.s_Lock) {
	// LN100FSM.s_Lock = false;
	// this.sendMessage(9);
	// return;
	// }
	// break;
	// }
	// case 11: {
	// if (!LN100FSM.s_GuideLight) {
	// LN100FSM.s_GuideLight = true;
	// this.sendMessage(10);
	// return;
	// }
	// break;
	// }
	// case 12: {
	// if (LN100FSM.s_GuideLight) {
	// LN100FSM.s_GuideLight = false;
	// this.sendMessage(11);
	// return;
	// }
	// break;
	// }
	// case 13: {
	// if (!LN100FSM.s_TrackMeasure) {
	// LN100FSM.s_TrackMeasure = true;
	// this.sendMessage(12);
	// return;
	// }
	// break;
	// }
	// case 14: {
	// if (LN100FSM.s_TrackMeasure) {
	// LN100FSM.s_TrackMeasure = false;
	// this.sendMessage(13);
	// return;
	// }
	// break;
	// }
	// case 15: {
	// if (!LN100FSM.s_LaserPoint) {
	// LN100FSM.s_LaserPoint = true;
	// this.sendMessage(30);
	// return;
	// }
	// break;
	// }
	// case 16: {
	// if (LN100FSM.s_LaserPoint) {
	// LN100FSM.s_LaserPoint = false;
	// this.sendMessage(31);
	// return;
	// }
	// break;
	// }
	// }
	// }

	public void Push(final LN100Event ln100Event) {
		switch (ln100Event.ordinal()) {

		case 0:
			if (!LN100FSM.s_Lock) {
				LN100FSM.s_Lock = true;
				this.sendMessage(1);
				return;
			}
			break;

		case 1:
			if (LN100FSM.s_Lock) {
				LN100FSM.s_Lock = false;
				this.sendMessage(2);
				return;
			}
			break;
		case 2:
			if (!LN100FSM.s_Track) {
				LN100FSM.s_Track = true;
				this.sendMessage(3);
				return;
			}
			break;
		case 3:
			if (LN100FSM.s_Track) {
				LN100FSM.s_Track = false;
				this.sendMessage(4);
				return;
			}
			break;
		case 4:
			if (!LN100FSM.s_TrackMeasure) {
				LN100FSM.s_TrackMeasure = true;
				this.sendMessage(5);
				return;
			}
			break;
		case 5:
			if (LN100FSM.s_TrackMeasure) {
				LN100FSM.s_TrackMeasure = false;
				this.sendMessage(6);
				return;
			}
			break;
		}
	}

	public void arouseMThread() {
		if (LN100FSM.mThread != null) {
			synchronized (LN100FSM.mThread) {
				LN100FSM.mThread.notify();
			}
		}
	}

	public void autoMeasurementStart() {
		LN100CommandSend.autoMeasurementStart(0, 2);
	}

	public void autoMeasurementStop() {
		LN100CommandSend.autoMeasurementStop();
	}

	public void batterLevelData(final int n) {
		Log.i("ln100batterLevelDatadata", "batterlevel :" + n);
		this.sendMessage(19, n);
	}

	public void closeConnect() {
		this.stopRotate();
		this.sendlaserPointeroff();
		this.guideLightOff();
		this.stopBatteryLevelRepeat();
		this.stopAutoTrack();
		this.autoMeasurementStop();
		this.stopMThread();
	}

	public void connect() {
		this.stopMThread();
		(LN100FSM.mThread = new MThread() {
			@Override
			public void doRun() {
				int n = 0;
				while (true) {
					while (true) {
						if (LN100FSM.s_Connect) {
							while (true) {
								try {
									while (true) {
										synchronized (this) {
											this.wait(500L);
											if (LN100FSM.s_Connect) {
												LN100FSM.this.stopRotate();
												LN100FSM.this.sendlaserPointeroff();
												LN100FSM.this.autoMeasurementStop();
												LN100FSM.this.guideLightOn();
												LN100FSM.this.startBatteryLevelRepeat();
												LN100FSM.this.selfLevelOn();
												return;
											}
											break;
										}
									}
								} catch (InterruptedException ex2) {
									ex2.printStackTrace();
								}
								break;
							}
							break;
						}
						continue;
					}
				}
			}
		}).start();
	}

	public void guideLightOff() {
		LN100CommandSend.guideLightOff();
	}

	public void guideLightOn() {
		LN100CommandSend.guideLightOn(0, 2);
	}

	public void initState() {
		LN100FSM.s_Connect = false;
		LN100FSM.s_Level = false;
		LN100FSM.s_AutoLevel = false;
		LN100FSM.s_Track = false;
		LN100FSM.s_Lock = false;
		LN100FSM.s_GuideLight = false;
		LN100FSM.s_LaserPoint = false;
		LN100FSM.s_TrackMeasure = false;
	}

	public void selfLevelOFF() {
		LN100CommandSend.selfLevelOFF();
	}

	public void selfLevelOn() {
		LN100CommandSend.selfLevelOn();
	}

	public void sendMessage(final int what) {
		final Message message = new Message();
		message.what = what;
		Const.app.getHandler().sendMessage(message);
	}

	public void sendMessage(final int what, final Object obj) {
		final Message message = new Message();
		message.what = what;
		message.obj = obj;
		Const.app.getHandler().sendMessage(message);
	}

	public void sendlaserPointeroff() {
		LN100CommandSend.sendlaserPointeroff();
	}

	public void sendlaserPointeron() {
		LN100CommandSend.stopRotation();
		LN100CommandSend.sendlaserPointeron(0, 1);
	}

	public void setAtmospheric(final int n, final int n2) {
		LN100CommandSend.setAtmospheric(n, n2);
	}

	public void setTargetType(final int n, final int n2) {
		LN100CommandSend.setTargetType(n, 34, n2);
	}

	public void startAutoTrack() {
		LN100CommandSend.stopRotation();
		LN100CommandSend.startAutoTrack(0);
		this.autoMeasurementStart();
	}

	public void startBatteryLevelRepeat() {
		LN100CommandSend.startBatteryLevelRepeat();
	}

	public void startRotateAngle(final int n, final int n2, final float n3, final int n4, final float n5) {
		LN100CommandSend.stopRotation();
		LN100CommandSend.startRotateAngle(n, n2, n3, n4, n5);
	}

	public void startRotateDown(final int n) {
		LN100CommandSend.stopRotation();
		LN100CommandSend.startRotateVelocity(n, 0);
	}

	public void startRotateLeft(final int n) {
		LN100CommandSend.stopRotation();
		LN100CommandSend.startRotateVelocity(0, -n);
	}

	public void startRotateRight(final int n) {
		LN100CommandSend.stopRotation();
		LN100CommandSend.startRotateVelocity(0, n);
	}

	public void startRotateUP(final int speed) {
		LN100FSM._speed = speed;
		LN100CommandSend.stopRotation();
		LN100CommandSend.startRotateVelocity(-speed, 0);
	}

	public void stopAutoTrack() {
		LN100CommandSend.stopRotation();
	}

	public void stopBatteryLevelRepeat() {
		LN100CommandSend.stopBatteryLevelRepeat();
	}

	public void stopMThread() {
		if (LN100FSM.mThread != null) {
			MThread.interrupted();
			LN100FSM.mThread = null;
		}
	}

	public void stopRotate() {
		LN100CommandSend.stopRotation();
	}

	public void trackMeasureData(final double n, final double n2, final double n3) {
		final Intent intent = new Intent();
		intent.putExtra("SlopeDistance", n);
		intent.putExtra("VerticalAngle", n2);
		intent.putExtra("HorizontalAngle", n3);
		this.sendMessage(34, intent);
	}

	public enum LN100Event {
		Lock("Lock", 0), UnLock("UnLock", 1), Track("Track", 2), UnTrack("UnTrack", 3),TrackMeasure("TrackMeasure", 4),UnTrackMeasure("UnTrackMeasure", 5);

		// public enum LN100Event {
		// Connect("Connect", 0),UnConnect("UnConnect",
		// 1),AutoLevel("AutoLevel", 2),UnAutoLevel("UnAutoLevel", 3),
		// Level("Level", 4),UnLevel("UnLevel", 5), Track("Track",
		// 6),UnTrack("UnTrack", 7),Lock("Lock", 8), UnLock("UnLock",
		// 9),GuideLight("GuideLight", 10),UnGuideLight("UnGuideLight",
		// 11),TrackMeasure("TrackMeasure",
		// 12),UnTrackMeasure("UnTrackMeasure", 13),LaserPoint("LaserPoint",
		// 14),UnLaserPoint("UnLaserPoint", 15);

		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private LN100Event(String name, int index) {
			this.name = name;
			this.index = index;
		}

	}
}
