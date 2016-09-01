package com.tianhedaoyun.lgmr.LN100;

import com.tianhedaoyun.lgmr.math.GeoMath;
import com.tianhedaoyun.lgmr.util.StringUtils;

import android.util.Log;

public class LN100CommandReturn {
	public static final String ACK;
	public static final String NAK;

	static {
		ACK = StringUtils.hexStr2Str("06");
		NAK = StringUtils.hexStr2Str("15");
	}

	public static boolean checkChecksum(final String s) {
		if (s.length() < 3) {
			return true;
		}
		final String encode = StringUtils.encode(s.substring(0, -3 + s.length()));
		final String substring = s.substring(-3 + s.length(), -1 + s.length());
		return StringUtils.checkChecksum(encode, substring);
	}

	public static void messageReturn(final byte[] array) {
		final String bytes2HexString = StringUtils.Bytes2HexString(array);
		final String hexStr2Str = StringUtils.hexStr2Str(bytes2HexString);
		if (checkChecksum(hexStr2Str)) {
			final String[] split = hexStr2Str.split(",");
			if (split.length >= 2) {
				if (split[0].equals("@IWCCS")) {
					returnCommunicationStartCommand(split);
					return;
				}
				if (split[0].equals("@IWCCE")) {
					returnCommunicationEndCommand(split);
					return;
				}
				if (split[0].equals("@MFILD")) {
					returnAutoMeasurementStart(split);
					return;
				}
				if (split[0].equals("@SFILD")) {
					returnAutoMeasurementStop(split);
					return;
				}
				if (split[0].equals("@MTILT")) {
					returnStartTiltAngleRepeat(split);
					return;
				}
				if (split[0].equals("@STILT")) {
					returnStopTiltAngleRepeat(split);
					return;
				}
				if (split[0].equals("@MBATT")) {
					returnStartBatteryLevelRepeat(split);
					return;
				}
				if (split[0].equals("@SBATT")) {
					returnStopBatteryLevelRepeat(split);
					return;
				}
				if (split[0].equals("@LSLON")) {
					returnSelfLevelOn(split);
					return;
				}
				if (split[0].equals("@LSLOF")) {
					returnSelfLevelOFF(split);
					return;
				}
				if (split[0].equals("@LGLON")) {
					returnGuideLightOn(split);
					return;
				}
				if (split[0].equals("@LGLOF")) {
					returnGuideLightOff(split);
					return;
				}
				if (split[0].equals("@LLPON")) {
					returnSendlaserPointeron(split);
					return;
				}
				if (split[0].equals("@LLPOF")) {
					returnSendlaserPointeroff(split);
					return;
				}
				if (split[0].equals("@RSPOS")) {
					returnStartRotateAngle(split);
					return;
				}
				if (split[0].equals("@RSSPD")) {
					returnStartRotateVelocity(split);
					return;
				}
				if (split[0].equals("@SMOTR")) {
					returnStopRotate(split);
					return;
				}
				if (split[0].equals("@RTRCK")) {
					returnStartAutoTrack(split);
					return;
				}
				if (split[0].equals("@IATMS")) {
					returnSetAtmospheric(split);
					return;
				}
				if (split[0].equals("@ITRGT")) {
					returnSetTargetType(split);
				}
			}
		}
	}

	public static void returnAutoMeasurementStart(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			LN100FSM.getInstance().Push(LN100FSM.LN100Event.TrackMeasure);
		} else if (!array[1].equals(LN100CommandReturn.NAK) && array.length >= 12) {
			final double checkStrToDouble = GeoMath.checkStrToDouble(array[1]);
			final double checkStrToDouble2 = GeoMath.checkStrToDouble(array[2]);
			final double checkStrToDouble3 = GeoMath.checkStrToDouble(array[3]);
			final int checkStrToInt = GeoMath.checkStrToInt(array[4]);
			final int checkStrToInt2 = GeoMath.checkStrToInt(array[5]);
			final int checkStrToInt3 = GeoMath.checkStrToInt(array[6]);
			final int checkStrToInt4 = GeoMath.checkStrToInt(array[7]);
			final int checkStrToInt5 = GeoMath.checkStrToInt(array[8]);
			GeoMath.checkStrToInt(array[9]);
			final int checkStrToInt6 = GeoMath.checkStrToInt(array[10]);

			switch (checkStrToInt2) {
			case 0: {
				LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnTrack);
				LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnLock);
				break;
			}
			case 2: {
				LN100FSM.getInstance().Push(LN100FSM.LN100Event.Lock);
				break;
			}
			case 3: {
				LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnLock);
				break;
			}
			}
			
			LN100FSM.getInstance().trackMeasureData(checkStrToDouble, checkStrToDouble2, checkStrToDouble3);
		}
	}

	public static void returnAutoMeasurementStop(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnTrackMeasure);
			return;
		}
		array[1].equals(LN100CommandReturn.NAK);
	}

	public static void returnCommunicationEndCommand(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnConnect);
			return;
		}
		array[1].equals(LN100CommandReturn.NAK);
	}

	public static void returnCommunicationStartCommand(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.Connect);
			return;
		}
		array[1].equals(LN100CommandReturn.NAK);
	}

	public static void returnGuideLightOff(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)
				&& array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnGuideLight);
		}
	}

	public static void returnGuideLightOn(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)
				&& array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.GuideLight);
		}
	}

	public static void returnSelfLevelOFF(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnAutoLevel);
		} else if (!array[1].equals(LN100CommandReturn.NAK) && array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnLevel);
		}
	}

	public static void returnSelfLevelOn(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.AutoLevel);
		} else if (!array[1].equals(LN100CommandReturn.NAK) && array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.Level);
		}
	}

	public static void returnSendlaserPointeroff(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)
				&& array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnLaserPoint);
		}
	}

	public static void returnSendlaserPointeron(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)
				&& array[1].equals("OK")) {
			// LN100FSM.getInstance().Push(LN100FSM.LN100Event.LaserPoint);
		}
	}

	public static void returnSetAtmospheric(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	private static void returnSetTargetType(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	public static void returnStartAutoTrack(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			LN100FSM.getInstance().Push(LN100FSM.LN100Event.Track);
			return;
		}
		array[1].equals(LN100CommandReturn.NAK);
	}

	public static void returnStartBatteryLevelRepeat(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)) {
			LN100FSM.getInstance().batterLevelData(GeoMath.checkStrToInt(array[1]));
		}
	}

	public static void returnStartRotateAngle(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK) && !array[1].equals(LN100CommandReturn.NAK)) {
			array[1].equals("OK");
		}
	}

	public static void returnStartRotateVelocity(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	public static void returnStartTiltAngleRepeat(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	public static void returnStopBatteryLevelRepeat(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	public static void returnStopRotate(final String[] array) {
		if (array[1].equals(LN100CommandReturn.ACK)) {
			LN100FSM.getInstance().Push(LN100FSM.LN100Event.UnTrack);
			return;
		}
		array[1].equals(LN100CommandReturn.NAK);
	}

	public static void returnStopTiltAngleRepeat(final String[] array) {
		if (!array[1].equals(LN100CommandReturn.ACK)) {
			array[1].equals(LN100CommandReturn.NAK);
		}
	}

	public static void sendMessage(final String s) {
		final String encode = StringUtils.encode(s);
		LN100Util.getInstance().sendMessageHandle(StringUtils.hexStr2Bytes(
				String.valueOf(encode) + LN100Util.getInstance().hexChecksum(encode) + StringUtils.HexCR));
	}
}
