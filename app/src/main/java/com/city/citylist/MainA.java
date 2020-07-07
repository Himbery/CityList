package com.city.citylist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.city.citylist.model.AppartmentModel;
import com.city.citylist.model.CityModel;
import com.city.citylist.model.Coordinates;
import com.city.citylist.model.CountryModel;
import com.city.citylist.mvp.Contract;
import com.city.citylist.mvp.Presenter;
import com.city.citylist.service.CityAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainA extends AppCompatActivity implements Contract.View {
    RecyclerView cityList;
    CityAdapter cityAdapter;
    List<AppartmentModel> cities = new ArrayList<>();
    List<CountryModel> countries = new ArrayList<>();
    Contract.Presenter presenter;
    Contract.Model model;
    private LocationManager locationManager;
    Location currentLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 777;
    int countryId, idCity;
    String city;
    int width, height;
    TextView nameCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        width = 200;
        height = 150;
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

    }

    @Override
    public void showCityList(List list) {
        cityList = (RecyclerView) findViewById(R.id.city_list);
        cityList.setLayoutManager(new LinearLayoutManager(this));
        cityAdapter = new CityAdapter(MainA.this, list);
        cityList.setAdapter(cityAdapter);
        nameCity.setText(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountry(List<CountryModel> list) {
        Float minDistance = null;
        CityModel closestCity = null;
        countryId = getCountryZipCode();
        countries = list;

        for (int index = 0; index < countries.size(); index++) {
            if (countries.get(index).id == countryId) {

                List<CityModel> cities = countries.get(index).cities;

                for (int i = 0; i < cities.size(); i++) {
                    CityModel city = cities.get(i);
                    Coordinates coordinates = city.coordinates;
                    Location cityLocation = new Location("locationA");
                    cityLocation.setLatitude(city.coordinates.lat);
                    cityLocation.setLongitude(city.coordinates.lon);

                    float distance = currentLocation.distanceTo(cityLocation);

                    if (minDistance == null || distance < minDistance) {
                        minDistance = distance;
                        closestCity = city;

                    }
                }
            }
        }
        idCity = closestCity.id;
        city = closestCity.name;

        presenter.getAppList(idCity, width, height, countryId);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter = new Presenter(this);
//                    presenter.selectCity(countries, currentLocation);
//                    presenter.onSuccess(cities);

                } else {

                }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 100, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            currentLocation = location;

            presenter = new Presenter(MainA.this);
            presenter.getCityList();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {

            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {

            }
        }
    };

    public int getCountryZipCode() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String CountryID = manager.getSimCountryIso().toUpperCase();

        switch (CountryID) {
            case "RU":
                return 643;
            case "BY":
                return 112;
            case "KZ":
                return 398;
            case "UA":
                return 804;
            case "AB":
                return 805;
            case "MD":
                return 806;
            case "KG":
                return 807;
            case "CZ":
                return 808;
            case "AZ":
                return 809;
            case "AM":
                return 810;
            case "HU":
                return 811;
            case "EE":
                return 812;
            case "LV":
                return 813;
            case "LT":
                return 814;
            case "GE":
                return 815;
            case "FI":
                return 816;
            case "ME":
                return 817;
            case "BG":
                return 818;
            case "UZ":
                return 824;
            case "TR":
                return 822;
            case "GR":
                return 819;
            case "RS":
                return 821;
            case "CY":
                return 881;
            case "DE":
                return 901;
            case "IL":
                return 926;
            case "IT":
                return 927;
            case "PL":
                return 987;
            case "ES":
                return 1018;

        }
        return 0;
    }

}
