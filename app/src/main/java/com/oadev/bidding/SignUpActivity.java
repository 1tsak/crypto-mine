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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText nameEditText, phoneNumberEditText, emailEditText, passwordEditText, rptpasswordEditText;
    CardView Signup;
    TextView logintxt;
    String name, phoneNumber, email, password, rptpassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //binding views
        nameEditText = findViewById(R.id.namefield);
        phoneNumberEditText = findViewById(R.id.phoneNumberfield);
        emailEditText = findViewById(R.id.emailfield);
        passwordEditText = findViewById(R.id.passwdfield);
        rptpasswordEditText = findViewById(R.id.rptpswdfield);
        Signup = findViewById(R.id.signupbtn);
        logintxt = findViewById(R.id.logintxt);
        progressBar = findViewById(R.id.progressbar);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                rptpassword = rptpasswordEditText.getText().toString();
                if (name.matches("")&&phoneNumber.matches("")&&email.matches("")&&password.matches("")&&rptpassword.matches("")){

                    Toast.makeText(SignUpActivity.this,"Please Fill All Required Fields!",Toast.LENGTH_LONG).show();

                }else{
                    if (rptpassword.equals(password)){
                        if (isEmailValid(email)) {
                            progressBar.setVisibility(View.VISIBLE);
                            doSignup(name,phoneNumber,email,password);

//                            Intent intent = new Intent(SignUpActivity.this, PhoneAuthActivity.class);
//                            intent.putExtra("action", "signup");
//                            intent.putExtra("name", name);
//                            intent.putExtra("phoneNumber", name);
//                            intent.putExtra("email", name);
//                            intent.putExtra("password", name);
//                            startActivity(intent);
                        }else{
                            Toast.makeText(SignUpActivity.this,"Invalid Email!",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(SignUpActivity.this,"Passwords do not Match!",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });


    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void doSignup(String name,String phone,String email,String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.signupUrl + "name=" + name + "&phone=" + phone + "&email=" + email + "&password=" + password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else
                                Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            ;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }
}