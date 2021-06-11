package com.oadev.mining.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oadev.mining.activity.MainActivity;
import com.oadev.mining.adapter.NewsAdapter;
import com.oadev.mining.adapter.TeamAdapter;
import com.oadev.mining.utility.Config;
import com.oadev.mining.utility.PrefManager;
import com.oadev.mining.R;
import com.oadev.model.NewsModel;
import com.oadev.model.TeamModel;
import com.oadev.model.User;
import com.oadev.mining.activity.ReferActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TeamFragment extends Fragment {
    View view;
    CardView inviteuser;
    TextView balanceView;
    ProgressBar progressBar;
    String membersCount;
    TextView memberCountTxt;
    ArrayList<TeamModel> teamModels;
    User user;
    RequestQueue requestQueue;
    RecyclerView recyclerView;



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
        memberCountTxt = view.findViewById(R.id.membercount);
        float amount = Config.amount;
        balanceView.setText(String.valueOf(amount));
        progressBar = new ProgressBar(getActivity());
        teamModels = new ArrayList<TeamModel>();
        recyclerView = view.findViewById(R.id.team_recycler);
        user = PrefManager.getInstance(getActivity()).getUser();
        inviteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ReferActivity.class));
            }
        });
        fetchdata();

        return view;
    }
    public TextView getBalanceView(){
        return balanceView;
    }

    private void fetchdata() {
        requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "fetchmembers" + "&refcode=" + user.getRefercode(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                               // JSONObject userobject = jsonObject.getJSONObject("data");
                                // Parse the input date

                                membersCount = jsonObject.getString("membercount");
                                memberCountTxt.setText(membersCount+" "+"Member (s)");


                            } else
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
    private void getTeam() {
        teamModels = new ArrayList<TeamModel>();

        ((MainActivity) getActivity()).progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "getteam"+"&refcode="+user.getRefercode(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                // Parse the input date
//                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                Date inputDate = fmt.parse(userobject.getString("minetime"));
//                                fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//                                datetime = fmt.format(inputDate);
                                final int numberOfItemsInResp = dataArray.length();
                                for (int i = 0; i < numberOfItemsInResp; i++) {
                                    try {
                                        JSONObject dataobject = dataArray.getJSONObject(i);
                                        teamModels.add(new TeamModel(dataobject.getString("name"), "", dataobject.getString("mining")));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                                    }
                                }


                                TeamAdapter adapter = new TeamAdapter(teamModels, getActivity());
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                                //Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                //updateCountdown();

                            } else
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
}