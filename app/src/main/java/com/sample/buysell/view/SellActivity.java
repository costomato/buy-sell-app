package com.sample.buysell.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.sample.buysell.adapters.model.ItemProduct;
import com.sample.buysell.databinding.ActivitySellBinding;
import com.sample.buysell.network.NetworkCall;
import com.sample.buysell.view.dialog.DatePickerFragment;
import com.sample.buysell.view.dialog.TimePickerFragment;
import com.sample.buysell.view.dialog.UnitsBottomSheetDialog;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class SellActivity extends AppCompatActivity
        implements UnitsBottomSheetDialog.BottomSheetListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private ActivitySellBinding binding;
    private TextView tvAddUnit, tvDatePick, tvTimePick, tvTotalPrice;
    private EditText etPrice, etTotalQty, etProductName;
    private Calendar calendar;
    private Button btnSave;
    public static ItemProduct updateItem = null;
    private NetworkCall networkCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initialise();
        binding.layoutUnit.setOnClickListener(v -> {
            UnitsBottomSheetDialog bottomSheetDialog = new UnitsBottomSheetDialog();
            bottomSheetDialog.show(getSupportFragmentManager(), "units");
        });
        tvDatePick.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });
        tvTimePick.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTotalPrice(s.toString().equals("") ? 0f
                                : Float.parseFloat(s.toString()),
                        etTotalQty.getText().toString().equals("") ? 0
                                : Integer.parseInt(etTotalQty.getText().toString()));
            }
        });
        etTotalQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setTotalPrice(
                        etPrice.getText().toString().equals("") ? 0f
                                : Float.parseFloat(etPrice.getText().toString()),
                        s.toString().equals("") ? 0
                                : Integer.parseInt(s.toString()));
            }
        });

        saveClick();
        setFields();
    }

    private void setTotalPrice(float price, int qty) {
        float totalPrice = price * qty;
        tvTotalPrice.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));
    }

    private void initialise() {
        tvAddUnit = binding.tvAddUnit;
        tvDatePick = binding.tvDatePick;
        tvTimePick = binding.tvTimePick;
        calendar = Calendar.getInstance();
        String currentDate = calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE)
                + (calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM"
                : (calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : ""));
        tvDatePick.setText(currentDate);
        tvTimePick.setText(currentTime);

        etPrice = binding.etPrice;
        etProductName = binding.etProductName;
        etTotalQty = binding.etTotalQty;
        tvTotalPrice = binding.tvTotalPrice;

        btnSave = binding.btnSave;
        networkCall = new NetworkCall(this);
    }

    private void saveClick() {
        btnSave.setOnClickListener(v -> {
            if (etProductName.getText().toString().isEmpty()
                    || etPrice.getText().toString().isEmpty()
                    || tvAddUnit.getText().toString().isEmpty()
                    || etTotalQty.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all the details",
                        Toast.LENGTH_SHORT).show();
            } else {
                String date = tvDatePick.getText().toString();
                String time = tvTimePick.getText().toString();
                String name = etProductName.getText().toString();
                float price = Float.parseFloat(etPrice.getText().toString());
                String unit = tvAddUnit.getText().toString();
                int qty = Integer.parseInt(etTotalQty.getText().toString());
                if (updateItem != null) {
                    Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                    ItemProduct itemProduct = new ItemProduct(updateItem.getID(), date,
                            time, name, price, unit, qty);
                    networkCall.putData(itemProduct);
                    int ndx = MainActivity.itemProducts.indexOf(updateItem);
                    MainActivity.itemProducts.get(ndx).setDate(date);
                    MainActivity.itemProducts.get(ndx).setTime(time);
                    MainActivity.itemProducts.get(ndx).setProductName(name);
                    MainActivity.itemProducts.get(ndx).setProductPrice(price);
                    MainActivity.itemProducts.get(ndx).setUnit(unit);
                    MainActivity.itemProducts.get(ndx).setProductQty(qty);
                    updateItem = null;
                    finish();
                } else {
                    ItemProduct itemProduct = new ItemProduct(getRandomUniqueId(), date,
                            time, name, price, unit, qty);
                    MainActivity.itemProducts.add(itemProduct);
                    etProductName.setText("");
                    etPrice.setText("");
                    tvAddUnit.setText("");
                    etTotalQty.setText("");
                    Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
                    networkCall.postData(itemProduct);
                }
            }
        });
    }

    private void setFields() {
        if (updateItem != null) {
            tvDatePick.setText(updateItem.getDate());
            tvTimePick.setText(updateItem.getTime());
            etProductName.setText(updateItem.getProductName());
            String price = updateItem.getProductPrice() + "";
            etPrice.setText(price);
            tvAddUnit.setText(updateItem.getUnit());
            String qty = updateItem.getProductQty() + "";
            etTotalQty.setText(qty);
        }
    }

    private int getRandomUniqueId() {
        return new Random().nextInt(999999);
    }

    @Override
    public void onButtonClicked(String text) {
        tvAddUnit.setText(text);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateFormat = dayOfMonth + "/" + month + "/" + year;
        tvDatePick.setText(dateFormat);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String timeFormat = hourOfDay + ":" + minute
                + (calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM"
                : (calendar.get(Calendar.AM_PM) == Calendar.PM ? "PM" : ""));
        tvTimePick.setText(timeFormat);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}