package com.oadev.mining.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.oadev.mining.R;
import com.oadev.mining.activity.AddMoneyActivity;


public class FragmentWallet extends Fragment {

    View view;
    CardView addmoney;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet, container, false);

        addmoney = view.findViewById(R.id.addMoney);
        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AddMoneyActivity.class));
            }
        });
        view.findViewById(R.id.withdrawButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bad Response, Api Error", Toast.LENGTH_LONG).show();
            }
        });
        return view;


    }
}