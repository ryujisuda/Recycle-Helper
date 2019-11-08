package com.raonhaze_pbl.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.RecursiveTask;


public class TmapActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    public Context mContext = null;
    public boolean m_bTrackingMode = true;
    public TMapGpsManager tmapgps = null;
    public static int mMarkerID;


    public ArrayList<String> mArrayMakerID = new ArrayList<String>();
    public ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();

    LinearLayout mapView;
    TMapView tMapView;
    String apiKey = "b14bb0e4-3a3d-4ef1-906d-a07789a347fc";

    @Override
    public void onLocationChange(Location location) {
        if (m_bTrackingMode) {
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
            //얘는 덕수궁 이 근처 default 위치.
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);

        mContext = this;
        //선언
        mapView = new LinearLayout(this);
        tMapView = new TMapView(this);
        //세팅
        tMapView.setSKTMapApiKey(apiKey); //발급받은 api 키

        addPoint();
        showMarkerPoint();
        tMapView.setCompassMode(true);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(17);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        mapView.addView(tMapView);
        setContentView(mapView);
        tmapgps = new TMapGpsManager(this);
        tmapgps.setMinTime(10);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);//연결된 인터넷으로 현 위치를 받는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1); //위치권한 탐색 허용 관련 내용
        }
        tmapgps.OpenGps();
        tMapView.setTrackingMode(true);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude;
                double longitude;
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                TMapPoint tp = new TMapPoint(latitude, longitude);

                Log.d("테스트",tp.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, locationListener);
        //갱신하는 초를 설정해도 어쩔때는 1초에 한번..어쩔떄는 20초에 한번.. 큰 의미는 없는 것 같다.. 하지만 움직임이 클 때는 갱신되는 게 더 힘들어 하는 것 같다.
    }

    public void addPoint()
    {
        m_mapPoint.add(new MapPoint("쓰레기통",37.510350,127.066847));
        m_mapPoint.add(new MapPoint("쓰레기통",37.372762,126.634802));
        m_mapPoint.add(new MapPoint("쓰레기통",37.377327,126.637196));
        m_mapPoint.add(new MapPoint("쓰레기통",37.377327,126.637196));
        m_mapPoint.add(new MapPoint("쓰레기통",37.382538,126.639059));
    }
    public void showMarkerPoint() {
        for (int i = 0; i < m_mapPoint.size(); i++){
            TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLatitude(),m_mapPoint.get(i).getLongtitude());
            TMapMarkerItem item1 = new TMapMarkerItem();

            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher_round);
            item1.setTMapPoint(point);
            item1.setName(m_mapPoint.get(i).getName());
            item1.setVisible(item1.VISIBLE);
            item1.setIcon(bitmap);
            item1.setCalloutTitle(m_mapPoint.get(i).getName());
            item1.setCanShowCallout(true);
            item1.setAutoCalloutVisible(true);

            String strID=String.format("pmarker%d",mMarkerID++);
            tMapView.addMarkerItem(strID,item1);
            mArrayMakerID.add(strID);
        }
    }

}