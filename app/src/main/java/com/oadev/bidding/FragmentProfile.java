package com.oadev.bidding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentProfile extends Fragment {

    View view;
    TextView name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        User user = PrefManager.getInstance(getActivity()).getUser();
        name = view.findViewById(R.id.name);

        name.setText(user.getUsername());
        view.findViewById(R.id.profileCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Bad Response, Api Error",Toast.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.myWalletCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Bad Response, Api Error",Toast.LENGTH_LONG).show();
            }
        });
        view.findViewById(R.id.logcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Bad Response, Api Error",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}