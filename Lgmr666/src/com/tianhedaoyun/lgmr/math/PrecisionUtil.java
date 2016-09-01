package com.tianhedaoyun.lgmr.math;

import java.math.BigDecimal;

public class PrecisionUtil {

	public static String decimal3(Double d) {
		BigDecimal bd = new BigDecimal(d + "");

		bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
		return bd+"";
	}

}
