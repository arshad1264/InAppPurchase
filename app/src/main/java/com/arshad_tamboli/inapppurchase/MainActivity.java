package com.arshad_tamboli.inapppurchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private BillingProcessor billingProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billingProcessor = new BillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        billingProcessor.initialize();

        Button buttonPayNow = findViewById(R.id.buttonPayNow);
        buttonPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingProcessor.purchase(MainActivity.this, "android.test.purchased");
            }
        });

    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Log.e(getClass().getSimpleName(), "onProductPurchased");
        Log.e(getClass().getSimpleName(), "Product Id " + productId);
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Log.e(getClass().getSimpleName(), "onPurchaseHistoryRestored");
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.e(getClass().getSimpleName(), "onBillingError");
    }

    @Override
    public void onBillingInitialized() {
        Log.e(getClass().getSimpleName(), "onBillingInitialized");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}