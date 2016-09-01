package com.gvitech.android;

public class EnumValue {
/**********************************************************
 * FdeCore
 *********************************************************/
public enum gviConnType {	
    gviConnectionUnknown			 (0),
    gviConnectionODBC                (1),//Œ¥ µœ÷
    gviConnectionMySql5x             (2),// mysql 5.5º∞“‘…œ
    gviConnectionFireBird2x          (3),//firebird2.1
    gviConnectionOCI11               (4),
    gviConnectionPg9                 (5), //PostgreSQL
    gviConnectionMSClient            (6),  // Microsoft SQLServer Native Client 2005º∞“‘…œ
    gviConnectionSpClient13          (7),  // Sybase Open Client  13 Œ¥ µœ÷
    gviConnectionCli9                (8),  // IBM Db2 v9 client. Call Level Interface. Œ¥ µœ÷
    gviConnectionSQLite2             (9),  // sqlite v2 client. Call Level Interface. Œ¥ µœ÷
    gviConnectionSQLite3             (10), // sqlite v3 client. Call Level Interface.
    gviConnectionShapeFile           (12), // Ersi Shape File .
    gviConnectionArcGISServer10      (13), //Ersi ArcGIS Server 10 .
    gviConnectionArcSDE9x            (14), // Arc SDE 9 C API .
    gviConnectionArcSDE10x           (15), // Arc SDE 10 C API .
    gviConnectionWFS                 (16), // WFS
    gviConnectionGMAP                (20), // GlobeMap“˝«Ê°£»´æ÷kv
    gviConnectionTileClient          (21), // TileClient°£
    gviConnectionPluginExtension     (999); // Õ®π˝◊‘∂®“Â≤Âº˛¿©’πµƒ ˝æ›‘¥
    
    private int val;
    private gviConnType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

public enum gviDataSetType {	
    gviDataSetAny,
    gviDataSetDbmsTable,
    gviDataSetObjectClassTable,
    gviDataSetFeatureClassTable,
    gviDataSetModelClassTable,
    gviDataSetImageClassTable
}

public enum gviFieldType {	
    gviFieldUnknown			 	(0),
    gviFieldInt8                (1),
    gviFieldInt16             	(2),
    gviFieldInt32          		(3),
    gviFieldInt64               (4),
    gviFieldFloat               (5), 
    gviFieldDouble            	(6),  
    gviFieldString         		(7),  
    gviFieldDate                (8),  
    gviFieldBlob             	(9),  
    gviFieldFID             	(10), 
    gviFieldUUID           		(11), 
    gviFieldXML      			(12),
    gviFieldGeometry            (99), 
    gviFieldANSIString          (999); 
    
    private int val;
    private gviFieldType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/**********************************************************
 * FdeGeometry
 *********************************************************/
/*!
 * 几何类型。
 *
 * 这16种类型Geometry均可实例化。
 */
public enum gviGeometryType {
    gviGeometryUnknown       ( 0),        /*!< 未知几何(Unknown type of geometry),非empty几何(顶点数为0);非空值(几何) */
    gviGeometryPoint         ( 1),        /*!< 点 */
    gviGeometryModelPoint    ( 2),        /*!< 带三维模型的点 */
    gviGeometryCircularArc   ( 6),        /*!< 圆弧段 */
    gviGeometryLine          ( 10),       /*!< 直线段(P = tP0+(1-t)P1,0.0 = <t< = 1.0,即vertex之间线性组合) */
    gviGeometryCircle        ( 11),         /*!< 圆 */
    gviGeometryPolyline      ( 30),       /*!< 多段线(由gviGeometryLine按照曲线要求两两连接而成) */
    gviGeometryRing          ( 31),       /*!< 环(一维) */
    gviGeometryCompoundLine  ( 32),       /*!< 复合线 */
    gviGeometryPolygon       ( 50),       /*!< 多边形 */
    gviGeometryTriMesh       ( 51),       /*!< 三角网格面(每个三角形满足P = aP0+bP1+cP2,a+b+c = 1.0,a>0&&b>0&&c>0,即vertex之间线性组合) */
    gviGeometryCollection    ( 70),       /*!< 几何集合 */
    gviGeometryMultiPoint    ( 71),       /*!< 多点 */
    gviGeometryMultiPolyline ( 72),       /*!< 多段线集合 */
    gviGeometryMultiPolygon  ( 73),       /*!< 多边形集合 */
    gviGeometryMultiTrimesh  ( 74),       /*!< 三角网格面集合 */
    gviGeometryClosedTriMesh ( 77),       /*!< 封闭三角网格面 */
    gviGeometryPointCloud    ( 100);       /*!< 点云 */
    
    private int val;
    private gviGeometryType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}




/**********************************************************
 * RenderControl
 *********************************************************/
public enum gviObjectType {
    gviObjectNone (0),           /*!< 空对象，当定义的某些节点，并不希望进行绘制求交等操作可以指定该类型 */
    gviObjectReferencePlane (2),     /*!< 基准平面对象 */
    gviObjectGroup (4),    /*!< 组容器 */
    
    gviObjectFeatureLayer   (0xFF + 1),    /*!< 地理要素图层 */
    gviObjectTerrain        (0xFF + 2),    /*!<  地形 */
    gviObjectRenderModelPoint     (0xFF + 3),    /*!<  ModelPoint的渲染对象*/
    gviObjectTerrainRoute   (0xFF + 5),    /*!<  用速度控制关键帧的相机路径 */
    gviObjectRenderPolyline   (0xFF + 6),          /*!<  Polyline的渲染对象*/
    gviObjectRenderPolygon    (0xFF + 7),          /*!<  Polygon的渲染对象 */
    gviObjectRenderTriMesh    (0xFF + 8),          /*!<  TriMesh的渲染对象 */
    gviObjectRenderMultiPoint (0xFF + 9),          /*!<  MultiPoint的渲染对象 */
    gviObjectRenderPoint      (0xFF + 10),         /*!<  Point的渲染对象 */
    
    gviObjectCameraTour       (0xFF + 11),          /*!<  用时间控制关键帧的相机路径 */
    gviObjectMotionPath            (0xFF + 12),         /*!< 物体运动路径 */
    gviObjectSkyBox                (0xFF + 16),         /*!< 天空盒 */
    gviObjectParticleEffect        (0xFF + 17),         /*!< 粒子特效 */
    gviObjectLabel                (0xFF + 18),          /*!< 文字标签对象 */
    gviObjectTableLabel            (0xFF + 19),          /*!< 文字表格标签对象 */
    gviObjectSkinnedMesh        (0xFF + 20),          /*!< 骨骼动画 */
    gviObjectRenderArrow        (0xFF + 21),      /*!< 箭头标绘 */
    gviObjectRenderMultiPolyline(0xFF + 22),      /*!< MultiPolyline的渲染对象 */
    gviObjectRenderMultiPolygon (0xFF + 23),       /*!< MultiPolygon的渲染对象 */
    gviObjectImageryLayer       (0xFF + 24),       /*!< 影像图层 */
    gviObjectRenderMultiTriMesh (0xFF + 25),       /*!< MultiTriMesh的渲染对象 */
    gviObjectTerrainHole        (0xFF + 26),         /*!< 地形挖洞 */
    gviObject3DTileLayer  (0xFF + 27),               /*!< 瓦片图层 */
    gviObjectTerrainVideo       (0xFF + 28),         /*!< 贴地视频 */
    gviObjectOverlayLabel       (0xFF + 29),          /*!< 屏幕标签 */
    gviObjectKmlGroup           (0xFF + 30),          /*!< KML组容器 */
    gviObjectDynamicObject      (0xFF + 31);          /*!< 运动路径 */
    
    private int val;
    private gviObjectType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}



/*!
 * 天空盒图片设定类型，假定人朝北向正立的方位。
 */
public enum gviSkyboxImageIndex   {
    gviSkyboxImageFront(0),        /*!< 北向 */
    gviSkyboxImageBack(1),        /*!< 南向 */
    gviSkyboxImageLeft(2),        /*!< 东向 */
    gviSkyboxImageRight(3),        /*!< 西向 */
    gviSkyboxImageTop(4),        /*!< 顶部，注意图片的上方靠南 */
    gviSkyboxImageBottom(5);        /*!< 底部，注意图片的上方靠北 */
    
    private int val;
    private gviSkyboxImageIndex(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}


/*!
 * 天气类型，可以模拟雨雪效果。
 */
public enum gviWeatherType {
    gviWeatherSunShine,             /*!< 晴天 */
    gviWeatherLightRain,            /*!< 小雨 */
    gviWeatherModerateRain,           /*!< 中雨 */
    gviWeatherHeavyRain,            /*!< 大雨 */
    gviWeatherLightSnow,            /*!< 小雪 */
    gviWeatherModerateSnow,           /*!< 中雪 */
    gviWeatherHeavySnow             /*!< 大雪 */
}

/*!
 * 雾效模式。
 */
public enum gviFogMode {
	gviFogNone,             /*!< 不启用雾效 */
	gviFogExp,            /*!< 一次指数 */
	gviFogExp2,           /*!< 二次指数 */
	gviFogLinear            /*!< 线性 */
}



/*!
 * 设定相机方位的参数标记。
 */
public enum gviSetCameraFlags {
    gviSetCameraNoFlags ( 0),        /*!< 所有参数全部设定，包括位置和转向 */
    gviSetCameraIgnoreX ( 1),        /*!< 忽略x坐标值的设定 */
    gviSetCameraIgnoreY ( 2),        /*!< 忽略y坐标值的设定 */
    gviSetCameraIgnoreZ ( 4),        /*!< 忽略z坐标值的设定 */
    gviSetCameraIgnorePosition ( 7),        /*!< 忽略位置的设定 */
    gviSetCameraIgnoreYaw (8),        /*!< 忽略Heading转向（Heading即相机（左右）摇摆的角度值） */
    gviSetCameraIgnorePitch ( 16),        /*!< 忽略Tilt转向（Tilt即相机（上下）俯仰的角度值） */
    gviSetCameraIgnoreRoll ( 32),        /*!< 忽略Roll转向（暂不可使用！） */
    gviSetCameraIgnoreOrientation ( 56);        /*!< 忽略所有转向 */
    
    private int val;
    private gviSetCameraFlags(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

public enum gviDepthTestMode
{
    gviDepthTestEnable ( 0),  // 正常深度检测（遮挡了就看不见，没遮挡才能看见）
    gviDepthTestDisable ( 1), // 禁用深度检测（全能看到）
    gviDepthTestAdvance ( 2), // 被遮挡的透明显示，没被遮挡的正常显示
    gviDepthTestAdvanceSecondDrawMaxDepth ( 1000); // 1km之内被遮挡画出半透效果
    
    private int val;
    private gviDepthTestMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}


/*!
 * 视口掩码。
 */
public enum gviViewportMask {
    gviViewNone ( 0x00),            /*!< 所有视口都不显示 */
    gviView0 ( 0x01);            /*!< 仅在第一个视口显示 */
    //gviView1 ( 0x02),            /*!< 仅在第二个视口显示 */
    //gviView2 ( 0x04),            /*!< 仅在第三个视口显示 */
    //gviView3 ( 0x08),            /*!< 仅在第四个视口显示 */
    //gviViewAllNormalView ( 0x0F),/*!< 正常的四个视口都显示(默认值) */
    //gviViewPIP ( 0x10);            /*!< 仅在画中画视口显示。*/
    
    private int val;
    private gviViewportMask(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}


/*!
 * 飞行模式。
 */
public enum gviFlyMode {
    gviFlyArc,      /*!<  距离远的时候会先向上飞，然后定位过去 */
    gviFlyLinear    /*!<  (默认)不向上飞，直接飞过去 */
}

/*!
 * 步行方式。
 */
public enum gviWalkMode {
	gviWalkAsGhost(0),      /*!<  幽灵模式，允许穿透所有物体，且不受重力影响 */
	gviWalkOnWalkGround(1), /*!<  (不推荐使用)仅在WalkGround上行走，允许穿透其他所有物体 */
	gviWalkOnAll(65535);     /*!<  (默认值)允许在所有对象上行走 */
	
	private int val;
    private gviWalkMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 相机飞到或跟随物体的方式。
 * @attention 2~8只有运动物体才起作用。
 */
public enum gviActionCode {
    gviActionFlyTo,   			/*!< 直接飞过去 */
    gviActionJump,  			/*!< 先向上再飞过去 */
    gviActionFollowBehind ,  	/*!< 正后方跟随（只会跟随heading角） */
    gviActionFollowAbove,   	/*!< 正上方跟随 */
    gviActionFollowBelow,   	/*!< 正下方跟随 */
    gviActionFollowLeft,    	/*!< 左侧跟随 */
    gviActionFollowRight,   	/*!< 右侧跟随 */
    gviActionFollowBehindAndAbove,  /*!< 后上方跟随 */
    gviActionFollowCockpit 		/*!< 座舱跟随（heading和tilt都会跟随） */
}


/*!
 * 碰撞检测模式。
 */
public enum gviCollisionDetectionMode {
    gviCollisionDisable (0),        /*!< 关闭碰撞检测 */
    gviCollisionOnlyKeyboard (1),	 /*!< 开启碰撞检测，keyboard only （默认值） */
    gviCollisionEnable (3);			 /*!< 开启碰撞检测，keyboard & mouse都支持 */
   
    private int val;
    private gviCollisionDetectionMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 定制的RObject的一些bool属性。
 */
public enum gviAttributeMask {
    gviAttributeHighlight (1),  /*!< 参与highlightHelper */
    gviAttributeCollision (2);  /*!< 参与碰撞检测 */
    
    private int val;
    private gviAttributeMask(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}
 
/*!
 * 交互模式。
 */
public enum gviInteractMode {
    gviInteractNormal ( 1),          /*!<  普通漫游模式（飞行模式） */
    gviInteractSelect ( 2),          /*!<  鼠标拾取模式（此模式下可进行鼠标拾取操作） */
    gviInteractMeasurement ( 3),     /*!<  测量模式 @see gviMeasurementMode */
    gviInteractEdit ( 4),            /*!<  几何数据编辑模式 */
    gviInteractWalk ( 5),            /*!<  步行模式（模拟地面行走模式） */
    gviInteractDisable ( 6),         /*!<  禁止交互模式（鼠标键盘按键都不可用，由二次开发接管交互） */
    gviInteract2DMap ( 7),           /*!<  二维模式 */
    gviInteractWalk2 ( 8);           /*!<  二维模式 */
    
    private int val;
    private gviInteractMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}


/*!
 * 鼠标拾取的时候需要过滤的对象类型掩码。
 */
public enum gviMouseSelectObjectMask {
    gviSelectNone            ( 0),    /*!< 空对象，当定义的某些节点，并不希望进行绘制绘制求交等操作可以指定该类型 */
    gviSelectFeatureLayer    ( 1),    /*!< 场景数据 */
    gviSelectTerrain         ( 2),    /*!< 地形数据 */
    gviSelectReferencePlane      ( 8),    /*!< 基准面 */
    gviSelectTerrainHole            ( 16),    /*!< 地形挖洞拾取 */
    gviSelectTiledFeatureLayer  ( 32),
    gviSelectLable              ( 64),   /*!< TableLable、Label、OverlayLabel共用此项 */
    gviSelectParticleEffect     ( 128),  /*!< 粒子系统 */
    gviSelectRenderGeometry     ( 256),  /*!< RenderGeometry */
    gviSelectSkinnedMesh        ( 512),  /*!< 骨骼动画 */
    gviSelectAll             ( 0xFFFF); /*!< 所有对象 */
    
    private int val;
    private gviMouseSelectObjectMask(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}


/*!
 * 鼠标拾取的触发方式。
 */
public enum gviMouseSelectMode {
    gviMouseSelectClick  ( 1),     /*!< 鼠标左键点选模式（up的时候触发） */
    gviMouseSelectDrag   ( 2),     /*!< 鼠标左键框选模式（up的时候触发） */
    gviMouseSelectMove   ( 4),     /*!< 鼠标移动模式（在鼠标移动时即会进行拾取操作，在没有任何键按下的情况下即会执行） */
    gviMouseSelectHover  ( 8);     /*!< 鼠标悬停模式（鼠标停留在一个位置一段时间后触发。该命令直接对应WM_HOVER消息，没有任何键按下的情况，可以用来做鼠标提示） */
    
    private int val;
    private gviMouseSelectMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 测量模式。
 */
public enum gviMeasurementMode {
	gviMeasureAerialDistance ( 0),          /*!<  任意点测距 */
	gviMeasureHorizontalDistance ( 1),          /*!<  水平空间测距 */
	gviMeasureVerticalDistance ( 2),     /*!<  垂直空间测距 */
	gviMeasureCoordinate ( 3),            /*!<  坐标测量 */
	gviMeasureArea ( 5),            /*!<  投影面积 */
	gviMeasureMultiaxisDistance ( 8);            /*!<  两点间多轴测距 */
    
    private int val;
    private gviMeasurementMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 三维视口模式。
 */
public enum gviViewportMode    {   
    gviViewportSinglePerspective (1),      /*!< (默认)单视口透视投影。 */  
    /*!
     * 头盔左一右一立体。只要识别到了oculus头盔，就立即起作用。
     */
    gviViewportStereoDualOculus (17);     
    
    private int val;
    private gviViewportMode(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 渲染器类型。
 */
public enum gviRenderType {	
	gviRenderSimple (0),
	gviRenderValueMap (1);
	
    private int val;
    private gviRenderType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 几何对象的符号类型。
 */
public enum gviGeometrySymbolType {	
	gviGeoSymbolPoint (0),		/*!< 点 */
	gviGeoSymbolImagePoint (1),	/*!< 图像点 */
	gviGeoSymbolModelPoint (2),	/*!< 模型 */
    gviGeoSymbolCurve (3),     	/*!< 线 */
    gviGeoSymbolSurface (4),   	/*!< 面 */
    gviGeoSymbol3DPolygon (5), 	/*!< 立体矩形 */
    gviGeoSymbolSolid (6),     	/*!< 体 */
    gviGeoSymbolPointCloud (7); /*!< 点云 */
	
    private int val;
    private gviGeometrySymbolType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

/*!
 * 域类型。
 */
public enum gviDomainType {	
	gviDomainRange (0),
	gviDomainCodedValue (1);
	
    private int val;
    private gviDomainType(int i){
    	this.val = i;
    }
    public int getValue(){
    	return this.val;
    }
}

}
