package com.oadev.mining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oadev.mining.utility.Config;
import com.oadev.mining.utility.PrefManager;
import com.oadev.mining.R;
import com.oadev.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ReferDialog extends AppCompatActivity implements View.OnClickListener {

    public CardView submit;
    EditText refEdit;
    ImageButton close;
    String ReferedBy;
    User user;
    ProgressBar progressBar;

//    public ReferDialog(Context mContext, String referedBy) {
//        super(mContext);
//        // TODO Auto-generated constructor stub
//        this.context = mContext;
//        ReferedBy = referedBy;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.refer_dialog);
        submit = (CardView) findViewById(R.id.submit);
        //close = (ImageButton) findViewById(R.id.close_dialog);
        refEdit = findViewById(R.id.refEditText);
        submit.setOnClickListener(this);
        //close.setOnClickListener(this);
        user = PrefManager.getInstance(ReferDialog.this).getUser();
        progressBar = new ProgressBar(ReferDialog.this);

        ReferedBy = user.getReferedby();
        if (!ReferedBy.equals("")) {
            refEdit.setText(ReferedBy);
            refEdit.setEnabled(false);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (ReferedBy.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (!refEdit.getText().toString().trim().equals(user.getRefercode())){
                        submitRef(refEdit.getText().toString().trim());
                    }else Toast.makeText(ReferDialog.this,"You Can't Enter Your Own Refercode!",Toast.LENGTH_LONG);

                }
                break;
//            case R.id.close_dialog:
//                finish();
//                break;
            default:
                break;
        }
    }

    private void submitRef(String refercode) {
        RequestQueue requestQueue = Volley.newRequestQueue(ReferDialog.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.functionUrl + "method=" + "updaterefer" + "&userid=" + user.getId() + "&refercode=" + refercode ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                //JSONObject userobject = jsonObject.getJSONObject("data");

                                Toast.makeText(ReferDialog.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                ReferedBy = refEdit.getText().toString().trim();
                                Config.referedby = ReferedBy;

                                startActivity(new Intent(ReferDialog.this, MainActivity.class));
                                finish();

                            } else
                                Toast.makeText(ReferDialog.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ReferDialog.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }
}