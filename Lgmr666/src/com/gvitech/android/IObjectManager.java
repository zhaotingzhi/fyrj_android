package com.gvitech.android;

import java.util.ArrayList;

import com.gvitech.android.EnumValue.*;

public class IObjectManager {

	private ISkyBox skyBox = null;
	private IReferencePlane referencePlane = null;

	public native boolean deleteObject(int oid);

	public native int[] createFeatureLayerT(String sdbFile, long geometryRender);

	public int[] createFeatureLayer(String sdbFile, IGeometryRender geometryRender) {
		if (geometryRender != null)
			return createFeatureLayerT(sdbFile, geometryRender._piObject);
		else
			return createFeatureLayerT(sdbFile, 0);
	}

	private native int getObjectByIdT(int oid);

	private native int createCameraTourT();

	private native int createLabelT(double x, double y, double z, String text, int textColor, int textSize, int bgColor,
			double verticalOffset);

	private native int create3DTileLayerT(String layerInfo, String password);

	private native int createRenderPolylineT(double[] arrPoints, int lineColor, float lineWidth);

	private native int createRenderPolygonT(double[] arrPoints, int fillColor);

	private native int createRenderPointT(double x, double y, double z, String imagePath, int size);

	public ICameraTour createCameraTour() {
		int oid = createCameraTourT();
		ICameraTour r = new ICameraTour(oid);
		return r;
	}

	public ILabel createLabel(double x, double y, double z, String text, int textColor, int textSize, int bgColor,
			double verticalOffset) {
		int oid = createLabelT(x, y, z, text, textColor, textSize, bgColor, verticalOffset);
		ILabel r = new ILabel(oid);
		return r;
	}

	public I3DTileLayer create3DTileLayer(String layerInfo, String password) {
		I3DTileLayer r = null;
		int oid = create3DTileLayerT(layerInfo, password);
		if (oid != 0) {
			r = new I3DTileLayer(oid);
		}
		return r;
	}

	public IRenderPolyline createRenderPolyline(ArrayList<Double> arrPoints, int lineColor, float lineWidth) {
		IRenderPolyline r = null;
		int arrSize = arrPoints.size();
		double[] dPoints = new double[arrSize];
		for (int i = 0; i < arrSize; i++) {
			dPoints[i] = (Double) arrPoints.get(i);
		}
		int oid = createRenderPolylineT(dPoints, lineColor, lineWidth);
		r = new IRenderPolyline(oid);
		return r;
	}

	public IRenderPolygon createRenderPolygon(ArrayList<Double> arrPoints, int fillColor) {
		int arrSize = arrPoints.size();
		double[] dPoints = new double[arrSize];
		for (int i = 0; i < arrSize; i++) {
			dPoints[i] = (Double) arrPoints.get(i);
		}
		int oid = createRenderPolygonT(dPoints, fillColor);
		IRenderPolygon r = new IRenderPolygon(oid);
		return r;
	}

	public IRenderPoint createRenderPoint(double x, double y, double z, String imagePath, int size) {
		int oid = createRenderPointT(x, y, z, imagePath, size);
		IRenderPoint r = new IRenderPoint(oid);
		return r;
	}

	private gviObjectType getObjectTypeFromInt(int n) {
		gviObjectType r = gviObjectType.gviObjectNone;
		for (gviObjectType e : gviObjectType.values()) {
			if (e.getValue() == n) {
				r = e;
				break;
			}
		}
		return r;
	}

	public IRObject getObjectById(int oid) {
		IRObject r = null;
		int nObjectType = getObjectByIdT(oid);
		gviObjectType objType = this.getObjectTypeFromInt(nObjectType);
		switch (objType) {
		case gviObjectReferencePlane:
			r = new IReferencePlane(oid);
			break;
		case gviObjectCameraTour:
			r = new ICameraTour(oid);
			break;
		case gviObjectSkyBox:
			r = new ISkyBox(oid);
			break;
		case gviObjectLabel:
			r = new ILabel(oid);
			break;
		case gviObjectRenderPoint:
			r = new IRenderPoint(oid);
			break;
		case gviObjectRenderPolyline:
			r = new IRenderPolyline(oid);
			break;
		case gviObjectRenderPolygon:
			r = new IRenderPolygon(oid);
			break;
		case gviObjectRenderModelPoint:
			r = new IRenderModelPoint(oid);
			break;
		case gviObject3DTileLayer:
			r = new I3DTileLayer(oid);
			break;
		case gviObjectFeatureLayer:
			r = new IFeatureLayer(oid);
			break;
		default:
			break;
		}

		return r;
	}

	public ISkyBox getSkyBox() {
		if (this.skyBox == null)
			this.skyBox = new ISkyBox(-1);
		return this.skyBox;
	}

	public IReferencePlane getReferencePlane() {
		if (this.referencePlane == null)
			this.referencePlane = new IReferencePlane(-2);
		return this.referencePlane;
	}
}
