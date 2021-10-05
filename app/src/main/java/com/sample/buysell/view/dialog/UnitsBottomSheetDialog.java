package com.sample.buysell.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sample.buysell.databinding.BottomSheetUnitsBinding;

public class UnitsBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BottomSheetUnitsBinding binding =
                BottomSheetUnitsBinding.inflate(inflater, container, false);

        binding.tvUnit1.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit1.getText().toString());
            dismiss();
        });
        binding.tvUnit2.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit2.getText().toString());
            dismiss();
        });
        binding.tvUnit3.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit3.getText().toString());
            dismiss();
        });
        binding.tvUnit4.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit4.getText().toString());
            dismiss();
        });
        binding.tvUnit5.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit5.getText().toString());
            dismiss();
        });
        binding.tvUnit6.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit6.getText().toString());
            dismiss();
        });
        binding.tvUnit7.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit7.getText().toString());
            dismiss();
        });
        binding.tvUnit8.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit8.getText().toString());
            dismiss();
        });
        binding.tvUnit9.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit9.getText().toString());
            dismiss();
        });
        binding.tvUnit10.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit10.getText().toString());
            dismiss();
        });
        binding.tvUnit11.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit11.getText().toString());
            dismiss();
        });
        binding.tvUnit12.setOnClickListener(v->{
            mListener.onButtonClicked(binding.tvUnit12.getText().toString());
            dismiss();
        });

        binding.ivClose.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement bottom sheet listener");
        }
    }
}
