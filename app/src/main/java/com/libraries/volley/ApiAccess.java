package com.libraries.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akhmey on 25/09/2016.
 */

public class ApiAccess {

    Context ctx;

    public ApiAccess(Context ctx){
        this.ctx = ctx;
    }

    public void get_data(String api_site,final VolleyCallback callback) {

        //String api_site = "http://192.168.43.57/pickup/index.php/api/pickup_order/checkinlist";
        Log.v("xpickup", "xResponse: " + api_site);
        // Log.v("xpickup","api_key : " + AppController.getApiKey());

        StringRequest strRequest = new StringRequest(Request.Method.GET, api_site,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);

                        Log.v("xpickup", "xResponse: " + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volly Error", error.toString());
                        VolleyLog.d("Errorvolley", "Errors : " + error.getMessage());
                        //VolleyLog.v("Errorvolley", "Errors : " + error.getMessage());
                    }
                }){
            @Override
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                // params.put("API_KEY",AppController.getApiKey());//
                return params;
            }
        };

        strRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        VolleySingleton.getInstance(this.ctx).addToRequestQueue(strRequest);
    }

    public void post_data(String api_site,final Map<String,String>  xparams,final VolleyCallback callback) {
        Log.v("xpickup", "xResponse: " + api_site);
        // Log.v("xpickup","api_key : " + AppController.getApiKey());

        StringRequest strRequest = new StringRequest(Request.Method.POST, api_site,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccessResponse(response);

                        Log.v("xpickup", "xResponse: " + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volly Error", error.toString());
                        VolleyLog.d("Errorvolley", "Errors : " + error.getMessage());
                        //VolleyLog.v("Errorvolley", "Errors : " + error.getMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                for (Map.Entry<String,String> entry : xparams.entrySet()) {
                    params.put(entry.getKey(), entry.getValue());
                }

                return params;
            }
            @Override
            public Map<String,String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                //params.put("API_KEY",AppController.getApiKey());//
                return params;
            }
        };
        //a03c2a01559b1c994ce4b8b3f148c440
        strRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        VolleySingleton.getInstance(this.ctx).addToRequestQueue(strRequest);
    }

    public void json_get_data(String api_site,final JVolleyCallback callback){
        JsonObjectRequest jsonRequest = new JsonObjectRequest(api_site,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        VolleySingleton.getInstance(this.ctx).addToRequestQueue(jsonRequest);
    }

    public interface VolleyCallback {
        void onSuccessResponse(String result);
    }

    public interface JVolleyCallback{
        void onSuccessResponse(JSONObject result);

    }
}

