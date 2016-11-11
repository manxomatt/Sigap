package com.models;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by akhmey on 09/10/2016.
 */

public class MapRendering extends DefaultClusterRenderer<Place> {
    Context ctx;
    public MapRendering(Context context, GoogleMap map,ClusterManager<Place> clusterManager) {
        //ctx = context;
        super(context, map, clusterManager);
    }


    protected void onBeforeClusterItemRendered(Place item, MarkerOptions markerOptions) {

        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(Tools.createBitmapFromView(ctx, marker_view)));
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getName());
        super.onBeforeClusterItemRendered(item, markerOptions);
    }
}
