package com.oadev.mining;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class FragmentHome extends Fragment {
    CardView start_refer;
    TextView countdown;
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
        start_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startActivity(new Intent(getActivity(),ReferActivity.class));
            }
        });



    }

    private void bindviews(View view) {
        start_refer = view.findViewById(R.id.start_refer);
        countdown = view.findViewById(R.id.mine_timer);
    }
    public TextView getCountdownView(){
        return countdown;
    }
}