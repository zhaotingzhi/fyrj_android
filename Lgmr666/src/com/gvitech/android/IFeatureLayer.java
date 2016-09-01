package com.gvitech.android;

import java.util.ArrayList;

import com.gvitech.android.EnumValue.gviViewportMask;

public class IFeatureLayer extends IRenderable{
	public IFeatureLayer(int oid) {
		super(oid);
	}
	
	private native void highlightFeatureT(int layerId, int featureId, int color);
	public native void unhighlightAll();
	
	public void highlightFeature(int featureId, int color){
		highlightFeatureT(this.objectId, featureId, color);
	}

	private native int searchByIdT(int layerId, int featureId);
	public IRowBuffer searchById(int featureId){		
		IRowBuffer buffer = new IRowBuffer();
		buffer._piObject = this.searchByIdT(this.objectId, featureId);
		return buffer;
	}
	
	private native int createRenderModelPointT(int layerId, int featureId);
	public IRenderModelPoint createRenderModelPoint(int featureId){			
		int oid = this.createRenderModelPointT(this.objectId, featureId);
		IRenderModelPoint modelpoint = new IRenderModelPoint(oid);
		return modelpoint;
	}
	
	private native void hiddenFeaturesT(int layerId, int[] featureIds);
	public void hiddenFeatures(ArrayList<Integer> arrFIDs){
		int arrSize = arrFIDs.size();
    	int[] dPoints = new int[arrSize];
    	for(int i=0; i<arrSize; i++){
    		dPoints[i] = (Integer) arrFIDs.get(i);
    	}
		hiddenFeaturesT(this.objectId, dPoints);
	}
	
	private native long searchT(int layerId, long filter);
	public IRowBufferCollection search(IQueryFilter filter){		
		IRowBufferCollection buffercollection = new IRowBufferCollection();		
		if(filter != null)
			buffercollection._piObject = searchT(this.objectId, filter._piObject);	
		else
			buffercollection._piObject = searchT(this.objectId, 0);	
		
		return buffercollection;
	}
	
	private native String getFeatureClassIdT(int layerId);
	public String getFeatureClassId(){
		return getFeatureClassIdT(this.objectId);
	}
	
	private native String getGeometryFieldNameT(int layerId);
	public String getGeometryFieldName(){
		return getGeometryFieldNameT(this.objectId);
	}
	
	private native String getDataSourceConnectionStringT(int layerId);
	public String getDataSourceConnectionString(){
		return getDataSourceConnectionStringT(this.objectId);
	}
	
	private native String getDataSetNameT(int layerId);
	public String getDataSetName(){
		return getDataSetNameT(this.objectId);
	}
	
	private native String getFeatureClassNameT(int layerId);
	public String getFeatureClassName(){
		return getFeatureClassNameT(this.objectId);
	}
	
	private native void setGroupVisibleMaskT(int layerId, int groupId, gviViewportMask newVal);
	public void setGroupVisibleMask(int groupId, gviViewportMask newVal){
		setGroupVisibleMaskT(this.objectId, groupId, newVal);
	}
	
	private native int getGroupVisibleMaskT(int layerId, int groupId);
	public gviViewportMask getGroupVisibleMask(int groupId){
		int nMode = getGroupVisibleMaskT(this.objectId, groupId);
		gviViewportMask r = gviViewportMask.gviViewNone;
		for(gviViewportMask e : gviViewportMask.values()){
			if(e.getValue() == nMode){
				r = e;
				break;
			}
		} 
		return r;
	}
	
}
