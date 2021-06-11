package com.oadev.mining.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.oadev.mining.utility.Config;
import com.oadev.mining.activity.LoginActivity;
import com.oadev.mining.activity.MainActivity;
import com.oadev.mining.adapter.NewsAdapter;
import com.oadev.model.NewsModel;
import com.oadev.mining.utility.PrefManager;
import com.oadev.mining.R;
import com.oadev.model.User;
import com.oadev.mining.activity.ReferActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentHome extends Fragment {
    public String datetime, currentdatetime;
    public ProgressBar progressBar;
    CardView start_refer;
    TextView countdown, availableCoin;
    CardView start_mine;
    View view;
    ArrayList<NewsModel> newsModels;
    RecyclerView recyclerView;
    float ninfactor = 0.000556f;
    User user;
    RequestQueue requestQueue;
    CountDownTimer mCountDownTimer;

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
        progressBar = new ProgressBar(getActivity());

        user = PrefManager.getInstance(getActivity()).getUser();

        float amount = Config.storedamount;
        availableCoin.setText(String.valueOf(amount));
        requestQueue = Volley.newRequestQueue(getActivity());
        Config.isTimerRunning = false;
        getNews();
        fetchdata();


        return view;
    }


    private void setListener() {
        start_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startActivity(new Intent(getActivity(), ReferActivity.class));
            }
        });

        start_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMine();
            }
        });


    }

    private void startMine() {

        turnOnMining();
    }
    public void updateCountdownUI() {

        long countdownTime = getTimeDiff();
        Config.currentamount = (86400 - (countdownTime / 1000)) * ninfactor;
        Config.amount += Config.currentamount;

        if (countdownTime > 0) {
            mCountDownTimer = new CountDownTimer(countdownTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    // Used for formatting digit to be in 2 digits only
                    Config.isTimerRunning = true;
                    NumberFormat f = new DecimalFormat("00");
                    long hour = (millisUntilFinished / 3600000) % 24;
                    long min = (millisUntilFinished / 60000) % 60;
                    long sec = (millisUntilFinished / 1000) % 60;
                    countdown.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    Config.amount += ninfactor;
                    availableCoin.setText(String.valueOf(Config.amount));
                    start_mine.setCardBackgroundColor(Color.parseColor("#4ea64e"));
                    Log.d("akbros", f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

                }

                public void onFinish() {
                    //updateCoinAmount(String.valueOf(amount));
                    start_mine.setCardBackgroundColor(Color.parseColor("#FF3E3E"));
                    switchMine("Daily_Mining","0","miningoff");

                }
            }.start();
        } else {
            if (Config.miningstatus.equals("1")){
                Toast.makeText(getActivity(), "Session Finished!, Click Mine to Start Again.", Toast.LENGTH_LONG).show();
                switchMine("Daily_Mining","0","miningoff");
            }


            availableCoin.setText(String.valueOf(Config.storedamount));

        }

    }
    private void switchMine(String tittle,String status, String method) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + method + "&userid=" + user.getId() + "&tittle=" + tittle + "&status" + status,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONObject userobject = jsonObject.getJSONObject("data");
                                fetchdata();
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
    public void turnOnMining() {
        long timediff = getTimeDiff();
        if (timediff < 0) {
            Log.d("userid", String.valueOf(user.getId()));
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET,Config.functionUrl + "method=" + "updateminingstatus" + "&userid=" + user.getId(),
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            progressBar.setVisibility(View.GONE);
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                if (!jsonObject.getBoolean("error")) {
//                                    JSONObject userobject = jsonObject.getJSONObject("data");
//                                    amount = Float.parseFloat(userobject.getString("amount"));
//
//                                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
            switchMine("Daily_Mining","1","miningon");


//                                } else
//                                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(getActivity(), "Mining In Progress!", Toast.LENGTH_LONG).show();
        }

    }
    private void fetchdata() {
        Config.currentamount = 0;
        Config.amount = 0;
        Config.miningstatus = null;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "getdata" + "&userid=" + user.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONObject userobject = jsonObject.getJSONObject("data");
                                // Parse the input date
                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date inputDate = fmt.parse(userobject.getString("minetime"));
                                fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                                datetime = fmt.format(inputDate);

                                Config.amount = Float.parseFloat(userobject.getString("amount"));
                                Config.storedamount = Float.parseFloat(userobject.getString("amount"));
                                Config.referedby = userobject.getString("referedby");
                                Config.miningstatus = userobject.getString("mining");
                                if (!Config.isTimerRunning) {
                                    updateCountdownUI();
                                }

                            } else
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException | ParseException e) {
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
    public long getTimeDiff() {
        SimpleDateFormat fmt;
        fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        currentdatetime = fmt.format(new Date());
        Log.d("akbros", currentdatetime);
        long difference = 00;
        try {
            Date date1 = fmt.parse(datetime);
            Date date2 = fmt.parse(currentdatetime);
            difference = date1.getTime() - date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("akbros", String.valueOf(difference));
        return difference;

    }
    public void LogOut() {
        PrefManager.getInstance(getActivity()).logout();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

    private void bindviews(View view) {
        start_refer = view.findViewById(R.id.start_refer);
        countdown = view.findViewById(R.id.mine_timer);
        availableCoin = view.findViewById(R.id.available_coin);
        recyclerView = view.findViewById(R.id.newsRecycler);
        start_mine = view.findViewById(R.id.start_mine);
        //referCodeButton = view.findViewById(R.id.refeOption);
    }

    public TextView getCountdownView() {
        return countdown;
    }

    public void setAvailableCoinView(String amount) {
        availableCoin.setText(amount);
    }

    public CardView getStart_mineView() {
        return start_mine;
    }

    private void getNews() {


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


                                NewsAdapter adapter = new NewsAdapter(newsModels, getActivity());
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mCountDownTimer.cancel();
        super.onDestroy();

    }
}