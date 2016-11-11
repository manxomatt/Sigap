package com.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.app.sigap.R;
import com.config.Config;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.libraries.utilities.PermissionUtil;
import com.libraries.utilities.Tools;
import com.libraries.volley.ApiAccess;
import com.libraries.volley.RouteParser;
import com.models.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_OBJ = "com.akhbra.akhbra.panicbutton.EXTRA_OBJ";

    private GoogleMap mMap;
    //private DatabaseHandler db;
    private ClusterManager<Place> mClusterManager;
    private View parent_view;
    private int cat[];
    private PlaceMarkerRenderer placeMarkerRenderer;

    // for single place
    private Place ext_place = null;
    private boolean isSinglePlace;
    HashMap<String, Place> hashMapPlaces = new HashMap<>();
    ArrayList<LatLng> MarkerPoints;

    // view for custom marker
    private ImageView icon, marker_bg;
    private View marker_view;
    Location my_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_location);

        // Get the ActionBar here to configure the way it behaves.
        /*
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_home); // set a custom icon for the default home button
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        */

        /*
        View viewActionBar = getLayoutInflater().inflate(R.layout.header, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        */

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        marker_view = inflater.inflate(R.layout.maps_marker, null);
        icon = (ImageView) marker_view.findViewById(R.id.marker_icon);
        marker_bg = (ImageView) marker_view.findViewById(R.id.marker_bg);

        ext_place = new Place();
        ext_place.setLat(Config.city_lat);
        ext_place.setLon(Config.city_lon);
        //(Place) getIntent().getSerializableExtra(EXTRA_OBJ);
        isSinglePlace = (ext_place != null);

        MarkerPoints = new ArrayList<>();

        initMapFragment();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = Tools.configActivityMaps(googleMap);
        CameraUpdate location;
        isSinglePlace =true;
        if (isSinglePlace) {
            marker_bg.setColorFilter(getResources().getColor(R.color.marker_secondary));
            MarkerOptions markerOptions = new MarkerOptions().title(ext_place.name).position(ext_place.getPosition());
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Tools.createBitmapFromView(SimLocationActivity.this, marker_view)));
            mMap.addMarker(markerOptions);
            location = CameraUpdateFactory.newLatLngZoom(ext_place.getPosition(), 12);
            //actionBar.setTitle(ext_place.name);
        } else {
            location = CameraUpdateFactory.newLatLngZoom(new LatLng(Config.city_lat, Config.city_lon), 9);
            mClusterManager = new ClusterManager<>(this, mMap);
            placeMarkerRenderer = new PlaceMarkerRenderer(this, mMap, mClusterManager);
            mClusterManager.setRenderer(placeMarkerRenderer);
            mMap.setOnCameraChangeListener(mClusterManager);
            //loadClusterManager(db.getAllPlace());
        }
        mMap.animateCamera(location);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Place place;
                if (hashMapPlaces.get(marker.getId()) != null) {
                    place = (Place) hashMapPlaces.get(marker.getId());
                } else {
                    place = ext_place;
                }
                //ActivityPlaceDetail.navigate(ActivityMaps.this, parent_view, place);
            }
        });
        // */
        my_location = Tools.getLastKnownLocation(SimLocationActivity.this);

        LatLng origin = new LatLng(my_location.getLatitude(),my_location.getLongitude());
        LatLng  dest  = ext_place.getPosition();
        showMyLocation();

        String strUrl = getUrl(origin,dest);

        //String data =
        plotNavigate(strUrl);
    }


    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */

    private void plotNavigate(String strUrl){ //} throws IOException {
        String data = "";

        ApiAccess apiAccess = new ApiAccess(this);

        apiAccess.get_data(strUrl, new ApiAccess.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                PolylineOptions lineOptions = null;
                JSONObject jObject;
                List<List<HashMap<String, String>>> routes = null;
                ArrayList<LatLng> points;

                try {
                    jObject = new JSONObject(result);
                    RouteParser parser = new RouteParser();
                    routes  = parser.parse(jObject);

                    for (int i = 0; i < routes.size(); i++) {
                        points = new ArrayList<>();
                        lineOptions = new PolylineOptions();

                        // Fetching i-th route
                        List<HashMap<String, String>> path = routes.get(i);

                        // Fetching all the points in i-th route
                        for (int j = 0; j < path.size(); j++) {
                            HashMap<String, String> point = path.get(j);

                            double lat = Double.parseDouble(point.get("lat"));
                            double lng = Double.parseDouble(point.get("lng"));
                            LatLng position = new LatLng(lat, lng);

                            points.add(position);
                        }

                        // Adding all the points in the route to LineOptions
                        lineOptions.addAll(points);
                        lineOptions.width(10);
                        lineOptions.color(R.color.theme_main_color_alpha_66);

                        Log.d("onPostExecute","onPostExecute lineoptions decoded");

                    }

                    // Drawing polyline in the Google Map for the i-th route
                    if(lineOptions != null) {
                        mMap.addPolyline(lineOptions);
                    }
                    else {
                        Log.d("onPostExecute","without Polylines drawn");
                    }



                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

       // return data;
    }


    private void showMyLocation() {
        if (PermissionUtil.isGroupPermissionGranted(this, Config.PERMISSIONS_LOCATION)) {
            // Enable / Disable my location button
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            //. getUiSettings().setMyLocationButtonEnabled(true);

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION );// .checkSelfPermission();
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    try {
                        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            showAlertDialogGps();
                        }else{
                            Location loc = Tools.getLastKnownLocation(SimLocationActivity.this);
                            CameraUpdate myCam = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 12);
                            mMap.animateCamera(myCam);
                        }
                    }catch (Exception e){}
                    return true;
                }
            });
        }
    }


    private void showAlertDialogGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_content_gps);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void initMapFragment() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private class PlaceMarkerRenderer extends DefaultClusterRenderer<Place> {
        public PlaceMarkerRenderer(Context context, GoogleMap map, ClusterManager<Place> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(Place item, MarkerOptions markerOptions) {
            //if (cat_id == -1) { // all place
                icon.setImageResource(R.drawable.round_shape);
            //} else {
            //    icon.setImageResource(cur_category.icon);
            //}
            marker_bg.setColorFilter(getResources().getColor(R.color.marker_primary));
            markerOptions.title(item.name);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Tools.createBitmapFromView(SimLocationActivity.this, marker_view)));
            if (ext_place != null && ext_place.id == item.id) {
                markerOptions.visible(false);
            }
        }

        @Override
        protected void onClusterItemRendered(Place item, Marker marker) {
            hashMapPlaces.put(marker.getId(), item);
            super.onClusterItemRendered(item, marker);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // Handle your other action bar items...
        switch (item.getItemId()) {
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
