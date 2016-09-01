package com.tianhedaoyun.lgmr.activity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gvitech.android.EnumValue.gviInteractMode;
import com.gvitech.android.IEnvelope;
import com.gvitech.android.IFeatureLayer;
import com.gvitech.android.IRenderModelPoint;
import com.gvitech.android.RenderControl;
import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.LN100.LN100CommandReturn;
import com.tianhedaoyun.lgmr.LN100.LN100CommandSend;
import com.tianhedaoyun.lgmr.LN100.LN100FSM;
import com.tianhedaoyun.lgmr.LN100.LN100Util;
import com.tianhedaoyun.lgmr.activity.ModelMain.PrismHandler;
import com.tianhedaoyun.lgmr.adapter.DragListAdapter;
import com.tianhedaoyun.lgmr.adapter.ListViewAdpter;
import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.bean.SettingData;
import com.tianhedaoyun.lgmr.bean.StationData;
import com.tianhedaoyun.lgmr.common.MThread;
import com.tianhedaoyun.lgmr.common.MyApp;
import com.tianhedaoyun.lgmr.floder.FolderFilePicker;
import com.tianhedaoyun.lgmr.floder.FolderFilePicker.PickPathEvent;
import com.tianhedaoyun.lgmr.fragment.SampleModels;
import com.tianhedaoyun.lgmr.fragment.SetStation;
import com.tianhedaoyun.lgmr.math.Arithmetic;
import com.tianhedaoyun.lgmr.math.PrecisionUtil;
import com.tianhedaoyun.lgmr.model.util.ModelUtil;
import com.tianhedaoyun.lgmr.receiver.MsgReceiver;
import com.tianhedaoyun.lgmr.rudder.Rudder;
import com.tianhedaoyun.lgmr.rudder.Rudder.RudderListener;
import com.tianhedaoyun.lgmr.service.WifiService;
import com.tianhedaoyun.lgmr.task.AnotherTask;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.FileUtil;
import com.tianhedaoyun.lgmr.util.PopMenu;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;
import com.tianhedaoyun.lgmr.util.SharedSettingUtil;
import com.tianhedaoyun.lgmr.util.SqliteUtil;
import com.tianhedaoyun.lgmr.util.StringUtils;
import com.tianhedaoyun.lgmr.util.WifiUtil;
import com.tianhedaoyun.lgmr.view.Panel;
import com.tianhedaoyun.lgmr.view.XListView;
import com.tianhedaoyun.lgmr.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ModelMain extends BaseActivity
		implements android.view.View.OnClickListener, android.view.View.OnTouchListener, IXListViewListener {

	private SurfaceView surfaceView;
	private String mPath;// 导出文件路径
	private String cachePath;
	private String PACH;
	private static ListView model_list;
	private MThread _mThread;
	private Panel panel;
	private View chouti;
	private XListView lv;
	private ImageView iv;
	private static ImageView wifi_lianjie;
	private static ImageView iv_measure;
	private ImageView iv_guide;
	private ImageView lofting;
	private ImageView iv_turn;
	private TextView point_x, point_y, point_z;
	private static TextView prism_robot;
	private TextView points;
	private static TextView around_distance;
	private static TextView about_distance;
	private static TextView around;
	private static TextView about;
	private static TextView z_distance;
	private static TextView z_loft_z;
	private static TextView tv_measure;
	private static TextView prism_ht;
	private TextView textall;
	private WifiService wifiService;
	private WifiUtil _mWifi;
	private MsgReceiver msgReceiver;
	private static StationData data;
	private static double sl;
	private static double va;
	private static double ha;
	private static Message msg1;
	private static Message msg2;
	private static Message msg3;
	private static Message msg4;
	private SoundPool sp;// 声明一个SoundPool
	private static IRenderModelPoint rpoi2 = null;
	// 判断机器人旋转状态
	private boolean isRotateLeft = false, isRotateRight = false;
	// 判断导向灯状态
	private boolean is_guide = false;
	// ------------------------------------------------------------------------------------------------------
	public static RelativeLayout coordinate, distance, move, function;

	private static ListView domain_list;
	private int musicdianji, musicyinxiao;// 定义一个整型用load（）；来设置suondID
	private TextView model_text, fy_id, fy_Name, fy_Role, fy_type, fy_x, fy_y, fy_z, fy_save, cancel;// 收集界面控件
	private EditText et_save;
	private Button btn_add, collect;
	private ListViewAdpter adapterviews; // 视图适配器
	private DragListAdapter adapter; // 模型适配器
	private static int flag_mouseMode = 1;
	private List<Map<String, Object>> viewlist;
	private AlertDialog dialog;// 视图窗口
	private String tablename;
	private SQLiteDatabase mydata = null;
	private RelativeLayout r_collect, bottom, r_connect, search, models, views, plan, image_lofting, measure,
			layout_sort, layout_filter, layout_sou, table, r_navigation, model_layout;
	private TextView measure11, models11, views11, image_lofting11;
	private ImageView measure1, models1, views1, image_lofting1;
	public static HashMap<Integer, ArrayList<Integer>> list_hide = new HashMap<Integer, ArrayList<Integer>>();
	public static ArrayList<Boolean> isShow;
	private ProgressDialog progressDialog = null;
	private ImageView suo;
	// 导出
	private ImageView export;

	public static class PrismHandler extends Handler {
		WeakReference<Activity> mActivityReference;

		PrismHandler(Activity activity) {
			mActivityReference = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final Activity activity = mActivityReference.get();
			if (msg.what == 1) {
				if (activity != null) {
					prism_ht.setText((Double) msg.obj + "");
				}
			}
		}

	}

	public static class MHandler extends Handler {
		WeakReference<Activity> mActivityReference;

		MHandler(Activity activity) {
			mActivityReference = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final Activity activity = mActivityReference.get();
			if (activity != null) {
				switch (msg.what) {
				case 1:
					wifi_lianjie.setImageResource(R.drawable.lock);
					break;
				case 2:
					wifi_lianjie.setImageResource(R.drawable.unlock);
					break;
				case 5:
					iv_measure.setImageResource(R.drawable.ranging);
					tv_measure.setText("测 距");
					break;
				case 6:
					iv_measure.setImageResource(R.drawable.stop);
					tv_measure.setText("停 止");
					break;

				case 34:
					Intent intent = (Intent) msg.obj;
					sl = intent.getDoubleExtra("SlopeDistance", 0);
					va = intent.getDoubleExtra("VerticalAngle", 0);
					ha = intent.getDoubleExtra("HorizontalAngle", 0);
					prism_robot.setText(PrecisionUtil.decimal3(sl));

					if (Const.isbacksightdone) {
						Const.isbacksightdone = false;
						Const.backsight_sl = sl;
						Const.backsight_va = va;
						Const.backsight_ha = ha;
					}

					if (Const.res_isdone1) {
						Const.res_isdone1 = false;
						Const.resection_sl1 = sl;
						Const.resection_va1 = va;
						Const.resection_ha1 = ha;
					}

					if (Const.res_isdone2) {
						Const.res_isdone2 = false;
						Const.resection_sl2 = sl;
						Const.resection_va2 = va;
						Const.resection_ha2 = ha;
					}

					data = SharedObjectUtil.readObject(activity, "station_info");
					if (data != null) {
						Const.robot_height = data.getRobot_height();
						Const.prism_height = data.getPrism_height();
						Const.controlPoint = data.getControl_point();
						Const.backsightPoint = data.getBacksight_point();
						Const.backsight_ha = data.getHoushi_ha();
						Const.backsight_va = data.getHoushi_va();

						if (Const.lofting_point == null) {
							Const.lofting_point = Const.backsightPoint;
						}

						Lofting_event(sl, va, ha, Const.lofting_point);

						ModelUtil.polyline(Const.controlPoint.getX(), Const.controlPoint.getY(),
								Const.controlPoint.getZ(), Const.shishi_point.getX(), Const.shishi_point.getY(),
								Const.shishi_point.getZ(), Const.lofting_point.getX(), Const.lofting_point.getY(),
								Const.lofting_point.getZ());
						if (Const.CONST_modelLayer != null) {
							if (rpoi2 == null) {
								rpoi2 = Const.CONST_modelLayer.createRenderModelPoint(1);
							}
							rpoi2.setX(Const.shishi_point.getX());
							rpoi2.setY(Const.shishi_point.getY());
							rpoi2.setZ(Const.shishi_point.getZ());
						}

					}

					break;
				default:
					break;
				}
			}
		}

		private void Lofting_event(double sl, double va, double ha, Point lofting_point) {
			// 获得实时坐标

			Const.shishi_point = Arithmetic.cal_coordinate1(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), Const.backsightPoint.getX(), Const.backsightPoint.getY(),
					Const.backsightPoint.getZ(), Const.backsight_ha, ha, sl, Const.backsight_va, va, Const.robot_height,
					Const.prism_height);
			int i, j;
			double a, b;

			// 判断左侧还是右侧
			i = Arithmetic.judge_direction1(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), lofting_point.getX(), lofting_point.getY(), lofting_point.getZ(),
					Const.shishi_point.getX(), Const.shishi_point.getY(), Const.shishi_point.getZ());
			// 判断左右移动距离
			a = Arithmetic.faxian_distance(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), lofting_point.getX(), lofting_point.getY(), lofting_point.getZ(),
					Const.shishi_point.getX(), Const.shishi_point.getY(), Const.shishi_point.getZ());
			// 判断前进还是后退
			j = Arithmetic.forwardOrGo(Const.controlPoint.getX(), Const.controlPoint.getY(), Const.controlPoint.getZ(),
					lofting_point.getX(), lofting_point.getY(), lofting_point.getZ(), Const.shishi_point.getX(),
					Const.shishi_point.getY(), Const.shishi_point.getZ());
			// 前进或者后退距离
			b = Arithmetic.forwardOrGo_distance(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), lofting_point.getX(), lofting_point.getY(), lofting_point.getZ(),
					Const.shishi_point.getX(), Const.shishi_point.getY(), Const.shishi_point.getZ());

			// 判断上移还是下移
			double z = Const.lofting_point.getZ() - Const.shishi_point.getZ();

			if (a <= 0.05 & b <= 0.05) {
				msg1 = Message.obtain();
				msg1.what = 1;
				msg1.obj = i + "," + a + "," + j * b;
				if (Const.CONST_changeTextColor) {
					about_distance.setTextColor(Color.GREEN);
					around_distance.setTextColor(Color.GREEN);
					z_distance.setTextColor(Color.GREEN);
				}
				double k = a;
				if (i == -1) {
					about.setText("左移");
					about_distance.setText(StringUtils.getDouble(k, "#.000") + "");
				} else if (i == 1) {
					about.setText("右移");
					about_distance.setText(StringUtils.getDouble(k, "#.000") + "");
				} else if (i == -2) {
					about.setText("顺时针旋转");
					about_distance.setText("");
				} else {
					about.setText("逆时针旋转");
					about_distance.setText("");
				}

				double b1 = j * b;
				if (b1 == 0) {
					around.setText("");
					around_distance.setText("0.000");
				} else if (b1 < 0) {
					around.setText("后退");
					around_distance.setText(StringUtils.getDouble(Math.abs(b), "#.000") + "");
				} else {
					around.setText("前进");
					around_distance.setText(StringUtils.getDouble(Math.abs(b), "#.000") + "");
				}

			} else {

				about_distance.setTextColor(Color.BLACK);
				around_distance.setTextColor(Color.BLACK);
				z_distance.setTextColor(Color.BLACK);

			}
			if (i == -1) {
				about.setText("左移");
				about_distance.setText(StringUtils.getDouble(a, "#.000") + "");
			} else if (i == 1) {
				about.setText("右移");
				about_distance.setText(StringUtils.getDouble(a, "#.000") + "");
			} else if (i == -2) {
				about.setText("顺时针旋转");
				about_distance.setText("");
			} else {
				about.setText("逆时针旋转");
				about_distance.setText("");
			}

			double b1 = j * b;
			if (b1 == 0) {
				around.setText("");
				around_distance.setText("0.000");
			} else if (b1 < 0) {
				around.setText("后退");
				around_distance.setText(StringUtils.getDouble(Math.abs(b1), "#.000") + "");
			} else {
				around.setText("前进");
				around_distance.setText(StringUtils.getDouble(Math.abs(b1), "#.000") + "");
			}
			if (z < 0) {
				z_loft_z.setText("下移");
				z_distance.setText(PrecisionUtil.decimal3(Math.abs(z)) + "");
			} else {
				z_loft_z.setText("上移");
				z_distance.setText(PrecisionUtil.decimal3(Math.abs(z)) + "");
			}

		};

	}

	public static class NHandler extends Handler {
		WeakReference<Context> mActivityReference;

		NHandler(Context context) {
			mActivityReference = new WeakReference<Context>(context);
		}

		@Override
		public void handleMessage(Message msg) {
			final Context context = mActivityReference.get();
			if (context != null) {
				switch (msg.what) {
				case 1:
					wifi_lianjie.setImageResource(R.drawable.wifi_lianjie);
					break;

				case 2:
					if (LN100Util.getInstance().isConnected()) {
						if (!LN100FSM.s_Lock) {
							wifi_lianjie.setImageResource(R.drawable.unlock);
						}
					} else {
						LN100CommandSend.connect(context);
						LN100CommandSend.communicationStartCommand();
						wifi_lianjie.setImageResource(R.drawable.unlock);
					}

					break;
				default:
					break;
				}
			}
		}
	}

	private PrismHandler prismHandler = new PrismHandler(ModelMain.this);
	// 与application绑定的handler
	private MHandler mHandler = new MHandler(ModelMain.this);

	private NHandler handler = new NHandler(ModelMain.this);

	// 排序，筛选功能
	private RelativeLayout sort_layout, filter_layout, suo_layout;
	private TextView btn_close;
	private TextView btn_find;
	private ListView lv_sort;
	private Switch swch;
	protected String desc = "asc";
	protected String name_sort;
	private ListView lv_filter;
	protected String name_file;
	protected String desc_file;
	private SettingData settingData;
	private ImageView setting;
	protected boolean isRotateTop;
	protected boolean isRotateBottom;
	private int mHeight;
	private Thread mmThread;
	protected boolean flag;
	protected boolean isPrism;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_model_main);
		flag = true;
		isPrism = true;
		Const.isrunning = true;
		data = SharedObjectUtil.readObject(getApplicationContext(), "station_info");
		if (data != null) {
			Const.robot_height = data.getRobot_height();
			Const.prism_height = data.getPrism_height();
			Const.controlPoint = data.getControl_point();
			Const.backsightPoint = data.getBacksight_point();
			Const.backsight_ha = data.getHoushi_ha();
			Const.backsight_va = data.getHoushi_va();
		}

		settingData = SharedSettingUtil.readObject(getApplicationContext(), "setting_data");
		if (settingData != null) {
			Const.CONST_temperature = settingData.getTemperature();
			Const.CONST_pressure = settingData.getPressure();
			Const.CONST_changeTextColor = settingData.isChangeTextColor();
			Const.CONST_playSounds = settingData.isPlaySounds();
		}

		Const.app = new MyApp();
		Const.app.setHandler(mHandler);
		Const.app.setNhandler(handler);

		prismHeightUpdate();

		LinearLayout laa = (LinearLayout) this.findViewById(R.id.li2);
		surfaceView = (SurfaceView) laa.findViewById(R.id.surfaceview);
		// 加抽屉
		panel = new Panel(this, surfaceView, LayoutParams.FILL_PARENT, 655);
		laa.addView(panel);
		chouti = LayoutInflater.from(getApplicationContext()).inflate(R.layout.panel_container2, null);
		panel.fillPanelContainer(chouti);

		Intent intent = getIntent();
		PACH = intent.getStringExtra("message");
		String[] name = PACH.split("/");
		tablename = name[name.length - 1].toString().split("[.]")[0].toString();
		Const.CONST_tablename = tablename;

		wifi_lianjie = (ImageView) findViewById(R.id.wifi_connect);
		wifi_lianjie.setOnClickListener(this);

		_mWifi = new WifiUtil(this);

		// 显示隐藏全局变量
		coordinate = (RelativeLayout) findViewById(R.id.coordinate);
		distance = (RelativeLayout) findViewById(R.id.distance);
		move = (RelativeLayout) findViewById(R.id.move);
		function = (RelativeLayout) findViewById(R.id.function);
		// 收集界面
		r_collect = (RelativeLayout) findViewById(R.id.r_collect);
		r_collect.setOnClickListener(this);
		collect = (Button) findViewById(R.id.collect);
		collect.setOnClickListener(this);
		et_save = (EditText) findViewById(R.id.et_save);
		fy_id = (TextView) findViewById(R.id.o_id);
		fy_Name = (EditText) findViewById(R.id.number_id);
		fy_Role = (TextView) findViewById(R.id.fy_pointRole);
		fy_type = (TextView) findViewById(R.id.type1);
		fy_x = (TextView) findViewById(R.id.fy_x);
		fy_y = (TextView) findViewById(R.id.fy_y);
		fy_z = (TextView) findViewById(R.id.fy_z);
		fy_save = (TextView) findViewById(R.id.fy_save);
		fy_save.setOnClickListener(this);
		cancel = (TextView) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		// 设置按钮
		setting = (ImageView) findViewById(R.id.sys_setting);
		setting.setOnClickListener(this);

		// 音效设置
		sp = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);// 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
		musicdianji = sp.load(this, R.raw.dianji, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
		musicyinxiao = sp.load(this, R.raw.yinxiao, 1);

		// 轴网功能
		// domain_list = (ListView) findViewById(R.id.domain_list);

		iv = (ImageView) findViewById(R.id.iv);
		iv.setOnClickListener(this);
		r_connect = (RelativeLayout) findViewById(R.id.r_connect);
		points = (TextView) chouti.findViewById(R.id.points);
		// Listview设置
		lv = (XListView) chouti.findViewById(R.id.lv);
		lv.setPullLoadEnable(true);
		lv.setPullRefreshEnable(true);
		lv.setXListViewListener(ModelMain.this);

		// 导出
		export = (ImageView) chouti.findViewById(R.id.export);
		export.setOnClickListener(this);

		model_list = (ListView) findViewById(R.id.model_list);
		model_layout = (RelativeLayout) findViewById(R.id.model_layout);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
		model_text = (TextView) findViewById(R.id.model_text);

		iv_measure = (ImageView) findViewById(R.id.iv_measure);
		iv_measure.setOnClickListener(this);
		tv_measure = (TextView) findViewById(R.id.tv_measure);
		iv_guide = (ImageView) findViewById(R.id.iv_guide);
		iv_guide.setOnClickListener(this);
		lofting = (ImageView) findViewById(R.id.lofting);
		lofting.setOnClickListener(this);
		iv_turn = (ImageView) findViewById(R.id.iv_turn);
		iv_turn.setOnClickListener(this);
		point_x = (TextView) findViewById(R.id.x);
		point_y = (TextView) findViewById(R.id.y);
		point_z = (TextView) findViewById(R.id.z);
		prism_robot = (TextView) findViewById(R.id.prism_robot);
		prism_ht = (TextView) findViewById(R.id.prism_ht);

		around_distance = (TextView) findViewById(R.id.around_distance);
		about_distance = (TextView) findViewById(R.id.about_distance);
		around = (TextView) findViewById(R.id.around);
		about = (TextView) findViewById(R.id.aboutt);
		z_distance = (TextView) findViewById(R.id.z_distance);
		z_loft_z = (TextView) findViewById(R.id.tv_loft_z);

		measure = (RelativeLayout) findViewById(R.id.measure);
		measure11 = (TextView) findViewById(R.id.measure11);
		measure1 = (ImageView) findViewById(R.id.measure1);
		measure.setOnTouchListener(this);

		views = (RelativeLayout) findViewById(R.id.views);
		views1 = (ImageView) findViewById(R.id.views1);
		views11 = (TextView) findViewById(R.id.views11);
		views.setOnTouchListener(this);

		image_lofting = (RelativeLayout) findViewById(R.id.image_lofting);
		image_lofting11 = (TextView) findViewById(R.id.image_lofting11);
		image_lofting1 = (ImageView) findViewById(R.id.image_lofting1);
		image_lofting.setOnTouchListener(this);

		models = (RelativeLayout) findViewById(R.id.models);
		models11 = (TextView) findViewById(R.id.models11);
		models1 = (ImageView) findViewById(R.id.models1);
		models.setOnTouchListener(this);

		sort_layout = (RelativeLayout) chouti.findViewById(R.id.sort_layout);
		filter_layout = (RelativeLayout) chouti.findViewById(R.id.filers_layout);
		suo = (ImageView) chouti.findViewById(R.id.suo);
		suo_layout = (RelativeLayout) chouti.findViewById(R.id.suo_layout);
		btn_close = (TextView) findViewById(R.id.find_closd);
		btn_find = (TextView) findViewById(R.id.btn_find);
		sort_layout.setOnClickListener(this);
		filter_layout.setOnClickListener(this);
		suo.setOnClickListener(this);
		btn_close.setOnClickListener(this);

		btn_find.setOnClickListener(this);

		layout_sort = (RelativeLayout) findViewById(R.id.layout_sort);
		lv_sort = (ListView) findViewById(R.id.lv_sort);
		textall = (TextView) findViewById(R.id.textall);
		textall.setOnClickListener(this);

		lv_sort.setAdapter(new ArrayAdapter<String>(this, R.layout.myspinner_dropdown, Const.getData()));
		swch = (Switch) findViewById(R.id.switch1);
		swch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					desc = Const.CONST_DESC;
				} else {
					desc = Const.CONST_ASC;
				}
				if (name_sort != null) {

					new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points,
							isShow, list_hide, adapter, model_layout, tablename, domain_list, progressDialog)
									.find_sort(mydata, tablename, name_sort, desc);
				}
			}
		});
		// lv_sort-----监听--------------------------------------------------------------------------------------------------
		lv_sort.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (Const.getData().get(arg2).equals(Const.CONST_A)) {
					name_sort = Const.CONST_POINTNUMBER;
				} else if (Const.getData().get(arg2).equals(Const.CONST_B)) {
					name_sort = Const.CONST_TYPE;
				} else if (Const.getData().get(arg2).equals(Const.CONST_C)) {
					name_sort = Const.CONST_POINTROLE;
				} else if (Const.getData().get(arg2).equals(Const.CONST_D)) {
					name_sort = Const.CONST_STATE;
				} else {
					name_sort = Const.CONST_OID;
				}

				if (name_sort != null) {
					new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points,
							isShow, list_hide, adapter, model_layout, tablename, domain_list, progressDialog)
									.find_sort(mydata, tablename, name_sort, desc);

				}
				layout_sort.setVisibility(View.GONE);
			}
		});
		layout_filter = (RelativeLayout) findViewById(R.id.layout_filter);
		lv_filter = (ListView) findViewById(R.id.lv_filter);
		lv_filter.setAdapter(new ArrayAdapter<String>(this, R.layout.myspinner_dropdown, Const.getDatafilter()));
		lv_filter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (Const.getDatafilter().get(arg2).equals(Const.CONST_Stake_Out)) {
					name_file = Const.CONST_Stake_Out;
					desc_file = Const.CONST_POINTROLE;
				} else if (Const.getDatafilter().get(arg2).equals(Const.CONST_Control_Point)) {
					name_file = Const.CONST_Control_Point;
					desc_file = Const.CONST_POINTROLE;
				} else if (Const.getDatafilter().get(arg2).equals(Const.CONST_lofting)) {
					name_file = Const.CONST_lofting;
					desc_file = Const.CONST_STATE;
				}

				if (name_file != null) {
					new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points,
							isShow, list_hide, adapter, model_layout, tablename, domain_list, progressDialog)
									.find_filter(mydata, tablename, desc_file, name_file);
					new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points,
							isShow, list_hide, adapter, model_layout, tablename, domain_list, progressDialog)
									.no_find_filter(mydata, tablename, desc_file, name_file);
				}
				layout_filter.setVisibility(View.GONE);

			}
		});
		layout_sou = (RelativeLayout) findViewById(R.id.layout_sou);

		// 绑定Service
		Intent intent_wifi = new Intent(this, WifiService.class);
		bindService(intent_wifi, conn, Context.BIND_AUTO_CREATE);

		// 动态注册广播接收器
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.tianhedaoyun.lgmr.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);
		progressDialog = ProgressDialog.show(ModelMain.this, "请稍等...", "获取数据中...", true);
		// 初始化缓存目录
		init_cachePath();
		// 初始化三维模型 并导入sdb文件
		initial_model();
		// 异步加载sdb文件
		// AsyncLoadSdb();
		// 开启测量线程
		// measureReturn();

		mHeight = surfaceView.getHeight();

	}

	public void Lofting_event(double sl2, double va2, double ha2, Point lofting_point) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		Const.CONST_txt.clear();
		Const.CONST_domainName.clear();
		Const.CONST_list.clear();
		Const.CONST_domain_.clear();
		Const.CONST_findlist.clear();
		finish();
	}

	private void prismHeightUpdate() {
		new Thread() {
			@Override
			public void run() {
				while (isPrism) {
					StationData data = SharedObjectUtil.readObject(getApplicationContext(), "station_info");
					if (data == null) {
						continue;
					} else {
						Message msg = Message.obtain();
						msg.what = 1;
						msg.obj = data.getPrism_height();
						prismHandler.sendMessage(msg);
					}
				}
			};
		}.start();
	}

	private void measureReturn() {

		Const.service1.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				// 发送反复测量命令
				LN100CommandSend.autoMeasurementStart(0, 4);
				while (flag) {
					System.out.println("Thread.currentThread().getId()++++++++@@@@@" + Thread.currentThread().getId());
					try {
						if (LN100Util.getInstance().getM_client() == null) {
							continue;
						}
						InputStream is = LN100Util.getInstance().getM_client().getInputStream();

						byte[] buffer = new byte[is.available()];
						is.read(buffer);
						// 解析返回的数据
						if (buffer.length == 0 || buffer == null) {
							continue;
						}
						LN100CommandReturn.messageReturn(buffer);
						Thread.sleep(50);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// mmThread = new MThread() {
		// @Override
		// public void doRun() {
		// try {
		// Thread.sleep(3000L);
		// } catch (InterruptedException e1) {
		// e1.printStackTrace();
		// }
		// // 发送反复测量命令
		// LN100CommandSend.autoMeasurementStart(0, 4);
		// while (flag) {
		// //
		// System.out.println("Thread.currentThread().getId()++++++++@@@@@"+Thread.currentThread().getId());
		// try {
		// if (LN100Util.getInstance().getM_client() == null) {
		// continue;
		// }
		// InputStream is =
		// LN100Util.getInstance().getM_client().getInputStream();
		//
		// byte[] buffer = new byte[is.available()];
		// is.read(buffer);
		// // 解析返回的数据
		// if (buffer.length == 0 || buffer == null) {
		// continue;
		// }
		// LN100CommandReturn.messageReturn(buffer);
		// Thread.sleep(50);
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// }
		// };
		// mmThread.start();

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.measure:// 测量

			if (MotionEvent.ACTION_DOWN == event.getAction()) {
				if (Const.CONST_CL) {
					measure1.setImageResource(R.drawable.measure_blue);
					measure11.setTextColor(Color.BLUE);
					Const.CONST_show_button_ = false;

					image_lofting1.setImageResource(R.drawable.views);
					image_lofting11.setTextColor(Color.BLACK);

					model_layout.setVisibility(View.GONE);
					views1.setImageResource(R.drawable.views);
					views11.setTextColor(Color.BLACK);

					models1.setImageResource(R.drawable.models);
					models11.setTextColor(Color.BLACK);

					ModelUtil.cl();
					Const.CONST_CL = false;
					return true;
				} else {
					measure1.setImageResource(R.drawable.measure);
					measure11.setTextColor(Color.BLACK);
					ModelUtil.cl_del();
					Const.CONST_CL = true;
					return true;
				}
			}
			break;

		case R.id.image_lofting:// 设站
			if (MotionEvent.ACTION_DOWN == event.getAction()) {
				if (Const.CONST_SZ) {
					image_lofting1.setImageResource(R.drawable.views_blue);
					image_lofting11.setTextColor(Color.BLUE);
					Const.CONST_show_button_ = false;

					measure1.setImageResource(R.drawable.measure);
					measure11.setTextColor(Color.BLACK);
					ModelUtil.cl_del();

					model_layout.setVisibility(View.GONE);
					views1.setImageResource(R.drawable.views);
					views11.setTextColor(Color.BLACK);

					models1.setImageResource(R.drawable.models);
					models11.setTextColor(Color.BLACK);

					if (!RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractNormal)) {

						RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
					}

					if (r_connect.getVisibility() == View.GONE) {
						// 开抽屉
						if (!panel.isOpen()) {
							panel.open(ModelMain.this, iv);
						}
						r_connect.setVisibility(View.VISIBLE);
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.r_connect, new SetStation()).addToBackStack(null).commit();

					} else {
						r_connect.setVisibility(View.GONE);
					}
					Const.CONST_SZ = false;
					return true;
				} else {
					image_lofting1.setImageResource(R.drawable.views);
					image_lofting11.setTextColor(Color.BLACK);
					// 关抽屉
					if (panel.isOpen()) {
						panel.close(iv, r_connect);
					}
					Const.CONST_SZ = true;
					return true;
				}
			}
			break;
		case R.id.views:// 视图
			if (MotionEvent.ACTION_DOWN == event.getAction()) {
				if (Const.CONST_ST) {
					views1.setImageResource(R.drawable.views_blue);
					views11.setTextColor(Color.BLUE);
					Const.CONST_show_button_ = false;

					measure1.setImageResource(R.drawable.measure);
					measure11.setTextColor(Color.BLACK);
					ModelUtil.cl_del();

					image_lofting1.setImageResource(R.drawable.views);
					image_lofting11.setTextColor(Color.BLACK);

					models1.setImageResource(R.drawable.models);
					models11.setTextColor(Color.BLACK);
					// 关抽屉
					if (panel.isOpen()) {
						panel.close(iv, r_connect);
					}
					model_layout.setVisibility(View.VISIBLE);
					model_text.setText(Const.CONST_st_text);
					btn_add.setVisibility(0);
					if (SqliteUtil.exits(tablename + "_views") == false) {
						SqliteUtil.createTableviews(tablename + "_views");
					}
					viewlist = SqliteUtil.findviews(mydata, tablename + "_views");
					adapterviews = new ListViewAdpter(viewlist, tablename + "_views", ModelMain.this);
					model_list.setAdapter(adapterviews);
					Const.CONST_ST = false;
					return true;
				} else {
					views1.setImageResource(R.drawable.views);
					views11.setTextColor(Color.BLACK);
					model_layout.setVisibility(View.GONE);
					Const.CONST_ST = true;
					return true;
				}
			}
			break;
		case R.id.models:// 模型
			if (MotionEvent.ACTION_DOWN == event.getAction()) {
				if (Const.CONST_MX) {
					models1.setImageResource(R.drawable.models_blue);
					models11.setTextColor(Color.BLUE);
					Const.CONST_show_button_ = true;

					measure1.setImageResource(R.drawable.measure);
					measure11.setTextColor(Color.BLACK);
					ModelUtil.cl_del();

					views1.setImageResource(R.drawable.views);
					views11.setTextColor(Color.BLACK);
					model_layout.setVisibility(View.GONE);

					image_lofting1.setImageResource(R.drawable.views);
					image_lofting11.setTextColor(Color.BLACK);
					// 关抽屉
					if (panel.isOpen()) {
						panel.close(iv, r_connect);
					}

					if (!RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractNormal)) {

						RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
					}

					if (flag_mouseMode == 2) {
						model_layout.setVisibility(8);
						flag_mouseMode = 1;
					} else {
						model_layout.setVisibility(0);
						model_list.setAdapter(adapter);
						flag_mouseMode = 2;
					}
					model_text.setText(Const.CONST_mx_text);
					btn_add.setVisibility(8);
					Const.CONST_MX = false;
					return true;
				} else {
					models1.setImageResource(R.drawable.models);
					models11.setTextColor(Color.BLACK);
					if (!RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractNormal)) {

						RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
					}
					if (flag_mouseMode == 2) {
						model_layout.setVisibility(8);
						flag_mouseMode = 1;
					} else {
						model_layout.setVisibility(0);
						model_list.setAdapter(adapter);
						flag_mouseMode = 2;
					}
					Const.CONST_show_button_ = false;
					Const.CONST_MX = true;
					return true;
				}
			}

			break;

		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv:
			panel.openOrClose(ModelMain.this, iv, r_connect);
			break;
		case R.id.textall:
			ModelUtil.texall();
			new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points, isShow,
					list_hide, adapter, model_layout, tablename, domain_list, progressDialog).sqlfall(cachePath,
							tablename, mydata);
			break;
		case R.id.wifi_connect:

			if (!LN100Util.getInstance().isConnected()) {
				Toast.makeText(getApplicationContext(), "请选择正确的wifi", Toast.LENGTH_LONG).show();
				return;
			} else {
				// 跳转或者弹出操作窗口
				lianjie_prism();
			}
			break;
		case R.id.iv_measure:
			if (LN100FSM.s_TrackMeasure) {
				LN100CommandSend.autoMeasurementStop();
			} else {
				LN100CommandSend.autoMeasurementStart(0, 4);
			}
			break;
		case R.id.lofting:
			if (r_connect.getVisibility() == View.GONE) {
				// 开抽屉
				if (!panel.isOpen()) {
					panel.open(ModelMain.this, iv);
				}
				r_connect.setVisibility(View.VISIBLE);
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.r_connect, new SetStation()).addToBackStack(null).commit();

			} else {
				r_connect.setVisibility(View.GONE);

			}
			break;

		case R.id.iv_guide:
			if (!is_guide) {
				LN100CommandSend.guideLightOn(1, 2);
				is_guide = true;
			} else {
				LN100CommandSend.guideLightOff();
				is_guide = false;
			}
			break;

		case R.id.iv_turn:
			// 选择放样点后，仪器自动移动到放样点所在大方向
			// 已知放样点，后视点，控制点

			if (SharedObjectUtil.readObject(getApplicationContext(), "station_info") == null) {
				Toast.makeText(getApplicationContext(), "请先架站", Toast.LENGTH_LONG).show();
				return;
			}

			double cosjj = Arithmetic.jiajiao(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), Const.lofting_point.getX(), Const.lofting_point.getY(),
					Const.lofting_point.getZ(), Const.shishi_point.getX(), Const.shishi_point.getY(),
					Const.shishi_point.getZ());
			double j = Math.acos(cosjj) / Math.PI / 2;

			int i1 = Arithmetic.judge_direction1(Const.controlPoint.getX(), Const.controlPoint.getY(),
					Const.controlPoint.getZ(), Const.lofting_point.getX(), Const.lofting_point.getY(),
					Const.lofting_point.getZ(), Const.shishi_point.getX(), Const.shishi_point.getY(),
					Const.shishi_point.getZ());

			if (i1 == -2 || i1 == -1) {
				LN100CommandSend.stopRotation();
				LN100CommandSend.startRotateAngle(1, 0, 0, 1, (float) j * 1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				LN100CommandSend.stopRotation();
				LN100CommandSend.startAutoTrack(0);
			}
			if (i1 == 2 || i1 == 1) {
				LN100CommandSend.stopRotation();
				LN100CommandSend.startRotateAngle(1, 0, 0, 1, (float) j * -1);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				LN100CommandSend.stopRotation();
				LN100CommandSend.startAutoTrack(0);
			}
			break;
		case R.id.btn_add:
			LayoutInflater layoutInflater = LayoutInflater.from(ModelMain.this);
			View views = layoutInflater.inflate(R.layout.views_tanc, null);
			final EditText views_et = (EditText) views.findViewById(R.id.et_views);
			dialog = new AlertDialog.Builder(ModelMain.this).setTitle("保存视图").setIcon(android.R.drawable.ic_dialog_info)
					.setView(views).setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (views_et.getText().length() != 0) {
								InputMethodManager imm = (InputMethodManager) getSystemService(
										Context.INPUT_METHOD_SERVICE);
								imm.hideSoftInputFromWindow(views_et.getWindowToken(), 0);
								double[] a = RenderControl.get().camera.getCamera();
								if (SqliteUtil.exits(tablename + "_views") == false) {
									SqliteUtil.createTableviews(tablename + "_views");
								}
								SqliteUtil.insertUseviews(mydata, tablename + "_views", views_et.getText().toString(),
										a);
								viewlist = SqliteUtil.findviews(mydata, tablename + "_views");
								adapterviews = new ListViewAdpter(viewlist, tablename + "_views", ModelMain.this);
								model_list.setAdapter(adapterviews);

							} else {
								Toast.makeText(ModelMain.this, "请输入视图名称！", Toast.LENGTH_LONG).show();
							}
						}
					}).setNegativeButton("取消", null).create();
			;
			dialog.show();
			WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
			params.width = 600;
			params.height = 500;
			dialog.getWindow().setAttributes(params);
			break;
		case R.id.btn_addModel:
			ModelUtil.setButton_add();
			btn_modelInfo.setVisibility(8);
			btn_showModel.setVisibility(8);
			btn_addModel.setVisibility(8);
			break;
		case R.id.btn_modelInfo:
			ModelUtil.setButton_modelInfo();
			model_text.setText(Const.CONST_mx_text);
			btn_add.setVisibility(8);
			btn_modelInfo.setVisibility(8);
			btn_showModel.setVisibility(8);
			btn_addModel.setVisibility(8);
			break;
		case R.id.btn_showModel:
			ModelUtil.setButton_showModel();
			btn_modelInfo.setVisibility(8);
			btn_showModel.setVisibility(8);
			btn_addModel.setVisibility(8);
			break;
		case R.id.collect:// 收集
			sp.play(musicdianji, 1, 1, 0, 0, 1);
			if (Const.point != null) {
				fy_Name.setText(Const.point.getPointNumber() + "_STK");
				fy_id.setText(Const.point.getOid() + "");
				fy_Role.setText(Const.point.getPointRole());
				fy_type.setText(Const.point.getType());
				fy_x.setText(PrecisionUtil.decimal3(Const.shishi_point.getX()));
				fy_y.setText(PrecisionUtil.decimal3(Const.shishi_point.getY()));
				fy_z.setText(PrecisionUtil.decimal3(Const.shishi_point.getZ()));
				et_save.setText(Const.point.getComment());
				r_collect.setVisibility(View.VISIBLE);
				r_connect.setVisibility(View.GONE);
			} else {
				Toast.makeText(v.getContext(), "请先选择放样点", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.fy_save:// 保存放样点
			// mHeight是contentView初始化完成的高度，contentView.getHeight()是键盘弹出后取的的高
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (mHeight < surfaceView.getHeight()) {
				// 隐藏键盘
				imm.hideSoftInputFromWindow(ModelMain.this.getCurrentFocus().getWindowToken(), 0);
			}
			if (fy_Name.getText().toString().length() != 0) {

				if (Const.shishi_point.getX() != 0 && Const.shishi_point.getY() != 0
						&& Const.shishi_point.getZ() != 0) {
					SqliteUtil.insertUseContentValues(mydata, tablename, Const.point.getOid(),
							fy_Role.getText().toString(), fy_type.getText().toString(), fy_Name.getText().toString(),
							Const.shishi_point.getX(), Const.shishi_point.getY(), Const.shishi_point.getZ(), "已放样点",
							et_save.getText().toString());
				} else {
					Toast.makeText(ModelMain.this, "请先进行架站操作！", Toast.LENGTH_LONG).show();
				}
				if (Const.CONST_modelLayer != null && Const.shishi_point.getX() != 0 && Const.shishi_point.getY() != 0
						&& Const.shishi_point.getZ() != 0) {
					Const.CONST_rpoi3 = Const.CONST_modelLayer.createRenderModelPoint(3);
					String a = Const.CONST_rpoi3.getFdeGeometry();
					Const.CONST_rpoi3.setX(Const.shishi_point.getX());
					Const.CONST_rpoi3.setY(Const.shishi_point.getY());
					Const.CONST_rpoi3.setZ(Const.shishi_point.getZ());
				}
				r_collect.setVisibility(View.GONE);

			} else {
				Toast.makeText(ModelMain.this, "名称不能为空！", Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.cancel:// 收集界面取消
			// mHeight是contentView初始化完成的高度，contentView.getHeight()是键盘弹出后取的的高
			InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (mHeight < surfaceView.getHeight()) {
				// 隐藏键盘
				imm2.hideSoftInputFromWindow(ModelMain.this.getCurrentFocus().getWindowToken(), 0);
			}
			r_collect.setVisibility(View.GONE);
			break;
		/*
		 * case R.id.img_location:// 轴网定位
		 * img_location.setScaleType(ImageView.ScaleType.FIT_CENTER);
		 * Toast.makeText(ModelMain.this, img_location.getHeight() + "",
		 * Toast.LENGTH_LONG).show(); if (img_location.getHeight() < 200) {
		 * img_location.setImageBitmap(
		 * tool.scaleBitmap(BitmapFactory.decodeResource(getResources(),
		 * R.drawable.img2), 1f)); } else {
		 * img_location.setImageResource(R.drawable.img_location); } break;
		 */
		/*
		 * case R.id.btn_setcamera:// 轴网飞入
		 * views1.setImageResource(R.drawable.views);
		 * views11.setTextColor(Color.BLACK);
		 * 
		 * image_lofting1.setImageResource(R.drawable.views);
		 * image_lofting11.setTextColor(Color.BLACK); // 关抽屉 if (panel.isOpen())
		 * { panel.close(iv, r_connect); }
		 * models1.setImageResource(R.drawable.models);
		 * models11.setTextColor(Color.BLACK); if
		 * (!RenderControl.get().getInteractMode().equals(gviInteractMode.
		 * gviInteractNormal)) {
		 * 
		 * RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal
		 * ); } measure1.setImageResource(R.drawable.measure);
		 * measure11.setTextColor(Color.BLACK); if
		 * (RenderControl.get().getInteractMode().equals(gviInteractMode.
		 * gviInteractMeasurement)) {
		 * RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal
		 * ); }
		 * 
		 * btn_modelInfo.setVisibility(8); btn_showModel.setVisibility(8);
		 * btn_addModel.setVisibility(8);
		 * r_navigation.setVisibility(View.VISIBLE);
		 * table.setVisibility(View.VISIBLE);
		 * chouti.setVisibility(View.VISIBLE); panel.xopen();
		 * iv.setVisibility(View.VISIBLE); if (flag_mouseMode == 4) { float x =
		 * img_location.getX() + (img_location.getWidth() / 2); float y =
		 * img_location.getY() + (img_location.getHeight() / 2); IPickResult pr
		 * = RenderControl.get().camera.screenToWorld(x, y); IVector3 vr = new
		 * IVector3(); // 高度判断 vr.set(pr.intersectX, pr.intersectY,
		 * Const.CONST_domain_.get(Const.CONST_selDomain) + 0.6);
		 * Toast.makeText(ModelMain.this,
		 * Const.CONST_domain_.get(Const.CONST_selDomain) + " " +
		 * Const.CONST_selDomain, Toast.LENGTH_LONG) .show(); IEulerAngle e =
		 * new IEulerAngle(); e.set(img_location.getRotation(), 0, 0);
		 * RenderControl.get().camera.setCamera(vr, e,
		 * gviSetCameraFlags.gviSetCameraNoFlags); back();
		 * ModelUtil.hideModels(false); } break;
		 */
		case R.id.sort_layout:
			if (layout_sort.getVisibility() == View.GONE) {
				layout_sort.setVisibility(View.VISIBLE);
				layout_filter.setVisibility(View.GONE);
				layout_sou.setVisibility(View.GONE);
			} else {
				layout_sort.setVisibility(View.GONE);
			}
			break;
		case R.id.filers_layout:
			if (layout_filter.getVisibility() == View.GONE) {
				layout_filter.setVisibility(View.VISIBLE);
				layout_sort.setVisibility(View.GONE);
				layout_sou.setVisibility(View.GONE);
			} else {
				layout_filter.setVisibility(View.GONE);
			}
			break;

		case R.id.suo:
			if (layout_sou.getVisibility() == View.GONE) {
				layout_sou.setVisibility(View.VISIBLE);
				layout_sort.setVisibility(View.GONE);
				layout_filter.setVisibility(View.GONE);
			} else {
				layout_sou.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_find:
			EditText et_find = (EditText) findViewById(R.id.et_find);
			if (et_find.getText().toString().length() > 0) {
				String str_find = et_find.getText().toString();
				new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points,
						isShow, list_hide, adapter, model_layout, tablename, domain_list, progressDialog)
								.findlike(mydata, tablename, str_find);

			} else {
				Toast.makeText(ModelMain.this, "请输入查询内容！", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.find_closd:
			layout_sou.setVisibility(View.GONE);
			break;

		case R.id.sys_setting:
			PopMenu popMenu = new PopMenu(this);
			popMenu.addItems(new String[] { "设置", "关于", "登录" });
			popMenu.setOnItemClickListener(new com.tianhedaoyun.lgmr.util.PopMenu.OnItemClickListener() {

				@Override
				public void onItemClick(int index) {
					if (index == 2) {
						Intent intent = new Intent(ModelMain.this, AtyLogin.class);
						startActivity(intent);
					}
					if (index == 1) {
						Intent intent = new Intent(ModelMain.this, About.class);
						startActivity(intent);
					}
					if (index == 0) {
						Intent intent1 = new Intent(ModelMain.this, AtySetting.class);
						startActivity(intent1);

					}
				}
			});
			popMenu.showAsDropDown(v);
			break;

		case R.id.export:

			FolderFilePicker picker = new FolderFilePicker(this, new PickPathEvent() {

				@Override
				public void onPickEvent(String resultPath) {
					mPath = resultPath;
					new AsyncTask<String, Void, Boolean>() {

						@Override
						protected void onPostExecute(Boolean result) {
							// TODO Auto-generated method stub
							if (result) {
								Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_LONG).show();
							}
						}

						@Override
						protected Boolean doInBackground(String... params) {
							// TODO Auto-generated method stub
							List<Point> list = SqliteUtil.find(mydata, tablename);
							StringBuffer buffer = new StringBuffer();
							for (int i = 0, len = list.size(); i < len; i++) {
								String a = list.get(i).getPointNumber() + "," + list.get(i).getPointRole() + ","
										+ list.get(i).getX() + "," + list.get(i).getY() + "," + list.get(i).getZ()
										+ "\n";
								buffer.append(a);

							}
							FileUtil.delCsv(getApplicationContext());
							FileUtil.saveCsv(buffer.toString(), getApplicationContext(), mPath, tablename);
							return true;
						}

					}.execute();
				}
			});
			picker.show();

			break;

		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 开启测量线程
		measureReturn();
		LN100FSM.s_Lock = false;
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 解除绑定
		unbindService(conn);
		// 注销广播
		unregisterReceiver(msgReceiver);
		flag = false;
		isPrism = false;
		// mmThread.currentThread().interrupt();
		Const.isrunning = false;
		sp.release();
		prismHandler.removeCallbacksAndMessages(null);
		handler.removeCallbacksAndMessages(null);
		mHandler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	private void lianjie_prism() {
		final View view = (FrameLayout) getLayoutInflater().inflate(R.layout.rudder, null);
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("操作机器人，连接棱镜");
		builder.setView(view);
		Rudder rudder = (Rudder) view.findViewById(R.id.rudder);

		rudder.setRudderListener(new RudderListener() {
			@Override
			public void onSteeringWheelChanged(int action, int angle) {
				if (action == Rudder.ACTION_RUDDER) {
					if (angle > 45 & angle < 135) {
						if (!isRotateTop) {
							// 实现机器人旋转
							if (isRotateBottom | isRotateLeft | isRotateRight) {
								LN100CommandSend.stopRotation();
								isRotateBottom = false;
								isRotateLeft = false;
								isRotateRight = false;
							}
							LN100CommandSend.startRotateVelocity(-8, 0);
							isRotateTop = true;
						}
					} else if (angle > 135 & angle < 225) {
						if (!isRotateLeft) {
							// 实现机器人旋转
							if (isRotateBottom | isRotateTop | isRotateRight) {
								LN100CommandSend.stopRotation();
								isRotateBottom = false;
								isRotateTop = false;
								isRotateRight = false;
							}
							LN100CommandSend.startRotateVelocity(0, 9);
							isRotateLeft = true;
						}

					} else if (angle > 225 & angle < 315) {
						if (!isRotateBottom) {
							// 实现机器人旋转
							if (isRotateTop | isRotateLeft | isRotateRight) {
								LN100CommandSend.stopRotation();
								isRotateTop = false;
								isRotateLeft = false;
								isRotateRight = false;
							}
							LN100CommandSend.startRotateVelocity(8, 0);
							isRotateBottom = true;
						}

					} else {
						if (!isRotateRight) {
							// 实现机器人旋转
							if (isRotateBottom | isRotateTop | isRotateLeft) {
								LN100CommandSend.stopRotation();
								isRotateLeft = false;
								isRotateBottom = false;
								isRotateTop = false;
							}
							LN100CommandSend.stopRotation();
							LN100CommandSend.startRotateVelocity(0, -9);
							isRotateRight = true;
						}
					}

				}
			}

			@Override
			public void onTouchLeave(int i) {
				if (i == 3) {
					LN100CommandSend.stopRotation();
					isRotateLeft = false;
					isRotateRight = false;
				}
			}
		});
		builder.setPositiveButton("连接", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (!LN100Util.getInstance().isConnected()) {
					Toast.makeText(getApplicationContext(), "请连接正确的wifi", Toast.LENGTH_LONG).show();
					return;
				} else {
					// 开始连接棱镜
					LN100CommandSend.stopRotation();
					LN100CommandSend.startAutoTrack(0);
				}

			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.create().show();
	}

	// 异步加载sdb文件
	private void AsyncLoadSdb() {
		_mThread = new MThread() {

			@Override
			public void doRun() {
				// TODO Auto-generated method stub
				new AnotherTask(ModelMain.this, PACH, cachePath, tablename, lv, point_x, point_y, point_z, points,
						progressDialog).execute();
			}
		};
		_mThread.start();
	}

	// 初始化缓存目录
	private void init_cachePath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cachePath = getExternalFilesDir("").getAbsolutePath();
		} else {
			cachePath = getFilesDir().getAbsolutePath();
		}
	}

	// 初始化三维模型
	private void initial_model() {
		setButton();
		loadModelListView();
		new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points, isShow,
				list_hide, adapter, model_layout, tablename, domain_list, progressDialog).initial_model(surfaceView);
	}
	/*
	 * // 捕捉返回键
	 * 
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * 
	 * if (keyCode == KeyEvent.KEYCODE_BACK) { startActivity(new
	 * Intent(ModelMain.this, SampleModels.class)); } return false; }
	 */

	// service
	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 返回一个MsgService对象
			wifiService = ((WifiService.MsgBinder) service).getService();

			wifiService.setMainActivity(_mWifi);

			wifiService.startListen();

		}
	};

	// lv上拉加载下拉刷新
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		lv.setRefreshTime(new Date().toString());
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		onLoad();
		new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points, isShow,
				list_hide, adapter, model_layout, tablename, domain_list, progressDialog).sqlfall(cachePath, tablename,
						mydata);
		ModelUtil.texall();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		onLoad();
		new ModelUtil(ModelMain.this, PACH, cachePath, point_x, point_y, point_z, model_list, lv, points, isShow,
				list_hide, adapter, model_layout, tablename, domain_list, progressDialog).sqlfall(cachePath, tablename,
						mydata);
		ModelUtil.texall();
	}

	public static ImageButton btn_modelInfo, btn_showModel, btn_addModel;
	public static ImageView img_location;
	private static ImageButton btn_setcamera;

	public void setButton() {
		img_location = ((ImageView) findViewById(R.id.img_location));
		img_location.setImageResource(R.drawable.img_location);
		img_location.setOnClickListener(this);

		btn_addModel = ((ImageButton) findViewById(R.id.btn_addModel));
		btn_addModel.setImageBitmap(
				tool.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.addmodel), 1.5f));
		btn_addModel.setVisibility(8);
		btn_addModel.setOnClickListener(this);

		btn_modelInfo = ((ImageButton) findViewById(R.id.btn_modelInfo));
		btn_modelInfo.setImageBitmap(
				tool.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.modelinfo), 1.5f));
		btn_modelInfo.setVisibility(8);
		btn_modelInfo.setOnClickListener(this);

		btn_showModel = ((ImageButton) findViewById(R.id.btn_showModel));
		btn_showModel.setImageBitmap(
				tool.scaleBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.showmodel), 1.5f));
		btn_showModel.setVisibility(8);
		btn_showModel.setOnClickListener(this);

		/*
		 * btn_setcamera = ((ImageButton) findViewById(R.id.btn_setcamera));
		 * btn_setcamera.setImageBitmap(
		 * tool.scaleBitmap(BitmapFactory.decodeResource(getResources(),
		 * R.drawable.btn_setcamera), 1f));
		 * btn_setcamera.setOnClickListener(this);
		 */
	}

	public void loadModelListView() {
		model_layout.setVisibility(4);
		Const.CONST_txt = new ArrayList<String>();
		Const.CONST_txt.add("工程图层");
		isShow = new ArrayList<Boolean>();
		isShow.add(true);
		adapter = new DragListAdapter(ModelMain.this, Const.CONST_txt, isShow);
	}

	public static void back() {

		btn_modelInfo.setVisibility(8);
		btn_showModel.setVisibility(8);
		btn_addModel.setVisibility(8);
		img_location.setVisibility(8);
		// btn_back.setVisibility(8);
		flag_mouseMode = 1;
		model_list.setVisibility(8);
		btn_setcamera.setVisibility(8);
		// RenderControl.get().camera.leaveOrthoModeT(); //
		RenderControl.get().setInteractMode(gviInteractMode.gviInteractWalk2);
		domain_list.setVisibility(8);

		btn_modelInfo.setVisibility(8);
		btn_showModel.setVisibility(8);
		btn_addModel.setVisibility(8);

		model_list.setVisibility(8);
		btn_setcamera.setVisibility(8);
		// RenderControl.get().camera.leaveOrthoModeT();
		RenderControl.get().setInteractMode(gviInteractMode.gviInteractWalk2);
		domain_list.setVisibility(8);
		if (img_location.getHeight() > 200) {
			img_location.setImageResource(R.drawable.img_location);
		}
		img_location.setVisibility(4);
	}

	private IEnvelope getMaxEnvelope() {
		IEnvelope featureEnvelope = null;
		for (int i = 0; i < Const.CONST_flIds.length; i++) {

			IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager
					.getObjectById(Const.CONST_flIds[i]);
			IEnvelope env1 = layer1.getEnvelope();
			if (featureEnvelope == null) {
				featureEnvelope = env1;
			} else {
				featureEnvelope.expandByEnvelope(env1);
			}
		}
		double[] a = RenderControl.get().camera.getCamera();
		IEnvelope e = new IEnvelope(a[0] - 5, a[0] + 5, a[1] - 5, a[1] + 5, a[2] - 5, a[2] + 5);
		featureEnvelope.expandByEnvelope(e);
		return featureEnvelope;
	}

}
