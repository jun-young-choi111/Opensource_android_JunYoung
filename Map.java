package com.example.firstcbnu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Map extends AppCompatActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{


    private GoogleMap mMap;
    private Marker currentMarker = null;

    private String getbuildingname;     //BuildingClicked.java로 부터 넘어온 부속건물(specifibuilding)의 이름값(String)을 담을 변수

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초


    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocatiion;
    LatLng currentPosition;


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;


    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent clickedintent2 = getIntent(); //BuildingClicked.java에서 넘어오는 정보들을 받기위해 "getIntent()"함수의 변수를 선언
        getbuildingname = clickedintent2.getStringExtra("건물이름");   //clickedintent2이라는 getIntent변수에 BuildingClicked.java로 부터 넘어온 선택된 아이템의 string값을 저장한다.


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.map);

        mLayout = findViewById(R.id.layout_main);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");

        mMap = googleMap;
        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동

        LatLng S4_1=new LatLng(36.62557,127.45440);
        LatLng E8=new LatLng(36.62546,127.45795);
        LatLng E9=new LatLng(36.62508,127.45719);
        LatLng E10=new LatLng(36.62489,127.45784);
        LatLng S1=new LatLng(36.62775,127.45673);
        LatLng S1_3=new LatLng(36.62668,127.45679);
        LatLng S1_6=new LatLng(36.62504,127.45590);
        LatLng E8_1=new LatLng(36.62659,127.45813);
        LatLng E8_10=new LatLng(36.62409,127.45804);
        LatLng N14=new LatLng(36.63093,127.45646);
        LatLng N15=new LatLng(36.62937,127.45783);
        LatLng N13=new LatLng(36.63005,127.45692);
        LatLng S21_4=new LatLng(36.62974,127.45281);
        LatLng E1_2=new LatLng(36.62875,127.46060);
        LatLng N20_1=new LatLng(36.63040,127.46088);
        LatLng E12_1=new LatLng(36.62329,127.45604);
        LatLng E7_1=new LatLng(36.62483,127.46081);
        LatLng S4_2=new LatLng(36.62520,127.45483);
        LatLng N16_2=new LatLng(36.63078,127.45727);
        LatLng S17_1=new LatLng(36.62743,127.45238);
        LatLng E8_11=new LatLng(36.62395,127.45938);
        LatLng N17_3=new LatLng(36.63145,127.45803);
        LatLng S1_7=new LatLng(36.62692,127.45705);
        LatLng S2=new LatLng(36.62638,127.45547);
        LatLng S8=new LatLng(36.62711,127.45398);
        LatLng S9=new LatLng(36.62766,127.45532);
        LatLng N10=new LatLng(36.63014,127.45475);
        LatLng N11=new LatLng(36.62930,127.45526);
        LatLng N12=new LatLng(36.62845,127.45741);
        LatLng E2=new LatLng(36.62817,127.45946);
        LatLng E3=new LatLng(36.62754,127.45881);
        LatLng N2=new LatLng(36.63193,127.45400);
        LatLng S14=new LatLng(36.62796,127.45430);

        setDefaultLocation();

        switch(getbuildingname) {
            case "S4-1 전자정보3관":
            mMap.addMarker(new MarkerOptions().position(S4_1).title("전자정보3관(S4-1)"));
            break;
            case "E8-7 전자정보1관":
            mMap.addMarker(new MarkerOptions().position(E8).title("전자정보1관(E8-7)"));
            break;
            case "E9 학연산공동기술연구원":
            mMap.addMarker(new MarkerOptions().position(E9).title("학연산공동기술연구원(E9)"));
            break;
            case "E10 전자정보2관":
            mMap.addMarker(new MarkerOptions().position(E10).title("전자정보2관(E10)"));
            break;
            case "S1-1 자연대1호관" :
            mMap.addMarker(new MarkerOptions().position(S1).title("자연대1호관(S1-1)"));
            break;
            case"S1-3 자연대3호관":
            mMap.addMarker(new MarkerOptions().position(S1_3).title("자연대3호관(S1-3)"));
            break;
            case "S1-6 자연대6호관":
            mMap.addMarker(new MarkerOptions().position(S1_6).title("자연대6호관(S1-6)"));
            break;
            case "E8-1 공과대학본관":
            mMap.addMarker(new MarkerOptions().position(E8_1).title("공과대학본관(E8-1)"));
            break;
            case "E8-10 제5공학관":
            mMap.addMarker(new MarkerOptions().position(E8_10).title("제5공학관(E8-10)"));
            break;
            case "N14 인문사회관":
            mMap.addMarker(new MarkerOptions().position(N14).title("인문사회관(N14)"));
            break;
            case "N15 사회과학대학":
            mMap.addMarker(new MarkerOptions().position(N15).title("사회과학대학(N15)"));
            break;
            case "N13 경영학관":
            mMap.addMarker(new MarkerOptions().position(N13).title("경영대학(N13)"));
            break;
            case "S21-4 농업생명환경대학":
            mMap.addMarker(new MarkerOptions().position(S21_4).title("농업생명환경대학(S21-4)"));
            break;
            case "E1-2 사범대학강의동":
            mMap.addMarker(new MarkerOptions().position(E1_2).title("사범대학강의동(E1-2)"));
            break;
            case "N20-1 생활과학대학":
            mMap.addMarker(new MarkerOptions().position(N20_1).title("생활과학대학(N20-1)"));
            break;
            case "E12-1 수의과대학":
            mMap.addMarker(new MarkerOptions().position(E12_1).title("수의과대학(E12-1)"));
            break;
            case "E7-1 의과대학1호관":
            mMap.addMarker(new MarkerOptions().position(E7_1).title("의과대학1호관(E7-1)"));
            break;
            case "S4-2나이팅게일관":
            mMap.addMarker(new MarkerOptions().position(S4_2).title("나이팅게일관(S4-2)"));
            break;
            case "N16-2 미술관":
            mMap.addMarker(new MarkerOptions().position(N16_2).title("미술관(N16-2)"));
            break;
            case "S17-1 양성재":
            mMap.addMarker(new MarkerOptions().position(S17_1).title("양성재(S17-1)"));
            break;
            case "E8-11 양진재":
            mMap.addMarker(new MarkerOptions().position(E8_11).title("양진재(E8-11)"));
            break;
            case"N17-3 개성재":
            mMap.addMarker(new MarkerOptions().position(N17_3).title("개성재(N17-3)"));
            break;
            case"S1-7 과학기술도서관":
            mMap.addMarker(new MarkerOptions().position(S1_7).title("과학기술도서관(S1-7)"));
            break;
            case"S2 전산정보원":
            mMap.addMarker(new MarkerOptions().position(S2).title("전산정보원(S2)"));
            break;
            case"S8 야외공연장":
            mMap.addMarker(new MarkerOptions().position(S8).title("야외공연장(S8)"));
            break;
            case"S9 박물관":
            mMap.addMarker(new MarkerOptions().position(S9).title("박물관(S9)"));
            break;
            case"N10 대학본부":
            mMap.addMarker(new MarkerOptions().position(N10).title("대학본부,국제교루본부(N10)"));
            break;
            case"N11 공동실험실습관":
            mMap.addMarker(new MarkerOptions().position(N11).title("공동실험실습관(N11)"));
            break;
            case"N12 중앙도서관":
            mMap.addMarker(new MarkerOptions().position(N12).title("중앙도서관(N12)"));
            break;
            case"E2 개신문화관":
            mMap.addMarker(new MarkerOptions().position(E2).title("개신문화관(E2)"));
            break;
            case"E3 제1학생회관":
            mMap.addMarker(new MarkerOptions().position(E3).title("제1학생회관(E3)"));
            break;
            case"N2 법학전문대학원":
            mMap.addMarker(new MarkerOptions().position(N2).title("법학전문대학원(N2)"));
            break;
            case"학교 지도 버튼":
                mMap.addMarker(new MarkerOptions().position(S4_1).title("전자정보3관(S4-1)"));
                mMap.addMarker(new MarkerOptions().position(E8).title("전자정보1관(E8-7)"));
                mMap.addMarker(new MarkerOptions().position(E9).title("학연산공동기술연구원(E9)"));
                mMap.addMarker(new MarkerOptions().position(E10).title("전자정보2관(E10)"));
                mMap.addMarker(new MarkerOptions().position(S1).title("자연대1호관(S1-1)"));
                mMap.addMarker(new MarkerOptions().position(S1_3).title("자연대3호관(S1-3)"));
                mMap.addMarker(new MarkerOptions().position(S1_6).title("자연대6호관(S1-6)"));
                mMap.addMarker(new MarkerOptions().position(E8_1).title("공과대학본관(E8-1)"));
                mMap.addMarker(new MarkerOptions().position(E8_10).title("제5공학관(E8-10)"));
                mMap.addMarker(new MarkerOptions().position(N14).title("인문사회관(N14)"));
                mMap.addMarker(new MarkerOptions().position(N15).title("사회과학대학(N15)"));
                mMap.addMarker(new MarkerOptions().position(N13).title("경영대학(N13)"));
                mMap.addMarker(new MarkerOptions().position(S21_4).title("농업생명환경대학(S21-4)"));
                mMap.addMarker(new MarkerOptions().position(E1_2).title("사범대학강의동(E1-2)"));
                mMap.addMarker(new MarkerOptions().position(N20_1).title("생활과학대학(N20-1)"));
                mMap.addMarker(new MarkerOptions().position(E12_1).title("수의과대학(E12-1)"));
                mMap.addMarker(new MarkerOptions().position(E7_1).title("의과대학1호관(E7-1)"));
                mMap.addMarker(new MarkerOptions().position(S4_2).title("나이팅게일관(S4-2)"));
                mMap.addMarker(new MarkerOptions().position(N16_2).title("미술관(N16-2)"));
                mMap.addMarker(new MarkerOptions().position(S17_1).title("양성재(S17-1)"));
                mMap.addMarker(new MarkerOptions().position(E8_11).title("양진재(E8-11)"));
                mMap.addMarker(new MarkerOptions().position(N17_3).title("개성재(N17-3)"));
                mMap.addMarker(new MarkerOptions().position(S1_7).title("과학기술도서관(S1-7)"));
                mMap.addMarker(new MarkerOptions().position(S2).title("전산정보원(S2)"));
                mMap.addMarker(new MarkerOptions().position(S8).title("야외공연장(S8)"));
                mMap.addMarker(new MarkerOptions().position(S9).title("박물관(S9)"));
                mMap.addMarker(new MarkerOptions().position(N10).title("대학본부,국제교루본부(N10)"));
                mMap.addMarker(new MarkerOptions().position(N11).title("공동실험실습관(N11)"));
                mMap.addMarker(new MarkerOptions().position(N12).title("중앙도서관(N12)"));
                mMap.addMarker(new MarkerOptions().position(E2).title("개신문화관(E2)"));
                mMap.addMarker(new MarkerOptions().position(E3).title("제1학생회관(E3)"));
                mMap.addMarker(new MarkerOptions().position(N2).title("법학전문대학원(N2)"));
                break;
            // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        }
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( Map.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

        mMap.addMarker(new MarkerOptions().position(new LatLng(37.4078498,-122.1511445)).title("Marker"));

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 현재 오동작을 해서 주석처리

        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                Log.d( TAG, "onMapClick :");
            }
        });
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());


                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                        + " 경도:" + String.valueOf(location.getLongitude());

                Log.d(TAG, "onLocationResult : " + markerSnippet);


                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);

                mCurrentLocatiion = location;
            }


        }

    };



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        if (checkPermission()) {

            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap!=null)
                mMap.setMyLocationEnabled(true);

        }


    }


    @Override
    protected void onStop() {

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }




    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);

    }


    public void setDefaultLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }

        return false;

    }



    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                }else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Map.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }



}