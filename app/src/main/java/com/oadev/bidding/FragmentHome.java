package com.oadev.bidding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FragmentHome extends Fragment {
    Button green, violet, red, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    View view;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        bindviews(view);
        setListener();

        return view;
    }

    private void setListener() {
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void bindviews(View view) {
        green = view.findViewById(R.id.greenbtn);
        violet = view.findViewById(R.id.violetbtn);
        red = view.findViewById(R.id.redbtn);
        num0 = view.findViewById(R.id.btnmbr0);
        num1 = view.findViewById(R.id.btnmbr1);
        num2 = view.findViewById(R.id.btnmbr2);
        num3 = view.findViewById(R.id.btnmbr3);
        num4 = view.findViewById(R.id.btnmbr4);
        num5 = view.findViewById(R.id.btnmbr5);
        num6 = view.findViewById(R.id.btnmbr6);
        num7 = view.findViewById(R.id.btnmbr7);
        num8 = view.findViewById(R.id.btnmbr8);
        num9 = view.findViewById(R.id.btnmbr9);
    }
}