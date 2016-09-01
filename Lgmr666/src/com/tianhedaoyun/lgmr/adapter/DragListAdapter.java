package com.tianhedaoyun.lgmr.adapter;

import java.util.ArrayList;
import com.gvitech.android.IFeatureLayer;
import com.gvitech.android.IRenderModelPoint;
import com.gvitech.android.RenderControl;
import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.util.Const;
import com.gvitech.android.EnumValue.gviViewportMask;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DragListAdapter extends BaseAdapter {
	private static final String TAG = "DragListAdapter";
	private ArrayList<String> txtConts;
	private ArrayList<Boolean> showConts;
	private Context context;
	public boolean isHidden;

	public DragListAdapter(Context context,	ArrayList<String> txt,ArrayList<Boolean> isShow) {
		this.context = context;
		this.txtConts = txt;
		this.showConts = isShow;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);

		TextView textView = (TextView) convertView.findViewById(R.id.item_cont);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.item_image);

		imageView.setOnClickListener(new OnClickListener() {
    		@Override
			public void onClick(View arg0) {
    			
    			if(!showConts.get(position)){
    				if(position==0){
    					for(int i=0;i<Const.CONST_flIds.length; i++)
    					{
    						IFeatureLayer layer = (IFeatureLayer)  RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
    						layer.setVisibleMask(gviViewportMask.gviView0);
    					}
    				}else{
    					IRenderModelPoint model = (IRenderModelPoint)RenderControl.get().objectManager.getObjectById(Integer.parseInt(txtConts.get(position)));
    					//if(model!=null)
    					model.setVisibleMask(gviViewportMask.gviView0);
    				}
    			}else{
    				if(position==0){
    					for(int i=0;i<Const.CONST_flIds.length; i++)
    					{
    						IFeatureLayer layer = (IFeatureLayer) RenderControl.get().objectManager.getObjectById(Const.CONST_flIds[i]);
    						layer.setVisibleMask(gviViewportMask.gviViewNone);
    					}
    				}else{
    					IRenderModelPoint model = (IRenderModelPoint) RenderControl.get().objectManager.getObjectById(Integer.parseInt(txtConts.get(position)));
    					//if(model!=null)
    					model.setVisibleMask(gviViewportMask.gviViewNone);
    				}
    			}
    			showConts.set(position, !showConts.get(position));
    			
    			notifyDataSetChanged();
			}
		});

		textView.setText(txtConts.get(position));
		if(showConts.get(position)){
			//imageView.setImageBitmap(typeConts.get(position));
			imageView.setBackgroundResource(R.drawable.button_on);
		}else{
			imageView.setBackgroundResource(R.drawable.button_off);
		}
		return convertView;
	}


	@Override
	public int getCount() {
		return txtConts.size();
	}

	@Override
	public Object getItem(int position) {
		return txtConts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}