package com.example.mypower;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Activty extends Fragment {
    Button btn1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        btn1=view.findViewById(R.id.btnenter);
        btn1.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),Tokenpage.class);
            startActivity(intent);
        });

        return view;
    }
}