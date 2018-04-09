package com.softcaze.nicolas.colorchange.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;
import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.CSVFile;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.R;
import com.softcaze.nicolas.colorchange.util.IabHelper;
import com.softcaze.nicolas.colorchange.util.IabResult;
import com.softcaze.nicolas.colorchange.util.Inventory;
import com.softcaze.nicolas.colorchange.util.Purchase;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ShopActivity extends AppCompatActivity {
    protected TextView price5Life, price10Life, price25Life, price50Life;
    protected RelativeLayout rel_progress_bar, rel_price5Life, rel_price10Life, rel_price25Life, rel_price50Life;
    protected DAO dao;
    private static final String TAG =
            "SHOP ACTIVITY";
    static final String ITEM_5_LIFE = "android.item.5.life";
    static final String ITEM_10_LIFE = "android.item.10.life";
    static final String ITEM_25_LIFE = "android.item.25.life";
    static final String ITEM_50_LIFE = "android.item.50.life";

    public static String ITEM_CLICKED = "";
    IabHelper mHelper;
    Activity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        InputStream inputStream = getResources().openRawResource(R.raw.in_app_products_com_softcaze_nicolas_colorchange);
        CSVFile csvFile = new CSVFile(inputStream);
        List priceCountry = csvFile.read();

        Log.i(TAG, "Price Country : " + getPrice(ITEM_5_LIFE, priceCountry));

        myActivity = this;

        dao = new DAO(this);
        price5Life = (TextView) findViewById(R.id.price5Life);
        price10Life = (TextView) findViewById(R.id.price10Life);
        price25Life = (TextView) findViewById(R.id.price25Life);
        price50Life = (TextView) findViewById(R.id.price50Life);
        rel_progress_bar = (RelativeLayout) findViewById(R.id.rel_progress_bar);
        rel_price5Life = (RelativeLayout) findViewById(R.id.rel_price5Life);
        rel_price10Life = (RelativeLayout) findViewById(R.id.rel_price10Life);
        rel_price25Life = (RelativeLayout) findViewById(R.id.rel_price25Life);
        rel_price50Life = (RelativeLayout) findViewById(R.id.rel_price50Life);

        price5Life.setText(getPrice(ITEM_5_LIFE, priceCountry));
        price10Life.setText(getPrice(ITEM_10_LIFE, priceCountry));
        price25Life.setText(getPrice(ITEM_25_LIFE, priceCountry));
        price50Life.setText(getPrice(ITEM_50_LIFE, priceCountry));

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
                    rel_progress_bar.setVisibility(View.GONE);
                }
            }
        });

        rel_price5Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITEM_CLICKED = ITEM_5_LIFE;
                try{
                    mHelper.launchPurchaseFlow(myActivity, ITEM_5_LIFE, 10001,
                            mPurchaseFinishedListener, getPayLoad());
                }
                catch (Exception e){
                    Log.i(TAG, "Impossible d'effectuer un achat");
                }
            }
        });

        rel_price10Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITEM_CLICKED = ITEM_10_LIFE;

                Log.i(TAG, "REL PRICE 10 LIFE");

                try{
                    mHelper.launchPurchaseFlow(myActivity, ITEM_10_LIFE, 10001,
                            mPurchaseFinishedListener, getPayLoad());
                }
                catch (Exception e){
                    Log.i(TAG, "Impossible d'effectuer un achat");
                }
            }
        });

        rel_price25Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITEM_CLICKED = ITEM_25_LIFE;

                try{
                    mHelper.launchPurchaseFlow(myActivity, ITEM_25_LIFE, 10001,
                            mPurchaseFinishedListener, getPayLoad());
                }
                catch (Exception e){
                    Log.i(TAG, "Impossible d'effectuer un achat");
                }
            }
        });

        rel_price50Life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ITEM_CLICKED = ITEM_50_LIFE;

                try{
                    mHelper.launchPurchaseFlow(myActivity, ITEM_50_LIFE, 10001,
                            mPurchaseFinishedListener, getPayLoad());
                }
                catch (Exception e){
                    Log.i(TAG, "Impossible d'effectuer un achat");
                }
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
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }

            if (purchase.getSku().equals(ITEM_5_LIFE)) {
                Log.i(TAG, "Purchase ITEM : " + ITEM_5_LIFE);
                consumeItem();
            }
            else if(purchase.getSku().equals(ITEM_10_LIFE)){
                Log.i(TAG, "Purchase ITEM : " + ITEM_10_LIFE);
                consumeItem();
            }
            else if(purchase.getSku().equals(ITEM_25_LIFE)){
                Log.i(TAG, "Purchase ITEM : " + ITEM_25_LIFE);
                consumeItem();
            }
            else if(purchase.getSku().equals(ITEM_50_LIFE)){
                Log.i(TAG, "Purchase ITEM : " + ITEM_50_LIFE);
                consumeItem();
            }
        }
    };

    public void consumeItem() {
        try{
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        }
        catch (Exception e){
            Log.i(TAG, "Impossible de consomer l'item step 1");
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                try{
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_CLICKED),
                            mConsumeFinishedListener);
                }
                catch (Exception e){
                    Log.i(TAG, "Impossible de consomer l'item step 2");
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {
                        switch (ITEM_CLICKED){
                            case ITEM_5_LIFE:
                                usePackLife(5);
                                break;
                            case ITEM_10_LIFE:
                                usePackLife(10);
                                break;
                            case ITEM_25_LIFE:
                                usePackLife(25);
                                break;
                            case ITEM_50_LIFE:
                                usePackLife(50);
                                break;
                        }

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
            try{
                mHelper.dispose();
            }
            catch (Exception e){
                Log.i(TAG, "mHelper.dispose() : " + e);
            }
        }
        mHelper = null;
    }

    public void usePackLife(int nbr){
        dao.open();

        dao.setNbrLife(dao.getNbrLife() + nbr);

        dao.close();
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        boolean result = false;

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        /*dao.open();

        if(payload.equals(dao.getPayLoad()))
        {
            result = true;
        }

        dao.close();

        return result;*/
        return  true;
    }

    public String getPayLoad(){
        String payLoad = "";

        dao.open();

        payLoad = dao.getPayLoad();

        dao.close();

        return payLoad;
    }

    public String getPrice(String item, List list){
        Float price = -1f;

        switch (item){
            case ITEM_5_LIFE:
                if(!GererListCountry(list, 1).equals("")) {
                    price = Float.valueOf(GererListCountry(list, 1));
                }
                break;
            case ITEM_10_LIFE:
                if(!GererListCountry(list, 2).equals("")) {
                    price = Float.valueOf(GererListCountry(list, 2));
                }
                break;
            case ITEM_25_LIFE:
                if(!GererListCountry(list, 3).equals("")) {
                    price = Float.valueOf(GererListCountry(list, 3));
                }
                break;
            case ITEM_50_LIFE:
                if(!GererListCountry(list, 4).equals("")) {
                    price = Float.valueOf(GererListCountry(list, 4));
                }
                break;
        }

        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);

        return String.valueOf(price/1000000);
    }

    public String GererListCountry(List list, int index){
        String result = "";
        boolean hasFindFirst = false;
        String[] str = (String[]) list.get(index);

        String listCountryStr = str[6];

        String[] listCountryTab = listCountryStr.split(";");

        for(int i = 0; i < listCountryTab.length; i++){
            if(listCountryTab[i].equals(" " + Constance.getCountry())){
                if(i + 1 < listCountryTab.length){
                    hasFindFirst = true;
                    result = listCountryTab[i + 1];
                }
            }

            if(hasFindFirst){
                break;
            }
        }

        return result;
    }
}
