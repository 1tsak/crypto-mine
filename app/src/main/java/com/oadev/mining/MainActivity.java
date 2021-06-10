package com.oadev.mining;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public ProgressBar progressBar;
    FragmentHome fragmentHome;
    HistoryFragment historyFragment;
    TeamFragment teamFragment;
    FragmentProfile fragmentProfile;
    User user;
    RequestQueue requestQueue;
    boolean doubleBackToExitPressedOnce = false;


    LinearLayout home, history, team, more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = PrefManager.getInstance(MainActivity.this).getUser();


        home = findViewById(R.id.home);
        history = findViewById(R.id.history);
        team = findViewById(R.id.team);
        more = findViewById(R.id.more);
        progressBar = findViewById(R.id.progressbar);
        fragmentHome = new FragmentHome();
        historyFragment = new HistoryFragment();
        teamFragment = new TeamFragment();
        fragmentProfile = new FragmentProfile();
        progressBar.setVisibility(View.VISIBLE);

        requestQueue = Volley.newRequestQueue(this);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentHome);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(historyFragment);
            }
        });

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(teamFragment);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(fragmentProfile);
            }
        });

        //fetchdata();
        setFragment(fragmentHome);

    }

//    public void updateCountdownUI() {
//
//        long countdownTime = getTimeDiff();
//        currentamount = (86400 - (countdownTime / 1000)) * ninfactor;
//        amount += currentamount;
//
//        if (countdownTime > 0) {
//                fragmentHome.getStart_mineView().setCardBackgroundColor(Color.parseColor("#4ea64e"));
//            new CountDownTimer(countdownTime, 1000) {
//                public void onTick(long millisUntilFinished) {
//                    // Used for formatting digit to be in 2 digits only
//                    NumberFormat f = new DecimalFormat("00");
//                    long hour = (millisUntilFinished / 3600000) % 24;
//                    long min = (millisUntilFinished / 60000) % 60;
//                    long sec = (millisUntilFinished / 1000) % 60;
//                    fragmentHome.getCountdownView().setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//                    amount += ninfactor;
//                    fragmentHome.setAvailableCoinView(String.valueOf(amount));
//                    Log.d("akbros", f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
//
//                }
//
//                public void onFinish() {
//                    //updateCoinAmount(String.valueOf(amount));
//                    fragmentHome.getStart_mineView().setCardBackgroundColor(Color.parseColor("#FF3E3E"));
//                    switchMine("Daily_Mining","0","miningoff");
//
//                }
//            }.start();
//        } else {
//            if (miningstatus.equals("1")){
//                Toast.makeText(getApplicationContext(), "Session Finished!, Click Mine to Start Again.", Toast.LENGTH_LONG).show();
//                switchMine("Daily_Mining","0","miningoff");
//            }
//
//
//            fragmentHome.setAvailableCoinView(String.valueOf(storedamount));
//
//        }
//
//    }
//
//    private void updateCoinAmount(String amount) {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "updatecoin" + "&userid=" + user.getId() + "&amount=" + amount,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.getBoolean("error")) {
//                                JSONObject userobject = jsonObject.getJSONObject("data");
//                                // Parse the input date
//
//                                Toast.makeText(getApplicationContext(), "Session Finished!, Click Mine to Start Again.", Toast.LENGTH_LONG).show();
//                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                fetchdata();
//
//                            } else
//                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }
//
//
//    public void turnOnMining() {
//        long timediff = getTimeDiff();
//        if (timediff < 0) {
//            Log.d("userid", String.valueOf(user.getId()));
////
////            StringRequest stringRequest = new StringRequest(Request.Method.GET,Config.functionUrl + "method=" + "updateminingstatus" + "&userid=" + user.getId(),
////                    new Response.Listener<String>() {
////                        @Override
////                        public void onResponse(String response) {
////                            progressBar.setVisibility(View.GONE);
////                            try {
////                                JSONObject jsonObject = new JSONObject(response);
////                                if (!jsonObject.getBoolean("error")) {
////                                    JSONObject userobject = jsonObject.getJSONObject("data");
////                                    amount = Float.parseFloat(userobject.getString("amount"));
////
////                                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                                    switchMine("Daily_Mining","1","miningon");
//
//
////                                } else
////                                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                    }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////                    progressBar.setVisibility(View.GONE);
////                    Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
////                    error.printStackTrace();
////                }
////            });
////
////            requestQueue.add(stringRequest);
//
//        } else {
//            Toast.makeText(MainActivity.this, "Mining In Progress!", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    private void fetchdata() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "getdata" + "&userid=" + user.getId(),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.getBoolean("error")) {
//                                JSONObject userobject = jsonObject.getJSONObject("data");
//                                // Parse the input date
//                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                Date inputDate = fmt.parse(userobject.getString("minetime"));
//                                fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//                                datetime = fmt.format(inputDate);
//
//                                amount = Float.parseFloat(userobject.getString("amount"));
//                                storedamount = Float.parseFloat(userobject.getString("amount"));
//                                referedby = userobject.getString("referedby");
//                                miningstatus = userobject.getString("mining");
//                                updateCountdownUI();
//
//                            } else
//                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        } catch (JSONException | ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }
//
//    private void switchMine(String tittle,String status, String method) {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + method + "&userid=" + user.getId() + "&tittle=" + tittle + "&status" + status,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.getBoolean("error")) {
//                                JSONObject userobject = jsonObject.getJSONObject("data");
//                                fetchdata();
//                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//
//                            } else
//                                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
//                error.printStackTrace();
//            }
//        });
//
//        requestQueue.add(stringRequest);
//    }
//
    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//
//    public long getTimeDiff() {
//        SimpleDateFormat fmt;
//        fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//        currentdatetime = fmt.format(new Date());
//        Log.d("akbros", currentdatetime);
//        long difference = 00;
//        try {
//            Date date1 = fmt.parse(datetime);
//            Date date2 = fmt.parse(currentdatetime);
//            difference = date1.getTime() - date2.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Log.d("akbros", String.valueOf(difference));
//        return difference;
//
//    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void LogOut() {
        PrefManager.getInstance(MainActivity.this).logout();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

}