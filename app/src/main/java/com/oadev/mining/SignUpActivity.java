package com.oadev.mining;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText nameEditText, phoneNumberEditText, emailEditText, passwordEditText, rptpasswordEditText;
    CardView Signup, googleLogin, fbLogin;
    TextView logintxt;
    String name, phoneNumber, email, password, rptpassword;
    ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1109;

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

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
        googleLogin = findViewById(R.id.googleLogin);
        fbLogin = findViewById(R.id.fbLogin);
        mAuth = FirebaseAuth.getInstance();
        createGoogleLoginRequest();

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEditText.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                rptpassword = rptpasswordEditText.getText().toString();
                if (name.matches("") && phoneNumber.matches("") && email.matches("") && password.matches("") && rptpassword.matches("")) {

                    Toast.makeText(SignUpActivity.this, "Please Fill All Required Fields!", Toast.LENGTH_LONG).show();

                } else {
                    if (rptpassword.equals(password)) {
                        if (isEmailValid(email)) {
                            progressBar.setVisibility(View.VISIBLE);
                            doSignup(name, phoneNumber, email, password);

//                            Intent intent = new Intent(SignUpActivity.this, PhoneAuthActivity.class);
//                            intent.putExtra("action", "signup");
//                            intent.putExtra("name", name);
//                            intent.putExtra("phoneNumber", name);
//                            intent.putExtra("email", name);
//                            intent.putExtra("password", name);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Invalid Email!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords do not Match!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });


    }

    private void doSignup(String name, String phone, String email, String password) {
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
    private void createGoogleLoginRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name, email;
                            name = user.getDisplayName();
                            email = user.getEmail();
                            doLogin("phone", "pass", "googleauth", name, email);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
    private void doLogin(String phone, String password, String method, String name, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.loginUrl + "method=" + method + "&phone=" + phone + "&password=" + password + "&name=" + name + "&email=" + email,
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
                                PrefManager.getInstance(SignUpActivity.this).userLogin(user);
                                progressBar.setVisibility(View.GONE);
                                finish();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                                Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            } else
                                Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
    private void googleLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}