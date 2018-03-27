package com.softcaze.nicolas.colorchange.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.softcaze.nicolas.colorchange.Database.DAO;
import com.softcaze.nicolas.colorchange.Model.Constance;
import com.softcaze.nicolas.colorchange.R;

import java.util.Calendar;

public class ShopActivity extends AppCompatActivity {
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        dao = new DAO(this);

        dao.open();

        if(dao.getNbrLife() < Constance.NBR_LIFE_MAX){
            dao.saveTimeLastLife(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        }
        else{
            dao.saveTimeLastLife("");
        }

        dao.setNbrLife(dao.getNbrLife() + 1);

        dao.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ShopActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
