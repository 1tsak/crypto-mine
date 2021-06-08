package com.oadev.mining;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    CardView start_refer;
    TextView countdown,availableCoin;
    long countdownTime;
    CardView start_mine;
    View view;
    ArrayList<NewsModel> newsModels;
    RecyclerView recyclerView;

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
        newsModels = new ArrayList<NewsModel>();
        fetchdata();


        return view;
    }



    private void setListener() {
        start_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startActivity(new Intent(getActivity(),ReferActivity.class));
            }
        });

        start_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().startService(new Intent(getActivity(), BroadcastService.class));
                startMine();
            }
        });

    }

    private void startMine() {

        ((MainActivity)getActivity()).setCountdownTime();
    }

    private void bindviews(View view) {
        start_refer = view.findViewById(R.id.start_refer);
        countdown = view.findViewById(R.id.mine_timer);
        availableCoin = view.findViewById(R.id.available_coin);
        recyclerView = view.findViewById(R.id.newsRecycler);
        start_mine= view.findViewById(R.id.start_mine);
    }
    public TextView getCountdownView(){
        return countdown;
    }
    public TextView getAvailableCoinView(){
        return availableCoin;
    }
    public CardView getStart_mineView(){
        return start_mine;
    }
    private void fetchdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "getnews",
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
                                        newsModels.add(new NewsModel(dataobject.getString("tittle"), dataobject.getString("image"), dataobject.getString("datetime"), dataobject.getString("content")));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                                    }
                                }


                                NewsAdapter adapter = new NewsAdapter(newsModels,getActivity());
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