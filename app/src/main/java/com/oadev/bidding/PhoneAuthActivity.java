package com.oadev.bidding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    String code;
    EditText otpEdittext;
    CardView Verify;
    Intent intent;
    String action;
    ImageButton back;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private final boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        getSupportActionBar().hide();

        Verify = findViewById(R.id.verifybtn);
        back = findViewById(R.id.backbtn);


        mAuth = FirebaseAuth.getInstance();

        intent = getIntent();
        action = intent.getStringExtra("action");


        phoneNumber = getIntent().getStringExtra("phoneNumber");

        // binding views

        otpEdittext = findViewById(R.id.et_otp);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    otpEdittext.setText(code);
                    //verifying the code
                    verifyCode(code);
                }

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(PhoneAuthActivity.this, "Code Sent", Toast.LENGTH_LONG).show();
                mVerificationId = s;
                mResendToken = forceResendingToken;

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneAuthActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        startAuthentication();
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PhoneAuthActivity.this, otpEdittext.getText().toString(), Toast.LENGTH_LONG).show();
                verifyCode(otpEdittext.getText().toString());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthActivity.super.onBackPressed();
            }
        });

    }

    public void startAuthentication() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneAuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            if (action.equals("login")) {
                                doLogin();
                            } else {
                                doSignup();
                            }

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(PhoneAuthActivity.this, message, Toast.LENGTH_LONG).show();


                        }
                    }
                });
    }

    private void doSignup() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String name, phone, email, password;
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phoneNumber");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.signupUrl + "name=" + name + "&phone=" + phone + "&email=" + email + "&password=" + password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(PhoneAuthActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(PhoneAuthActivity.this, LoginActivity.class));
                            } else
                                Toast.makeText(PhoneAuthActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhoneAuthActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    private void doLogin() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String phone, password;
        phone = intent.getStringExtra("phoneNumber");
        password = intent.getStringExtra("password");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.loginUrl + "phone=" + phone + "&password=" + password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
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
                                PrefManager.getInstance(PhoneAuthActivity.this).userLogin(user);
                                finish();
                                startActivity(new Intent(PhoneAuthActivity.this, MainActivity.class));

                                Toast.makeText(PhoneAuthActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } else
                                Toast.makeText(PhoneAuthActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhoneAuthActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}