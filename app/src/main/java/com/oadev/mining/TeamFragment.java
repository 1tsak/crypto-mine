package com.oadev.mining;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TeamFragment extends Fragment {
    View view;
    CardView inviteuser;
    TextView balanceView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team, container, false);
        inviteuser = view.findViewById(R.id.invite_user);
        balanceView = view.findViewById(R.id.balanceview);
        float amount = ((MainActivity)getActivity()).amount;
        balanceView.setText(String.valueOf(amount));
        inviteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),ReferActivity.class));
            }
        });

        return view;
    }
    public TextView getBalanceView(){
        return balanceView;
    }
}