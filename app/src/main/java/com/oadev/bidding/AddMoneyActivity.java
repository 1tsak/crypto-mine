package com.oadev.bidding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyActivity extends AppCompatActivity {
    CardView startPaytmPayment;
    String amountToAdd;
    EditText amountfield;
    ProgressBar progressBar;
    String orderIdString;
    private Integer ActivityRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        startPaytmPayment = findViewById(R.id.startPaymentPaytm);
        amountfield = findViewById(R.id.amounttoadd);
        progressBar = findViewById(R.id.progressbar);
        startPaytmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountToAdd = amountfield.getText().toString();
                orderIdString = generateOrderId();
                String errors = "";
                if (orderIdString.equalsIgnoreCase("")) {
                    errors = "Enter valid Order ID here\n";
                    Toast.makeText(AddMoneyActivity.this, errors, Toast.LENGTH_SHORT).show();
                } else if (amountToAdd.equalsIgnoreCase("")) {
                    errors = "Enter valid Amount here\n";
                    Toast.makeText(AddMoneyActivity.this, errors, Toast.LENGTH_SHORT).show();
                } else {
                    getToken();
                }
            }
        });
    }

    private void getToken() {
        Log.e("TAG", " get token start");
        progressBar.setVisibility(View.VISIBLE);
        PaytmServiceWrapper serviceWrapper = new PaytmServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", Config.paytmMID, orderIdString, amountToAdd);
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e("TAG", " respo " + response.isSuccessful());
                progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getBody().getTxnToken() != "") {
                            Log.e("TAG", " transaction token : " + response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken());
                        } else {
                            Log.e("TAG", " Token status false");
                        }
                    }
                } catch (Exception e) {
                    Log.e("TAG", " error in Token Res " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("TAG", " response error " + t.toString());
            }
        });
    }

    public void startPaytmPayment(String token) {

        String txnTokenString = token;
        // for test mode use it
        // String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + Config.paytmMID + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + amountToAdd;
        //Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID=" + orderIdString;
        Log.e("TAG", " callback URL " + callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, Config.paytmMID, txnTokenString, amountToAdd, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e("paytm", "Response (onTransactionResponse) : " + bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e("paytm", "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e("paytm", " onErrorProcess " + s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e("paytm", "Clientauth " + s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e("paytm", " UI error " + s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e("paytm", " error loading web " + s + "--" + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e("paytm", "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e("paytm", " transaction cancel " + s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, ActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("paytm", " result code " + resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e("paytm", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            Log.e("paytm", " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e("paytm", " data response - " + data.getStringExtra("response"));

            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        } else {
            Log.e("paytm", " payment failed");
        }
    }

    public String generateOrderId() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min = 1000, max = 9999;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return date + String.valueOf(randomNum);
    }
}