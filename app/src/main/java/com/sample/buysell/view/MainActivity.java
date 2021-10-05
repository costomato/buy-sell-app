package com.sample.buysell.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.buysell.adapters.ItemProductAdapter;
import com.sample.buysell.adapters.model.ItemProduct;
import com.sample.buysell.databinding.ActivityMainBinding;
import com.sample.buysell.network.NetworkCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static List<ItemProduct> itemProducts;
    private RecyclerView rvProducts;
    private ItemProductAdapter productAdapter;
    private TextView tvPurchaseAmount, tvSellAmount, tvDetailAmount, tvPurchaseAmount2, tvSellAmount2;
    private Button btnPurchase, btnSell;

    private NetworkCall networkCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialise();
        setupRecyclerView();

        btnPurchase.setOnClickListener(v -> {
            Toast.makeText(this,
                    "Only SELL feature implemented", Toast.LENGTH_SHORT).show();
        });

        btnSell.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SellActivity.class))
        );
    }

    private void initialise() {
        itemProducts = new ArrayList<>();
        rvProducts = binding.rvProducts;
        tvDetailAmount = binding.tvDetailAmountCard2;
        tvPurchaseAmount = binding.tvPurchaseAmount;
        tvPurchaseAmount2 = binding.tvPurchaseAmountCard2;
        tvSellAmount2 = binding.tvSellAmountCard2;
        tvSellAmount = binding.tvSellAmount;
        btnPurchase = binding.btnPurchase;
        btnSell = binding.btnSell;

        networkCall = new NetworkCall(this);
        getData();
    }

    private void setupRecyclerView() {
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ItemProductAdapter(this, itemProducts);
        rvProducts.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(position -> {
            SellActivity.updateItem = itemProducts.get(position);
            startActivity(new Intent(MainActivity.this, SellActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged();
        updateData();
    }

    private void updateData() {
        float totalSell = 0;
        for (ItemProduct item : itemProducts) {
            totalSell += item.getProductQty() * item.getProductPrice();
        }
        String formatSell = totalSell + "";
        tvSellAmount.setText(formatSell);
        tvSellAmount2.setText(formatSell);
        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH)
                + "/" + c.get(Calendar.YEAR);
        tvDetailAmount.setText(date);
    }

    public void getData() {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, NetworkCall.BASE_URL + "api/getTransactionList", null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject currentObject = array.getJSONObject(i);
                            long id;
                            try {
                                id = currentObject.getLong("ID");
                            } catch (Exception e) {
                                id = i;
                            }

                            String date = currentObject.getString("date");
                            String time = currentObject.getString("time");
                            String name = currentObject.getString("name");
                            String unit = currentObject.getString("unit");
                            int quantity = currentObject.getInt("quantity");
                            float totalPrice = (float) currentObject.getDouble("totalPrice");
                            itemProducts.add(new ItemProduct(id, date, time, name, totalPrice, unit, quantity));
                            productAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("TAG", "getData: " + error);
                }
        );
        Volley.newRequestQueue(this).add(getRequest);
    }
}