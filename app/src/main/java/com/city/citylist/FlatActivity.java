package com.city.citylist;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.city.citylist.model.Photo;
import com.city.citylist.service.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FlatActivity extends AppCompatActivity {
    TextView priceDay;
    TextView priceNight;
    TextView priceMonth;
    TextView title;
    TextView address;
    TextView description;
    String name, addres, desc;
    int day, night, month;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    int period=2500, delay = 2500;
    Timer swipeTimer;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_activity);
        priceDay = (TextView) findViewById(R.id.price_day);
        priceNight = (TextView) findViewById(R.id.price_night);
        priceMonth = (TextView) findViewById(R.id.price_month);
        title = (TextView) findViewById(R.id.title);
        address = (TextView) findViewById(R.id.address);
        description = (TextView) findViewById(R.id.description);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        ArrayList<Photo> filelist = (ArrayList<Photo>)getIntent().getSerializableExtra("photos");
        name = getIntent().getStringExtra("title");
        addres = getIntent().getStringExtra("address");
        desc = getIntent().getStringExtra("description");
        day = getIntent().getIntExtra("price_day", 0);
        night = getIntent().getIntExtra("price_night", 0);
        month = getIntent().getIntExtra("price_month", 0);


        viewPagerAdapter = new ViewPagerAdapter(this, filelist);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();

        title.setText(name);
        address.setText(addres);
        description.setText(desc);
        priceMonth.setText("месяц - " + month);
        priceDay.setText("сутки - " + day);
        priceNight.setText("ночь - " + night);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == filelist.size()) {
                        currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        },delay,period);
    }
}
