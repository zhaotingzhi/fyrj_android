package com.tianhedaoyun.lgmr.model.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.gvitech.android.EnumValue.gviActionCode;
import com.gvitech.android.EnumValue.gviConnType;
import com.gvitech.android.EnumValue.gviDataSetType;
import com.gvitech.android.EnumValue.gviDomainType;
import com.gvitech.android.EnumValue.gviInteractMode;
import com.gvitech.android.EnumValue.gviMeasurementMode;
import com.gvitech.android.EnumValue.gviMouseSelectObjectMask;
import com.gvitech.android.EnumValue.gviObjectType;
import com.gvitech.android.EnumValue.gviSetCameraFlags;
import com.gvitech.android.EnumValue.gviViewportMask;
import com.gvitech.android.ICodedValueDomain;
import com.gvitech.android.IConnectionInfo;
import com.gvitech.android.IDataSource;
import com.gvitech.android.IDataSourceFactory;
import com.gvitech.android.IDomain;
import com.gvitech.android.IEulerAngle;
import com.gvitech.android.IFdeCursor;
import com.gvitech.android.IFeatureClass;
import com.gvitech.android.IFeatureDataset;
import com.gvitech.android.IFeatureLayer;
import com.gvitech.android.IFieldInfo;
import com.gvitech.android.IFieldInfoCollection;
import com.gvitech.android.ILabel;
import com.gvitech.android.IPickResult;
import com.gvitech.android.IQueryFilter;
import com.gvitech.android.IRenderModelPoint;
import com.gvitech.android.IRenderPoint;
import com.gvitech.android.IRenderPolyline;
import com.gvitech.android.IRowBuffer;
import com.gvitech.android.IRowBufferCollection;
import com.gvitech.android.ISimpleGeometryRender;
import com.gvitech.android.IVector3;
import com.gvitech.android.RenderControl;
import com.gvitech.android.RenderControlCallback;
import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.activity.ModelMain;
import com.tianhedaoyun.lgmr.activity.tool;
import com.tianhedaoyun.lgmr.adapter.DragListAdapter;
import com.tianhedaoyun.lgmr.adapter.MyAdapters;
import com.tianhedaoyun.lgmr.bean.Point;
import com.tianhedaoyun.lgmr.math.PrecisionUtil;
import com.tianhedaoyun.lgmr.task.AnotherTask;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.SharedObjectUtil;
import com.tianhedaoyun.lgmr.util.SqliteUtil;
import com.tianhedaoyun.lgmr.view.XListView;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ModelUtil {

	static ArrayList<Double> arrPoints = new ArrayList<Double>();
	static ArrayList<Double> arrPoints1 = new ArrayList<Double>();
	public static IRenderPolyline po, po1;// 画线用
	public static String selectObj = "";
	public static int flag_mouseMode = 1;
	public static IRenderModelPoint tempModelPoint = null;
	private String findname;
	private String tablename;
	private int findoid;
	private TextView point_x, point_y, point_z, points;
	private static Context context;
	private static ListView model_list;
	private XListView lv;
	private IRenderPoint rPoint = null;
	private String PACH;
	private String cachePath;
	private ProgressDialog progressDialog;
	public static ArrayList<Boolean> isShow;
	private static HashMap<Integer, ArrayList<Integer>> list_hide;
	private static DragListAdapter adapter;
	private static RelativeLayout model_layout;
	private static ListView domain_list;
	static HashMap<String, Integer> domain_c = new HashMap<String, Integer>();
	static ArrayList<String> domainName = new ArrayList<String>();

	public ModelUtil(Context context, String PACH, String cachePath, TextView point_x, TextView point_y,
			TextView point_z, ListView model_list, XListView lv, TextView points, ArrayList<Boolean> isShow,
			HashMap<Integer, ArrayList<Integer>> list_hide, DragListAdapter adapter,
			RelativeLayout model_layout, String tablename, ListView domain_list, ProgressDialog progressDialog) {
		// TODO Auto-generated constructor stub
		this.point_x = point_x;
		this.point_y = point_y;
		this.point_z = point_z;
		this.PACH = PACH;
		this.cachePath = cachePath;
		this.context = context;
		this.model_list = model_list;
		this.lv = lv;
		this.points = points;
		this.isShow = isShow;
		this.list_hide = list_hide;
		this.adapter = adapter;
		this.model_layout = model_layout;
		this.tablename = tablename;
		this.domain_list = domain_list;
		this.progressDialog=progressDialog;
	}

	// 初始化三维模型
	public void initial_model(SurfaceView surfaceView) {
		RenderControl.get().initialize(context, surfaceView, context.getAssets(), new RenderControlCallback() {
			public void onInitialFinished() {
				RenderControl.get().objectManager.getSkyBox().setDefaultSkybox();
				RenderControl.get().camera.setFlyTime(1);
				RenderControl.get().viewport.setCompassOffset(-32.f, -64.f);
				RenderControl.get().viewport.setThumbtackOffset(-32.f, -150.f);
				RenderControl.get()
				.setMouseSelectObjectMask(gviMouseSelectObjectMask.gviSelectRenderGeometry.getValue()
						| gviMouseSelectObjectMask.gviSelectLable.getValue()
						| gviMouseSelectObjectMask.gviSelectFeatureLayer.getValue());

				String macCode = RenderControl.get().getSignatureCode();

				new AnotherTask(context, PACH, cachePath, tablename, lv, point_x, point_y, point_z, points,progressDialog).execute();

			}

			public void onPause() {
            System.out.println("onPause2");
			}

			public void onRest() {
				RenderControl.get().objectManager.getSkyBox().setDefaultSkybox();
			}

			public void onDestroy() {

			}

			public void onTouch(View paramView, MotionEvent event) {
				if (RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractMeasurement)) {
					return;
				}
				float x = event.getX();
				float y = event.getY();
				if(tablename!=null){
					Const.CONST_findlist= SqliteUtil.find(null, tablename);
					Const.CONST_madapter = new MyAdapters(context);
				}

				IPickResult pr = RenderControl.get().camera.screenToWorld(x, y);
				ModelMain.btn_modelInfo.setVisibility(8);
				ModelMain.btn_showModel.setVisibility(8);
				ModelMain.btn_addModel.setVisibility(8);
				/*
				 * if(flag_mouseMode==4){ img_location.setVisibility(0);
				 * img_location.setX(x-img_location.getWidth()/2);
				 * img_location.setY(y-img_location.getHeight()/2); return; }
				 */

				if (pr != null && pr.pickObject != null) {
					gviObjectType objType = pr.pickObject.getType();
					switch (objType) {
					case gviObjectRenderPoint: {
						set_xyz(pr);
						setRed();
					}
					break;
					case gviObjectRenderPolyline: {
						set_xyz(pr);
						setRed();
					}
					break;
					case gviObjectLabel: {
						set_xyz(pr);
						setRed();
					}
					break;
					case gviObjectRenderPolygon: {
						set_xyz(pr);
						setRed();
					}
					break;
					// ------------------------------------
					case gviObjectFeatureLayer: {
						
						IFeatureLayer layer = (IFeatureLayer) pr.pickObject;
						layer.unhighlightAll();
						for (int i = 1; i < Const.CONST_txt.size(); i++) {
							IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager
									.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
							if (model.getName().equals(selectObj)) {
								model.unhighlight();
								break;
							}
						}
						if ((layer.objectId() + "-" + pr.fid).equals(selectObj)) {
							selectObj = "";
							setRed();
							return;
						}
						layer.highlightFeature(pr.fid, Color.BLUE);
						selectObj = layer.objectId() + "-" + pr.fid;
						System.out.println("拾取到的X:" + x + "  ;拾取到的Y:" + y);
						
						Const.CONST_IntTest = RenderControl.get().camera.getCamera();
						
						if(Const.CONST_show_button_){showButton(x, y);}
						if (flag_mouseMode == 3) {
							IRowBuffer rb = layer.searchById(pr.fid);
							String Properties = rb.GetBlob(rb.FieldIndex("Properties"));
							Properties = Properties.replace("l version=\"1.0\" standalone=\"yes\"?>", "");
							try {
								ArrayList<HashMap<String, String>> list_info = new ArrayList<HashMap<String, String>>();
								// 添加数字 从1添加到5
								StringReader sr = new StringReader(Properties);
								InputSource is = new InputSource(sr);
								DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								DocumentBuilder builder = factory.newDocumentBuilder();
								Document doc = builder.parse(is);

								for (int i = 0; i < doc.getElementsByTagName("Property").getLength(); i++) {
									HashMap<String, String> item = new HashMap<String, String>();
									// Node a =
									// doc.getElementsByTagName("Property").item(i);
									Element nl = (Element) doc.getElementsByTagName("Property").item(i);
									if (nl.getAttribute("Name").equals("PointNumber")) {
										findname = nl.getTextContent();
									}
									item.put("txt1", nl.getAttribute("Name"));
									item.put("txt2", nl.getTextContent());
									list_info.add(item);
								}

								SimpleAdapter adapter_info = new SimpleAdapter(context, list_info, R.layout.list_item2,
										new String[] { "txt1", "txt2" }, new int[] { R.id.txt1, R.id.txt2 });
								model_list.setAdapter(adapter_info);

							} catch (Throwable e) {
								new RuntimeException(e);
							}
						}
						set_xyz(pr);
						setRed();

						if (layer.getFeatureClassName().equals(Const.CONST_conventional_model)) {
							IRowBuffer rb = layer.searchById(pr.fid);
							String Properties = rb.GetBlob(rb.FieldIndex("Properties"));
							Properties = Properties.replace("l version=\"1.0\" standalone=\"yes\"?>", "");
							StringReader sr = new StringReader(Properties);
							InputSource is = new InputSource(sr);
							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
							DocumentBuilder builder;
							Document doc = null;
							try {
								builder = factory.newDocumentBuilder();
								doc = builder.parse(is);
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for (int i = 0; i < doc.getElementsByTagName("Property").getLength(); i++) {
								HashMap<String, String> item = new HashMap<String, String>();
								// Node a =
								// doc.getElementsByTagName("Property").item(i);
								Element nl = (Element) doc.getElementsByTagName("Property").item(i);
								if (nl.getAttribute("Name").equals("PointNumber")) {
									findname = nl.getTextContent();
								}

							}

							layer.highlightFeature(pr.fid, Color.RED);
							IRowBuffer rb111 = layer.searchById(pr.fid);
							findoid = rb111.GetInt32(rb111.FieldIndex("oid"));
							int findid = 0;
							for (int j = 0; j < Const.CONST_findlist.size(); j++) {
								if (Const.CONST_findlist.get(j).getPointNumber().equals(findname)) {
									findid = j + 1;
								
								}
							}
							// -----------------------------
							lv.setAdapter(Const.CONST_madapter);
							lv.setSelection(findid);
							lv.smoothScrollToPositionFromTop(findid, 0);
							Const.CONST_madapter.setSelectItem(findid);
							Const.CONST_madapter.notifyDataSetInvalidated();
							
							
							
					

							if (SharedObjectUtil.readObject(context, "station_info") != null) {
								Const.lofting_point = Const.CONST_findlist.get(findid - 1);
							}else{
								Const.point = Const.CONST_findlist.get(findid - 1);
							}


							if (Const.CONST_PD) {
								if (Const.CONST_modelLayer != null) {
									if (Const.CONST_rpoi1 != null) {
										RenderControl.get().objectManager.deleteObject(Const.CONST_rpoi1.objectId());
									}
									Const.CONST_rpoi1 = Const.CONST_modelLayer.createRenderModelPoint(2);
									Const.CONST_rpoi1.setX(Const.point.getX());
									Const.CONST_rpoi1.setY(Const.point.getY());
									Const.CONST_rpoi1.setZ(Const.point.getZ());
								}
							}

							btnCreateLabel(Const.CONST_findlist.get(findid - 1).getX(), Const.CONST_findlist.get(findid - 1).getY(),
									Const.CONST_findlist.get(findid - 1).getZ(), findname);

							setRed();	

						}

						if (rPoint != null)
							RenderControl.get().objectManager.deleteObject(rPoint.objectId());
						CharSequence txtOut = null;
						txtOut = txtOut + ";FeatureClassId = " + layer.getFeatureClassId() + ";GeometryFieldName = "
								+ layer.getGeometryFieldName() + ";DataSourceConnectionString = "
								+ layer.getDataSourceConnectionString() + ";DataSetName = " + layer.getDataSetName()
								+ ";FeatureClassName = " + layer.getFeatureClassName();

						IRowBuffer buffer = layer.searchById(pr.fid);
						int fcnt = buffer.GetFieldCount();
						IFieldInfoCollection cols = buffer.GetFields();
						for (int i = 0; i < fcnt; i++) {
							IFieldInfo field = cols.GetFieldInfo(i);
							String fname = field.getName();
							txtOut = txtOut + ";Name = " + fname;

							switch (field.getFieldType()) {
							case gviFieldInt8:
								byte v1 = buffer.GetInt8(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v1;
								break;
							case gviFieldInt16:
								short v2 = buffer.GetInt16(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v2;
								break;
							case gviFieldInt32:
								int v3 = buffer.GetInt32(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v3;
								break;
							case gviFieldInt64:
								long v4 = buffer.GetInt64(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v4;
								break;
							case gviFieldFloat:
								float v5 = buffer.GetFloat(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v5;
								break;
							case gviFieldDouble:
								double v6 = buffer.GetFloat(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v6;
								break;
							case gviFieldString:
								String v7 = buffer.GetString(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v7;
								break;
							case gviFieldDate:
								String v8 = buffer.GetDateTime(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v8;
								break;
							case gviFieldBlob:
								String v9 = buffer.GetBlob(i);
								txtOut = txtOut + ";pos = " + i + " value = " + v9;
								break;
							default:
								break;
							}
						}

					}
					break;
					case gviObjectRenderModelPoint: {
						IRenderModelPoint model = (IRenderModelPoint) pr.pickObject;
						model.highlight(Color.RED);
						if ((model.getName()).equals(selectObj)) {
							model.unhighlight();
							selectObj = "";
							return;
						}
						selectObj = model.getName();
						if(Const.CONST_show_button_){showButton(x, y);}
						setRed();
					}
					break;

					default:
						set_xyz(pr);
						setRed();
						break;
					}
				} else {
					// 点选时清空处理
					IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[0]);
					layer1.unhighlightAll();
					for (int i = 1; i < Const.CONST_txt.size(); i++) {
						IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
						if (model!=null&&model.getName().equals(selectObj)) {
							model.unhighlight();
							break;
						}
					}
					selectObj = "";
					return;
				}

			}

			public void onCameraFlyFinished(int type) {
			}

		});
	}

	public void sqlfall(String path, final String tablename, SQLiteDatabase mydata) {
		if (SqliteUtil.find(mydata, tablename).size() == 0) {
			point(path, tablename, mydata);
		}
		Const.CONST_findlist = SqliteUtil.find(mydata, tablename);
		Const.CONST_madapter = new MyAdapters(context);
		lv.setAdapter(Const.CONST_madapter);
		points.setText("共" + SqliteUtil.find(mydata, tablename).size() + "" + "条信息");
		lvItemEvent(tablename);
	}

	private void lvItemEvent(final String tablename) {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				System.out.println("POSITIPN:" + position);
				Const.CONST_madapter.setSelectItem(position);
				Const.CONST_madapter.notifyDataSetInvalidated();
				lv.smoothScrollToPositionFromTop(position, 0);
				Const.point = Const.CONST_findlist.get(position - 1);
				System.out.println("坐标：" + Const.point.getX() + "FFFFFFFFFFFFFFFFFFFFFFFfff");
				point_x.setText(PrecisionUtil.decimal3(Const.point.getX()));
				point_y.setText(PrecisionUtil.decimal3(Const.point.getY()));
				point_z.setText(PrecisionUtil.decimal3(Const.point.getZ()));
				if (Const.CONST_PD) {
					if (Const.CONST_modelLayer != null) {
						if (Const.CONST_rpoi1 != null) {
							RenderControl.get().objectManager.deleteObject(Const.CONST_rpoi1.objectId());
						}
						Const.CONST_rpoi1 = Const.CONST_modelLayer.createRenderModelPoint(2);
						Const.CONST_rpoi1.setX(Const.point.getX());
						Const.CONST_rpoi1.setY(Const.point.getY());
						Const.CONST_rpoi1.setZ(Const.point.getZ());
					}
				}
				ModelUtil.btnCreateLabel(Const.point.getX(), Const.point.getY(), Const.point.getZ(),
						Const.point.getPointNumber());
				ModelUtil.flyTo(Const.point.getX(), Const.point.getY(), Const.point.getZ(), 3.0, 0.0, -45.0, 0.0);

				setItemRed();

				if (Const.point.getState() == null) {
					Const.CONST_setdian = false;
				} else {
					Const.CONST_setdian = true;
				}
				Const.CONST_madapter.setdian(Const.CONST_setdian, tablename);

				if (SharedObjectUtil.readObject(context, "station_info") != null) {
					Const.lofting_point = Const.point;
				}

			}
		});
	}

	// 遍历点的代码
	public static void point(String path, String tablename, SQLiteDatabase mydata) {
		IConnectionInfo ci = IConnectionInfo.create();
		ci.setType(gviConnType.gviConnectionSQLite3);
		gviConnType nType = ci.getType();
		ci.setDataBaseName(path);
		String dbname = ci.getDataBaseName();
		IDataSourceFactory dsFactory = new IDataSourceFactory();
		boolean hasSource = dsFactory.hasDataSource(ci);
		if (hasSource == false) {
			return;
		}
		IDataSource ds = dsFactory.openDataSource(ci);
		if (ds.get_piObject() == 0) {
			return;
		}

		Integer dbCnt = 0;
		String[] dbnames = dsFactory.getDataBaseNames(ci, true, dbCnt);
		ci.free();
		Integer dsetCnt = 0;
		String[] dsetnames = ds.getFeatureDatasetNames(dsetCnt);
		IFeatureDataset dset = ds.openFeatureDataset(dsetnames[0]);
		if (dset.get_piObject() == 0)
			return;
		Integer fcCnt = 0;
		String[] fcnames = dset.getNamesByType(fcCnt, gviDataSetType.gviDataSetFeatureClassTable);
		for (int q = 0; q < fcCnt; q++) {
			if (fcnames[q].equals("常规模型")) {
				IFeatureClass fc = dset.openFeatureClass(fcnames[q]);
				if (fc.get_piObject() == 0)
					return;
				IQueryFilter qf = IQueryFilter.create();
				if (qf.get_piObject() == 0)
					return;
				qf.setWhereClause("");
				String where = qf.getWhereClause();
				IFdeCursor cursor = fc.search(qf, false);
				IRowBuffer buffer = null;

				while ((buffer = cursor.NextRow()).get_piObject() != 0) {
					int fcnt = buffer.GetFieldCount();
					IFieldInfoCollection cols = buffer.GetFields();

					Point point = new Point();
					for (int i = 0; i < fcnt; i++) {
						IFieldInfo field = cols.GetFieldInfo(i);
						if (field.getName().equals("Geometry")) {
							String xyz = buffer.GetGeometry(i);
							xyz = xyz.replace("point z (", "").replace(")", "");
							String[] nameArrays = xyz.split(" ");
							System.out.println("!!!!!!!!!!!!!!"+xyz);
							point.setX(Double.parseDouble(nameArrays[0]));
							point.setY(Double.parseDouble(nameArrays[1]));
							point.setZ(Double.parseDouble(nameArrays[2]));

						}
						if (field.getName().equals("Properties")) {
							String Properties = buffer.GetBlob(buffer.FieldIndex("Properties"));
							point.setOid(buffer.GetInt32(buffer.FieldIndex("oid")));// 测试oid是否循环

							Properties = Properties.replace("l version=\"1.0\" standalone=\"yes\"?>", "");
							if (Properties != "") {
								try {
									// 添加数字 从1添加到5
									StringReader sr = new StringReader(Properties);
									InputSource is = new InputSource(sr);
									DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
									DocumentBuilder builder = factory.newDocumentBuilder();
									Document doc = builder.parse(is);

									for (int a = 0; a < doc.getElementsByTagName("Property").getLength(); a++) {

										Element nl = (Element) doc.getElementsByTagName("Property").item(a);
										if (nl.getAttribute("Name").toString().equals("PointNumber")) {
											point.setPointNumber(nl.getTextContent());
										}
										if (nl.getAttribute("Name").toString().equals("PointRole")) {
											point.setPointRole(nl.getTextContent());

										}
										if (nl.getAttribute("Name").toString().equals("类型")) {
											point.setType(nl.getTextContent());
										}
									}
								} catch (Throwable e) {
									new RuntimeException(e);
								}
							}
						}
					}
					SqliteUtil.insertUseContentValues(mydata, tablename, point);
				}

			}
		}
	}

	// 加载sdb
	public static void lvModel(String path, String cachePath) {
		
		
		Const.CONST_NewDate = RenderControl.get().objectManager.createFeatureLayer(context.getExternalFilesDir("").getAbsolutePath()+"/newData.sdb", null);
		// 加载SDB场景
		Const.CONST_flIds = RenderControl.get().objectManager.createFeatureLayer(path, null);

		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
			layer1.setMinVisiblePixels(0);
		}

		for (int i = 0; i < Const.CONST_NewDate.length; i++) {
			IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_NewDate[i]);

			if (layer.getFeatureClassName().equals(Const.CONST_lofting_robot)) {
				Const.CONST_modelLayer = layer;
			}
		}

		SqliteUtil.initial(cachePath);

	}

	// 画线功能
	public static void polyline(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3,
			double z3) {
		// TODO Auto-generated method stub
		arrPoints.add(x1);
		arrPoints.add(y1);
		arrPoints.add(z1);
		arrPoints.add(x2);
		arrPoints.add(y2);
		arrPoints.add(z2);
		if (po == null) {
			po = RenderControl.get().objectManager.createRenderPolyline(arrPoints, Color.RED, 0);
		}
		arrPoints1.add(x2);
		arrPoints1.add(y2);
		arrPoints1.add(z2);
		arrPoints1.add(x3);
		arrPoints1.add(y3);
		arrPoints1.add(z3);
		if (po1 == null) {
			po1 = RenderControl.get().objectManager.createRenderPolyline(arrPoints1, Color.GREEN, 0);
		}
		po.setFdeGeometry("linestring z(" + x1 + " " + y1 + " " + z1 + "," + x2 + " " + y2 + " " + z2 + ")");
		po1.setFdeGeometry("linestring z(" + x2 + " " + y2 + " " + z2 + "," + x3 + " " + y3 + " " + z3 + ")");

	}

	// 测量功能
	public static void cl() {

		if (RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractMeasurement)) {
			ModelMain.back();

		} else {
			RenderControl.get().setInteractMode(gviInteractMode.gviInteractMeasurement);
			RenderControl.get().setMeasurementMode(gviMeasurementMode.gviMeasureMultiaxisDistance);
		}

	}

	public static void cl_del() {
		if (RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractMeasurement)) {
			RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
		}
	}

	public static void cl_new(){
		if( RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractMeasurement)){
			RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
		}else{
			RenderControl.get().setInteractMode(gviInteractMode.gviInteractMeasurement);
			RenderControl.get().setMeasurementMode(gviMeasurementMode.gviMeasureMultiaxisDistance);
		}

	}



	// 模型功能
	public static void mx() {
		if (!RenderControl.get().getInteractMode().equals(gviInteractMode.gviInteractNormal)) {

			RenderControl.get().setInteractMode(gviInteractMode.gviInteractNormal);
		}
	}

	// 轴网功能
	public static void zw(IVector3 vr, IEulerAngle e) {
		RenderControl.get().camera.setCamera(vr, e, gviSetCameraFlags.gviSetCameraNoFlags);
	}

	// 标签功能
	static ILabel m_lbl = null;

	public static void btnCreateLabel(Double x, Double y, Double z, String name) {
		if (m_lbl != null) {
			RenderControl.get().objectManager.deleteObject(m_lbl.objectId());
		}
		m_lbl = RenderControl.get().objectManager.createLabel(x, y, z, name, Color.argb(255, 255, 255, 255), 25,
				Color.argb(200, 108, 116, 255), 0.02);
		//RenderControl.get().camera.flyToObject(m_lbl.objectId(),gviActionCode.gviActionFlyTo);
	}
	// 飞入功能
	public static void flyTo(Double x, Double y, Double z, Double distance, Double heading, Double tilt, Double roll) {
		IVector3 v = new IVector3();
		v.set(x, y, z);
		IEulerAngle e = new IEulerAngle();
		e.set(heading, tilt, roll);
		RenderControl.get().camera.lookAt(v, distance, e);
	}

	// 设置显示X,Y,Z
	private void set_xyz(IPickResult pr) {
		point_x.setText(PrecisionUtil.decimal3(pr.intersectX));
		point_y.setText(PrecisionUtil.decimal3(pr.intersectY));
		point_z.setText(PrecisionUtil.decimal3(pr.intersectZ));
	}

	public static void setButton_add() {
		if (checkObject(false)) {
			int lid = Integer.parseInt(selectObj.split("-")[0]);
			int oid = Integer.parseInt(selectObj.split("-")[1]);
			IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(lid);
			layer.unhighlightAll();
			Boolean isNew = true;
			if (list_hide.containsKey(lid)) {
				ArrayList<Integer> a = list_hide.get(lid);
				a.add(oid);
				list_hide.put(lid, a);
				layer.hiddenFeatures(a);
				isNew = false;
			} else {
				ArrayList<Integer> a = new ArrayList<Integer>();
				a.add(oid);
				list_hide.put(lid, a);
				layer.hiddenFeatures(a);
			}
			IRenderModelPoint rpoi1 = layer.createRenderModelPoint(oid);
			rpoi1.setName(selectObj);
			rpoi1.setVisibleMask(gviViewportMask.gviViewNone);
			Const.CONST_txt.add(rpoi1.objectId() + "");
			isShow.add(false);
			selectObj = "";
			adapter.notifyDataSetChanged();
		}
	}

	public static void setButton_modelInfo() {
		if (flag_mouseMode == 3) {
			model_layout.setVisibility(8);
			flag_mouseMode = 1;
		} else {
			model_layout.setVisibility(0);
			flag_mouseMode = 3;
			if (selectObj.indexOf("-") != -1) {
				int lid = Integer.parseInt(selectObj.split("-")[0]);
				int oid = Integer.parseInt(selectObj.split("-")[1]);
				IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(lid);
				IRowBuffer rb = layer.searchById(oid);

				String Properties = rb.GetBlob(rb.FieldIndex("Properties"));
				Properties = Properties.replace("l version=\"1.0\" standalone=\"yes\"?>", "");
				try {
					ArrayList<HashMap<String, String>> list_info = new ArrayList<HashMap<String, String>>();
					// 添加数字 从1添加到5
					StringReader sr = new StringReader(Properties);
					InputSource is = new InputSource(sr);
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(is);

					for (int i = 0; i < doc.getElementsByTagName("Property").getLength(); i++) {
						HashMap<String, String> item = new HashMap<String, String>();
						// Node a =
						// doc.getElementsByTagName("Property").item(i);
						Element nl = (Element) doc.getElementsByTagName("Property").item(i);
						item.put("txt1", nl.getAttribute("Name"));
						item.put("txt2", nl.getTextContent());
						list_info.add(item);
					}

					SimpleAdapter adapter_info = new SimpleAdapter(context, list_info, R.layout.list_item2,
							new String[] { "txt1", "txt2" }, new int[] { R.id.txt1, R.id.txt2 });
					model_list.setAdapter(adapter_info);

				} catch (Throwable e) {
					new RuntimeException(e);
				}
			}
		}
	}

	public static void setButton_showModel() {
		if (selectObj == "") {
			return;
		}
		if (tempModelPoint == null) {
			if (checkObject(true)) {
				int lid = Integer.parseInt(selectObj.split("-")[0]);
				int oid = Integer.parseInt(selectObj.split("-")[1]);

				IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(lid);
				layer.unhighlightAll();
				hideModels(false);
				tempModelPoint = layer.createRenderModelPoint(oid);
				tempModelPoint.setName(selectObj);
			}
			for (int i = 0; i < Const.CONST_flIds.length; i++) {
				IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
				layer1.setVisibleMask(gviViewportMask.gviViewNone);
			}

			selectObj = "";
		} else {
			if (checkObject(false)) {
				RenderControl.get().objectManager.deleteObject(tempModelPoint.objectId());
			}
			for (int i = 0; i < Const.CONST_flIds.length; i++) {
				IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
				layer1.setVisibleMask(gviViewportMask.gviView0);
			}
			tempModelPoint = null;
		}
	}

	public static void showButton(float x, float y) {
		ModelMain.btn_showModel.setVisibility(0);
		System.out.println("xxxxx:" + x + "   ;YYYYYY" + y);
		ModelMain.btn_showModel.setTranslationX(x);
		ModelMain.btn_showModel.setTranslationY(y);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				ModelMain.btn_modelInfo.setVisibility(0);
				ModelMain.btn_addModel.setVisibility(0);
				float x = ModelMain.btn_showModel.getTranslationX();
				float y = ModelMain.btn_showModel.getTranslationY();
				System.out.println("xxxxx:" + x + " ;yyyyy" + y);
				ModelMain.btn_showModel.setTranslationX(x - 200);
				ModelMain.btn_showModel.setTranslationY(y);
				ModelMain.btn_modelInfo.setTranslationX(x -100);
				ModelMain.btn_modelInfo.setTranslationY(y);
				ModelMain.btn_addModel.setTranslationX(x);
				ModelMain.btn_addModel.setTranslationY(y - 70);
			}
		}, 50);
	}


	private static Boolean checkObject(Boolean bool) {
		for (int i = 1; i < Const.CONST_txt.size(); i++) {
			IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager
					.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
			if (model.getName().equals(selectObj)) {
				if (bool) {
					model.setVisibleMask(gviViewportMask.gviView0);
					tempModelPoint = model;
					isShow.set(i, true);
				} else {
					model.setVisibleMask(gviViewportMask.gviViewNone);
					isShow.set(i, false);
				}

				adapter.notifyDataSetChanged();
				selectObj = "";
				return false;
			}

		}
		return true;
	}

	public static void hideModels(Boolean bool){
		if(bool){
			for(int i=1;i<Const.CONST_txt.size();i++){
				IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
				model.setVisibleMask(gviViewportMask.gviViewNone);
			}
			for(int i=0;i<Const.CONST_flIds.length; i++){
				IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
				layer1.hiddenFeatures(new ArrayList<Integer>());
				for(int a=0;a<Const.CONST_domainName.size();a++){
					layer1.setGroupVisibleMask(domain_c.get(Const.CONST_domainName.get(a)), gviViewportMask.gviView0);
				}
				layer1.setGroupVisibleMask(Const.CONST_domainName.size(), gviViewportMask.gviView0);
				layer1.setVisibleMask(gviViewportMask.gviView0);
			}
		}else{
			for(int i=1;i<Const.CONST_txt.size();i++){
				IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
				if(isShow.get(i)) {	
					model.setVisibleMask(gviViewportMask.gviView0);}
				else{
					model.setVisibleMask(gviViewportMask.gviViewNone);}
			}

			for(int i=0;i<Const.CONST_flIds.length; i++)
			{
				IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
				layer1.hiddenFeatures(new ArrayList<Integer>());
				for(int a=0;a<Const.CONST_domainName.size();a++){
					layer1.setGroupVisibleMask(domain_c.get(Const.CONST_domainName.get(a)), gviViewportMask.gviView0);
				}
				layer1.setGroupVisibleMask(Const.CONST_domainName.size(), gviViewportMask.gviView0);

				if(list_hide.containsKey(Const.CONST_flIds[i])){
					ArrayList<Integer> a=list_hide.get(Const.CONST_flIds[i]);
					layer1.hiddenFeatures(a);
				}
			}
		}
	}

	public static void showModels() {
		for (int i = 1; i < Const.CONST_txt.size(); i++) {
			IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager
					.getObjectById(Integer.parseInt(Const.CONST_txt.get(i)));
			if (isShow.get(i)) {
				model.setVisibleMask(gviViewportMask.gviView0);
			} else {
				model.setVisibleMask(gviViewportMask.gviViewNone);
			}

		}
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
			layer1.hiddenFeatures(new ArrayList<Integer>());
			if (list_hide.containsKey(Const.CONST_flIds[i])) {
				ArrayList<Integer> a = list_hide.get(Const.CONST_flIds[i]);
				layer1.hiddenFeatures(a);
			}
		}
	}

	public static  void getDomain() {
		if (Const.CONST_domainName.size() != 0) {
			return;
		}
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
			layer1.setGroupVisibleMask(2, gviViewportMask.gviViewNone);

			IRowBufferCollection cols = layer1.search(null);
			for (int j = 0; j < cols.GetCount(); j++) {
				IRowBuffer buffer = cols.GetRowBuffer(j);
				int fcnt = buffer.GetFieldCount();
				IFieldInfoCollection fcols = buffer.GetFields();
				for (int f = 0; f < fcnt; f++) {
					IFieldInfo field = fcols.GetFieldInfo(f);
					String fname = field.getName();
					System.out.println(fname);
					IDomain domain = field.GetDomain();
					if (domain.get_piObject() == 0)
						continue;
					if (domain.getDomainType() == gviDomainType.gviDomainCodedValue) {
						ICodedValueDomain codeDomain = new ICodedValueDomain(domain.get_piObject());
						int nCodes = codeDomain.GetCodeCount();
						HashMap<Double, String> domain_temp = new HashMap<Double, String>();
						for (int c = 0; c < nCodes; c++) {
							String codeName = codeDomain.getCodeName(c);
							Double v6 = (Double) codeDomain.GetDouble(c);
							// System.out.println(codeName+","+v6+","+c);
							Const.CONST_domainName.add(codeName);
							Const.CONST_domain_.put(codeName, v6);
							domain_c.put(codeName, c);
							domain_temp.put(v6, codeName);
						}
						ArrayList<String> temp_arr = new ArrayList<String>();
						while (Const.CONST_domainName.size() > 1) {
							Double temp = 0.0;
							for (int a = 0; a < Const.CONST_domainName.size(); a++) {
								if (a == 0) {
									temp = Const.CONST_domain_.get(Const.CONST_domainName.get(a));
									continue;
								}
								temp = Math.max(temp, Const.CONST_domain_.get(Const.CONST_domainName.get(a)));

							}
							System.out.println(temp);
							temp_arr.add(domain_temp.get(temp));
							Const.CONST_domainName.remove(domain_temp.get(temp));
						}
						temp_arr.add(Const.CONST_domainName.get(0));
						Const.CONST_domainName = temp_arr;

					}
					domain_list.setVisibility(0);
					String[] a = (String[]) Const.CONST_domainName.toArray(new String[Const.CONST_domainName.size()]);
					domain_list.setAdapter(new ArrayAdapter<String>(context, R.layout.list_item2, R.id.txt1, a));
					domain_list.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long rowid) {
							Const.CONST_selDomain = Const.CONST_domainName.get(position);
							showDomain(position);
						}
					});
					return;
				}
			}
		}
	}

	public static void getSelDomain(float z){
		Const.CONST_selDomain = Const.CONST_domainName.get(0);
		for(int a=0;a<Const.CONST_domainName.size();a++){
			Double height = Const.CONST_domain_.get(Const.CONST_domainName.get(a));
			if(height<z){
				Const.CONST_selDomain = Const.CONST_domainName.get(a);
				return;
			}
		}
	}

	public static void showDomain(int position) {
		String name = Const.CONST_domainName.get(position);
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layer1 = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
			for (int a = 0; a < Const.CONST_domainName.size(); a++) {
				Double height = Const.CONST_domain_.get(Const.CONST_domainName.get(a));
				Double height1 = Const.CONST_domain_.get(name);
				if (height > height1) {
					layer1.setGroupVisibleMask(domain_c.get(Const.CONST_domainName.get(a)), gviViewportMask.gviViewNone);

				} else {
					layer1.setGroupVisibleMask(domain_c.get(Const.CONST_domainName.get(a)), gviViewportMask.gviView0);
				}
				layer1.setGroupVisibleMask(Const.CONST_domainName.size(), gviViewportMask.gviViewNone);
			}
		}
		for (int i = 0; i < domain_list.getCount(); i++) {
			domain_list.getChildAt(i).setBackgroundColor(Color.WHITE);
		}
		domain_list.getChildAt(position).setBackgroundResource(R.drawable.sidebar_reproduction);
	}

	public void find_sort(SQLiteDatabase mydata, String tablename, String name_sort, String desc) {
		// TODO Auto-generated method stub
		Const.CONST_findlist = SqliteUtil.find_sort(mydata, tablename, name_sort, desc);
		Const.CONST_madapter = new MyAdapters(context);
		lv.setAdapter(Const.CONST_madapter);
		lvItemEvent(tablename);
	}

	
	public void find_filter(SQLiteDatabase mydata, String tablename, String desc_file, String name_file) {
		// TODO Auto-generated method stub
		Const.CONST_findlist = SqliteUtil.find_filter(mydata, tablename, desc_file, name_file);
		Const.CONST_madapter = new MyAdapters(context);
		lv.setAdapter(Const.CONST_madapter);
		lvItemEvent(tablename);
		
	}
	
	
	public void no_find_filter(SQLiteDatabase mydata, String tablename, String desc_file, String name_file) {
		// TODO Auto-generated method stub
		Const.CONST_no_findlist = SqliteUtil.no_find_filter(mydata, tablename, desc_file, name_file);
		Const.CONST_find_filter=new ArrayList<Integer>();
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
		IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
	
		for(int t=0;t<Const.CONST_no_findlist.size();t++){
			if (layer.getFeatureClassName().equals(Const.CONST_conventional_model)){ 
				
				Const.CONST_find_filter.add(Const.CONST_no_findlist.get(t).getOid());
				
			}
		}
		if (layer.getFeatureClassName().equals(Const.CONST_conventional_model)){
		layer.hiddenFeatures(Const.CONST_find_filter);
		}
		}
	}

	public static void texall(){
		Const.CONST_no_findlist =null;
		Const.CONST_find_filter=new ArrayList<Integer>();
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
		IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
	
		if (layer.getFeatureClassName().equals(Const.CONST_conventional_model)){
		layer.hiddenFeatures(Const.CONST_find_filter);
		}
		}	
		
		
		
	}
	
	
	
	public void findlike(SQLiteDatabase mydata, String tablename, String str) {
		// TODO Auto-generated method stub
		Const.CONST_findlist = SqliteUtil.findlike(mydata, tablename, str);
		Const.CONST_madapter = new MyAdapters(context);
		lv.setAdapter(Const.CONST_madapter);
		lvItemEvent(tablename);
	}

	public static  void setItemRed(){
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layerlv = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
			if (layerlv.getFeatureClassName().equals(Const.CONST_conventional_model)) {
				layerlv.unhighlightAll();
				layerlv.highlightFeature(Const.point.getOid(), Color.RED);
			}

			for(int t=0;t<Const.CONST_findlist.size();t++){
				if (layerlv.getFeatureClassName().equals(Const.CONST_conventional_model)) {
					if(Const.CONST_findlist.get(t).getPointRole().equals("Control Point")){
						layerlv.highlightFeature(Const.CONST_findlist.get(t).getOid(), Color.RED);
					}
				}
			}
		}
	}

	public static void setRed(){
		for (int i = 0; i < Const.CONST_flIds.length; i++) {
			IFeatureLayer layerlv = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);

			for(int t=0;t<Const.CONST_findlist.size();t++){
				if (layerlv.getFeatureClassName().equals(Const.CONST_conventional_model)) {
					if(Const.CONST_findlist.get(t).getPointRole().equals("Control Point")){
						layerlv.highlightFeature(Const.CONST_findlist.get(t).getOid(), Color.RED);
					}
				}
			}
		}
	}
}
