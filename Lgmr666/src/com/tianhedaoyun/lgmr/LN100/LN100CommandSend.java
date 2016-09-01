package com.tianhedaoyun.lgmr.LN100;

import com.tianhedaoyun.lgmr.util.StringUtils;

import android.content.Context;

public class LN100CommandSend {

	/**
	 * 开始自动测量命令
	 * 
	 * @param n
	 *            output rare(0,1,2)
	 * @param n2
	 *            averrage number of times(0,1,2,3,4)默认为4
	 */
	public static void autoMeasurementStart(final int n, final int n2) {
		sendMessage("@MFILD," + n + "," + n2 + ",");
	}

	/**
	 * 停止测量命令
	 */
	public static void autoMeasurementStop() {
		sendMessage("@SFILD,");
	}

	
	/**
	 * 通信结束命令
	 */
	public static void communicationEndCommand() {
		sendMessage("@IWCCE,");
	}

	/**
	 * 通信开始命令
	 */
	public static void communicationStartCommand() {
		sendMessage("@IWCCS,");
	}

	/**
	 * 开始连接
	 */
	public static void connect(Context context) {
		LN100Util.getInstance().connect(context);
	}

	/**
	 * 导向灯关
	 */
	public static void guideLightOff() {
		sendMessage("@LGLOF,");
	}

	/**
	 * 导向灯开
	 * 
	 * @param n
	 *            模式（0，1）默认0
	 * @param n2
	 *            power （0~2）默认2
	 */
	public static void guideLightOn(final int n, final int n2) {
		sendMessage("@LGLON," + n + "," + n2 + ",");
	}

	/**
	 * 整平关
	 */
	public static void selfLevelOFF() {
		sendMessage("@LSLOF,");
	}

	/**
	 * 整平开
	 */
	public static void selfLevelOn() {
		sendMessage("@LSLON,");
	}

	public static void sendMessage(final String s) {
		final String encode = StringUtils.encode(s);
		final String hexChecksum = LN100Util.getInstance().hexChecksum(encode);
		final String string = String.valueOf(encode) + hexChecksum + StringUtils.HexCR;
		LN100Util.getInstance().sendMessageHandle(StringUtils.hexStr2Bytes(string));
	}

	/**
	 * 激光指示器关
	 */
	public static void sendlaserPointeroff() {
		sendMessage("@LLPOF,");
	}

	/**
	 * 激光指示器开
	 * @param n
	 * （0，1）默认0，常亮
	 * @param n2
	 * power 0~1,默认1
	 */
	public static void sendlaserPointeron(final int n, final int n2) {
		sendMessage("@LLPON," + n + "," + n2 + ",");
	}
	/**
	 * 输入大气数据
	 * @param n
	 * 温度（-30~60℃）默认15℃
	 * @param n2
	 * 压强（500~1400hpa)默认1013hpa
	 */
	public static void setAtmospheric(final int n, final int n2) {
		sendMessage("@IATMS," + n + "," + n2 + ",");
	}
	/**
	 * 目标类型设置（棱镜）
	 * @param n
	 * （0，1)默认1(360°）
	 * @param n2
	 * 棱镜直径1~300 默认34
	 * @param n3
	 * 常量-99.9~99.9 默认是-7
	 */
	public static void setTargetType(final int n, final int n2, final int n3) {
		sendMessage("@ITRGT," + n + "," + n2 + "," + n3 + ",");
	}

	/**
	 * 自动追踪
	 * @param n
	 * 0,1
	 */
	public static void startAutoTrack(final int n) {
		sendMessage("@RTRCK," + n + ",");
	}

	/**
	 * 电量
	 */
	public static void startBatteryLevelRepeat() {
		sendMessage("@MBATT,");
	}

	/**
	 * 旋转到指定的角度
	 * @param n
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 */
	public static void startRotateAngle(final int n, final int n2, final float n3, final int n4, final float n5) {
		sendMessage("@RSPOS," + n + "," + n2 + "," + n3 + "," + n4 + "," + n5 + ",");
	}

	/**
	 * 旋转速度
	 * @param n
	 * @param n2
	 */
	public static void startRotateVelocity(final int n, final int n2) {
		sendMessage("@RSSPD," + n + "," + n2 + ",");
	}

	/**
	 * ？？？？？倾斜角度的重复测量
	 * ？？？？？
	 * @param n
	 */
	public static void startTiltAngleRepeat(final int n) {
		sendMessage("@MTILT," + n + ",");
	}

	/**
	 * 停止电量
	 */
	public static void stopBatteryLevelRepeat() {
		sendMessage("@SBATT,");
	}

	/**
	 * 停止旋转
	 */
	public static void stopRotation() {
		sendMessage("@SMOTR,");
	}

	/**
	 * ？？？？？？？？？？停止倾斜角度的测量
	 */
	public static void stopTiltAngleRepeat() {
		sendMessage("@STILT,");
	}
}
