package com.oadev.bidding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText phoneEditText, passwdEditText;
    CardView Login;
    TextView signup;
    String phoneNumber, password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // binding views
        Login = findViewById(R.id.Login);
        signup = findViewById(R.id.signuptxt);
        phoneEditText = findViewById(R.id.phoneNumberfield);
        passwdEditText = findViewById(R.id.passwdfield);
        progressBar = findViewById(R.id.progressbar);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!phoneEditText.getText().toString().isEmpty() && !passwdEditText.getText().toString().isEmpty()) {
                    phoneNumber = phoneEditText.getText().toString();
                    password = passwdEditText.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    doLogin(phoneNumber, password);

//                    Intent intent = new Intent(LoginActivity.this, PhoneAuthActivity.class);
//                    intent.putExtra("action", "login");
//                    intent.putExtra("phoneNumber", phoneNumber);
//                    intent.putExtra("password", password);
//                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void doLogin(String phone, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.loginUrl + "phone=" + phone + "&password=" + password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                JSONObject userobject = jsonObject.getJSONObject("user");
                                User user = new User(
                                        userobject.getInt("id"),
                                        userobject.getString("name"),
                                        userobject.getString("email"),
                                        userobject.getString("amount"),
                                        userobject.getString("password")
                                );
                                PrefManager.getInstance(LoginActivity.this).userLogin(user);
                                finish();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } else
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }

}