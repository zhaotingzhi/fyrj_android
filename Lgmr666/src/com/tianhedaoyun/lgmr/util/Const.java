package com.tianhedaoyun.lgmr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gvitech.android.IFeatureLayer;
import com.gvitech.android.IRenderModelPoint;
import com.gvitech.android.ISimpleGeometryRender;
import com.tianhedaoyun.lgmr.adapter.MyAdapters;
import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.bean.ShiShiPoint;
import com.tianhedaoyun.lgmr.bean.StationData;
import com.tianhedaoyun.lgmr.common.MyApp;

public class Const {
	/*
	 * zhao
	 */
	public static ExecutorService service = Executors.newFixedThreadPool(2);
	public static ExecutorService service1 = Executors.newFixedThreadPool(2);
	public static boolean isrunning;
	public static MyApp app;
	public static double robot_height;
	public static double prism_height;
	public static Point point;
	public static Point controlPoint = Point.getInstance();
	public static Point backsightPoint;
	public static Point lofting_point;
	public static ShiShiPoint shishi_point = ShiShiPoint.getInstance();

	public static boolean isSetControlPoint;
	public static boolean isbacksightdone;
	public static double backsight_va;
	public static double backsight_sl;
	public static StationData stationData = StationData.getInstance();

	public static Point res_ponit1;
	public static boolean res_isdone1;
	public static double resection_sl1;
	public static double resection_va1;
	public static boolean res_isdone2;
	public static Point res_ponit2;
	public static double resection_sl2;
	public static double resection_va2;
	public static double backsight_ha;
	public static double resection_ha1;
	public static double resection_ha2;
	// 架站机器人模型状态
	public static boolean CONST_PD;
	// 架站棱镜模型状态
	public static boolean CONST_M;

	public static IFeatureLayer CONST_modelLayer;
	public static int[] CONST_flIds;
	public static int[] CONST_NewDate;
	public static String CONST_NewDate_Path = "";

	public static IRenderModelPoint CONST_rpoi1 = null;
	public static IRenderModelPoint CONST_rpoi3 = null;

	// 设置
	public static int CONST_temperature;
	public static int CONST_pressure;
	public static boolean CONST_changeTextColor;
	public static boolean CONST_playSounds;

	/*
	 * 常量
	 */
	// "64:cc:2e:87:ff:42" 自己
	// "0c:1d:af:56:8f:93"
	public static final String CONST_iskey = "0c:1d:af:56:8f:93";
	public static final String CONST_Time = "2016-11-01 12:00:00";
	public static final String CONST_Time_g = "软件已过期！请联系天河道云客服：010-89029018";
	public static final String CONST_iskey_g = "您还没有权限，请联系天河道云客服：010-89029018";
	public static final String CONST_A = "名称"; /* 排序功能常量对应 */
	public static final String CONST_B = "任务";
	public static final String CONST_C = "状态";
	public static final String CONST_D = "类型";
	public static final String CONST_POINTNUMBER = "PointNumber";
	public static final String CONST_TYPE = "type";
	public static final String CONST_POINTROLE = "PointRole";
	public static final String CONST_STATE = "State";
	public static final String CONST_OID = "oid";
	public static final String CONST_DESC = "desc";
	public static final String CONST_ASC = "asc";
	public static final String CONST_Stake_Out = "Stake Out";
	public static final String CONST_Control_Point = "Control Point";
	public static final String CONST_lofting = "已放样点";
	public static final String CONST_lofting_robot = "设备模型";
	public static final String CONST_conventional_model = "常规模型";
	public static final String CONST_st_text = "自定义视图选项";
	public static final String CONST_mx_text = "模型功能选项";

	/*
	 * 变量
	 */
	public static MyAdapters CONST_madapter;
	public static List<Point> CONST_findlist;
	public static List<Point> CONST_no_findlist;
	public static boolean CONST_show_button_ = false;
	public static boolean CONST_SZ = true;
	public static boolean CONST_CL = true;
	public static boolean CONST_ST = true;
	public static boolean CONST_MX = true;
	public static boolean CONST_ZW = true;
	public static boolean CONST_setdian = false;
	public static String CONST_selDomain = "";
	public static HashMap<String, Double> CONST_domain_ = new HashMap<String, Double>();

	public static List<Map<String, Object>> CONST_list = new ArrayList<Map<String, Object>>();

	public static ArrayList<String> CONST_domainName = new ArrayList<String>();

	public static ArrayList<String> CONST_txt;

	public static ArrayList<Integer> CONST_find_filter;

	public static double CONST_IntTest[];

	public static String CONST_tablename;

	private Const() {
	}

	public static List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("Oid");
		data.add("名称");
		data.add("任务");
		data.add("类型");
		data.add("状态");
		return data;
	}

	public static List<String> getDatafilter() {
		List<String> data = new ArrayList<String>();
		data.add("Stake Out");
		data.add("Control Point");
		data.add("已放样点");
		return data;
	}
}