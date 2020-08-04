package com.mythmayor.mainproject.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.offlinemap.OfflineMapActivity;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.mythmayor.basicproject.base.BaseTitleBarActivity;
import com.mythmayor.basicproject.utils.CommonUtil;
import com.mythmayor.basicproject.utils.DateUtil;
import com.mythmayor.basicproject.utils.IntentUtil;
import com.mythmayor.basicproject.utils.LogUtil;
import com.mythmayor.basicproject.utils.ProjectUtil;
import com.mythmayor.basicproject.utils.ToastUtil;
import com.mythmayor.mainproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/7/20.
 * 高德地图测试页面
 * 高德开放平台：https://lbs.amap.com/
 */
public class MapTestActivity extends BaseTitleBarActivity implements LocationSource, AMapLocationListener {

    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn05;
    private Button btn06;
    private Button btn07;
    private Button btn08;
    private Button btn09;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;
    private Button btn18;
    private Button btn19;
    private Button btn20;
    private Button btn21;
    private Button btn22;
    private Button btn23;
    private Button btn24;
    private MapView mMapView;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mLocationListener;
    private AMap mAmap;
    private AMapLocation mAMapLocation;
    private UiSettings mUiSettings;
    private GeoFenceClient mGeoFenceClient;//地理围栏
    private Marker mScreenMarker;
    private static final float ZOOM_MAP = 12;
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public int getSubLayoutResId() {
        return R.layout.activity_map_test;
    }

    @Override
    public void initSubView(View view) {
        btn01 = (Button) findViewById(R.id.btn_01);
        btn02 = (Button) findViewById(R.id.btn_02);
        btn03 = (Button) findViewById(R.id.btn_03);
        btn04 = (Button) findViewById(R.id.btn_04);
        btn05 = (Button) findViewById(R.id.btn_05);
        btn06 = (Button) findViewById(R.id.btn_06);
        btn07 = (Button) findViewById(R.id.btn_07);
        btn08 = (Button) findViewById(R.id.btn_08);
        btn09 = (Button) findViewById(R.id.btn_09);
        btn10 = (Button) findViewById(R.id.btn_10);
        btn11 = (Button) findViewById(R.id.btn_11);
        btn12 = (Button) findViewById(R.id.btn_12);
        btn13 = (Button) findViewById(R.id.btn_13);
        btn14 = (Button) findViewById(R.id.btn_14);
        btn15 = (Button) findViewById(R.id.btn_15);
        btn16 = (Button) findViewById(R.id.btn_16);
        btn17 = (Button) findViewById(R.id.btn_17);
        btn18 = (Button) findViewById(R.id.btn_18);
        btn19 = (Button) findViewById(R.id.btn_19);
        btn20 = (Button) findViewById(R.id.btn_20);
        btn21 = (Button) findViewById(R.id.btn_21);
        btn22 = (Button) findViewById(R.id.btn_22);
        btn23 = (Button) findViewById(R.id.btn_23);
        btn24 = (Button) findViewById(R.id.btn_24);
        mMapView = (MapView) findViewById(R.id.mapView);
    }

    @Override
    public void initSubEvent() {
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn05.setOnClickListener(this);
        btn06.setOnClickListener(this);
        btn07.setOnClickListener(this);
        btn08.setOnClickListener(this);
        btn09.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn24.setOnClickListener(this);
    }

    @Override
    public void initSubData(Intent intent) {
        initAmap();
        initUiSettings();//初始化UI设置
        initLocationOption();//初始化定位客户端
        initLocationStyle();//设置地图定位的基础样式
        initGeoFence();//初始化地理围栏
    }

    private void initAmap() {
        mAmap = mMapView.getMap();
        mAmap.showBuildings(true);//显示3D建筑
        mAmap.showMapText(true);//显示底图文字
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(ZOOM_MAP));
        mAmap.setLocationSource(this);//设置定位资源监听(包含激活定位和销毁定位)
        mAmap.setMyLocationEnabled(true);//触发定位
        showIndoorMap();
    }

    private void initUiSettings() {
        mUiSettings = mAmap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(true);//设置比例尺控件是否可见
        mUiSettings.setZoomControlsEnabled(true);//设置缩放按钮是否可见
        mUiSettings.setCompassEnabled(true);//设置指南针是否可见
        mUiSettings.setMyLocationButtonEnabled(true);//设置定位按钮是否可见
        mUiSettings.setScrollGesturesEnabled(true);//设置拖拽手势是否可用
        mUiSettings.setZoomGesturesEnabled(true);//设置双指缩放手势是否可用
        mUiSettings.setTiltGesturesEnabled(true);//设置倾斜手势是否可用
        mUiSettings.setRotateGesturesEnabled(true);//设置旋转手势是否可用
        mUiSettings.setAllGesturesEnabled(true);//设置所有手势是否可用
        mUiSettings.setIndoorSwitchEnabled(true);//设置室内地图楼层切换控件是否可见
        mUiSettings.setGestureScaleByMapCenter(true);//设置是否以地图中心点缩放
    }

    private void initLocationOption() {
        AMapLocationClientOption.AMapLocationMode mode;
        if (ProjectUtil.isGpsOpen(this)) {
            mode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;//高精度模式。
            LogUtil.d("当前定位模式为 --->>> 高精度模式");
        } else if (ProjectUtil.isGpsNetWorkOpen(this)) {
            mode = AMapLocationClientOption.AMapLocationMode.Battery_Saving;//低功耗模式。
            LogUtil.d("当前定位模式为 --->>> 低功耗模式");
        } else {
            mode = AMapLocationClientOption.AMapLocationMode.Device_Sensors;//仅限设备模式。
            LogUtil.d("当前定位模式为 --->>> 仅限设备模式");
        }
        if (mLocationOption == null) {
            mLocationOption = new AMapLocationClientOption()
                    .setLocationMode(mode)
                    .setNeedAddress(true)//设置是否返回地址信息（默认返回地址信息）
                    .setMockEnable(true)//设置是否允许模拟位置,默认为true，允许模拟位置
                    .setInterval(2000)//设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
                    .setHttpTimeOut(8000)//单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
                    .setLocationCacheEnable(true);//缓存机制默认开启，可以通过以下接口进行关闭。
        } else {
            mLocationOption.setLocationMode(mode);
        }
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());//初始化定位
            mLocationClient.setLocationListener(this);//设置定位回调监听
            mLocationClient.setLocationOption(mLocationOption);
        } else {
            mLocationClient.setLocationOption(mLocationOption);
        }
        mLocationClient.startLocation();//启动定位
    }

    private void initLocationStyle() {
        MyLocationStyle style = new MyLocationStyle();

        //定位一次,显示当前的位置，显示蓝点，蓝点不移动，手机转动蓝点也不改变方向，视角不会移动到屏幕中间
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        //定位一次，视角移动到地图中心点
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //连续定位、视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        //连续定位、视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动（1秒1次定位）
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        //连续定位、视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动(1秒1次定位）默认执行此种模式。
        style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        //连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        //连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动
        //style.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);

        //高德地图自动定位时间间隔
        style.interval(2000);
        //设置自定义的定位图标
        Bitmap locationBm = BitmapFactory.decodeResource(getResources(), R.mipmap.img_clear);
        //style.myLocationIcon(BitmapDescriptorFactory.fromBitmap(locationBm));
        //设置自定的图标锚点,这里的参数含义，是指描点在图标位置的百分比，
        // 例（0.5f,0.4f）意思是以图标宽度的50%，高度的40%为中心点，旋转
        //style.anchor(0.5f, 0.5f);
        //设置精度圈边框颜色，不想要设置为透明色即可
        style.strokeColor(Color.GREEN);
        //设置精度圈边框宽度
        style.strokeWidth(6);
        //设置精度圈填充颜色，不想要直接设置为透明色即可
        style.radiusFillColor(Color.TRANSPARENT);
        style.showMyLocation(true);
        //为Map添加定位
        mAmap.setMyLocationStyle(style);
    }

    private void initGeoFence() {
        mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        //设置希望侦测的围栏触发行为，默认只侦测用户进入围栏的行为
        //GEOFENCE_IN: 进入地理围栏, GEOFENCE_OUT: 退出地理围栏, GEOFENCE_STAYED: 停留在地理围栏内10分钟
        mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN | GeoFenceClient.GEOFENCE_OUT | GeoFenceClient.GEOFENCE_STAYED);
        mGeoFenceClient.setGeoFenceListener(new GeoFenceListener() {
            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> list, int errorCode, String s) {
                if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {//判断围栏是否创建成功
                    ToastUtil.showToast("添加围栏成功");
                    //geoFenceList是已经添加的围栏列表，可据此查看创建的围栏
                } else {
                    ToastUtil.showToast("添加围栏失败");
                }
            }
        });
    }

    @Override
    public void setTitleBar() {
        setLeftImage(true, R.mipmap.arrow_left);
        setTopTitle(true, "高德地图测试");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btn01) {
            moveToTargetPosition();
        } else if (v == btn02) {
            showCustomMapBounds(true);
        } else if (v == btn03) {
            showCustomMapBounds(false);
        } else if (v == btn04) {
            screenCapture();
        } else if (v == btn05) {
            changeMapLayer(AMap.MAP_TYPE_NORMAL);
        } else if (v == btn06) {
            changeMapLayer(AMap.MAP_TYPE_SATELLITE);
        } else if (v == btn07) {
            changeMapLayer(AMap.MAP_TYPE_NIGHT);
        } else if (v == btn08) {
            changeMapLayer(AMap.MAP_TYPE_NAVI);
        } else if (v == btn09) {
            changeMapLayer(AMap.MAP_TYPE_BUS);
        } else if (v == btn10) {
            IntentUtil.startActivity(this, OfflineMapActivity.class);
        } else if (v == btn11) {
            createGeoFence01();
        } else if (v == btn12) {
            createGeoFence02();
        } else if (v == btn13) {
            createGeoFence03();
        } else if (v == btn14) {
            createGeoFence04();
        } else if (v == btn15) {
            createGeoFence05();
        } else if (v == btn16) {
            addMarkerInScreenCenter();
        } else if (v == btn17) {
            removeMarker();
        } else if (v == btn18) {
            haiDianArea();
        } else if (v == btn19) {
            chaoyangArea();
        } else if (v == btn20) {
            drawCircle();
        } else if (v == btn21) {
            drawPolygon01();
        } else if (v == btn22) {
            drawPolygon02();
        } else if (v == btn23) {
            drawPolygon03();
        } else if (v == btn24) {
            drawPolyline();
        }
    }

    private void moveToTargetPosition() {
        //这个类就是设置地图移动的参数，CameraPosition，参数1---要移动到的经纬度，参数2---地图的放缩级别zoom，参数3---地图倾斜度，参数4---地图的旋转角度
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(30.67, 104.07), 10, 0, 0));
        //带动画的移动
        mAmap.animateCamera(mCameraUpdate, 5000, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onCancel() {
            }
        });
        //不带动画的移动
        mAmap.moveCamera(mCameraUpdate);
    }

    private void showCustomMapBounds(boolean enable) {
        if (enable) {
            LatLng southwestLatLng = new LatLng(30.67, 104.07);
            LatLng northeastLatLng = new LatLng(30.77, 104.17);
            LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
            mAmap.setMapStatusLimits(latLngBounds);
        } else {
            mAmap.setMapStatusLimits(null);
        }
    }

    //地图截屏
    private void screenCapture() {
        mAmap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {
            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int i) {
                if (null == bitmap) {
                    return;
                }
                try {
                    String path = getExternalFilesDir("") + "/MyMap/";
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(path + "test" + ".png");
                    boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuffer sb = new StringBuffer();
                    if (b)
                        sb.append("截屏成功 ");
                    else {
                        sb.append("截屏失败 ");
                    }
                    if (i != 0)
                        sb.append("地图渲染完成，截屏无网格");
                    else {
                        sb.append("地图未渲染完成，截屏有网格");
                    }
                    ToastUtil.showToast(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 显示室内地图,注意只有在地图在放缩级别>=17之间才看得到室内地图
     * 默认是不显示室内地图
     */
    private void showIndoorMap() {
        mAmap.showIndoorMap(true);
    }

    /**
     * 地图图层切换
     */
    private void changeMapLayer(int layer) {
        //正常地图: AMap.MAP_TYPE_NORMAL
        //卫星地图: AMap.MAP_TYPE_SATELLITE
        //夜间地图: AMap.MAP_TYPE_NIGHT
        //导航地图,不太常用: AMap.MAP_TYPE_NAVI
        //公交地图，不太常用: AMap.MAP_TYPE_BUS
        mAmap.setMapType(layer);
    }

    private void createGeoFence01() {//关键字围栏
        mGeoFenceClient.addGeoFence("首开广场", "写字楼", "北京", 1, "000FATE23（考勤打卡）");
    }

    private void createGeoFence02() {//POI围栏
        //创建一个中心点坐标
        DPoint centerPoint = new DPoint();
        //设置中心点纬度
        centerPoint.setLatitude(39.123D);
        //设置中心点经度
        centerPoint.setLongitude(116.123D);
        //执行添加围栏的操作
        mGeoFenceClient.addGeoFence("肯德基", "餐饮", centerPoint, 1000F, 10, "自有ID");
    }

    private void createGeoFence03() {//行政区围栏
        mGeoFenceClient.addGeoFence("海淀区", "00FDTW103（在北京海淀的化妆品促销活动）");
    }

    private void createGeoFence04() {//自定义圆围栏
        //创建一个中心点坐标
        DPoint centerPoint = new DPoint();
        //设置中心点纬度
        centerPoint.setLatitude(39.123D);
        //设置中心点经度
        centerPoint.setLongitude(116.123D);
        mGeoFenceClient.addGeoFence(centerPoint, 500F, "自有业务Id");
    }

    private void createGeoFence05() {//自定义多边形围栏
        List<DPoint> points = new ArrayList<DPoint>();
        points.add(new DPoint(39.992702, 116.470470));
        points.add(new DPoint(39.994387, 116.472498));
        points.add(new DPoint(39.994478, 116.474161));
        points.add(new DPoint(39.993163, 116.474504));
        points.add(new DPoint(39.991363, 116.472605));
        mGeoFenceClient.addGeoFence(points, "自有业务ID");

        /*LatLng latLng1 = new LatLng(39.992702, 116.470470);
        LatLng latLng2 = new LatLng(39.994387, 116.472498);
        LatLng latLng3 = new LatLng(39.994478, 116.474161);
        LatLng latLng4 = new LatLng(39.993163, 116.474504);
        LatLng latLng5 = new LatLng(39.991363, 116.472605);*/
        LatLng latLng1 = new LatLng(28.234259, 112.879588);
        LatLng latLng2 = new LatLng(28.226905, 112.879266);
        LatLng latLng3 = new LatLng(28.226735, 112.883751);
        LatLng latLng4 = new LatLng(28.237529, 112.882185);
        // 声明 多边形参数对象
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        //polygonOptions.add(latLng1, latLng2, latLng3, latLng4, latLng5);
        polygonOptions.add(latLng1, latLng2, latLng3, latLng4);
        polygonOptions.strokeWidth(8) // 多边形的边框
                .strokeColor(Color.argb(255, 30, 144, 255)) // 边框颜色
                .fillColor(Color.argb(50, 135, 206, 250));   // 多边形的填充色
        mAmap.addPolygon(polygonOptions);
    }


    private void addMarkerInScreenCenter() {
        LatLng latLng = mAmap.getCameraPosition().target;//目的地(target)/缩放级别(zoom)/方向(bearing)/倾斜角度(tilt)
        Point screenPosition = mAmap.getProjection().toScreenLocation(latLng);
        mScreenMarker = mAmap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_main_tab_selected)));
        //设置Marker在屏幕上,不跟随地图移动
        //mScreenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        //设置Marker在地图上,跟随地图移动
        mScreenMarker.setPosition(latLng);
    }

    private void removeMarker() {
        //mScreenMarker.remove();//清除单个Marker
        mAmap.clear();//清除所有Marker
    }

    private void haiDianArea() {
        DistrictSearch search = new DistrictSearch(this);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("海淀区");//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
            @Override
            public void onDistrictSearched(DistrictResult districtResult) {
                //在回调函数中解析districtResult获取行政区划信息
                //在districtResult.getAMapException().getErrorCode()=1000时调用districtResult.getDistrict()方法
                //获取查询行政区的结果，详细信息可以参考DistrictItem类。
            }
        });//绑定监听器
        search.searchDistrictAsyn();//开始搜索
    }

    private void chaoyangArea() {
        DistrictSearch search = new DistrictSearch(this);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("朝阳区");//传入关键字
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
            @Override
            public void onDistrictSearched(DistrictResult districtResult) {
                //在回调函数中解析districtResult获取行政区划信息
                //在districtResult.getAMapException().getErrorCode()=1000时调用districtResult.getDistrict()方法
                //获取查询行政区的结果，详细信息可以参考DistrictItem类。
            }
        });//绑定监听器
        search.searchDistrictAsyn();//开始搜索
    }

    private void drawCircle() {//绘制圆
        LatLng latLng = mAmap.getCameraPosition().target;//目的地(target)/缩放级别(zoom)/方向(bearing)/倾斜角度(tilt)
        mAmap.addCircle(new CircleOptions().
                center(latLng).
                radius(1000).
                fillColor(CommonUtil.getColor(R.color.color_33000000)).
                strokeColor(CommonUtil.getColor(R.color.color_red)).
                strokeWidth(5));
    }

    private void drawPolygon01() {//绘制椭圆
        mAmap.addPolygon(new PolygonOptions()
                .addAll(createRectangle(new LatLng(31.238068, 121.501654), 1, 1))
                .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
        PolygonOptions options = new PolygonOptions();
        int numPoints = 400;
        float semiHorizontalAxis = 5f;
        float semiVerticalAxis = 2.5f;
        double phase = 2 * Math.PI / numPoints;
        for (int i = 0; i <= numPoints; i++) {
            options.add(new LatLng(BEIJING.latitude
                    + semiVerticalAxis * Math.sin(i * phase),
                    BEIJING.longitude + semiHorizontalAxis
                            * Math.cos(i * phase)));
        }
        // 绘制一个椭圆
        mAmap.addPolygon(options.strokeWidth(25)
                .strokeColor(Color.argb(50, 1, 1, 1))
                .fillColor(Color.argb(50, 1, 1, 1)));
    }

    /**
     * 生成一个长方形的四个坐标点
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth,
                                         double halfHeight) {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));
        return latLngs;
    }

    private void drawPolygon02() {//绘制长方形
        // 绘制一个长方形
        mAmap.addPolygon(new PolygonOptions()
                .addAll(createRectangle(SHANGHAI, 1, 1))
                .fillColor(Color.LTGRAY).strokeColor(Color.RED).strokeWidth(1));
    }

    private void drawPolygon03() {//绘制五边形
        // 定义多边形的5个点点坐标
        LatLng latLng1 = new LatLng(42.742467, 79.842785);
        LatLng latLng2 = new LatLng(43.893433, 98.124035);
        LatLng latLng3 = new LatLng(33.058738, 101.463879);
        LatLng latLng4 = new LatLng(25.873426, 95.838879);
        LatLng latLng5 = new LatLng(30.8214661, 78.788097);
        // 声明 多边形参数对象
        PolygonOptions polygonOptions = new PolygonOptions();
        // 添加 多边形的每个顶点（顺序添加）
        polygonOptions.add(latLng1, latLng2, latLng3, latLng4, latLng5);
        polygonOptions.strokeWidth(15) // 多边形的边框
                .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
                .fillColor(Color.argb(1, 1, 1, 1));   // 多边形的填充色
        mAmap.addPolygon(polygonOptions);
    }

    private void drawPolyline() {
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(39.999391, 116.135972));
        latLngs.add(new LatLng(39.898323, 116.057694));
        latLngs.add(new LatLng(39.900430, 116.265061));
        latLngs.add(new LatLng(39.955192, 116.140092));
        mAmap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)).setUseTexture(true));
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationListener = onLocationChangedListener;
        initLocationOption();
    }

    @Override
    public void deactivate() {
        releaseClient();
    }

    private void releaseClient() {
        mLocationListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
            mLocationClient = null;
        }
        if (mGeoFenceClient != null) {
            mGeoFenceClient.removeGeoFence();//清除所有围栏
            mGeoFenceClient = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) mMapView.onResume();//重新绘制加载地图
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMapView != null) mMapView.onPause();//暂停地图的绘制
        if (mLocationClient != null) mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) mMapView.onDestroy();//销毁地图
        releaseClient();
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            LatLng latLng;
            if (aMapLocation.getErrorCode() == 0) {
                int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                float accuracy = aMapLocation.getAccuracy();//获取精度信息
                String address = aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                String country = aMapLocation.getCountry();//国家信息
                String province = aMapLocation.getProvince();//省信息
                String city = aMapLocation.getCity();//城市信息
                String district = aMapLocation.getDistrict();//城区信息
                String street = aMapLocation.getStreet();//街道信息
                String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
                String cityCode = aMapLocation.getCityCode();//城市编码
                String adCode = aMapLocation.getAdCode();//地区编码
                String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息
                String buildingId = aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                String floor = aMapLocation.getFloor();//获取当前室内定位的楼层
                int gpsAccuracyStatus = aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                String time = DateUtil.getAll(aMapLocation.getTime());//获取定位时间
                LogUtil.d("address: " + address);
                //可在其中解析amapLocation获取相应内容。
                mAMapLocation = aMapLocation;
                if (mLocationClient != null) {
                    mLocationClient.stopLocation();
                }
                mLocationListener.onLocationChanged(aMapLocation);//显示系统定位小蓝点
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            } else {
                latLng = new LatLng(0, 0);//定位失败时 默认的地图中心点
                ToastUtil.showToast(aMapLocation.getErrorInfo());
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                LogUtil.d("AmapError: location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
            mAmap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//定位回调后,将地图移动至定位点
        }
    }
}
