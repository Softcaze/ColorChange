package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.R;
import com.softcaze.nicolas.colorchange.util.IabHelper;
import com.softcaze.nicolas.colorchange.util.IabResult;
import com.softcaze.nicolas.colorchange.util.Inventory;
import com.softcaze.nicolas.colorchange.util.Purchase;

import java.util.ArrayList;
import java.util.Calendar;

public class ShopActivity extends AppCompatActivity {
    protected TextView price5Life, price10Life, price25Life, price50Life;
    protected DAO dao;
    protected IInAppBillingService mService;
    protected ArrayList<String> listProducts = new ArrayList<String>();
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int REQUEST_CODE = 1001;
    private static final String TAG =
            "SHOP ACTIVITY";
    static final String ITEM_5_LIFE = "android.item.5.life";
    static final String ITEM_10_LIFE = "android.item.10.life";
    static final String ITEM_25_LIFE = "android.item.25.life";
    static final String ITEM_50_LIFE = "android.item.50.life";
    IabHelper mHelper;
    Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        myActivity = this;

        dao = new DAO(this);
        price5Life = (TextView) findViewById(R.id.price5Life);
        price10Life = (TextView) findViewById(R.id.price10Life);
        price25Life = (TextView) findViewById(R.id.price25Life);
        price50Life = (TextView) findViewById(R.id.price50Life);

        dao.open();

        if (dao.getNbrLife() < Constance.NBR_LIFE_MAX) {
            dao.saveTimeLastLife(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        } else {
            dao.saveTimeLastLife("");
        }

        dao.setNbrLife(dao.getNbrLife() + 1);

        dao.close();

        String base64EncodedPublicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm/aiy+l5cxM2bNJnY01NIlWn21Kx36UUu6exedJPBtItCTVU/vznRvP3sJ+be2c7Z3ois11GqiGYysVZJEso0IZW13Abdzbwxwma44S271zh9tVDm8poIBvs3BH4db4k9LkYg/tG1twS9LUjwHY0oSsTuUEn0KZ91CXUnX9yinW4yQrTCKf846VKZY5+yvmhXZ6rIZKeXzUo18JD00j3NBF2Pg00a2nzNkT1ctY9s5Yve/44fujNWTbbaXBUc68fMMSaul5/ve+UnPjuzcpSDgKEvVrwrX3bxeolDW9UUbSb9bDd1LFMVlevJeBzdDr1Ue12+Q700oGfdLa89CfQHQIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result)
            {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " +
                            result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });

        price5Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHelper.launchPurchaseFlow(myActivity, ITEM_5_LIFE, 10001,
                        mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        price10Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mHelper.launchPurchaseFlow(myActivity, ITEM_10_LIFE, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        price25Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mHelper.launchPurchaseFlow(myActivity, ITEM_25_LIFE, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
            }
        });

        price50Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mHelper.launchPurchaseFlow(myActivity, ITEM_50_LIFE, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        @Override
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            }
            else if (purchase.getSku().equals(ITEM_5_LIFE)) {
                Log.i(TAG, "Purchase ITEM");
                consumeItem();
            }
        }
    };

    public void consumeItem() {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_5_LIFE),
                        mConsumeFinishedListener);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        // OBJET ACHETE (DO SOMETHING)
                        Log.i(TAG, "CONSUME ITEM");
                    } else {
                        // handle error
                    }
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ShopActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
        }
        mHelper = null;
    }
}
