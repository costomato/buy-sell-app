package com.sample.buysell.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.buysell.adapters.model.ItemProduct;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkCall {
    public static final String BASE_URL = "https://e9fa-2405-201-3017-1073-c49c-201d-8a49-5e6a.ngrok.io/";
    private final Context context;
    private final RequestQueue queue;

    public NetworkCall(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void postData(ItemProduct itemProduct) {
        JSONObject params = new JSONObject();
        try {
            params.put("ID", itemProduct.getID());
            params.put("date", itemProduct.getDate());
            params.put("time", itemProduct.getTime());
            params.put("name", itemProduct.getProductName());
            params.put("unit", itemProduct.getUnit());
            params.put("quantity", itemProduct.getProductQty());
            params.put("totalPrice", itemProduct.getProductPrice());
            params.put("typeOfTransaction", "POST");

        } catch (JSONException e) {
            Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/createTransaction", params,
                response -> Toast.makeText(context, "Data sent to server!", Toast.LENGTH_LONG).show(),
                error -> {
                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                });

        queue.add(jsonObjectRequest);
    }


    public void putData(ItemProduct itemProduct) {
        JSONObject params = new JSONObject();
        try {
            params.put("ID", itemProduct.getID());
            params.put("date", itemProduct.getDate());
            params.put("time", itemProduct.getTime());
            params.put("name", itemProduct.getProductName());
            params.put("unit", itemProduct.getUnit());
            params.put("quantity", itemProduct.getProductQty());
            params.put("totalPrice", itemProduct.getProductPrice());
            params.put("typeOfTransaction", "POST");

        } catch (JSONException e) {
            Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, BASE_URL + "api/updateTransaction", params,
                response -> Toast.makeText(context, "Data updated", Toast.LENGTH_LONG).show(),
                error -> {
                    Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                });

        queue.add(jsonObjectRequest);
    }

}
