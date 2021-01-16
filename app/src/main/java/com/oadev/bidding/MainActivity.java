package com.oadev.bidding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    LinearLayout home, wallet, profile;
    int BidAmount = 10, BidCount = 1, TotalBidMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = findViewById(R.id.home);
        wallet = findViewById(R.id.wallet);
        profile = findViewById(R.id.profile);
        setFragment(new FragmentHome());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new FragmentHome());
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new FragmentWallet());
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new FragmentProfile());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showVioletBidConfirmDialog() {
        Button tenRsBid, hundredRsBid, thousandRsBid, Cancel, Confirm;
        TextView BidCounttxt, TotalContractMoney;
        ImageButton DecreaseCount, IncreaseCount;

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.confirm_bid_violet_layout, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        tenRsBid = dialogView.findViewById(R.id.contract_tenRs);
        hundredRsBid = dialogView.findViewById(R.id.contract_hundredRs);
        thousandRsBid = dialogView.findViewById(R.id.contract_ThousandRs);
        DecreaseCount = dialogView.findViewById(R.id.decrease_count);
        IncreaseCount = dialogView.findViewById(R.id.increase_count);
        Cancel = dialogView.findViewById(R.id.cancel_button);
        Confirm = dialogView.findViewById(R.id.confirm_button);
        BidCounttxt = dialogView.findViewById(R.id.contract_count_Txt);
        TotalContractMoney = dialogView.findViewById(R.id.TotalContractMoneyTxt);

        BidCounttxt.setText(String.valueOf(BidCount));

        tenRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        hundredRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 100;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        thousandRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 1000;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }
        });

        DecreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 0)
                    BidCount = BidCount - 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        IncreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 10)
                    BidCount = BidCount + 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                BidCount = 1;
                alertDialog.dismiss();
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Submit Bid
            }
        });


        alertDialog.show();

    }

    public void showRedBidConfirmDialog() {
        Button tenRsBid, hundredRsBid, thousandRsBid, Cancel, Confirm;
        TextView BidCounttxt, TotalContractMoney;
        ImageButton DecreaseCount, IncreaseCount;

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.confirm_bid_red_layout, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        tenRsBid = dialogView.findViewById(R.id.contract_tenRs);
        hundredRsBid = dialogView.findViewById(R.id.contract_hundredRs);
        thousandRsBid = dialogView.findViewById(R.id.contract_ThousandRs);
        DecreaseCount = dialogView.findViewById(R.id.decrease_count);
        IncreaseCount = dialogView.findViewById(R.id.increase_count);
        Cancel = dialogView.findViewById(R.id.cancel_button);
        Confirm = dialogView.findViewById(R.id.confirm_button);
        BidCounttxt = dialogView.findViewById(R.id.contract_count_Txt);
        TotalContractMoney = dialogView.findViewById(R.id.TotalContractMoneyTxt);

        BidCounttxt.setText(String.valueOf(BidCount));

        tenRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        hundredRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 100;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        thousandRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 1000;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }
        });

        DecreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 0)
                    BidCount = BidCount - 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        IncreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 10)
                    BidCount = BidCount + 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                BidCount = 1;
                alertDialog.dismiss();
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Submit Bid
            }
        });


        alertDialog.show();

    }

    public void showGreenBidConfirmDialog() {
        Button tenRsBid, hundredRsBid, thousandRsBid, Cancel, Confirm;
        TextView BidCounttxt, TotalContractMoney;
        ImageButton DecreaseCount, IncreaseCount;

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.confirm_bid_green_layout, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        tenRsBid = dialogView.findViewById(R.id.contract_tenRs);
        hundredRsBid = dialogView.findViewById(R.id.contract_hundredRs);
        thousandRsBid = dialogView.findViewById(R.id.contract_ThousandRs);
        DecreaseCount = dialogView.findViewById(R.id.decrease_count);
        IncreaseCount = dialogView.findViewById(R.id.increase_count);
        Cancel = dialogView.findViewById(R.id.cancel_button);
        Confirm = dialogView.findViewById(R.id.confirm_button);
        BidCounttxt = dialogView.findViewById(R.id.contract_count_Txt);
        TotalContractMoney = dialogView.findViewById(R.id.TotalContractMoneyTxt);

        BidCounttxt.setText(String.valueOf(BidCount));

        tenRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        hundredRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 100;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        });

        thousandRsBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 1000;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
                tenRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                hundredRsBid.setBackgroundColor(getResources().getColor(R.color.bg_color));
                thousandRsBid.setBackgroundColor(getResources().getColor(R.color.primary_color));
            }
        });

        DecreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 0)
                    BidCount = BidCount - 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        IncreaseCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BidCount != 10)
                    BidCount = BidCount + 1;
                TotalBidMoney = BidCount * BidAmount;
                BidCounttxt.setText(String.valueOf(BidCount));
                String totalMoney = String.valueOf(TotalBidMoney);
                TotalContractMoney.setText("TOTAL CONTRACT MONEY = " + totalMoney);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BidAmount = 10;
                BidCount = 1;
                alertDialog.dismiss();
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Submit Bid
            }
        });


        alertDialog.show();

    }
}