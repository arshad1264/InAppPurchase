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

        billingProcessor = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAymMSvUXl3f5LrFUexh7ew9D8mWD8wVRIpO4FoEp0nI257gOthPziJ2prRy3aygNvznew9ZX53SLC3Ih2C6jCMn1Dtp/Qkq6RTlOEkhhzWD58K1K1WT/GByPHKszf1fZXcT3jRM8qouIiGGUDb8kU2b5hrXIpDNeXxHSiJW+46LEnpg42u4K0SEKEhSp9Xwd0uY7DNNUiDh5LT84siA/+SxE/52DwzPasrPv/qZl6xOWVeSib2L6ETjhpt1Y61EXHNXwsfAp/TtlxwHojoqKHXOVAiUo/fsCRazsHnC70bG16YQViWjLyIFGpDaG/gn37CZ0xe1frvgwxs+WknvtF9QIDAQAB", this);
        billingProcessor.initialize();

        /*
        android.test.purchased
        android.test.canceled
        android.test.refunded
        android.test.item_unavailable
        */

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
        Log.e(getClass().getSimpleName(), "Error " + error.toString());
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

    @Override
    public void onDestroy() {
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
    }

}