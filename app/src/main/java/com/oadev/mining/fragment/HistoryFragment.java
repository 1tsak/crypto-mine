package com.oadev.mining.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oadev.mining.R;
import com.oadev.mining.activity.MainActivity;
import com.oadev.mining.adapter.HistoryAdapter;
import com.oadev.mining.utility.Config;
import com.oadev.mining.utility.PrefManager;
import com.oadev.model.HistoryModel;
import com.oadev.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    RecyclerView recyclerView;
    User user;
    ArrayList<HistoryModel> hsmodel;
    TextView empty, totalBalance;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.history_recycler);
        empty = view.findViewById(R.id.no_item);
        totalBalance = view.findViewById(R.id.total_balance);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setRefreshing(true);

        user = PrefManager.getInstance(getActivity()).getUser();
        fetchdata();
        float amount = Config.amount;
        totalBalance.setText(String.valueOf(amount)+ " "+"NiN");
//        if(hsmodel.isEmpty()){
//            empty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }else{
//            empty.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        }
        return view;
    }

    private void fetchdata() {

        hsmodel = new ArrayList<HistoryModel>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "gethistory" + "&userid=" + user.getId(),
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
                                        hsmodel.add(new HistoryModel(dataobject.getString("tittle"), dataobject.getString("datetime"), dataobject.getString("amount"), dataobject.getString("status")));


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                                    }
                                }


                                HistoryAdapter adapter = new HistoryAdapter(hsmodel);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                                ((MainActivity) getActivity()).progressBar.setVisibility(View.GONE);
                                mSwipeRefreshLayout.setRefreshing(false);
                                totalBalance.setText(String.valueOf(Config.amount)+ " "+"NiN");
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

    public TextView getTotalBalanceView() {
        return totalBalance;
    }

    @Override
    public void onRefresh() {
        fetchdata();
    }
}