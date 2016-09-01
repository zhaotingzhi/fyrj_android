package com.tianhedaoyun.lgmr.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.activity.ModelMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SampleModels extends Fragment {
	private GridView gview;
	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	private String[] iconName = { "Demo Model", "House", "Medical Hospital" ,"文件查找"};
	private int[] icon = { R.drawable.demo, R.drawable.house, R.drawable.hospital,R.drawable.filepickeractivity_item_folder_ic};
	View view;
	StringBuffer buffer;
	CharSequence[] aa;
	String pach;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.modelstyle, container, false);
		gview = (GridView) view.findViewById(R.id.gview);

//		List<String> getFiles = GetFiles(Environment.getExternalStorageDirectory() + "/", ".sdb", true);
//		aa = getFiles.toArray(new CharSequence[getFiles.size()]);

		// 新建List
		data_list = new ArrayList<Map<String, Object>>();
		// 获取数据
		getData();
		// 新建适配器
		String[] from = { "image", "text" };
		int[] to = { R.id.image, R.id.text };
		sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.model_item, from, to);
		// 配置适配器
		gview.setAdapter(sim_adapter);
		// 注册监听事件
		gview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (position == 3) {
					/*Popup();*/
					 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		             intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
		             intent.addCategory(Intent.CATEGORY_OPENABLE);
		             startActivityForResult(intent,1);
				}else {
					pach = view.getContext().getExternalFilesDir("").getAbsolutePath()+"/data1.sdb";
					Intent intent = new Intent();
					intent.putExtra("message", pach);
					intent.setClass(view.getContext(), ModelMain.class);
					startActivity(intent);
				}

			}
		});
		return view;

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)  {  
        if (resultCode == Activity.RESULT_OK)  
        {  
            Uri uri = data.getData();  
            pach=uri.toString().substring(7);
            
            String[] pach_pd=pach.split("\\.");
         
             if(pach_pd[pach_pd.length-1].equals("sdb")){
            	
                Intent intent = new Intent();
     			intent.putExtra("message", pach);
     			intent.setClass(view.getContext(), ModelMain.class);
     			startActivity(intent);
        }else{
        	Toast.makeText(view.getContext(), "请选择正确的sdb文件！", Toast.LENGTH_LONG).show();
        }
           
        }  
     }   
	

	public List<Map<String, Object>> getData() {
		// cion和iconName的长度是相同的，这里任选其一都可以
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}

		return data_list;
	}

//	public void Popup() {
//		// 弹框
//		new AlertDialog.Builder(view.getContext()).setTitle("请选择").setIcon(android.R.drawable.ic_dialog_info)
//				.setSingleChoiceItems(aa, 0, new DialogInterface.OnClickListener() {
//
//					public void onClick(DialogInterface dialog, int which) {
//						System.out.println(which + "xxxx" + dialog);
//						Intent intent = new Intent();
//						intent.putExtra("message", aa[which]);
//						intent.setClass(view.getContext(), ModelMain.class);
//						startActivity(intent);
//						dialog.dismiss();
//
//					}
//				}).setNegativeButton("取消", null).show();
//	}
//
//	private List<String> apklist = new ArrayList<String>(); // 结果 List

//	public List<String> GetFiles(String Path, String Extension, boolean IsIterative) // 搜索目录，扩展名(判断的文件类型的后缀名)，是否进入子文件夹
//	{
//		File[] files = new File(Path).listFiles();
//		for (int i = 0; i < files.length; i++) {
//			File f = files[i];
//			if (f.isFile()) {
//				if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) // 判断扩展名
//					apklist.add(f.getPath());
//				if (!IsIterative)
//					break; // 如果不进入子集目录则跳出
//			} else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) // 忽略点文件（隐藏文件/文件夹）
//				GetFiles(f.getPath(), Extension, IsIterative); // 这里就开始递归了
//		}
//		return apklist;
//	}

}
