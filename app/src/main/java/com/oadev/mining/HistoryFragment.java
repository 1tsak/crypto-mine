package com.oadev.mining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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


public class HistoryFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    User user;
    ArrayList<HistoryModel> hsmodel;
    TextView empty,totalBalance;


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

        user = PrefManager.getInstance(getActivity()).getUser();
        hsmodel = new ArrayList<HistoryModel>();
        fetchdata();
        float amount = Config.storedamount;
        totalBalance.setText(String.valueOf(amount));
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
                                for (int i = 0; i < response.length(); i++) {
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
    public TextView getTotalBalanceView(){
        return totalBalance;
    }
}