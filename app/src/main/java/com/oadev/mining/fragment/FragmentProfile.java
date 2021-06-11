package com.oadev.mining.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.oadev.mining.activity.MainActivity;
import com.oadev.mining.utility.PrefManager;
import com.oadev.mining.R;
import com.oadev.model.User;
import com.oadev.mining.activity.ReferActivity;
import com.oadev.mining.activity.RolesActivity;
import com.oadev.mining.activity.WhitePagesActivity;

public class FragmentProfile extends Fragment {

    View view;
    TextView name, username;
    User user;
    CardView referBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = PrefManager.getInstance(getActivity()).getUser();
        name = view.findViewById(R.id.name_txt);
        username = view.findViewById(R.id.username_txt);
        referBtn = view.findViewById(R.id.refer_btn);
        name.setText(user.getUsername());
        username.setText("@" + user.getUsername());
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).LogOut();
            }
        });

        view.findViewById(R.id.contactUsLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactEmail("Support NiN Coin Market");
            }
        });

        view.findViewById(R.id.whitepaperLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), WhitePagesActivity.class));
            }
        });
        view.findViewById(R.id.rolesLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), RolesActivity.class));
            }
        });
        referBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ReferActivity.class));
            }
        });

        return view;
    }

    public void contactEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:abc@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}