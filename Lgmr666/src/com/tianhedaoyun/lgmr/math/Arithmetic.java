package com.tianhedaoyun.lgmr.math;

import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.bean.ShiShiPoint;
import com.tianhedaoyun.lgmr.util.Const;

public class Arithmetic {

	// 计算AB或者AC方位角--杨博

	private static double jisuanfangweijiao(double E1, double N1, double z1, double E2, double N2, double z2) {
		double fwj_1 = 10.0;
		double E = E2 - E1;
		double N = N2 - N1;
		double length_1 = Math.sqrt(E * E + N * N);
		double asin_1 = Math.asin(Math.abs(E) / length_1);// 计算AB与北方向的夹角
		// 当AB在第一象限或在正北方向坐标轴上时
		if (E >= 0) {
			if (N > 0) {
				fwj_1 = asin_1 * 180 / Math.PI;
			}
		}
		// 当AB在第四象限或在正东方向坐标轴上时
		if (E > 0) {
			if (N <= 0) {
				fwj_1 = 180 - asin_1 * 180 / Math.PI;
			}
		}
		// 当AB在第二象限或在正西方向坐标轴上时
		if (E <= 0) {
			if (N > 0) {
				fwj_1 = 360 - asin_1 * 180 / Math.PI;
			}
		}
		// 当AB在第三象限或在正南方向坐标轴上时
		if (E < 0) {
			if (N <= 0) {
				fwj_1 = 180 + asin_1 * 180 / Math.PI;
			}
		}
		return fwj_1;
	}

	// 求实时方位角
	private static double jisuanshishifangweijiao(double x1, double y1, double z1, double x2, double y2, double z2,
			double ac, double ab) {
		double ac_ab = ac - ab;

		if (ac_ab < 0) {
			ac_ab = ac_ab + 1;
		}

		double ad = jisuanfangweijiao(x1, y1, z1, x2, y2, z2) + ac_ab * 360;
		return ad;
	}

	// 计算实时坐标
	public static double[] cal_coordinate(double x1, double y1, double z1, double x2, double y2, double z2, double a1,
			double a2, double sc, double v1, double v2, double l1, double l2) {
		// 计算实时方位角
		double ac = jisuanshishifangweijiao(x1, y1, z1, x2, y2, z2, a2, a1);
		double x3 = x1 + sc * Math.sin(Math.toRadians(ac)) * Math.cos(Math.toRadians(Math.abs(90.0 - v2 * 360)));
		double y3 = y1 + sc * Math.cos(Math.toRadians(ac)) * Math.cos(Math.toRadians(Math.abs(90.0 - v2 * 360)));
		double z3 = z1 + l1 - l2 + sc * Math.sin(Math.toRadians(90 - v2 * 360));

		double[] ds = { x3, y3, z3 };

		return ds;

	}

	public static ShiShiPoint cal_coordinate1(double x1, double y1, double z1, double x2, double y2, double z2,
			double a1, double a2, double sc, double v1, double v2, double l1, double l2) {
		// 计算实时方位角
		double ac = jisuanshishifangweijiao(x1, y1, z1, x2, y2, z2, a2, a1);
		double x3 = x1 + sc * Math.sin(Math.toRadians(ac)) * Math.cos(Math.toRadians(Math.abs(90.0 - v2 * 360)));
		double y3 = y1 + sc * Math.cos(Math.toRadians(ac)) * Math.cos(Math.toRadians(Math.abs(90.0 - v2 * 360)));
		double z3 = z1 + l1 - l2 + sc * Math.sin(Math.toRadians(90 - v2 * 360));

		Const.shishi_point.setX(x3);
		Const.shishi_point.setY(y3);
		Const.shishi_point.setZ(z3);
		return Const.shishi_point;
	}

	// 判断实时坐标在放样点的左侧还是右侧

	public static int judge_direction(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3,
			double N3, double Z3) {

		double tmpx = (E1 - E2) / (N1 - N2) * (N3 - N2) + E2;

		if (tmpx == E3) {
			return 0;// 在线上
		}
		if (tmpx > E3) {
			return 1;// 在右边
		}
		if (tmpx < E3) {
			return -1;// 在左边
		}
		return 0;

	}

	// 已知两点坐标求平面距离
	public static double point_distance(double E1, double N1, double Z1, double E2, double N2, double Z2) {
		double distance = Math.sqrt(Math.pow(E1 - E2, 2) + Math.pow(N1 - N2, 2));
		return distance;
	}

	// 计算左右移动距离
	public static double faxian_distance(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3,
			double N3, double Z3) {
		// 计算AC方位角
		double ac = jisuanfangweijiao(E1, N1, Z1, E2, N2, Z2);

		// 计算仪器到放样点的距离
		double ac_distance = point_distance(E1, N1, Z1, E2, N2, Z2);

		// 计算实时方位角
		double ad = jisuanfangweijiao(E1, N1, Z1, E3, N3, Z3);

		// 计算角度差
		double dac = Math.abs(ad - ac);

		// 计算移动距离
		double cf = 2.0 * Math.sin(Math.toRadians(dac / 2)) * ac_distance;

		return cf;

	}

	// 前进或者后退距离
	public static double forwardOrGo_distance(double E1, double N1, double Z1, double E2, double N2, double Z2,
			double E3, double N3, double Z3) {
		// 求仪器点和放样点的距离
		double a = point_distance(E1, N1, Z1, E2, N2, Z2);
		// 求仪器点和实时坐标的距离
		double b = point_distance(E1, N1, Z1, E3, N3, Z3);

		return Math.abs(b - a);

	}

	// 判断前进还是后退

	public static int forwardOrGo(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3,
			double N3, double Z3) {

		// ac距离
		double ac = point_distance(E1, N1, Z1, E2, N2, Z2);
		// ad距离
		double ad = point_distance(E1, N1, Z1, E3, N3, Z3);
		if (ac > ad) {
			return -1;
		} else if (ac < ad) {
			return 1;
		} else {
			return 0;
		}

	}

	// 按边角后方交会计算P点坐标
	public static double[] cal_p_coordinate(double E1, double N1, double Z1, double E2, double N2, double Z2,
			double zj_sp_1, double zj_sp_2, double zj_cz_1, double zj_cz_2, double s1, double s2, double L1,
			double L2) {
		double PI = Math.PI;
		// 计算AB方位角
		double fwj = 0.0;
		fwj = jisuanfangweijiao(E1, N1, Z1, E2, N2, Z2);
		// 计算AB水平长度
		double length = 0.0;
		length = Math.sqrt(Math.pow(E2 - E1, 2) + Math.pow(N2 - N1, 2));
		// 计算AP和BP夹角，可正可负
		double APB = 0;// APB夹角，可正可负
		APB = zj_sp_2 - zj_sp_1;
		if (APB < 0)// 当相减的值为负时，需要加360度变成正值。
		{
			APB = APB + 1;
		}
		// 计算角PAB
		double PAB = 0;
		double s2_sp = 0.0;
		s2_sp = s2 * Math.cos(Math.toRadians(90 - zj_cz_2 * 360));
		double s1_sp = 0.0;
		s1_sp = s1 * Math.cos(Math.toRadians(90 - zj_cz_1 * 360));
		double PAB_cos;
		PAB_cos = s1_sp * s1_sp + length * length - s2_sp * s2_sp;
		PAB = Math.sin(Math.toRadians(APB * 360)) * s2_sp / length;
		PAB = Math.asin(PAB) * 180 / PI;
		if (PAB_cos < 0) {
			PAB = (180 - Math.abs(PAB)) * PAB / (Math.abs(PAB));
		}
		// 计算AP方位角
		double fwjAP = 0;
		fwjAP = fwj + PAB;
		// 计算P点坐标
		double E = 100.0;
		double N = 100.0;
		double Z = 0.0;
		E = E1 + s1 * Math.sin(Math.toRadians(fwjAP)) * Math.cos(Math.toRadians(90 - zj_cz_1 * 360));
		N = N1 + s1 * Math.cos(Math.toRadians(fwjAP)) * Math.cos(Math.toRadians(90 - zj_cz_1 * 360));
		Z = Z1 - s1 * Math.sin(Math.toRadians(90 - zj_cz_1 * 360)) + L2 - L1;
		double[] coordinate = { E, N, Z };
		return coordinate;
	}

	public static int judge_direction1(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3,
			double N3, double Z3) {

		double Z4 = faxiangliang(E1, N1, Z1, E2, N2, Z2, E3, N3, Z3);

		double COS_JJ = jiajiao(E1, N1, Z1, E2, N2, Z2, E3, N3, Z3);
		int i = 0;
		if (Z4 > 0) {
			if (COS_JJ >= 0) {
				i = -1;// 左移
			} else {
				i = -2;// 顺时针
			}
		}

		if (Z4 < 0) {
			if (COS_JJ >= 0) {
				i = 1;// 右移
			} else {
				i = 2;// 逆时针
			}
		}
		return i;
	}

	// 求法向量
	public static double faxiangliang(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3,
			double N3, double Z3) {
		double E4 = 0.0;// 法向量
		double N4 = 0.0;// 法向量
		double Z4 = 0.0;// 法向量

		E4 = ((N2 - N1) * (Z3 - Z1) - (Z2 - Z1) * (N3 - N1));

		N4 = ((Z2 - Z1) * (E3 - E1) - (E2 - E1) * (Z3 - Z1));

		return Z4 = ((E2 - E1) * (N3 - N1) - (N2 - N1) * (E3 - E1));
	}

	// 求夹角的cos值
	public static double jiajiao(double E1, double N1, double Z1, double E2, double N2, double Z2, double E3, double N3,
			double Z3) {
		double AB = Math.sqrt((E2 - E1) * (E2 - E1) + (N2 - N1) * (N2 - N1));// 向量ＡＢ的模
		double AC = Math.sqrt((E3 - E1) * (E3 - E1) + (N3 - N1) * (N3 - N1));// 向量ＡＣ的模
		double A_B = (E3 - E1) * (E2 - E1) + (N3 - N1) * (N2 - N1);// 向量ＡＢ与ＡＣ的向量积
		double COS_JJ = A_B / (AB * AC);
		return COS_JJ;

	}

}
