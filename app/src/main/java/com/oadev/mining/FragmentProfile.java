package com.oadev.mining;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.io.File;

public class FragmentProfile extends Fragment {

    View view;
    TextView name,username;
    User user;
    CardView referBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = PrefManager.getInstance(getActivity()).getUser();
        name = view.findViewById(R.id.name_txt);
        username = view.findViewById(R.id.username_txt);
        referBtn = view.findViewById(R.id.refer_btn);
        name.setText(user.getUsername());
        username.setText("@"+user.getUsername());
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).LogOut();
            }
        }); view.findViewById(R.id.contactUsLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactEmail("Support NiN Coin Market");
            }
        });view.findViewById(R.id.whitepaperLyt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(String.valueOf(getResources().getIdentifier("wp",
                        "raw", getActivity().getPackageName()))); // Here you declare your pdf path
                Intent pdfViewIntent = new Intent(Intent.ACTION_VIEW);
                pdfViewIntent.setDataAndType(Uri.fromFile(file),"application/pdf");
                pdfViewIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                Intent intent = Intent.createChooser(pdfViewIntent, "Open File");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here, or something
                }
            }
        });
        referBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(),ReferActivity.class));
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