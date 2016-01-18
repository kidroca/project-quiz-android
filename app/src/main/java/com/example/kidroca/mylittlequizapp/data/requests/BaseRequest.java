package com.example.kidroca.mylittlequizapp.data.requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kidroca.mylittlequizapp.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by kidroca on 16.1.2016 г..
 */
public class BaseRequest extends StringRequest {

    private static final String TAG = "Response Error";

    public BaseRequest(
            int method,
            String url,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            try {
                JSONObject jObj = new JSONObject(new String(volleyError.networkResponse.data));
                Log.d(TAG, jObj.toString(2));

                String errorMessage = generateErrorMessage(jObj);
                return new VolleyError(errorMessage, volleyError);
            } catch (JSONException e) {
                // Cant tell from the response message  handle the error
                // in the relevant context using the status code
                return new VolleyError(String.valueOf(volleyError.networkResponse.statusCode), volleyError);
            }
        }

        return volleyError;
    }

    private String generateErrorMessage(JSONObject jObj) throws JSONException {
        String error;
        if (jObj.has(AppConfig.RESPONSE_ERROR_DESCRIPTION_FIELD)) {
            error = jObj.getString(AppConfig.RESPONSE_ERROR_DESCRIPTION_FIELD);
        } else if (jObj.has(AppConfig.RESPONSE_ERROR_MODEL_STATE)) {
            JSONObject modelStateErrors = jObj.getJSONObject(AppConfig.RESPONSE_ERROR_MODEL_STATE);
            Iterator<String> keys = modelStateErrors.keys();

            StringBuilder sb = new StringBuilder();
            while (keys.hasNext()) {
                JSONArray jsonArray = modelStateErrors.getJSONArray(keys.next());
                sb.append(String.format("%s \n", jsonArray.join("\n")));
            }

            error = sb.toString();
        } else {
            error = "The server responded with an error";
        }

        return error;
    }
}
