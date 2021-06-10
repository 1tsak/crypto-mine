package com.oadev.mining;

import android.app.Dialog;
import android.content.Context;
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
        close = (ImageButton) findViewById(R.id.close_dialog);
        refEdit = findViewById(R.id.refEditText);
        submit.setOnClickListener(this);
        close.setOnClickListener(this);
        user = PrefManager.getInstance(ReferDialog.this).getUser();
        progressBar = new ProgressBar(ReferDialog.this);

        ReferedBy = getIntent().getStringExtra("referer");
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
                    submitRef(refEdit.getText().toString().trim());
                }
                break;
            case R.id.close_dialog:
                finish();
                break;
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
                                JSONObject userobject = jsonObject.getJSONObject("data");

                                Toast.makeText(ReferDialog.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                ReferedBy = refEdit.getText().toString().trim();
                                Config.referedby = ReferedBy;

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