package com.example.kidroca.mylittlequizapp.data.json;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.example.kidroca.mylittlequizapp.data.VolleySingelton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class Requester {
    public static final String TAG = Requester.class.getSimpleName();

    public static JSONObject requestQuizzesJson(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<String> requestFuture = RequestFuture.newFuture();

        StringRequest request = new StringRequest(Request.Method.GET,
                url, requestFuture, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Return stored collection
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", VolleySingelton.getInstance().getAuthToken().toString());
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        requestQueue.add(request);

        try {
            response = new JSONObject(requestFuture.get(30000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            Log.d(TAG, e + "");
        } catch (ExecutionException e) {
            Log.d(TAG, e + "");
        } catch (TimeoutException e) {
            Log.d(TAG, e + "");
        } catch (Exception e) {
            Log.e("NastyError", e.getMessage(), e);
        }

        return response;
    }
}
